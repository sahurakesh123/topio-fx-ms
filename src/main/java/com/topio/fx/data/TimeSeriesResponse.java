package com.topio.fx.data;



import java.util.Map;

import lombok.Data;

@Data
public class TimeSeriesResponse {
    String source = "USD";
    Map<String, CurrencyRate> rates;
}
