package org.example.bankproject.exchange.model.dto;

import java.math.BigDecimal;

public class ExchangeResultDto {

    private String fromCurrency;
    private String toCurrency;
    private BigDecimal amountGiven;
    private BigDecimal amountReceived;
    private double rate;
}
