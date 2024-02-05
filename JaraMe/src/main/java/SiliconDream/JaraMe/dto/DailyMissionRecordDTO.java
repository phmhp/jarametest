package SiliconDream.JaraMe.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import SiliconDream.JaraMe.domain.JaraUs;
import SiliconDream.JaraMe.domain.MissionPost;
import SiliconDream.JaraMe.domain.User;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
public class DailyMissionRecordDTO {




    private LocalDate missionDate;
    private User user;
    private MissionPost missionPost;
    private boolean missionResult;

    private JaraUs jaraUs;

}
