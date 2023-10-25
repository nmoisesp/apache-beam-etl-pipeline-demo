package com.demo.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AwardDTO {

    @JsonUnwrapped
    private List<WinnerDTO> min = new ArrayList<>();
    private List<WinnerDTO> max = new ArrayList<>();
}
