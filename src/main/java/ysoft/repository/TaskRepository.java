package ysoft.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ysoft.domain.Task;

import java.util.Collection;

/**
 * Created by sturm on 30.08.2016.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
    Collection<Task> findByTaskListUserName(String userName);
}
