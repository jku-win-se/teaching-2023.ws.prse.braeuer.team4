package at.jku.se.prse.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class MonatDTO {

    @Getter
    @Setter
    public String name;

    @Getter
    @Setter
    public Integer val;
}