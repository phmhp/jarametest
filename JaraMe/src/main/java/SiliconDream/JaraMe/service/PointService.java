package SiliconDream.JaraMe.service;

import org.springframework.stereotype.Service;
import SiliconDream.JaraMe.repository.PointRepository;
import SiliconDream.JaraMe.domain.User;

import java.time.LocalDateTime;


public interface PointService {

   String checkIn(Long userId, LocalDateTime dateTime);
   String passTicket(Long userId);

   int pointPlus(Long userId, int changeAmount,Long jaraUsId);


}
