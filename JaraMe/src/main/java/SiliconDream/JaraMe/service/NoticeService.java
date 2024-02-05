package SiliconDream.JaraMe.service;


import SiliconDream.JaraMe.dto.CalendarDTO;
import SiliconDream.JaraMe.dto.CalendarMissionHistoryDTO;
import SiliconDream.JaraMe.dto.CalendarPointDTO;
import SiliconDream.JaraMe.dto.NoticeDTO;

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
