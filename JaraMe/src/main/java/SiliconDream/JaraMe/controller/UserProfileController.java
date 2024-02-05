package SiliconDream.JaraMe.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import SiliconDream.JaraMe.service.UserProfileService;
import SiliconDream.JaraMe.dto.UserProfileInfoDTO;

@RestController
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    // 프로필 정보 반환
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션 존재 시 반환, 없으면 null 반환
        if (session == null || session.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in.");
        }

        Long userId = (Long) session.getAttribute("userId"); // 세션에서 userId 가져오기
        UserProfileInfoDTO userProfileInfo = userProfileService.getUserProfileInfo(userId);

        if(userProfileInfo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User profile not found.");
        }

        return ResponseEntity.ok(userProfileInfo);
    }
}
