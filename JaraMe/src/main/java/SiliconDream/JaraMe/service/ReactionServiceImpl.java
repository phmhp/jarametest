package SiliconDream.JaraMe.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import siliconDream.jaraMe.domain.MissionPost;
import siliconDream.jaraMe.domain.Reaction;
import siliconDream.jaraMe.domain.User;
import siliconDream.jaraMe.dto.MissionReactionDTO;
import siliconDream.jaraMe.repository.MissionPostRepository;
import siliconDream.jaraMe.repository.ReactionRepository;
import siliconDream.jaraMe.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ReactionServiceImpl implements ReactionService {

    private final UserRepository userRepository;
    private final MissionPostRepository missionPostRepository;
    private final ReactionRepository reactionRepository;
    private final NotificationService notificationService;

    public ReactionServiceImpl(UserRepository userRepository,
                               MissionPostRepository missionPostRepository,
                               ReactionRepository reactionRepository,
                               NotificationService notificationService) {

        this.userRepository = userRepository;
        this.missionPostRepository = missionPostRepository;
        this.reactionRepository = reactionRepository;
        this.notificationService = notificationService;
    }

    public ResponseEntity<String> addReaction(MissionReactionDTO missionReactionDTO, Long userId) {
        //가능한 reactionType
        List<String> reactionTypes = new ArrayList<>();
        reactionTypes.add("like");
        reactionTypes.add("good");
        reactionTypes.add("smile");


        Optional<Reaction> reactionOptional = reactionRepository.findReactionByMissionPost_MissionPostIdAndUser_UserId(missionReactionDTO.getMissionPostId(), userId);
        if (reactionOptional.isEmpty()) {
            //예외처리 : 리액션 타입 잘못 전달받았을 때 (ex- likeeee, gosssod, smiile )
            if (!reactionTypes.contains(missionReactionDTO.getReactionType())) {

                log.info("log test : test success");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리액션 타입이 잘못되었습니다.");

            }

            Reaction reaction = new Reaction();
            reaction.setUser(userRepository.findByUserId(userId));
            reaction.setMissionPost(missionPostRepository.findByMissionPostId(missionReactionDTO.getMissionPostId()));
            reaction.setReactionType(missionReactionDTO.getReactionType());

            reactionRepository.save(reaction);
            // 알림 메시지 생성 및 발송
            User reactingUser = userRepository.findByUserId(userId);
            MissionPost post = missionPostRepository.findByMissionPostId(missionReactionDTO.getMissionPostId());
            String notificationMessage = String.format("%s님이 당신의 게시물에 %s를 보냈습니다.",
                    reactingUser.getNickname(),
                    missionReactionDTO.getReactionType());
            /* 서버 배포 테스트 중 에러나서 주석처리
            notificationService.createNotification(post.getUser().getUserId(), notificationMessage);
*/
            return ResponseEntity.status(HttpStatus.OK).body(String.format("%s를 누르셨습니다!", missionReactionDTO.getReactionType()));
        } else if (reactionOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("이미 %s를 누르셨습니다.", reactionOptional.get().getReactionType()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리액션을 누를 수 없습니다.");

    }

    public String deleteReaction(MissionReactionDTO missionReactionDTO, Long userId) {
//가능한 reactionType
        List<String> reactionTypes = new ArrayList<>();
        reactionTypes.add("like");
        reactionTypes.add("good");
        reactionTypes.add("smile");

        Optional<Reaction> reactionOptional = reactionRepository.findReactionByMissionPost_MissionPostIdAndUser_UserId(missionReactionDTO.getMissionPostId(), userId);
        if (reactionOptional.isPresent()) {

            //예외처리 : 리액션 타입 잘못 전달받았을 때 (ex- likeeee, gosssod, smiile )
            if (!reactionTypes.contains(missionReactionDTO.getReactionType())) {

                log.info("log test : test success");
                return "리액션 타입이 잘못되었습니다.";

            } else if (!reactionOptional.get().getReactionType().equals(missionReactionDTO.getReactionType())) {
                return "리액션 타입이 잘못되었습니다.";
            } else {
                reactionRepository.delete(reactionOptional.get());
                return "리액션이 취소되었습니다.";

            }
        } else {
            return "리액션 취소에 실패했습니다.";
        }
    }
}
