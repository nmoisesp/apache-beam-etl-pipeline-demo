package com.demo.controller;

import com.demo.dto.AwardDTO;
import com.demo.exceptions.HttpRestException;
import com.demo.model.Award;
import com.demo.service.ProducerAwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/producers")
public class ProducerAwardsController {

    @Autowired
    private ProducerAwardService producerAwardService;

    @GetMapping(
            value = "/awards",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<AwardDTO> getProducersAwards() throws HttpRestException {
        try {
            AwardDTO awardDto = producerAwardService.getProducersAwardsAndInterval().asDto();
            return ResponseEntity.ok().body(awardDto);
        } catch (Exception e) {
            throw new HttpRestException(e.getMessage());
        }
    }
}