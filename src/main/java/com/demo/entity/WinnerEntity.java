package com.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "winner")
public class WinnerEntity {

    @Id
    @GeneratedValue
    private Integer id;
    private String producer;
    private Integer previousWin;
    private Integer followingWin;
    private Integer interval;

    public WinnerEntity(){

    }

    public WinnerEntity(String producer, Integer previousWin, Integer followingWin, Integer interval) {
        this.producer = producer;
        this.previousWin = previousWin;
        this.followingWin = followingWin;
        this.interval = interval;
    }
}