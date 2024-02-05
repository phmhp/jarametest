package SiliconDream.JaraMe.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import siliconDream.jaraMe.domain.JaraUs;
import siliconDream.jaraMe.domain.JoinUsers;
import siliconDream.jaraMe.domain.User;
import siliconDream.jaraMe.dto.JaraUsDTO;
import siliconDream.jaraMe.repository.JaraUsRepository;
import siliconDream.jaraMe.service.JaraUsService;
import siliconDream.jaraMe.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/jaraus")
@RequiredArgsConstructor
public class JaraUsController {

    @Autowired
    private final JaraUsService jaraUsService;
    private final UserService userService;

    @InitBinder("jaraUsDTO")
    public void jaraUsDTOInitBinder(WebDataBinder webDataBinder) {
    }

/*

    @GetMapping("/new-jaraUs")
    public String newJaraUsForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //수정한 부분
        String userIdString = authentication.getName();
        Long userId = Long.parseLong(userIdString);
        User participant = userService.findUserByUserId(userId);

        model.addAttribute("participant", participant);
        model.addAttribute("jaraUsDTO", new JaraUsDTO());
        return "jaraUs/form";
    }
*/

    @PostMapping("/create")
    public ResponseEntity<?> createNewJaraUs(@RequestBody @Valid JaraUsDTO jaraUsDTO, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            log.warn("사용자 검증 오류");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 검증 오류");
        }

        User user = (User) session.getAttribute("user");

        try {
            if (jaraUsDTO.getRecurrence() == null || jaraUsDTO.getRecurrence().isEmpty()) {
                throw new IllegalArgumentException("반복 주기 설정 필요");
            }

            jaraUsDTO.setJaraUsProfileImage("your_image_url_or_base64_data");
            Long currentUserId = user.getUserId();
            JaraUs createdNewJaraUs = jaraUsService.createNewJaraUs(jaraUsDTO, currentUserId);

            Long createdJaraUsId = createdNewJaraUs.getJaraUsId();

            log.info("자라어스 ID: {}", createdJaraUsId);
            return ResponseEntity.ok(Map.of(
                    "message", "자라어스 생성 성공",
                    "jaraUsId", createdJaraUsId
            ));
        } catch (IllegalArgumentException e) {
            log.error("올바르지 않은 입력: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("에러", e.getMessage()));
        }
    }

    @PostMapping("/checkJaraUsNameDuplicate")
    public ResponseEntity<String> checkJaraUsNameDuplicate(@RequestParam("jaraUsName") String jaraUsName) {
        boolean isJaraUsNameDuplicate = jaraUsService.jaraUsNameCheck(jaraUsName);
        if (isJaraUsNameDuplicate) {
            return ResponseEntity.badRequest().body("자라어스 이름 중복");
        }
        return ResponseEntity.ok("사용 가능한 이름");
    }
    @PostMapping("/participate")
    public ResponseEntity<?> participateInJaraUs(@RequestBody @Valid JaraUsDTO jaraUsDTO, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            log.warn("사용자 인증되지 않음");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 인증되지 않음");
        }

        User user = (User) session.getAttribute("user");

        try {
            Long currentUserId = user.getUserId();
            jaraUsService.participateInJaraUs(jaraUsDTO, currentUserId);
            // 성공 응답 반환
            return ResponseEntity.ok("자라어스 가입 성공");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdrawFromJaraUs(@RequestBody @Valid JaraUsDTO jaraUsDTO, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            log.warn("사용자 검증 오류");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 검증 오류");
        }

        User user = (User) session.getAttribute("user");

        try {
            Long jaraUsId = jaraUsDTO.getJaraUsId();
            Long currentUserId = user.getUserId();
            jaraUsService.withdrawFromJaraUs(jaraUsId, currentUserId);
            return ResponseEntity.ok("자라어스 탈퇴 완료");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("자라어스를 찾을 수 없음");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/edit-admin")
    public ResponseEntity<?> editJaraUsByAdmin(
            @RequestBody JaraUsDTO jaraUsDTO,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            log.warn("사용자 검증 오류");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 검증 오류");
        }

        User adminUser = (User) session.getAttribute("user");

        try {
            Long jaraUsId = jaraUsDTO.getJaraUsId(); // Assuming JaraUsDTO has a getter for jaraUsId
            jaraUsService.editJaraUsByAdmin(jaraUsId, adminUser.getUserId(), jaraUsDTO);
            return ResponseEntity.ok("관리자를 넘겼습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("자라어스를 찾을 수 없음.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
    }
    @GetMapping("/expired")
    public ResponseEntity<List<JaraUs>> findExpiredJaraUs() {
        List<JaraUs> expiredJaraUs = jaraUsService.findExpiredJaraUs();
        return ResponseEntity.ok(expiredJaraUs);
    }

    @PostMapping("/edit-information")
    public ResponseEntity<?> editJaraUsInformation(
            @RequestBody JaraUsDTO jaraUsDTO,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            log.warn("사용자 검증 오류");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 검증 오류");
        }

        User editorUser = (User) session.getAttribute("user");

        try {
            JaraUs editedJaraUs = jaraUsService.editJaraUsInformation(editorUser.getUserId(), jaraUsDTO);
            return ResponseEntity.ok("자라어스 정보 수정 성공");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("자라어스를 찾을 수 없음");
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/information")
    public ResponseEntity<?> getJaraUsInformation(@RequestParam Long jaraUsId, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 검증 오류");
            }

            JaraUs jaraUsInfo = jaraUsService.findByjaraUsId(jaraUsId);
            JaraUsDTO jaraUsDTO = jaraUsService.convertToDTO(jaraUsInfo);
            return ResponseEntity.ok(jaraUsDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("JaraUs를 찾을 수 없음");
        }
    }

    @GetMapping("/my-groups")
    public ResponseEntity<?> getMyGroups(HttpServletRequest request) {


        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 검증 오류");
        }

        User user = (User) session.getAttribute("user");

        try {
            List<JaraUsDTO> myGroups = jaraUsService.getJaraUsListForUser(user.getUserId());
            return ResponseEntity.ok(myGroups);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 그룹을 찾을 수 없음");
        }
    }

        // 검색 기능 추가
    @GetMapping("/search")
    public ResponseEntity<List<JaraUsDTO>> searchJaraUs(@RequestParam String keyword) {
        List<JaraUsDTO> searchResults = jaraUsService.searchJaraUs(keyword);
        return ResponseEntity.ok(searchResults);
    }
 }

