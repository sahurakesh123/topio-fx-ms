package com.topio.fx.data;


import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Table(name = "exchange_rates", uniqueConstraints = { @UniqueConstraint(columnNames = { "date", "currency" }) })
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String currency;

    String value;

    String date;
}

