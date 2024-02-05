package SiliconDream.JaraMe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siliconDream.jaraMe.dto.UserProfileInfoDTO;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserService userService;

    @Override
    public UserProfileInfoDTO getUserProfileInfo(Long userId) {
        UserProfileInfoDTO userProfileInfoDTO = new UserProfileInfoDTO();
        userProfileInfoDTO.setPoints(userService.getPoints(userId));
        userProfileInfoDTO.setPassTicket(userService.getPassTicket(userId));
        userProfileInfoDTO.setParticipatingJaraUsCount(userService.getParticipatingJaraUsCount(userId));

        // 필요한 경우 추가 정보를 설정할 수 있습니다.
        return userProfileInfoDTO;
    }
}
