package SiliconDream.JaraMe.service;

import org.springframework.web.multipart.MultipartFile;
import siliconDream.jaraMe.domain.User;
import siliconDream.jaraMe.dto.LoginResponse;
import siliconDream.jaraMe.dto.UserDto;

import java.io.IOException;

import java.util.List;

public interface UserService {

    boolean create(UserDto userDto);

    String findUserEmailByEmail(String email);

    boolean emailCheck(String email);


    boolean isPasswordConfirmed(UserDto userDto);

    List<User> getAllUsers();

    void saveUser(User user);

    //수정한 부분
    User findUserByUserId(Long userId);

    //로그인 메소드
    LoginResponse login(String email, String password);


    User findUserByEmail(String email);

    // 회원 탈퇴
    void deleteUser(Long userId);

    // 프로필 사진 업로드 및 수정
    String updateProfileImage(Long userId, MultipartFile profileImagePath) throws IOException;

    //닉네임 변경 메소드
    boolean changeNickname(Long userId, String newNickname, String password);


    //사용자 패스 티켓
    int getPassTicket(Long userId);

    // 사용자의 보유 포인트 가져오기
    int getPoints(Long userId);

    // 사용자가 참여 중인 JaraUs 개수 가져오기
    int getParticipatingJaraUsCount(Long userId);

    boolean nicknameCheck(String nickname);

}
