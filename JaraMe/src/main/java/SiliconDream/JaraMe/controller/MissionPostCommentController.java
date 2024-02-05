package SiliconDream.JaraMe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import SiliconDream.JaraMe.dto.MissionCommentDTO;

@RestController
public class MissionPostCommentController {

    //미션 인증글 댓글 등록
    @PostMapping("/comment")
    public void missionComment(@RequestBody MissionCommentDTO missionCommentDTO) {
        //missionPostCommentService.missionComment();
    }}