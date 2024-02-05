package SiliconDream.JaraMe.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import siliconDream.jaraMe.domain.Recurrence;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
public class JaraUsDTO {

    public JaraUsDTO(Long adminUserId, Long jaraUsId, String jaraUsName, String missionName, String explanation, String rule,

                    String jaraUsProfileImage, String interest, Integer maxMember, String display, LocalDate startDate,
                    LocalDate endDate, Set<Recurrence> recurrence) {

        this.adminUserId = adminUserId;
        this.jaraUsId = jaraUsId;
        this.jaraUsName = jaraUsName;
        this.missionName = missionName;
        this.explanation = explanation;
        this.rule = rule;
        this.jaraUsProfileImage = jaraUsProfileImage;
        this.maxMember = maxMember;

        this.interest = interest;

        this.display = display;
        this.startDate = startDate;
        this.endDate = endDate;
        this.recurrence = recurrence;
    }

    private Long adminUserId;
    private Long jaraUsId;
    private String jaraUsName;
    private String missionName;
    private String explanation;
    private String rule;
    private String jaraUsProfileImage;

    private String interest;
    private Integer maxMember;
    private String display;

    private LocalDate startDate;
    private LocalDate endDate;
    private Set<Recurrence> recurrence;


    public String getInterest() {
        return interest;
    }
    public void setInterest(String interest) {
        this.interest = interest;
    }


}