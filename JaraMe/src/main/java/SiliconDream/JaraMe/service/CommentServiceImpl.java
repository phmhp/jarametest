package SiliconDream.JaraMe.service;

import org.springframework.stereotype.Service;
import siliconDream.jaraMe.domain.Comment;
import siliconDream.jaraMe.domain.MissionPost;
import siliconDream.jaraMe.dto.MissionCommentDTO;
import siliconDream.jaraMe.repository.CommentRepository;
import siliconDream.jaraMe.repository.MissionPostRepository;
import siliconDream.jaraMe.repository.UserRepository;

import java.time.LocalDate;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final MissionPostRepository missionPostRepository;

    public CommentServiceImpl(CommentRepository commentRepository,
                              UserRepository userRepository,
                              MissionPostRepository missionPostRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.missionPostRepository = missionPostRepository;
    }

    public boolean addComment(Long userId, MissionCommentDTO missionCommentDTO) {
        if (!missionCommentDTO.getCommentContent().isBlank()) {
            Comment comment = new Comment();
            comment.setUser(userRepository.findByUserId(userId));
            comment.setMissionPost(missionPostRepository.findByMissionPostId(missionCommentDTO.getMissionPostId()));
            comment.setCommentDate(missionCommentDTO.getCommentDateTime());
            comment.setCommentContent(missionCommentDTO.getCommentContent());
            commentRepository.save(comment);
            return true;
        }
        return false;
    }

    public String deleteComment(Long commentId, Long userId) {
        //미션 종료일 지난 거면 못하도록 예외처리하기

        Comment comment = commentRepository.findCommentByCommentId(commentId);
        MissionPost missionPost = comment.getMissionPost();
        LocalDate missionEndDate = missionPost.getJaraUs().getEndDate();

        if (comment.getUser().getUserId()!=userId) {
            return "작성자가 일치하지 않습니다.";
        } else if (LocalDate.now().isAfter(missionEndDate)){
            return "삭제 가능한 기간이 지났습니다.";
        }
          else if (comment.getUser().getUserId().equals(userId)) {
            commentRepository.delete(commentRepository.findByCommentId(commentId));
            return "댓글이 삭제되었습니다.";

        } else {
            return "댓글 삭제에 실패했습니다.";
        }
    }
}
