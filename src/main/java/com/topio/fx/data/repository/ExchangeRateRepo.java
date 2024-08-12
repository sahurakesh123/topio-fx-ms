package com.topio.fx.data.repository;


import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.topio.fx.data.ExchangeRate;

@Repository
public interface ExchangeRateRepo extends CrudRepository<ExchangeRate, UUID> {

    public ExchangeRate findDistinctFirstByDateAndCurrency(String date, String currency);
}
