package com.demo.dto;

import com.demo.model.Award;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AwardDTO {

    @JsonUnwrapped
    private Award awards = new Award();

    public AwardDTO(Award awards) {
        this.awards = awards;
    }
}
