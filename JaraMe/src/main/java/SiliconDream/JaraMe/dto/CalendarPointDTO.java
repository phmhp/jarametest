package SiliconDream.JaraMe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarPointDTO {
    //포인트 내역
    //private int totalPoint;
    private int changeAmount;
    private boolean plusOrMinus;
    private String task;


}
