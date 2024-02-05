package SiliconDream.JaraMe.service;

import SiliconDream.JaraMe.domain.User;

import java.util.List;
import java.util.Optional;

public interface JoinUsersService {

    public Optional<List<Long>> findUserIdsByJaraUsId(Long JaraUsId);

}
