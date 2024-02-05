package SiliconDream.JaraMe.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import SiliconDream.JaraMe.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u.email FROM User u WHERE u.email = :email")
    String findEmailByEmail(@Param("email") String email);

    @Query("SELECT u.nickname FROM User u WHERE u.nickname = :nickname")
    String findNicknameByNickname(@Param("nickname") String nickname);


    //수정한 부분
    User findByUserId(Long userId);

    //이메일로 사용자 찾기
    User findByEmail(String email);

    //닉네임으로 사용자 찾기
    Optional<User> findByNickname(String nickname);

    @Query ("SELECT u.passTicket " +
            "FROM User u " +
            "WHERE u.userId = :userId")
    int findPassTicketByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE User u " +
            "SET u.passTicket = :passTicket " +
            "WHERE u.userId = :userId")
    void updatePassTicketByUserId(Long userId,int passTicket);

}
