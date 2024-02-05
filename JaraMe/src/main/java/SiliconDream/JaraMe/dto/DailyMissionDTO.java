package SiliconDream.JaraMe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyMissionDTO {
    //미션 이름, 자라어스 이름, 미션 수행 상황 (f는 인증바로가기, f는 인증완료)



    private boolean dailyMissionResult;
    private Long jaraUsId;
    private String jaraUsName;
    private String missionName;

}
