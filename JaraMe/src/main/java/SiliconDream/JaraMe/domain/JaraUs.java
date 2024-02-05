package SiliconDream.JaraMe.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table
public class JaraUs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private Long jaraUsId;
    private String jaraUsName;
    private String missionName;
    private String explanation;
    private String rule;
    private String jaraUsProfileImage;
    private String interest;
    private Integer maxMember;
    private String display;

    @NotNull
    @Column(nullable = false, unique = true)
    private LocalDate startDate;
    @NotNull
    @Column(nullable = false, unique = true)
    private LocalDate endDate;


    @ElementCollection(targetClass = Recurrence.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "JaraUsAndRecurrence", joinColumns = @JoinColumn(name = "jaraUsId"))
    @Enumerated(EnumType.STRING)
    private Set<Recurrence> recurrence;


    @OneToMany(mappedBy = "jaraUs", cascade = CascadeType.ALL)
    private Set<JoinUsers> joinUsers = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "admin_user_id", nullable = false)
    private User administrator;

    //TODO: getter and setter

    public Long getAdminUserId() {
        return administrator.getUserId();
    }
    public void setAdminUserId(User administrator) { this.administrator = administrator; }
    public Long getJaraUsId() {
        return jaraUsId;
    }

    public void setJaraUsId(Long jaraUsId) {
        this.jaraUsId = jaraUsId;
    }

    public String getJaraUsName() {
        return jaraUsName;
    }

    public void setJaraUsName(String jaraUsName) {
        this.jaraUsName = jaraUsName;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getJaraUsProfileImage() {
        return jaraUsProfileImage;
    }

    public void setJaraUsProfileImage(String jaraUsProfileImage) {
        this.jaraUsProfileImage = jaraUsProfileImage;
    }

    public Integer getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(Integer maxMember) {
        this.maxMember = maxMember;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<Recurrence> getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(Set<Recurrence> recurrence) {
        this.recurrence = recurrence;
    }
    public Set<JoinUsers> getJoinUsers() {
        return joinUsers;
    }

    public void setJoinUsers(Set<JoinUsers> joinUsers) {
        this.joinUsers = joinUsers;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
    
    public User getAdministrator() {
        return administrator;
    }

    public void setAdministrator(User administrator) {
        this.administrator = administrator;
    }
}


