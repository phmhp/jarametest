package SiliconDream.JaraMe.dto;

import java.time.LocalDate;

public class ToDoListDto {
    private Long todoListId;


    public void setTodayDate(LocalDate todayDate) {
        this.todayDate = todayDate;
    }

    private LocalDate todayDate;
    private String teskName;
    private boolean teskStatus;

    // 생성자, getter, setter
    public LocalDate getTodayDate() {
        return todayDate;
    }

    public String getTeskName() {
        return teskName;
    }

    public void setTeskName(String teskName) {
        this.teskName = teskName;
    }

    public boolean isTeskStatus() {
        return teskStatus;
    }

    public void setTeskStatus(boolean teskStatus) {
        this.teskStatus = teskStatus;
    }

    public Long getTodoListId() {
        return todoListId;
    }

    public void setTodoListId(Long todoListId) {
        this.todoListId = todoListId;
    }
}

