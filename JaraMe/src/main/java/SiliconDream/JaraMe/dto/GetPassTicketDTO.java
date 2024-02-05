package SiliconDream.JaraMe.dto;

import lombok.Getter;
import lombok.Setter;
import SiliconDream.JaraMe.dto.UndoneDateDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
public class GetPassTicketDTO {

    private int passTicket;

    private Optional<List<UndoneDateDTO>> undoneDates;
}
