package ysoft.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dsturm on 8/31/2016.
 */
@Entity
public class TaskList extends AbstractEntity {

    @Column(nullable = false)
    private String userName;

    @OneToMany(mappedBy = "taskList")
    private Set<Task> tasks = new HashSet<>();

    protected TaskList(){}

    public TaskList(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public Set<Task> getTasks() {
        return tasks;
    }
}
