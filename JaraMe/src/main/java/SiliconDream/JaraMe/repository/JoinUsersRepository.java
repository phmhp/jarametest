package SiliconDream.JaraMe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import SiliconDream.JaraMe.domain.JoinUsers;
import SiliconDream.JaraMe.domain.User;

import java.util.List;
import java.util.Optional;
@Repository
public interface JoinUsersRepository extends JpaRepository<JoinUsers,Long> {

    @Query("SELECT ju.userId " +
            "FROM JoinUsers j " +
            "LEFT JOIN j.jaraUs jj " +
            "LEFT JOIN j.user ju " +
            "WHERE jj.jaraUsId = :jaraUsId")
    Optional<List<Long>> findUser_UserIdsByJaraUs_JaraUsId(Long jaraUsId);

    @Query("SELECT jj.jaraUsId " +
            "FROM JoinUsers j " +
            "LEFT JOIN j.jaraUs as jj " +
            "LEFT JOIN j.user ju " +
            "WHERE ju.userId = :userId")
    Optional<List<Long>> findJaraUs_jaraUsIdsByUser_userId(Long userId);

}
