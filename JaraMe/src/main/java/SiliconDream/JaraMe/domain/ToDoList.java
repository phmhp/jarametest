package SiliconDream.JaraMe.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class ToDoList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoListId;

    private LocalDate todayDate;
    private String teskName;
    private boolean teskStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") //외래키 확인해야됨.
    private User user;

    public Long getTodoListId() {
        return todoListId;
    }

    public void setTodoListId(Long todoListId) {
        this.todoListId = todoListId;
    }

    public void setTeskName(String teskName) {
        this.teskName = teskName;
    }

    public void setTeskStatus(boolean teskStatus) {
        this.teskStatus = teskStatus;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // 새로운 getter 메소드
    public boolean isTeskStatus() {
        return teskStatus;
    }

    public void setTodayDate(LocalDate todayDate) {
        this.todayDate = todayDate;
    }

    public LocalDate getTodayDate() {
        return todayDate;
    }
}

