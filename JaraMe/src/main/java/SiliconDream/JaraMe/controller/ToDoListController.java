package SiliconDream.JaraMe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import SiliconDream.JaraMe.domain.ToDoList;
import SiliconDream.JaraMe.service.ToDoListService;

@RestController
@RequestMapping("/todolist")
public class ToDoListController {

    @Autowired
    private ToDoListService toDoListService;

    @PostMapping("/create")
    public ResponseEntity<?> createTesk(@SessionAttribute(name="userId", required=true) Long userId, @RequestParam String teskName) {
        ToDoList toDoList = toDoListService.createTesk(userId, teskName);
        return ResponseEntity.ok().body(toDoList);
    }

    @DeleteMapping("/delete/{todoListId}")
    public ResponseEntity<?> deleteTesk(@PathVariable Long todoListId) {
        toDoListService.deleteTesk(todoListId);
        return ResponseEntity.ok().body("Tesk deleted successfully");
    }

    @PostMapping("/toggle/{todoListId}")
    public ResponseEntity<?> toggleTeskStatus(@PathVariable Long todoListId) {
        toDoListService.toggleTeskStatus(todoListId);
        return ResponseEntity.ok().body("Tesk status toggled successfully");
    }
}
