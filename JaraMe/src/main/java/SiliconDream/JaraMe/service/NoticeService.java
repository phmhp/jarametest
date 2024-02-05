package SiliconDream.JaraMe.service;


import siliconDream.jaraMe.dto.CalendarDTO;
import siliconDream.jaraMe.dto.CalendarMissionHistoryDTO;
import siliconDream.jaraMe.dto.CalendarPointDTO;
import siliconDream.jaraMe.dto.NoticeDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface NoticeService {
    Optional<NoticeDTO> findNoticeMessageByUserIdAndNoticeStatus(Long userId);

    Optional<CalendarDTO> getCalendar(LocalDate selectedDate, Long userId);

    Optional<List<CalendarPointDTO>> getCalendarPointDTO(LocalDate selectedDate, Long userId);
    Optional<List<CalendarMissionHistoryDTO>> getCalendarMissionHistoryDTO(LocalDate selectedDate, Long userId);
    Optional<List<CalendarMissionHistoryDTO>> getCalendarDailyMissionDTO(LocalDate selectedDate, Long userId);
}
