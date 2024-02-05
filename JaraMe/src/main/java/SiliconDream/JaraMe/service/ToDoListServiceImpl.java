package SiliconDream.JaraMe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siliconDream.jaraMe.domain.ToDoList;
import siliconDream.jaraMe.domain.User;
import siliconDream.jaraMe.repository.ToDoListRepository;
import siliconDream.jaraMe.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ToDoListServiceImpl implements ToDoListService {

    @Autowired
    private ToDoListRepository toDoListRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ToDoList createTesk(Long userId, String teskName) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        ToDoList toDoList = new ToDoList(); // 기본 생성자를 사용
        toDoList.setTodayDate(LocalDate.now()); // 오늘 날짜 설정
        toDoList.setTeskName(teskName); // 할 일 이름 설정
        toDoList.setTeskStatus(false); // 초기 상태는 미완료로 설정
        toDoList.setUser(user); // 할 일을 생성한 사용자 설정
        return toDoListRepository.save(toDoList);
    }

    @Override
    public void deleteTesk(Long todoListId) {
        toDoListRepository.deleteById(todoListId);
    }

    @Override
    public void toggleTeskStatus(Long todoListId) {
        ToDoList toDoList = toDoListRepository.findById(todoListId)
                .orElseThrow(() -> new RuntimeException("Tesk not found"));
        toDoList.setTeskStatus(!toDoList.isTeskStatus()); // 현재 상태를 반전시킴
        toDoListRepository.save(toDoList);
    }
    @Override
    public boolean hasIncompleteTasks(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // 현재 날짜에 해당하는 미완료 투두리스트 항목을 찾기.
        List<ToDoList> incompleteTasks = toDoListRepository.findByUserAndTeskStatus(user, false);
        return !incompleteTasks.isEmpty(); // 미완료 항목이 있다면 true, 아니면 false 반환
    }
}
