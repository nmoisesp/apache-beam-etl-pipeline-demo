package com.demo.service;

import com.demo.entity.WinnerEntity;
import com.demo.model.Award;
import com.demo.repository.WinnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProducerAwardServiceImpl implements ProducerAwardService {

    @Autowired
    private WinnerRepository winnerRepository;

    @Override
    public Award getProducersAwardsAndInterval(){
        List<WinnerEntity> winners = this.winnerRepository.findAll();
        Award award = new Award();

        if (winners != null && winners.size() > 0) {
            Optional<WinnerEntity> maxInterval = winners.stream().max(Comparator.comparing(WinnerEntity::getInterval));
            calculateIntervals(winners, award, maxInterval, Boolean.TRUE);
            Optional<WinnerEntity> minInterval = winners.stream().min(Comparator.comparing(WinnerEntity::getInterval));
            calculateIntervals(winners, award, minInterval, Boolean.FALSE);
        }
        return award;
    }

    private static void calculateIntervals(List<WinnerEntity> winners, Award award, Optional<WinnerEntity>  entity, Boolean isMax) {
        if (entity.isPresent()) {
            List<WinnerEntity> intervalsCalculated = winners.stream().filter(element -> entity.get().getInterval()
                    .equals(element.getInterval())).collect(Collectors.toList());
            intervalsCalculated.forEach(winner -> {
                if (isMax) award.getMax().add(winner); else award.getMin().add(winner);
            });
        }
    }
}