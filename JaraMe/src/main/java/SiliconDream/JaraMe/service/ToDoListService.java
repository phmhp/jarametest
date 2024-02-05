package SiliconDream.JaraMe.service;

import siliconDream.jaraMe.domain.ToDoList;
import siliconDream.jaraMe.domain.User;

public interface ToDoListService {
    ToDoList createTesk(Long userId, String teskName); 
    void deleteTesk(Long todoListId);
    void toggleTeskStatus(Long todoListId);
    boolean hasIncompleteTasks(Long userId);
}
