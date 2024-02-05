package SiliconDream.JaraMe.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity

@Table
public class PointHistory {
    //포인트 차감/적립 시에 포인트 적립내용에 출석체크,오늘의미션완료,미션완주 이렇게 들어갈 것
    //출석체크 : checkIn
    //패스권구매 : passTicket
    //오늘의미션완료 : dailyMission (미션개수변수)
    //미션완주 : missionComplete (자라어스아이디, 참여율)



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointHistoryId;

    private int point;
    private int changeAmount;
    private boolean plusOrMinus;
    private LocalDateTime transactionTime;

    @Column(columnDefinition = "boolean default false")
    private boolean notice;//erd다이어그림에 업데이트하기
    private String task;
    //fk
    @ManyToOne
    @JoinColumn(name="user")
    private User user;


    //TODO: getter and setter
    public Long getPointHistoryId() {
        return pointHistoryId;
    }

    public void setPointHistoryId(Long pointHistoryId) {
        this.pointHistoryId = pointHistoryId;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(int changeAmount) {
        this.changeAmount = changeAmount;
    }

    public boolean isPlusOrMinus() {
        return plusOrMinus;
    }

    public void setPlusOrMinus(boolean plusOrMinus) {
        this.plusOrMinus = plusOrMinus;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public boolean isNotice() {
        return notice;
    }

    public void setNotice(boolean notice) {
        this.notice = notice;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
