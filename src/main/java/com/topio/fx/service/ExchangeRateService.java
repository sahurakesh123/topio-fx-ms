package com.topio.fx.service;


import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.topio.fx.data.CurrencyRate;
import com.topio.fx.data.FXRateResponse;
import com.topio.fx.data.ExchangeRate;
import com.topio.fx.data.FrankfurterResponse;
import com.topio.fx.data.TimeSeriesResponse;
import com.topio.fx.data.repository.ExchangeRateRepo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class ExchangeRateService {

    ExchangeRateRepo exchangeRateRepo;

    static final String ACCEPTABLE_TARGETS_CURRENCY ="EUR,GBP,JPY,CZK";

    public FXRateResponse getFx(String target) throws Exception {
        var currentDate = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now()));
        var response = new FXRateResponse();
        response.setDate(currentDate);
        List<ExchangeRate> data = new ArrayList<>();
        if (target == null || target.isBlank()) {
            target="EUR,GBP,JPY,CZK";
        }
        for (var t: target.split(",")) {
            List<ExchangeRate> d = new ArrayList<>();
            var res = exchangeRateRepo.findDistinctFirstByDateAndCurrency(currentDate, t);
            // If data for particular target is empty
            if (res == null) {
                d = getFxFromApi(t, currentDate);
            } else {
                d.add(res);
            }
            data.addAll(d);
        }
        // If entire data is empty
        if (data.isEmpty()) {
            data = getFxFromApi(target, currentDate);
        }
        List<CurrencyRate> rates = new ArrayList<>();
        data.forEach(dat -> rates.add(new CurrencyRate(dat.getCurrency(), dat.getValue())));
        response.setRates(rates);
        return response;
    }

    private List<ExchangeRate> getFxFromApi(String target, String date) throws Exception {
        var data = getFrankfurterResponse(target, date);
        List<ExchangeRate> response = new ArrayList<>();
        data.getRates().forEach((k, v) -> {
            ExchangeRate rate = new ExchangeRate();
            rate.setDate(date);
            rate.setCurrency(k);
            rate.setValue(v.toString());
            response.add(rate);
            saveResponseData(date, k, v.toString());
        });
        return response;
    }

    private void saveResponseData(String date, String currency, String value) {
        ExchangeRate exchangeRates = new ExchangeRate();
        exchangeRates.setDate(date);
        exchangeRates.setCurrency(currency);
        exchangeRates.setValue(value);
        exchangeRateRepo.save(exchangeRates);
    }

    private static FrankfurterResponse getFrankfurterResponse(String target, String date) throws URISyntaxException {
        log.info("Fetching from API - Currency = {}, Date = {}", target, date);
        
        RestTemplate restTemplate = new RestTemplate();
        var uri = String.format("https://api.frankfurter.app/%s?from=USD&to=%s", date == null ? "latest" : date, target);
        return restTemplate.getForObject(uri, FrankfurterResponse.class);
    }

    public TimeSeriesResponse getTimeSeriesByCurrency(String targetCurrency) throws Exception {
        Map<String, CurrencyRate> timeSeries = new HashMap<>();
        for(int i = 0 ; i <= 2; i++){
            var currentDate = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now().minus(i, ChronoUnit.DAYS)));
            List<ExchangeRate> data = new ArrayList<>();
            var res = exchangeRateRepo.findDistinctFirstByDateAndCurrency(currentDate, targetCurrency);
            if(res == null){
                data = getFxFromApi(targetCurrency, currentDate);
            } else  {
                data.add(res);
            }

            data.forEach(dat -> {
                CurrencyRate currencyRate = new CurrencyRate(dat.getCurrency(), dat.getValue());
                timeSeries.put(currentDate, currencyRate);
            });

        }

        TimeSeriesResponse timeSeriesResponse = new TimeSeriesResponse();
        timeSeriesResponse.setRates(timeSeries);
        return timeSeriesResponse;
    }


}
