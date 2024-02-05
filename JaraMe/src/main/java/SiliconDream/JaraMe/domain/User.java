package SiliconDream.JaraMe.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



@Entity

@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private Long userId;


    @Column(nullable = true)
    private String profileImage;

    @Getter
    @Column(length=20, nullable = false, columnDefinition = "VARCHAR (255)")
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String interest;

    @Column(columnDefinition = "boolean default false") //기본값 false로 설정
    private boolean checkIn;

    @Column(columnDefinition = "int default 0") //기본값 0으로 설정
    private int point ;

    @Column(columnDefinition = "int default 0")//기본값 0으로 설정
    private int passTicket;

    //FK
    @OneToMany(mappedBy="user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MissionPost> missionPost;

    @OneToMany(mappedBy="user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> comment;

    @OneToMany(mappedBy="user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Reaction> reaction;


    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<JoinUsers> joinUsers;
    
    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ToDoList> toDoList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Notification> notifications;

    // User가 관리하는 JaraUs 목록
    @OneToMany(mappedBy = "administrator", fetch = FetchType.LAZY)
    private Set<JaraUs> administeredJaraUses = new HashSet<>();

/*
    @OneToMany(mappedBy="userId")
    @JsonIgnore
    private List<MissionHistory> missionHistory;


*/

    //TODO: getter and setter
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;

    }

    public boolean isCheckIn() {
        return checkIn;
    }

    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getPassTicket() {
        return passTicket;
    }

    public void setPassTicket(int passTicket) {
        this.passTicket = passTicket;
    }

    public List<MissionPost> getMissionPost() {
        return missionPost;
    }

    public void setMissionPost(List<MissionPost> missionPost) {
        this.missionPost = missionPost;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public List<Reaction> getReaction() {
        return reaction;
    }

    public void setReaction(List<Reaction> reaction) {
        this.reaction = reaction;
    }

    public List<JoinUsers> getJoinUsers() {
        return joinUsers;
    }

    public void setJoinUsers(List<JoinUsers> joinUsers) {
        this.joinUsers = joinUsers;
    }
    public List<ToDoList> getToDoList() {
        return toDoList;
    }

    public void setToDoList(List<ToDoList> toDoList) {
        this.toDoList = toDoList;
    }
    
    public Set<JaraUs> getAdministeredJaraUses() {
        return administeredJaraUses;
    }

    public void setAdministeredJaraUses(Set<JaraUs> administeredJaraUses) {
        this.administeredJaraUses = administeredJaraUses;

    }
/*
    public List<MissionHistory> getMissionHistory() {
        return missionHistory;
    }

    public void setMissionHistory(List<MissionHistory> missionHistory) {
        this.missionHistory = missionHistory;
    }

*/

}
