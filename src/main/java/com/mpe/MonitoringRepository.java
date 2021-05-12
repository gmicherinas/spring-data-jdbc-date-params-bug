package com.mpe;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author George Micherinas
 * Date 12/05/2021
 */
@Transactional(readOnly = true)
public interface MonitoringRepository extends CrudRepository<Monitoring, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE monitoring SET preview_start = CAST(:start_time AS timestamp) - interval '30 minutes', actual_start=:start_time WHERE id= :id")
    Integer updateWithCast(@Param("id") Long id, @Param("start_time") LocalDateTime startTime);

    @Transactional(noRollbackFor = BadSqlGrammarException.class) // just so tests are happy.
    @Modifying
    @Query("UPDATE monitoring SET preview_start = :start_time - interval '30 minutes', actual_start=:start_time WHERE id= :id")
    Integer updateWithoutCast(@Param("id") Long id, @Param("start_time") LocalDateTime startTime);

    @Transactional(noRollbackFor = BadSqlGrammarException.class) // just so tests are happy.
    @Modifying
    @Query("UPDATE monitoring SET preview_start = :start_time - interval '30 minutes', actual_start=:start_time WHERE id= :id")
    Integer updateWithoutCast(@Param("id") Long id, @Param("start_time") Date startTime);

    @Transactional
    @Modifying
    @Query("UPDATE monitoring SET preview_start = :start_time - interval '30 minutes', actual_start=:start_time WHERE id= :id")
    Integer updateWithoutCast(@Param("id") Long id, @Param("start_time") java.sql.Timestamp startTime);
}
