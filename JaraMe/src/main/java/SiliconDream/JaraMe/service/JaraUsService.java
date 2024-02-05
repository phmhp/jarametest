package SiliconDream.JaraMe.service;

import jakarta.validation.Valid;
import siliconDream.jaraMe.domain.JaraUs;
import siliconDream.jaraMe.dto.JaraUsDTO;

import java.util.List;

public interface JaraUsService {


    JaraUs createNewJaraUs(JaraUsDTO jaraUsDTO, Long userId);

    void participateInJaraUs(@Valid JaraUsDTO jaraUsId, Long userId);

    void withdrawFromJaraUs(Long jaraUsId, Long currentUserId);

    void editJaraUsByAdmin(Long jaraUsId, Long adminUserId, JaraUsDTO jaraUsDTO);

    JaraUs editJaraUsInformation(Long userId, JaraUsDTO jaraUsDTO);

    List<JaraUs> findExpiredJaraUs();

    // 검색 기능 추가
    List<JaraUsDTO> searchJaraUs(String keyword);

    boolean hasIncompleteMissions(Long userId);


    JaraUsDTO convertToDTO(JaraUs jaraUs);


    /////////////////////////////////////////추가
    List<JaraUs> findEndDateYesterDay();


    JaraUs findByjaraUsId(Long jaraUsId);

    boolean jaraUsNameCheck(String jaraUsName);

    List<JaraUsDTO> getJaraUsListForUser(Long userId);

    //List<JaraUsDTO> getJaraUsListForUser(Long userId);

}
