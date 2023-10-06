package com.demo.repository;

import com.demo.entity.WinnerEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WinnerRepository extends ListCrudRepository<WinnerEntity, Long> {

}