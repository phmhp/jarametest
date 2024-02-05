package SiliconDream.JaraMe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import SiliconDream.JaraMe.domain.ToDoList;
import SiliconDream.JaraMe.domain.User;

import java.util.List;

public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {
    List<ToDoList> findByUserAndTeskStatus(User user, boolean teskStatus);
}
