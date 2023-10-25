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

        if (winners != null) {
            Optional<WinnerEntity> minInterval = winners.stream().min(Comparator.comparing(WinnerEntity::getIntervals));
            Optional<WinnerEntity> maxInterval = winners.stream().max(Comparator.comparing(WinnerEntity::getIntervals));

            if (minInterval.isPresent()) {
                List<WinnerEntity> minWinners = winners.stream().filter(element -> minInterval.get().getIntervals()
                        .equals(element.getIntervals())).collect(Collectors.toList());
                if (minWinners.size() > 0) {
                    minWinners.forEach(winner -> {
                        award.getMin().add(winner);
                    });
                } else {
                    award.getMin().add(minInterval.get());
                }
            }

            if (maxInterval.isPresent()) {
                List<WinnerEntity> maxWinners = winners.stream().filter(element -> maxInterval.get().getIntervals()
                        .equals(element.getIntervals())).collect(Collectors.toList());
                maxWinners.forEach(winner -> {
                    award.getMax().add(winner);
                });
            } else {
                award.getMax().add(maxInterval.get());
            }
        }
        return award;
    }
}