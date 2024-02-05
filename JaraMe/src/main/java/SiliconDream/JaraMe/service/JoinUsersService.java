package SiliconDream.JaraMe.service;

import siliconDream.jaraMe.domain.User;

import java.util.List;
import java.util.Optional;

public interface JoinUsersService {

    public Optional<List<Long>> findUserIdsByJaraUsId(Long JaraUsId);

}
