package SiliconDream.JaraMe.service;

import org.springframework.stereotype.Service;
import siliconDream.jaraMe.domain.User;
import siliconDream.jaraMe.repository.JoinUsersRepository;

import java.util.List;
import java.util.Optional;


@Service
public class JoinUsersServiceImpl implements JoinUsersService {
    private final JoinUsersRepository joinUsersRepository;
    public JoinUsersServiceImpl(JoinUsersRepository joinUsersRepository){
        this.joinUsersRepository=joinUsersRepository;
    }

    public Optional<List<Long>> findUserIdsByJaraUsId(Long jaraUsId){
        Optional<List<Long>> userIdOptional =  joinUsersRepository.findUser_UserIdsByJaraUs_JaraUsId(jaraUsId);
        return userIdOptional;

    }
}
