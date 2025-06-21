package org.example.bankproject.exchange.model.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class ExchangeResultDto {

    private String fromCurrency;
    private String toCurrency;
    private BigDecimal amountGiven;
    private BigDecimal amountReceived;
    private double rate;
}
