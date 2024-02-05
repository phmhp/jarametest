package SiliconDream.JaraMe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import SiliconDream.JaraMe.domain.JaraUs;
import SiliconDream.JaraMe.dto.JaraUsDTO;
import SiliconDream.JaraMe.dto.sampleDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface JaraUsRepository extends JpaRepository<JaraUs, Long> {
/*
    boolean existsByPath(String path);
*/
    List<JaraUs> findJaraUsByAdministrator_UserId(Long adminUserId);

    @Query("SELECT j FROM JaraUs j WHERE j.endDate < :today")
    List<JaraUs> findExpiredJaraUs(LocalDate today);
    @Query("SELECT j FROM JaraUs j WHERE j.endDate = :yesterDay")
    List<JaraUs> findEndDateYesterDay(@Param("yesterDay")LocalDate yesterDay);

    JaraUs findByJaraUsId(Long jaraUsId);

    @Query("SELECT j FROM JaraUs j WHERE lower(j.jaraUsName) LIKE lower(concat('%', :keyword, '%')) OR lower(j.missionName) LIKE lower(concat('%', :keyword, '%'))")
    List<JaraUs> searchByKeyword(@Param("keyword") String keyword);


    Optional<JaraUs> findByjaraUsId(Long jaraUsId);

    @Query("SELECT j.jaraUsName FROM JaraUs j WHERE j.jaraUsName = :jaraUsName")
    String findJaraUsNameByJaraUsName(@Param("jaraUsName") String jaraUsName);

    //List<JaraUs> findAllByJoinUsers_UserId(Long userId);

}
