package SiliconDream.JaraMe.service;

import org.springframework.stereotype.Service;
import siliconDream.jaraMe.repository.PointRepository;
import siliconDream.jaraMe.domain.User;

import java.time.LocalDateTime;


public interface PointService {

   String checkIn(Long userId, LocalDateTime dateTime);
   String passTicket(Long userId);

   int pointPlus(Long userId, int changeAmount,Long jaraUsId);


}
