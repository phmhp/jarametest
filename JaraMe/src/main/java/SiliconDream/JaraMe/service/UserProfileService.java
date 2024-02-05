package SiliconDream.JaraMe.service;

import SiliconDream.JaraMe.dto.UserProfileInfoDTO;

public interface UserProfileService {
    UserProfileInfoDTO getUserProfileInfo(Long userId);
}
