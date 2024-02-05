package SiliconDream.JaraMe.service;

import SiliconDream.JaraMe.domain.ToDoList;
import SiliconDream.JaraMe.domain.User;

public interface ToDoListService {
    ToDoList createTesk(Long userId, String teskName); 
    void deleteTesk(Long todoListId);
    void toggleTeskStatus(Long todoListId);
    boolean hasIncompleteTasks(Long userId);
}
