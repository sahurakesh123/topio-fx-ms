package com.topio.fx.data;


import java.util.Date;
import java.util.Map;

import lombok.Data;

@Data
public class FrankfurterResponse {

    int amount;
    String base;
    Date date;
    Map<String, Double> rates;
}
