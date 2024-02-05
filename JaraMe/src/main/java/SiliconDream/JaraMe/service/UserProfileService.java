package SiliconDream.JaraMe.service;

import siliconDream.jaraMe.dto.UserProfileInfoDTO;

public interface UserProfileService {
    UserProfileInfoDTO getUserProfileInfo(Long userId);
}
