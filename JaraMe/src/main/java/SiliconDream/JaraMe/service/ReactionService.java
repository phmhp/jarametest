package SiliconDream.JaraMe.service;

import org.springframework.http.ResponseEntity;

import SiliconDream.JaraMe.dto.MissionReactionDTO;

public interface ReactionService {


    ResponseEntity<String> addReaction(MissionReactionDTO missionReactionDTO, Long userId);

    String deleteReaction(MissionReactionDTO missionReactionDTO, Long userId);
}
