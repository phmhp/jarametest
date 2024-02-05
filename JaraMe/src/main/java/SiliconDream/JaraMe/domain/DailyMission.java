package SiliconDream.JaraMe.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter

@Entity
public class DailyMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dailyMissionId;

    @Column(columnDefinition = "boolean default false") //기본값 false로 설정
    private boolean dailyMissionResult;


    @Column(nullable = true)
    private LocalDateTime doneDateTime;
    private LocalDate scheduleDate; //인증예정일 날짜 (오늘날짜의 데일리미션이 맞는지 확인하기 위함)


    @OneToOne //확인 예정
    @JoinColumn(name="missionPost", insertable = true, updatable = true) //updatable = false?
    private MissionPost missionPost;

    //FK
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "jaraUs")
    private JaraUs jaraUs;



}
