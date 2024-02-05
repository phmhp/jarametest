package SiliconDream.JaraMe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import siliconDream.jaraMe.domain.Comment;
import siliconDream.jaraMe.dto.CommentDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    void delete (Comment comment);
    Comment findByCommentId(Long commentId);
    Comment save(Comment comment);

    void deleteCommentByCommentId(Long commentId);

    Comment findCommentByCommentId(Long commentId);

    @Query("SELECT  new siliconDream.jaraMe.dto.CommentDTO("+
            "c.commentId, c.commentContent, c.commentDate, cu.nickname, cu.profileImage) "+
            "FROM Comment c "+
            "LEFT JOIN c.user cu "+
            "WHERE c.missionPost.missionPostId = :missionPostId")
    Optional<List<CommentDTO>> findCommentByMissionPost_MissionPostId(Long missionPostId);
}
