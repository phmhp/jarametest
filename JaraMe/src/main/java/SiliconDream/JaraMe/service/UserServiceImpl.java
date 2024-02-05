package SiliconDream.JaraMe.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
/*커밋 전 취소
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
*/
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import SiliconDream.JaraMe.domain.User;
import SiliconDream.JaraMe.dto.LoginResponse;
import SiliconDream.JaraMe.dto.UserDto;
import SiliconDream.JaraMe.repository.JoinUsersRepository;
import SiliconDream.JaraMe.repository.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    private JoinUsersRepository joinUsersRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public boolean create(UserDto userDto) {
        if (!isPasswordConfirmed(userDto)) {
            return false; // Password confirmation failed
        }

        // Check for email duplication using JpaRepository
        if (emailCheck(userDto.getEmail())) {

            return false; // Duplicate email
        }

        // Check for nickname duplication using JpaRepository
        if (nicknameCheck(userDto.getNickname())) {
            return false; // Duplicate nickname
        }

        // Create and save the user entity
        User user = new User();
        user.setProfileImage(userDto.getProfileImage());
        user.setNickname(userDto.getNickname());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setInterest(userDto.getInterest());
        user.setCheckIn(false);

        user.setPoint(0);
        user.setPassTicket(0);

        userRepository.save(user);
        return true; // Successful registration
    }

    public boolean emailCheck(String email) {
        return userRepository.findEmailByEmail(email) != null;
    }

    public boolean nicknameCheck(String nickname) {
        return userRepository.findNicknameByNickname(nickname) != null;
    }
    @Override
    public String findUserEmailByEmail(String email) {
        return userRepository.findEmailByEmail(email);
    }





    @Override
    public boolean isPasswordConfirmed(UserDto userDto) {
        return userDto.getPassword().equals(userDto.getConfirmPassword());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findUserByUserId(Long userId) {

        //수정한 부분
        return userRepository.findByUserId(userId);
    }

    @Override
    public LoginResponse login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return new LoginResponse(false, "USER_NOT_FOUND", null);
        } else if (!user.getPassword().equals(password)) {
            return new LoginResponse(false, "INVALID_PASSWORD", null);
        }
        return new LoginResponse(true, null, user);

    }
    @Override
    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public String updateProfileImage(Long userId, MultipartFile image) throws IOException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String imageUrl = uploadImage(image); // 이미지 업로드 로직
        user.setProfileImage(imageUrl);
        userRepository.save(user);

        return imageUrl;
    }

    private String uploadImage(MultipartFile image) throws IOException {
        // 이미지 파일 이름 생성
        String fileName = generateUniqueFileName(image.getOriginalFilename());

        // 이미지를 저장할 경로 설정
        Path imagePath = Paths.get("uploads/images", fileName);

        // 이미지 파일을 지정된 경로에 저장
        Files.copy(image.getInputStream(), imagePath);

        // 저장된 이미지의 경로 반환
        return imagePath.toString();
    }

    private String generateUniqueFileName(String originalFilename) {
        // 타임스탬프를 이용한 고유 식별자 생성
        String timestamp = String.valueOf(Instant.now().toEpochMilli());

        // 원본 파일의 확장자를 유지하기 위한 처리
        String extension = "";
        int extensionIndex = originalFilename.lastIndexOf('.');
        if (extensionIndex > 0) {
            extension = originalFilename.substring(extensionIndex); // 파일 확장자 포함
        }

        // 고유한 파일 이름 생성
        return timestamp + "_" + originalFilename + extension;
    }

    @Override
    public boolean changeNickname(Long userId, String newNickname, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        if (userRepository.findByNickname(newNickname).isPresent()) {
            return false; // 닉네임 중복
        }

        user.setNickname(newNickname);
        userRepository.save(user);
        return true; // 닉네임 변경 성공
    }

    public int getPassTicket(Long userId) {
        return userRepository.findPassTicketByUserId(userId);
    }
    @Override
    public int getPoints(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getPoint();
    }

    @Override
    public int getParticipatingJaraUsCount(Long userId) {
        return joinUsersRepository.findJaraUs_jaraUsIdsByUser_userId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .size();

    }
}
