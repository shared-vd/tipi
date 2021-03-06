package ch.sharedvd.tipi.engine.repository;

import ch.sharedvd.tipi.engine.model.ActivityState;
import ch.sharedvd.tipi.engine.model.DbActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends JpaRepository<DbActivity, Long> {

    List<DbActivity> findByFqn(String fqn);

    List<DbActivity> findChildren(DbActivity parent);

    List<DbActivity> findByState(ActivityState state);

    List<DbActivity> findByStateOrRequestEndExecution(ActivityState state, boolean reqEnd);

    List<DbActivity> findByGroupAndState(String groupName, ActivityState state);

    List<DbActivity> findExecutingActivities(String topProcessName);

    @Query(value = "select distinct p.fqn " +
            "from DbTopProcess p " +
            "   left join p.children as a " +
            "where (a.state = ?1 " +
            "       and a.requestEndExecution = false)" +
            "   OR (p.state = ?1 " +
            "       and p.requestEndExecution = false)")
    List<String> findTopProcessNamesByStateAndReqEnd(ActivityState state);


//    @Query(nativeQuery = true, value = "SELECT DISTINCT p.FQN FROM TP_ACTIVITY a "
//            + "	JOIN TP_ACTIVITY p ON (p.ID = a.PROCESS_FK OR p.ID = a.ID) "
//            + "WHERE p.DTYPE='process' AND a.STATE = ?1 AND a.REQUEST_END_EXECUTION = 0")
//    List<String> findTopProcessNamesByStateAndReqEnd(String state);

    List<DbActivity> findByParentId(long parentId);

    List<DbActivity> findByRequestEndExecutionOrderById(boolean state);

}
