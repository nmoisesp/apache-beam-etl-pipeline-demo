package com.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WinnerDTO {

    private String producer;
    private Integer previousWin;
    private Integer followingWin;
    private Integer intervals;
}