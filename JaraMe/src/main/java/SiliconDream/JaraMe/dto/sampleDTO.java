package SiliconDream.JaraMe.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import siliconDream.jaraMe.domain.Recurrence;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
public class sampleDTO {

    public sampleDTO(String sampleName, Set<Recurrence> recurrence) {
        this.sampleName = sampleName;
        this.recurrence = recurrence;
    }



    private Long sampleId;

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    private String sampleName;
    private Set<Recurrence> recurrence;


    //TODO: getter and setter
    public Set<Recurrence> getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(Set<Recurrence> recurrence) {
        this.recurrence = recurrence;
    }

}