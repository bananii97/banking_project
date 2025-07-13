package org.example.bankproject.exchange.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rate {

    private String currency;
    private String code;
    private double bid;
    private double ask;
}
