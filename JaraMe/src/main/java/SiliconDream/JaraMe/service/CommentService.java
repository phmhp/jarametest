package SiliconDream.JaraMe.service;

import SiliconDream.JaraMe.dto.MissionCommentDTO;

public interface CommentService {


    boolean addComment(Long userId, MissionCommentDTO missionCommentDTO);


    String deleteComment(Long commentId, Long userId);

}
