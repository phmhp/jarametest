package SiliconDream.JaraMe.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import siliconDream.jaraMe.dto.GetPassTicketDTO;
import siliconDream.jaraMe.dto.UndoneDateDTO;
import siliconDream.jaraMe.repository.MissionHistoryRepository;
import siliconDream.jaraMe.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PassTicketServiceImpl implements PassTicketService {
    private final UserRepository userRepository;
    private final UserService userService;
    private MissionHistoryRepository missionHistoryRepository;


    public PassTicketServiceImpl(UserService userService, MissionHistoryRepository missionHistoryRepository,
                                 UserRepository userRepository) {
        this.userService = userService;
        this.missionHistoryRepository = missionHistoryRepository;
        this.userRepository = userRepository;
    }

    public GetPassTicketDTO getPassTicket(Long userId) {
        GetPassTicketDTO getPassTicketDTO = new GetPassTicketDTO();


        int passTicket = userService.getPassTicket(userId);
        log.info("passTicket:{}", passTicket);
        getPassTicketDTO.setPassTicket((int) passTicket);


        Optional<List<Object[]>> undoneDatesOptional = missionHistoryRepository.findMissionDateByUser_UserIdAndMissionResult(userId, false);
        List<UndoneDateDTO> undoneDateDTOs = new ArrayList<>();

        if (undoneDatesOptional.isPresent()) {
            for (Object[] object : undoneDatesOptional.get()) {
                log.info("object:{} / undoneDate? => object[0]:{}", object, object[0]);
                log.info("object:{} / count? => object[1]:{}", object, object[1]);
                UndoneDateDTO undoneDateDTO = new UndoneDateDTO((LocalDate) object[0], ((Number) object[1]).intValue());
                undoneDateDTOs.add(undoneDateDTO);
            }
            getPassTicketDTO.setUndoneDates(Optional.of(undoneDateDTOs));
        }
        return getPassTicketDTO;
    }

    public boolean usePassTicket(Long userId, LocalDate selectedDate) {
        log.info("userId: {} / passTicket:{}",userId, userRepository.findPassTicketByUserId(userId) );
        int passTicket = userRepository.findPassTicketByUserId(userId);
        if (passTicket >= 1) {
            log.info("here");
            missionHistoryRepository.updateMissionResultByUser_UserIdAndMissionDate(userId, selectedDate, true);
            log.info("here2");
            userRepository.updatePassTicketByUserId(userId,userRepository.findPassTicketByUserId(userId)-1);
            return true;
        }

        return false;
    }
}
