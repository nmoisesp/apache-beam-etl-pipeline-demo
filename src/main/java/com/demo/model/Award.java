package com.demo.model;

import com.demo.dto.AwardDTO;
import com.demo.dto.WinnerDTO;
import com.demo.entity.WinnerEntity;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Award {

    private ModelMapper mapper = new ModelMapper();
    private List<WinnerEntity> min = new ArrayList<>();
    private List<WinnerEntity> max = new ArrayList<>();

    public AwardDTO asDto(){
        List<WinnerDTO> min = Arrays.asList(mapper.map(this.getMin(), WinnerDTO[].class));
        List<WinnerDTO> max = Arrays.asList(mapper.map(this.getMax(), WinnerDTO[].class));

        AwardDTO awardDTO = new AwardDTO();
        awardDTO.setMin(
                min
                .stream()
                .sorted()
                .collect(Collectors.toList()));

        awardDTO.setMax(
                max
                .stream()
                .sorted()
                .collect(Collectors.toList()));

        return awardDTO;
    }
}
