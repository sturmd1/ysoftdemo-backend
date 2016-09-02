package ysoft.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by sturm on 30.08.2016.
 */
@Entity
public class Task extends AbstractEntity {

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(nullable = false)
    private boolean done;

    @JsonIgnore
    @ManyToOne
    private TaskList taskList;

    protected Task(){}

    public Task(TaskList taskList) {
        this.taskList = taskList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public TaskList getTaskList() {
        return taskList;
    }
}
