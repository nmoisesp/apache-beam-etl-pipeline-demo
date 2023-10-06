package com.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "winner")
public class WinnerEntity {

    @Id
    @GeneratedValue
    private Integer id;
    private String producer;
    private Integer previousWin;
    private Integer followingWin;
    private Integer intervals;

    public WinnerEntity(){

    }

    public WinnerEntity(String producer, Integer previousWin, Integer followingWin, Integer intervals) {
        this.producer = producer;
        this.previousWin = previousWin;
        this.followingWin = followingWin;
        this.intervals = intervals;
    }

    @Override
    public String toString() {
        return "WinnerEntity{" +
                "id=" + id +
                ", producer='" + producer + '\'' +
                ", previousWin=" + previousWin +
                ", followingWin=" + followingWin +
                ", intervals=" + intervals +
                '}';
    }
}