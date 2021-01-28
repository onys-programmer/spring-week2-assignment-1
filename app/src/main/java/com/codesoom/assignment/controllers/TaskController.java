package com.codesoom.assignment.controllers;

import com.codesoom.assignment.TaskNotFoundException;
import com.codesoom.assignment.models.Task;
import com.codesoom.assignment.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    private Long newId = 0L;

    @GetMapping
    public List<Task> list() {
        return taskRepository.getTasks();
    }

    @GetMapping("/{id}")
    public Task get(@PathVariable Long id) throws IOException {
        return findTask(id);
    }

    @PostMapping
    public Task create(@RequestBody Task task) {
        task.setId(generateId());
        taskRepository.getTasks().add(task);

        return task;
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task newTask) throws IOException {
        Task task = findTask(id);
        task.update(newTask);

        return task;
    }

    private Long generateId() {
        newId += 1;
        return newId;
    }

    private Task findTask(Long id) throws IOException {
        return taskRepository.getTasks().stream()
                .filter(task -> Objects.equals(task.getId(), id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException("There is no task with that id"));
    }

}
