package SiliconDream.JaraMe.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import SiliconDream.JaraMe.domain.Schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Schedule save(Schedule schedule);

    @Query("SELECT s.scheduleDate FROM Schedule s LEFT JOIN s.jaraUs as sj WHERE sj.jaraUsId = :jaraUsId")
    Set<LocalDate> findScheduleDateByJaraUsId(Long jaraUsId);


    @Query("SELECT sj.jaraUsId " +
            "FROM Schedule s " +
            "LEFT JOIN s.jaraUs as sj " +
            "WHERE s.scheduleDate = :today AND sj.jaraUsId = :jaraUsId")
    Optional<Long> findJaraUs_JaraUsIdByScheduleDateAndJaraUsId(LocalDate today, Long jaraUsId);

}
