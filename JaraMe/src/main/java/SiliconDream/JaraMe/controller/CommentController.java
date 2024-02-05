package SiliconDream.JaraMe.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import SiliconDream.JaraMe.domain.User;
import SiliconDream.JaraMe.dto.MissionCommentDTO;
import SiliconDream.JaraMe.service.CommentService;
@RestController

@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;

    }

    //미션 인증글 댓글 등록
    @PostMapping("/add")
    //예외처리 : 공백만 아니면 될 것같음.

    public ResponseEntity<String> addComment(@RequestBody MissionCommentDTO missionCommentDTO, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId;
        if (session == null){//todo: 로직 추가하기
        }
        User user = (User) session.getAttribute("user");
       // log.info("log:userId:{}", user.getUserId());
        userId = user.getUserId();
        boolean result = commentService.addComment(userId, missionCommentDTO);

        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body("댓글이 등록되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("댓글 등록이 실패했습니다.");
        }
    }

    //미션 인증글 댓글 삭제
    @DeleteMapping("/delete")
    //예외처리 : 해당 미션 인증글에서 실제로 댓글 삭제되었는지 확인
    public ResponseEntity<String> deleteComment(@RequestParam Long commentId,HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId;
        if (session == null){//todo: 로직 추가하기
        }
        User user = (User) session.getAttribute("user");
        // log.info("log:userId:{}", user.getUserId());
        userId = user.getUserId();
        String resultMessage = commentService.deleteComment(commentId, userId);
        if (resultMessage.equals("댓글이 삭제되었습니다.")) {
            return ResponseEntity.status(HttpStatus.OK).body(resultMessage);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMessage);
        }


    }
}

