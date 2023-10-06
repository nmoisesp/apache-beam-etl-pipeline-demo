package com.demo.controller;

import com.demo.dto.AwardDTO;
import com.demo.exceptions.HttpRestException;
import com.demo.service.MovieAwardService;
import org.apache.beam.repackaged.core.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/movies")
public class RestController {

    @Autowired
    private MovieAwardService movieAwardService;

    @GetMapping(
            value = StringUtils.EMPTY,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<AwardDTO> getAll() throws HttpRestException {
        try {
            return ResponseEntity.ok().body(movieAwardService.getAll());
        } catch (Exception e) {
            throw new HttpRestException(e.getMessage());
        }
    }
}