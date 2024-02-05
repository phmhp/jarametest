package SiliconDream.JaraMe.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import SiliconDream.JaraMe.domain.JaraUs;
import SiliconDream.JaraMe.domain.JoinUsers;
import SiliconDream.JaraMe.domain.Recurrence;
import SiliconDream.JaraMe.domain.User;
import SiliconDream.JaraMe.dto.DailyMissionDTO;
import SiliconDream.JaraMe.dto.JaraUsDTO;
import SiliconDream.JaraMe.repository.JaraUsRepository;
import SiliconDream.JaraMe.repository.DailyMissionRepository;
import SiliconDream.JaraMe.repository.JoinUsersRepository;
import SiliconDream.JaraMe.repository.ScheduleRepository;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
@Transactional
public class JaraUsServiceImpl implements JaraUsService {

    private final JaraUsRepository jaraUsRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserService userService;
    private final ScheduleService scheduleService;
    private final JoinUsersRepository joinUsersRepository;
    private final DailyMissionRepository dailyMissionRepository;

    @Override
    public JaraUs createNewJaraUs(JaraUsDTO jaraUsDTO, Long userId) {
        User administrator = userService.findUserByUserId(userId);

        String jaraUsProfileImage = (jaraUsDTO.getJaraUsProfileImage() != null) ? jaraUsDTO.getJaraUsProfileImage() : "default_image_url";
        LocalDate startDate = jaraUsDTO.getStartDate();
        LocalDate endDate = jaraUsDTO.getEndDate();

        if (jaraUsNameCheck(jaraUsDTO.getJaraUsName())) {
            throw new IllegalArgumentException("자라어스 이름 중복");
        }

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("시작일과 종료일을 채워주세요.");
        }

        if (startDate.isBefore(LocalDate.now().plusDays(1))) {
            throw new IllegalArgumentException("시작일은 적어도 내일 이후여야 합니다.");
        }

        if ((jaraUsDTO.getJaraUsName() == null) || (jaraUsDTO.getMissionName() == null) ||
                (jaraUsDTO.getRecurrence() == null) || (jaraUsDTO.getStartDate() == null) ||
                (jaraUsDTO.getEndDate() == null) || (jaraUsDTO.getInterest() == null) ||
                (jaraUsDTO.getMaxMember() == null) || (jaraUsDTO.getDisplay() == null) ||
                (jaraUsDTO.getRule() == null)) {
            throw new IllegalArgumentException("필수 필드를 채워주세요.");
        }


        JaraUs jaraUs = new JaraUs();
        jaraUs.setJaraUsName(jaraUsDTO.getJaraUsName());
        jaraUs.setMissionName(jaraUsDTO.getMissionName());
        jaraUs.setAdminUserId(administrator);
        jaraUs.setRecurrence(jaraUsDTO.getRecurrence());
        jaraUs.setStartDate(jaraUsDTO.getStartDate());
        jaraUs.setEndDate(jaraUsDTO.getEndDate());
        jaraUs.setJaraUsProfileImage(jaraUsProfileImage);
        jaraUs.setMaxMember(jaraUsDTO.getMaxMember());
        jaraUs.setDisplay("public");
        jaraUs.setExplanation(jaraUsDTO.getExplanation());
        jaraUs.setRule(jaraUsDTO.getRule());
        // List<String>를 String으로 대체
        String allowedInterests = "study,health,hobby";
        String userInterest = jaraUsDTO.getInterest();

        // 사용자의 관심사가 유효한지 확인
        if (userInterest == null || userInterest.isEmpty() || !Arrays.asList(allowedInterests.split(",")).contains(userInterest)) {
            throw new IllegalArgumentException("study, health, hobby 중에서 1개를 선택하세요.");
        }

        // jaraUs에 userInterest 저장
        jaraUs.setInterest(userInterest);

        JaraUs savedJaraUs = jaraUsRepository.save(jaraUs); // JaraUs 레코드 저장

        JoinUsers joinUsers = new JoinUsers();
        joinUsers.setUser(administrator);
        joinUsers.setJaraUs(jaraUs);
        joinUsers.setSignUpDate(LocalDate.now());

        jaraUs.getJoinUsers().add(joinUsers);

        // 스케줄링 작업 실행
        scheduleService.jaraUsScheduling(savedJaraUs);

