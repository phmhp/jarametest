package SiliconDream.JaraMe.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class CalendarDTO {
  private Optional<List<CalendarPointDTO>> calendarPointDTOs;
  private Optional<List<CalendarMissionHistoryDTO>> calendarMissionHistoryDTOs;

}
