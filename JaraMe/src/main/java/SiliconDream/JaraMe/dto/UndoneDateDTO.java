package SiliconDream.JaraMe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@AllArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
public class UndoneDateDTO {

    private LocalDate undoneDate;
    private int count;
}
