package com.topio.fx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.topio.fx.data.FXRateResponse;
import com.topio.fx.data.TimeSeriesResponse;
import com.topio.fx.service.ExchangeRateService;

@RestController
@RequestMapping("/api/exchangeRate")
public class ExchangeRateController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ExchangeRateService exchangeRateService;

	@GetMapping(value = "/fx")
	@ResponseBody
	public FXRateResponse getFx(@RequestParam("target") String target) throws Exception {
		FXRateResponse obj = exchangeRateService.getFx(target);
		return obj;
	}


    @GetMapping(path = "/{targetCurrency}")
    public TimeSeriesResponse getFxByTargetCurrencyTimeSeries(@PathVariable(name = "targetCurrency") String targetCurrency){
        try {
            return exchangeRateService.getTimeSeriesByCurrency(targetCurrency);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
}