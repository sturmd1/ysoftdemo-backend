package ysoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ysoft.domain.Priority;
import ysoft.domain.Task;
import ysoft.domain.TaskList;
import ysoft.repository.TaskListRepository;
import ysoft.repository.TaskRepository;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * Created by sturm on 30.08.2016.
 */
@RestController
@RequestMapping("{userName}/tasks")
public class TaskController {

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private TaskRepository taskRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Task saveTask(@PathVariable String userName, @RequestBody Task input) {
        TaskList taskList = taskListRepository.findByUserName(userName);
        if (taskList != null) {
            Task task = new Task(taskList);
            task.setTitle(input.getTitle());
            task.setDescription(input.getDescription());
            task.setPriority(input.getPriority());
            return taskRepository.save(task);
        }
        throw new TaskListNotFoundException(userName);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        try {
            taskRepository.delete(taskId);
        } catch (EmptyResultDataAccessException e) {
            throw new TaskNotFoundException(taskId);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Task updateTask(@PathVariable String userName, @RequestBody Task input) {
        if (taskListRepository.findByUserName(userName) == null) {
            throw new TaskListNotFoundException(userName);
        }

        Task task = taskRepository.findOne(input.getId());
        if (task == null) {
            throw new TaskNotFoundException(input.getId());
        }

        task.setTitle(input.getTitle());
        task.setDescription(input.getDescription());
        task.setPriority(input.getPriority());
        task.setDone(input.isDone());
        return taskRepository.save(task);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Task> getTasks(@PathVariable String userName) {
        if (taskListRepository.findByUserName(userName) == null) {
            //the user doesn't have a task list yet, let's create it now for simplicity
            taskListRepository.save(new TaskList(userName));
        }
        return taskRepository.findByTaskListUserName(userName);
    }

    @PostConstruct
    private void createDefaultTaskList() {
        //
        TaskList taskList = taskListRepository.save(new TaskList("david"));
        Task task = new Task(taskList);
        task.setTitle("Pass the Ysoft Interview");
        task.setDescription("Do your best to prove your knowledge");
        task.setPriority(Priority.HIGH);
        taskRepository.save(task);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private static class TaskNotFoundException extends RuntimeException {
        TaskNotFoundException(Long taskId) {
            super("Task " + taskId + " doesn't exist!");
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private static class TaskListNotFoundException extends RuntimeException {
        TaskListNotFoundException(String userName) {
            super("TaskList for " + userName + " doesn't exist!");
        }
    }
}
