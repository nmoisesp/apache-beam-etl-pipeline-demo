package com.demo.model;

import com.demo.entity.WinnerEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Award {

    private List<WinnerEntity> min = new ArrayList<>();
    private List<WinnerEntity> max = new ArrayList<>();

}
