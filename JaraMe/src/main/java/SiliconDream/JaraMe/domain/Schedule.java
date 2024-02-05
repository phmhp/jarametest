package SiliconDream.JaraMe.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private Long scheduleId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="jaraUs")
    private JaraUs jaraUs;

    private LocalDate scheduleDate;

    //TODO: getter and setter

    public JaraUs getJaraUs() {
        return jaraUs;
    }
   public void setJaraUs(JaraUs jaraUs) {
        this.jaraUs = jaraUs;
    }


    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }


    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
}
