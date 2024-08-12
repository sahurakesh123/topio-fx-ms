package com.topio.fx.data;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrencyRate {
    String target;
    String value;
}
