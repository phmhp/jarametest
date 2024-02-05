package SiliconDream.JaraMe.domain;

import lombok.ToString;

@ToString
//@Getter
//@AllArgsConstructor

public enum Recurrence {
    SUNDAY("SUNDAY"),
    MONDAY("MONDAY"),
    TUESDAY("TUESDAY"),
    WEDNESDAY("WEDNESDAY"),
    THURSDAY("THURSDAY"),
    FRIDAY("FRIDAY"),
    SATURDAY("SATURDAY");

    private final String value;

    Recurrence(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Recurrence fromValue(String value) {
        for (Recurrence recurrence : values()) {
            if (recurrence.value.equalsIgnoreCase(value)) {
                return recurrence;
            }
        }
        throw new IllegalArgumentException("설정값외" + value);
    }
}