        return savedJaraUs;
    }
    public boolean jaraUsNameCheck(String jaraUsName) {
        return jaraUsRepository.findJaraUsNameByJaraUsName(jaraUsName) != null;
    }

    @Override
    public void participateInJaraUs(@Valid JaraUsDTO jaraUsDTO, Long userId) {
        JaraUs jaraUs = jaraUsRepository.findById(jaraUsDTO.getJaraUsId())
                .orElseThrow(() -> new EntityNotFoundException("자라어스를 찾을 수 없음"));

        User participant = userService.findUserByUserId(userId);

        if (jaraUs.getJoinUsers().stream().anyMatch(joinUser -> joinUser.getUser().equals(participant))) {
            throw new IllegalStateException("이미 가입된 사용자");
        }

        if (jaraUs.getJoinUsers().size() >= jaraUs.getMaxMember()) {
            throw new IllegalStateException("인원 초과");
        }



        JoinUsers joinUsers = new JoinUsers();
        joinUsers.setUser(participant);
        joinUsers.setJaraUs(jaraUs);
        joinUsers.setSignUpDate(LocalDate.now());


        jaraUs.getJoinUsers().add(joinUsers);

        jaraUsRepository.save(jaraUs);
    }

    @Override
    public void withdrawFromJaraUs(Long jaraUsId, Long currentUserId) {
        JaraUs jaraUs = jaraUsRepository.findById(jaraUsId)
                .orElseThrow(() -> new EntityNotFoundException("가입되어있지 않음"));

        if (jaraUs.getAdminUserId().equals(currentUserId)) {
            throw new IllegalStateException("관리자는 탈퇴할 수 없음. 관리자를 넘겨주세요.");
        }

        JoinUsers joinUserToRemove = jaraUs.getJoinUsers().stream()
                .filter(joinUser -> joinUser.getUser().getUserId().equals(currentUserId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("가입되어있지 않음"));

        jaraUs.getJoinUsers().remove(joinUserToRemove);

        jaraUs.setJoinUsers(jaraUs.getJoinUsers());


        jaraUsRepository.save(jaraUs);
    }


    @Override
    public void editJaraUsByAdmin(Long jaraUsId, Long adminUserId, JaraUsDTO jaraUsDTO) {
        JaraUs jaraUs = jaraUsRepository.findById(jaraUsId)
                .orElseThrow(() -> new EntityNotFoundException("자라어스를 찾을 수 없음."));

        User adminUser = userService.findUserByUserId(adminUserId);

        if (!jaraUs.getAdminUserId().equals(adminUser.getUserId())) {
            throw new IllegalStateException("자라어스의 관리자가 아닙니다.");
        }

        // Get the list of joinUsers sorted by signup date
        List<JoinUsers> sortedJoinUsers = jaraUs.getJoinUsers().stream()
                .sorted(Comparator.comparing(JoinUsers::getSignUpDate))
                .collect(Collectors.toList());

        if (!sortedJoinUsers.isEmpty()) {
            JoinUsers nextAdmin = sortedJoinUsers.get(1);
            User newAdminUser = nextAdmin.getUser();

            jaraUs.setAdminUserId(newAdminUser);
        }

        jaraUsRepository.save(jaraUs);
    }

    @Override
    public List<JaraUs> findExpiredJaraUs() {
        List<JaraUs> allJaraUs = jaraUsRepository.findAll();

        List<JaraUs> expiredJaraUs = allJaraUs.stream()
                .filter(jaraUs -> jaraUs.getEndDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());

        return expiredJaraUs;
    }

    @Override
    public JaraUs editJaraUsInformation(Long userId, JaraUsDTO jaraUsDTO) {
        JaraUs jaraUs = jaraUsRepository.findById(jaraUsDTO.getJaraUsId())
                .orElseThrow(() -> new EntityNotFoundException("자라어스를 찾을 수 없음"));

        User editorUser = userService.findUserByUserId(userId);

        // Check if the editor is the administrator of the JaraUs
        if (!jaraUs.getAdminUserId().equals(editorUser.getUserId())) {
            throw new IllegalStateException("관리자만 수정 가능");
        }

        jaraUs.setJaraUsName(jaraUsDTO.getJaraUsName());
        jaraUs.setMissionName(jaraUsDTO.getMissionName());
        jaraUs.setRecurrence(jaraUsDTO.getRecurrence());



        LocalDate startDate = jaraUsDTO.getStartDate();
        if (startDate != null && startDate.isBefore(LocalDate.now().plusDays(1))) {
            throw new IllegalArgumentException("시작일은 적어도 내일 이후여야 합니다.");
        }
        jaraUs.setStartDate(startDate);

        LocalDate endDate = jaraUsDTO.getEndDate();
        jaraUs.setEndDate(endDate);

        // Save the updated JaraUs entity
        return jaraUsRepository.save(jaraUs);
    }

    @Override
    public JaraUs findByjaraUsId(Long jaraUsId) {
        return jaraUsRepository.findByjaraUsId(jaraUsId)
                .orElseThrow(() -> new EntityNotFoundException("JaraUs not found"));
    }

    @Override
    public List<JaraUsDTO> getJaraUsListForUser(Long userId) {
        List<Long> jaraUsIds = joinUsersRepository.findJaraUs_jaraUsIdsByUser_userId(userId)
                .orElseThrow(() -> new RuntimeException("자라어스 없음"));

        return jaraUsIds.stream()
                .map(jaraUsRepository::findByjaraUsId)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    //미션완주일이 어제인 그룹 찾아내기
    public List<JaraUs> findEndDateYesterDay() {
        return jaraUsRepository.findEndDateYesterDay(LocalDate.now().minusDays(1));
    }

    //검색기능
    @Override
    public List<JaraUsDTO> searchJaraUs(String keyword) {
    return jaraUsRepository.searchByKeyword(keyword)
                           .stream()
                           .map(jaraUs -> convertToDTO(jaraUs))
                           .sorted(Comparator.comparing(JaraUsDTO::getJaraUsId)) //jarausid기준으로 오름차순 정렬
                           .collect(Collectors.toList());
}


    //DTO변환메소드 (JaraUs엔티티 인스턴스를 JaraUsDTO 객체로 변환하는 작업 수행) 
    public JaraUsDTO convertToDTO(JaraUs jaraUs) {
        JaraUsDTO jaraUsDTO = new JaraUsDTO();
        jaraUsDTO.setAdminUserId(jaraUs.getAdministrator() != null ? jaraUs.getAdministrator().getUserId() : null);
        jaraUsDTO.setJaraUsId(jaraUs.getJaraUsId());
        jaraUsDTO.setJaraUsName(jaraUs.getJaraUsName());
        jaraUsDTO.setMissionName(jaraUs.getMissionName());
        jaraUsDTO.setExplanation(jaraUs.getExplanation());
        jaraUsDTO.setRule(jaraUs.getRule());
        jaraUsDTO.setJaraUsProfileImage(jaraUs.getJaraUsProfileImage());
        jaraUsDTO.setMaxMember(jaraUs.getMaxMember());
        jaraUsDTO.setDisplay(jaraUs.getDisplay());
        jaraUsDTO.setStartDate(jaraUs.getStartDate());
        jaraUsDTO.setEndDate(jaraUs.getEndDate());
        Set<Recurrence> recurrenceSet = jaraUs.getRecurrence();// JaraUs 엔티티 객체에서 Recurrence 집합을 가져옴.
        jaraUsDTO.setRecurrence(recurrenceSet);
        return jaraUsDTO;
    }

    public boolean hasIncompleteMissions(Long userId) {
        // 사용자가 참여하고 있는 모든 자라어스의 ID를 가져온다.
        List<Long> jaraUsIds = joinUsersRepository.findJaraUs_jaraUsIdsByUser_userId(userId)
                .orElseThrow(() -> new RuntimeException("No JaraUs found for the user"));

        for (Long jaraUsId : jaraUsIds) {
            // 각 자라어스에 대한 오늘의 미션 완료 여부를 확인
            List<DailyMissionDTO> dailyMissions = dailyMissionRepository
                    .findDailyMissionDTOByScheduleDateAndUser_UserId(LocalDate.now(), userId);

            for (DailyMissionDTO mission : dailyMissions) {
                if (!mission.isDailyMissionResult() && mission.getJaraUsName().equals(jaraUsRepository.findById(jaraUsId).get().getJaraUsName())) {
                    return true; // 미완료 미션이 있다면 true 반환
                }
            }
        }

        return false; // 모든 자라어스의 미션을 완료했다면 false 반환
    }

}
