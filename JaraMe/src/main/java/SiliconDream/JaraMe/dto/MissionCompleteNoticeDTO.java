package SiliconDream.JaraMe.dto;

public class MissionCompleteNoticeDTO {
public MissionCompleteNoticeDTO(int earnPoint, String missionName, String jaraUsName, String period){
    this.earnPoint=earnPoint;
    this.missionName=missionName;
    this.jaraUsName=jaraUsName;
    this.period = period;
}
    //미션 이름 , 자라어스 이름, 기간, 지급 포인트
    private int earnPoint;
    private String missionName;
    private String jaraUsName;
    private String period;

    public int getEarnPoint() {
        return earnPoint;
    }

    public String getMissionName() {
        return missionName;
    }

    public String getJaraUsName() {
        return jaraUsName;
    }

    public String getPeriod() {
        return period;
    }

    


}
