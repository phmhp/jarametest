package SiliconDream.JaraMe.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
/*커밋 전 취소

import org.springframework.security.core.Authentication;
 */
import org.springframework.web.bind.annotation.*;
import SiliconDream.JaraMe.domain.User;

import SiliconDream.JaraMe.dto.GetMissionPostDTO;
import SiliconDream.JaraMe.dto.MissionPostDTO;
import SiliconDream.JaraMe.service.MissionPostService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/missionPost")
public class MissionPostController {
    private final MissionPostService missionPostService;

    @Autowired
    public MissionPostController(MissionPostService missionPostService) {
        this.missionPostService = missionPostService;
    }

    //미션 인증글 등록 =>테스트 완료 / 예외처리 전
    //TODO: 오늘 해당 유저가 해당 자라어스에 대한 미션인증글을 이미 작성했다면
    @PostMapping("/post")
    public ResponseEntity<String> missionPost(@RequestBody MissionPostDTO missionPostDTO, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId;
        if (session == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("미션 인증글 등록에 실패했습니다.");

        }

            User user = (User) session.getAttribute("user");
            log.info("log:userId:{}", user.getUserId());
            userId = user.getUserId();

            boolean result = missionPostService.missionPost(missionPostDTO, userId);
            if (result) {
                return ResponseEntity.status(HttpStatus.OK).body("미션 인증글이 등록되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("미션 인증글 등록에 실패했습니다.");
            }


    }
/*테스트 목적 컨트롤러
    @PutMapping("/update") //=> 테스트 완료 / 예외처리 전
    //오늘의 미션 상태 업데이트 및 오늘의 미션 완료 시 포인트 지급 =>TODO: missionPostService말고 다른 곳으로 옮기기.  / Controller는 따로 없어도?
    public String dailyMissionUpdate(@SessionAttribute(name="userId", required=true) Long userId Long userId, @RequestParam Long missionPostId, @RequestParam Long jaraUsId) {
        //        void  dailyMissionFinish(Long userId, Long jaraUsId, MissionPost savedMissionPost, LocalDateTime postedDateTime);

        String returnMassage = missionPostService.dailyMissionUpdate(userId, jaraUsId, missionPostId);

        return returnMassage;
    }
*/

    //미션 인증글 조회 =>테스트 완료 / 예외처리 전
    @GetMapping("/get")
    public ResponseEntity<?> getMissionPost(@RequestParam Long missionPostId, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
    log.info("session.getId:{}", session);

        Long userId;
        if (session == null) {
           // GetMissionPostDTO getMissionPostDTO2 = new GetMissionPostDTO(); //수정예정
           // return getMissionPostDTO2;
            //ResponseEntity.status(HttpStatus.BAD_REQUEST).body("미션 인증글 등록에 실패했습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        User user = (User) session.getAttribute("user");
        log.info("log:userId:{}", user.getUserId());
        userId = user.getUserId();

        boolean exist = missionPostService.existMissionPost(missionPostId);
        if (exist){
            GetMissionPostDTO getMissionPostDTO = missionPostService.getMissionPostDetails(missionPostId, userId);
            return ResponseEntity.ok(getMissionPostDTO);
        } else {
            return ResponseEntity.badRequest().body("해당하는 게시글이 존재하지 않습니다.");
        }

    }

    @GetMapping("/All-post")
    public ResponseEntity<List<MissionPostDTO>> getAllMissionPostsForJaraUs(@RequestParam(name = "jaraUsId") Long jaraUsId) {
        // Assuming you want to retrieve all mission posts without specifying a particular JaraUs
        List<MissionPostDTO> missionPosts = missionPostService.getAllMissionPosts(jaraUsId);

        // Check if the mission post is empty and process appropriately, e.g. returning 404
        if (missionPosts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(missionPosts);
    }

    @GetMapping("/my-post")
    public ResponseEntity<?> getMyMissionPostsForJaraUs(@RequestParam Long jaraUsId, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 검증 오류");
        }

        User user = (User) session.getAttribute("user");

        List<MissionPostDTO> myMissionPosts = missionPostService.getMyMissionPostsForJaraUs(jaraUsId, user.getUserId());


        // Check if the list is empty and handle it appropriately, e.g., return 404
        if (myMissionPosts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(myMissionPosts);
    }


    //미션 인증글 수정 => 테스트완료, 예외처리 전
    //TODO: 오늘=> 내용과 공개/익명 정보 모두 수정 가능
    //TODO: 오늘이 아닌 경우 => 공개/익명 정보만 수정 가능
    @PostMapping("/update")
    public ResponseEntity<String> updateMissionPost(@RequestParam Long missionPostId, @RequestBody MissionPostDTO missionPostDTO,HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId;
        if (session == null){//todo: 로직 추가하기
          }
        User user = (User) session.getAttribute("user");
        log.info("log:userId:{}", user.getUserId());
        userId = user.getUserId();


        String resultMessage = missionPostService.updateMissionPost(missionPostId, missionPostDTO, userId, LocalDate.now());

        //수행결과에 따라 (수정되거나 수정되지않은) 미션 인증글 조회 결과 반환
        if (resultMessage.equals("미션 인증글이 수정되었습니다.")) {
            return ResponseEntity.status(HttpStatus.OK).body(resultMessage);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMessage);
        }


    }
/*
    //미션 인증글 삭제
    // 미션 진행 중일 때만 삭제 가능, 미션 종료되고는 삭제 불가능
    @DeleteMapping("/delete")
    public String deleteMissionPost(@RequestParam Long missionPostId, @SessionAttribute(name = "userId" ){
        return missionPostService.deleteMissionPost(missionPostId, userId);

    }
*/



}
