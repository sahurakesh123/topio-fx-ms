package com.topio.fx.data;


import java.util.List;

import lombok.Data;

@Data
public class FXRateResponse {
    String date;
    String source = "USD";
    List<CurrencyRate> rates;

}
