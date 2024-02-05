package SiliconDream.JaraMe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import siliconDream.jaraMe.domain.ToDoList;
import siliconDream.jaraMe.domain.User;

import java.util.List;

public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {
    List<ToDoList> findByUserAndTeskStatus(User user, boolean teskStatus);
}
