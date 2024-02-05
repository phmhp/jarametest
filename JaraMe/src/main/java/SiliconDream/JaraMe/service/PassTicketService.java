package SiliconDream.JaraMe.service;

import SiliconDream.JaraMe.dto.GetPassTicketDTO;

import java.time.LocalDate;

public interface PassTicketService {

    GetPassTicketDTO getPassTicket(Long userId);

    boolean usePassTicket(Long userId, LocalDate selectedDate);
}
