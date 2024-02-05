package SiliconDream.JaraMe.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.ModelAndView;
//import SiliconDream.JaraMe.domain.User;
import SiliconDream.JaraMe.dto.LoginResponse;
import SiliconDream.JaraMe.dto.UserDto;

import SiliconDream.JaraMe.repository.UserRepository;

//import SiliconDream.JaraMe.dto.UserProfileInfoDTO;
import SiliconDream.JaraMe.service.UserProfileService;
import SiliconDream.JaraMe.service.UserService;

import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    @Autowired
    private UserRepository userRepository;
    private UserProfileService userProfileService;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // 회원 가입을 위한 엔드포인트

    @PostMapping("/checkNicknameDuplicate")
    public ResponseEntity<String> checkNicknameDuplicate(@RequestParam ("nickname") String nickname) {
        boolean isNicknameDuplicate = userService.nicknameCheck(nickname);
        if (isNicknameDuplicate) {
            return ResponseEntity.badRequest().body("닉네임 중복");
        }
        return ResponseEntity.ok("닉네임 사용 가능");
    }

    @PostMapping("/checkEmailDuplicate")
    public ResponseEntity<String> checkEmailDuplicate(@RequestParam("email") String email) {
        boolean isEmailDuplicate = userService.emailCheck(email);
        if (isEmailDuplicate) {
            return ResponseEntity.badRequest().body("이메일 중복");
        }
        return ResponseEntity.ok("이메일 사용 가능");
    }
    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserDto userDto, BindingResult bindingResult) {

        // 비밀번호 확인 검증
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("비밀번호 확인이 일치하지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            // 만약 검증 오류가 있다면, 상세한 검증 오류 응답을 반환
            String validationErrors = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("검증 오류가 발생했습니다: " + validationErrors);
        } else {
            boolean isSuccess = userService.create(userDto);

            if (!isSuccess) {
                // 사용자 생성에 문제가 있는 경우, 일반적인 사용자 생성 오류 응답을 반환
                return ResponseEntity.badRequest().body("사용자 생성에 실패했습니다. 다시 시도해주세요.");
            } else {
                // 사용자 생성이 성공한 경우, 성공 응답을 반환
                return ResponseEntity.ok("사용자가 성공적으로 생성되었습니다.");
            }
        }
    }


    // 로그인 처리
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody UserDto userDto, HttpServletRequest request) {
        LoginResponse response = userService.login(userDto.getEmail(), userDto.getPassword());

        if (!response.isSuccess()) {
            // 로그인 실패 시 오류 응답 반환
            return ResponseEntity.badRequest().body(response);
        } else {
            // 로그인 성공 시 세션에 사용자 정보 저장
            HttpSession session = request.getSession(); // 세션 가져오기 또는 생성하기
            session.setAttribute("userId", response.getUser().getUserId()); // 세션에 사용자 정보 저장

            //로그 확인용
            log.info("session.getId:{}", session.getId());


            // 성공 응답 반환
            return ResponseEntity.ok(response);
        }
    }

    // 로그아웃 처리
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 현재 세션 가져오기, 새 세션은 생성하지 않음

        if (session != null) {
            session.invalidate(); // 세션 무효화
            return ResponseEntity.ok().body("Logged out successfully");
        } else {
            // 활성 세션이 없는 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No active session found");
        }
    }

     //프로필 이미지 업데이트
    @PostMapping("/updateProfileImage")
    public ResponseEntity<?> updateProfileImage(@RequestParam("userId") Long userId,
                                                @RequestParam("image") MultipartFile image) {
        try {
            String imageUrl = userService.updateProfileImage(userId, image);
            return ResponseEntity.ok().body("Profile image updated successfully. New Image URL: " + imageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating profile image");
        }
    }

    //회원탈퇴
    @DeleteMapping("/delete")
    @Transactional
    public ResponseEntity<?> deleteUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 현재 세션 가져오기, 없으면 null 반환
        if (session == null || session.getAttribute("userId") == null) {
            // 세션 자체가 없거나 세션에 userId가 없는 경우
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in or session expired.");
        }

        Long userId = (Long) session.getAttribute("userId"); // 세션에서 userId 가져오기
        userRepository.findById(userId).ifPresent(user -> {
            userRepository.delete(user); // 사용자 삭제
            session.invalidate(); // 세션 무효화
        });

        return ResponseEntity.ok().body("User successfully deleted");
    }



    //닉네임 변경
    @PostMapping("/changeNickname")
    public ResponseEntity<?> changeNickname(@RequestBody UserDto userDto) {
        try {
            boolean success = userService.changeNickname(userDto.getUserid(), userDto.getNickname(), userDto.getPassword());
            if (success) {
                return ResponseEntity.ok().body("Nickname changed successfully");
            } else {
                return ResponseEntity.badRequest().body("Nickname is already in use");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error changing nickname");
        }

    }

}
