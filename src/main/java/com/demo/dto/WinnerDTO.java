package com.demo.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.beam.repackaged.core.org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class WinnerDTO implements Comparable<WinnerDTO>{

    private String producer;
    private Integer previousWin;
    private Integer followingWin;
    private Integer interval;

    @Override
    public int compareTo(WinnerDTO winnerDTO) {
        if (StringUtils.isNotEmpty(winnerDTO.producer)
                && StringUtils.isNotEmpty(this.producer))
            return this.producer.compareTo(winnerDTO.getProducer());
        return -1;
    }
}