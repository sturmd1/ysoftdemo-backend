package ysoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ysoft.domain.TaskList;

/**
 * Created by dsturm on 8/31/2016.
 */
public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    TaskList findByUserName(String userName);
}
