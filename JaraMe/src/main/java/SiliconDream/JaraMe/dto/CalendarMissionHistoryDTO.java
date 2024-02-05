package SiliconDream.JaraMe.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;


@Getter
@Setter
public class CalendarMissionHistoryDTO {


    //미션인증현황 (미완료+완료)
    private LocalDate missionDate;

    private Long jaraUsId;
    private String jaraUsName;
    private String missionName;
    private Optional<Long> missionPostId;
    private boolean missionResult;

}
