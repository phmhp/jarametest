package SiliconDream.JaraMe.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import siliconDream.jaraMe.domain.*;
import siliconDream.jaraMe.dto.CommentDTO;

import siliconDream.jaraMe.dto.MissionPostDTO;
import siliconDream.jaraMe.dto.GetMissionPostDTO;
import siliconDream.jaraMe.dto.DailyMissionDTO;
import siliconDream.jaraMe.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MissionPostServiceImpl implements MissionPostService {
    private final PointHistoryRepository pointHistoryRepository;

    private final MissionPostRepository missionPostRepository;
    private final ScheduleRepository scheduleRepository;
    private final DailyMissionRepository dailyMissionRepository;
    private final PointRepository pointRepository;
    private final MissionHistoryRepository missionHistoryRepository;
    private final UserRepository userRepository;
    private final JaraUsRepository jaraUsRepository;
    private final CommentRepository commentRepository;
    private final ReactionRepository reactionRepository;


    @Autowired
    public MissionPostServiceImpl(MissionPostRepository missionPostRepository,
                                  ScheduleRepository scheduleRepository,
                                  DailyMissionRepository dailyMissionRepository,
                                  PointRepository pointRepository,
                                  MissionHistoryRepository missionHistoryRepository,
                                  UserRepository userRepository,
                                  JaraUsRepository jaraUsRepository,
                                  CommentRepository commentRepository,
                                  ReactionRepository reactionRepository,
                                  PointHistoryRepository pointHistoryRepository) {

        this.missionPostRepository = missionPostRepository;
        this.scheduleRepository = scheduleRepository;
        this.dailyMissionRepository = dailyMissionRepository;
        this.pointRepository = pointRepository;
        this.missionHistoryRepository = missionHistoryRepository;
        this.userRepository = userRepository;
        this.jaraUsRepository = jaraUsRepository;
        this.commentRepository = commentRepository;
        this.reactionRepository = reactionRepository;
        this.pointHistoryRepository = pointHistoryRepository;
    }

    //미션 인증글 작성
    //TODO: 만약 오늘 해당 미션에 대해 인증글을 하나 올렸다면 못올리도록 하기
    //TODO: 작성에 대한 규칙?
    public boolean missionPost(MissionPostDTO missionPostDTO, Long userId) {
        //제목이나 내용이 공백인지?
        if (missionPostDTO.getTextTitle().isBlank() || missionPostDTO.getTextContent().isBlank()) {
            return false;
        }
        MissionPost missionPost = new MissionPost();
        missionPost.setAnonymous(missionPostDTO.isAnonymous());
        missionPost.setDisplay(missionPostDTO.isDisplay());
        missionPost.setTextTitle(missionPostDTO.getTextTitle());
        missionPost.setTextContent(missionPostDTO.getTextContent());
        missionPost.setImageContent(missionPostDTO.getImageContent());
        missionPost.setPostDateTime(missionPostDTO.getPostDateTime());
        missionPost.setUser(userRepository.findByUserId(userId));

        Long jaraUsId = missionPostDTO.getJaraUsId();
        missionPost.setJaraUs(jaraUsRepository.findByJaraUsId(jaraUsId));


        MissionPost savedMissionPost = missionPostRepository.save(missionPost);
        Long savedMissionPostId = savedMissionPost.getMissionPostId();

        dailyMissionUpdate(userId, jaraUsId, savedMissionPostId);
        return true;
        /*if (savedMissionPost) {

            //TODO: dailyMission테이블에도 missionPostId 저장하기

        } else if (savedMissionPost.isEmpty()) {
            return false;
        }else {return false;}

*/      //예외처리 : 해당 유저가 없는데 저장하려는 경우 에러처리하기

    }

    public void dailyMissionUpdate(Long userId, Long jaraUsId, Long missionPostId) {
        missionPostRepository.findByMissionPostId(missionPostId);
        //저장한 missionPostId가 오늘의 미션 중 어느 미션을 인증하고자했던 것인지 파악
        // dailyMission 테이블에서 dailyMissionId로 레코드를 찾은 다음,
        Long dailyMissionId = dailyMissionRepository.findDailyMissionIdByUser_UserIdAndJaraUs_JaraUsId(userId, jaraUsId);
        MissionPost savedMissionPost = missionPostRepository.findByMissionPostId(missionPostId);
        dailyMissionRepository.updateDailyMissionStatus(true, dailyMissionId, savedMissionPost, savedMissionPost.getPostDateTime());

        dailyMissionFinish(userId);
    }

    //오늘의 미션 완료
//미션 인증글 등록 시 호출됨. => 미션 인증 여부를 업데이트한 후, '오늘의 미션' 전체를 인증했는지 여부를 확인해서 모두 True인 경우 포인트 부여.
    @Transactional
    public void dailyMissionFinish(Long userId) {

        // dailyMissionResult를 true로 설정하고,
        // savedMissionPostId,
        // postedDateTime 값으로 업데이트한다.


        // TODO: 오늘의 미션 전부 완료했는지 알아보는 부분

        List<DailyMission> dailyMissionList = dailyMissionRepository.findByUser_UserId(userId);


        // dailyMission 테이블에서 userId로 필터링했을 때 dailyMissionResult 컬럼이 모두 T인 경우,
        //       레코드가 몇개인지 개수 세기
        //       그리고 레코드의 개수*3만큼의 포인트 지급
        //전달받은 dailyMissionList의 dailyMissionStatus 컬럼이 모두 true
        boolean allTrue = dailyMissionList.stream()
                .allMatch(dailyMission -> dailyMission.isDailyMissionResult());

        //모든 레코드의 컬럼 값이 true라면 포인트 지급
        if (allTrue) {
            int taskNumber = dailyMissionList.size();
            int earnedPoint = taskNumber * 3;
            pointRepository.updateDailyMission(userId, earnedPoint);
            /**추가**/
            PointHistory pointHistory = new PointHistory();
            pointHistory.setPoint(earnedPoint + userRepository.findByUserId(userId).getPoint());
            pointHistory.setChangeAmount(earnedPoint);
            pointHistory.setPlusOrMinus(true);
            pointHistory.setTransactionTime(LocalDateTime.now());
            pointHistory.setNotice(true);
            pointHistory.setTask(String.format("dailyMission (%s)", taskNumber));
            pointHistory.setUser(userRepository.findByUserId(userId));
            pointHistoryRepository.save(pointHistory);
        }
        //포인트 지급에 대한 return string 해야할 것같음! 수정 예정

    }

    public boolean existMissionPost(Long missionPostId) {
        Optional<MissionPost> missionPostOptional = missionPostRepository.findMissionPostByMissionPostId(missionPostId); //댓글,리액션까지 전달.
        if (missionPostOptional.isEmpty()) {
            return false;
        }
        return true;
    }


    //미션 인증글 조회
    //TODO: 해당 missionPostId가 없다면?
    //TODO: 비공개된 게시글이거나 삭제된 게시글이라면 ?
    public GetMissionPostDTO getMissionPostDetails(Long missionPostId, Long userId) {


        //레코드 찾기
        //missionPostRepository.findByMissionPostId(missionPostId); //인증글 레코드만 전달받을 수 있음.
        Optional<MissionPost> missionPostOptional = missionPostRepository.findMissionPostByMissionPostId(missionPostId); //댓글,리액션까지 전달.
        //TODO: 조회하는 사람이 해당 인증글에 단 리액션 정보 전달?
        Optional<String> reactionTypeOptional = reactionRepository.findReactionTypeByMissionPost_MissionPostIdAndUser_UserId(missionPostId, userId);

        Optional<List<CommentDTO>> commentOptional = commentRepository.findCommentByMissionPost_MissionPostId(missionPostId);

        return makeGetMissionPostDTO(missionPostOptional, reactionTypeOptional, commentOptional);

    }


    //미션 인증글 조회를 위한 DTO 형성
    public GetMissionPostDTO makeGetMissionPostDTO(Optional<MissionPost> missionPostOptional, Optional<String> reactionTypeOptional, Optional<List<CommentDTO>> commentOptional) {
        GetMissionPostDTO getMissionPostDTO = new GetMissionPostDTO();

        missionPostOptional.ifPresent(missionPost -> {
            getMissionPostDTO.setMissionPostId(missionPost.getMissionPostId());
            getMissionPostDTO.setTextTitle(missionPost.getTextTitle());
            getMissionPostDTO.setTextContent(missionPost.getTextContent());
            getMissionPostDTO.setImageContent(missionPost.getImageContent());
            getMissionPostDTO.setPostDateTime(missionPost.getPostDateTime());
            getMissionPostDTO.setNickname(missionPost.getUser().getNickname());
            getMissionPostDTO.setProfileImage(missionPost.getUser().getProfileImage());
            getMissionPostDTO.setDisplay(missionPost.isDisplay());
            getMissionPostDTO.setAnonymous(missionPost.isAnonymous());
        });

        reactionTypeOptional.ifPresent(getMissionPostDTO::setReactionType);

        commentOptional.ifPresent(getMissionPostDTO::setCommentDTO);

        return getMissionPostDTO;
    }


    //미션에 참여한 유저들의 참여율 알아내기 => 스케줄링 구현 후에 할 수 있을 듯.
    public int missionParticipationRate(Long userId, Long jaraUsId) {

        //인증해야하는 날짜 전체 알아내기
        Set<LocalDate> totalDates = scheduleRepository.findScheduleDateByJaraUsId(jaraUsId);
        log.info("totalDates:{}", totalDates);
        int totalNum = totalDates.size();//총 인증해야하는 횟수
        int postNum = 0; //실제로 인증한 횟수

        if (totalDates.size() < 3) {
            return 0;
        }

        //해당 유저가 인증한 날짜들 알아내기 //=> 수정해야함
        Set<LocalDate> postedDates = missionHistoryRepository.findMissionDateByUser_UserIdAndJaraUs_JaraUsIdAndMissionResult(userId, jaraUsId, true);
        log.info("userId:{} / postedDates:{}", userId, postedDates);

        int result = 0;

        for (LocalDate oneOfTotal : totalDates) {
            if (postedDates.contains(oneOfTotal)) {
                postNum++;
                log.info("postNum:{}", postNum);
            }
        }

        float oneThird = totalNum * ((float) 1 / (float) 3);
        float twoThird = totalNum * ((float) 2 / (float) 3);
        float total = (float) totalNum;

        if (postNum == totalNum) {
            result = 50;
            log.info("first");
        } else if (postNum < total && postNum >= twoThird) {
            result = 20;
            log.info("second");
        } else if (postNum < twoThird && postNum >= oneThird) {
            result = 10;
            log.info("third");
        } else if (postNum < oneThird) {
            result = 0;
            log.info("fourth");

        } else {
            result = -1; //에러에 해당 }


        }
        log.info("result = {}", result);

        return result;
    }

    public boolean compareMissionPost(MissionPostDTO missionPostDTO, Long missionPostId) {
        boolean result;
        Optional<MissionPost> missionPost = missionPostRepository.findMissionPostByMissionPostId(missionPostId);
        boolean anonymous = missionPost.get().isAnonymous() == missionPostDTO.isAnonymous();
        boolean display = missionPost.get().isDisplay() == missionPostDTO.isDisplay();

        boolean textTitle = missionPost.get().getTextTitle().equals(missionPostDTO.getTextTitle());
        boolean textContent = missionPost.get().getTextContent().equals(missionPostDTO.getTextContent());
        boolean imageContent = missionPost.get().getImageContent().equals(missionPostDTO.getImageContent());
        log.info("anonymous:{}", anonymous);
        log.info("display:{}", display);
        log.info("textTitle:{}", textTitle);
        log.info("textTitle-기존{}", missionPost.get().getTextTitle());
        log.info("textTitle-입력{}", missionPostDTO.getTextTitle());
        log.info("textContent:{}", textContent);
        log.info("imageContent:{}", imageContent);

        //수정된 부분이 없으면 false 반환
        if (anonymous && display && textTitle && textContent && imageContent) {
            log.info("all result");
            return false;
        } else {
            log.info("??");
            return true;
        }
    }


    //미션인증글 수정
    public String updateMissionPost(Long missionPostId, MissionPostDTO missionPostDTO, Long userId, LocalDate todayDate) {

        MissionPost missionPost = missionPostRepository.findByMissionPostId(missionPostId);
        if ((missionPost.getPostDateTime().toLocalDate()).equals(todayDate)) {

            boolean compare = compareMissionPost(missionPostDTO, missionPostId);
            log.info("compare");
            if (!compare) {
                log.info("compare==false?:{}", compare);
                return "수정된 부분이 없습니다.";
            } else if (compare) {

                //dto에서 값을 꺼내서 전달 (수정할 값)
                boolean display = missionPostDTO.isDisplay();
                boolean anonymous = missionPostDTO.isAnonymous();

                //인증 게시글 본문 (제목,내용,첨부이미지)
                String textTitle = missionPostDTO.getTextTitle();
                String textContent = missionPostDTO.getTextContent();
                String imageContent = missionPostDTO.getImageContent();

                missionPostRepository.updateMissionPostByMissionPostId(missionPostId, display, anonymous, textTitle, textContent, imageContent);
                return "미션 인증글이 수정되었습니다.";
            }
        } else if (!(missionPost.getPostDateTime().toLocalDate()).equals(todayDate)) { //해당 유저가 작성한 글은 맞지만 오늘 미션이 아니라면
            log.info("todayDate:{}", todayDate);
            log.info("(missionPost.getPostDateTime().toLocalDate()):{}", missionPost.getPostDateTime().toLocalDate());

            return "오늘 등록한 미션 인증글만 수정 가능합니다.";
        }
        return "수정에 실패했습니다.";
    }


    @Override
    public List<MissionPostDTO> getAllMissionPosts(Long jaraUsId) {
        List<MissionPost> missionPosts = missionPostRepository.findByJaraUs_JaraUsId(jaraUsId);
        return convertToMissionPostDTOList(missionPosts);
    }

    private List<MissionPostDTO> convertToMissionPostDTOList(List<MissionPost> missionPosts) {
        return missionPosts.stream()
                .map(this::convertToMissionPostDTO)
                .collect(Collectors.toList());
    }

    public List<MissionPostDTO> getMyMissionPostsForJaraUs(Long jaraUsId, Long userId) {
        List<MissionPost> myMissionPosts = missionPostRepository.findAllByJaraUs_JaraUsIdAndUser_UserId(jaraUsId, userId);
        return convertToMissionPostDTOList(myMissionPosts);
    }


    private MissionPostDTO convertToMissionPostDTO(MissionPost missionPost) {
        MissionPostDTO missionPostDTO = new MissionPostDTO();
        missionPostDTO.setMissionPostId(missionPost.getMissionPostId());
        missionPostDTO.setTextTitle(missionPost.getTextTitle());
        missionPostDTO.setTextContent(missionPost.getTextContent());
        missionPostDTO.setImageContent(missionPost.getImageContent());
        missionPostDTO.setPostDateTime(missionPost.getPostDateTime());
        missionPostDTO.setNickname(missionPost.getUser().getNickname());
        missionPostDTO.setProfileImage(missionPost.getUser().getProfileImage());

        return missionPostDTO;
    }
/*
    //미션인증글 삭제 => 보류
    //TODO : 예외처리 : 미션인증글이 삭제되면서 미션 기록, 오늘의 미션, 해당 미션인증글에 달린 댓글,리액션 모두 삭제돼야함.
    @Transactional
    public String deleteMissionPost(Long missionPostId, Long userId) {
        MissionPost missionPost = missionPostRepository.findByMissionPostId(missionPostId);

        JaraUs jaraUs = jaraUsRepository.findByJaraUsId(missionPost.getJaraUs().getJaraUsId());
        LocalDate endDate = jaraUs.getEndDate();
        log.info("test/ start ");
        if (missionPost.getUser().getUserId().equals(userId)) {
            if (endDate.isAfter(LocalDate.now())) {
                log.info("test/ start 2");

                if (missionPost.getPostDateTime().toLocalDate().equals(LocalDate.now())){
                    DailyMission dailyMission =dailyMissionRepository.findDailyMissionByMissionPost_MissionPostId(missionPostId);
                    log.info("test/ start 3");
                    dailyMission.setMissionPost(null);
                    dailyMissionRepository.save(dailyMission);
                }
                else if (!missionPost.getPostDateTime().toLocalDate().equals(LocalDate.now())){
                    log.info("test/ start 4");
                    MissionHistory missionHistory = missionHistoryRepository.findMissionHistoryIdByMissionPost_MissionPostId(missionPostId);
                    log.info("missionPost:{}",missionPost);
                    missionHistory.setMissionPost(null);
                    log.info("missionPost:{}",missionPost);
                    missionHistoryRepository.save(missionHistory);
                }
                missionPostRepository.delete(missionPost);
                return "삭제가 완료되었습니다.";
            } else if (endDate.isBefore(LocalDate.now())) {
                return "미션이 종료되어 삭제가 불가능합니다.";
            }
        }else if (!missionPost.getUser().getUserId().equals(userId)){
        return "작성자가 일치하지않습니다.";} return "삭제를 실패했습니다.";
    }*/
}

