package SiliconDream.JaraMe.service;

import siliconDream.jaraMe.dto.MissionCommentDTO;

public interface CommentService {


    boolean addComment(Long userId, MissionCommentDTO missionCommentDTO);


    String deleteComment(Long commentId, Long userId);

}
