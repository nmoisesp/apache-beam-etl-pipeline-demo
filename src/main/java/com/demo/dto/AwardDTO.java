package com.demo.dto;

import com.demo.model.Award;
import lombok.Getter;
import lombok.Setter;
import org.apache.beam.repackaged.core.org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class AwardDTO {

    private Award awards = new Award();
    private String message = StringUtils.EMPTY;

    public AwardDTO(Award awards, String message) {
        this.awards = awards;
        this.message = message;
    }
}
