package org.example.bankproject.exchange.model.dto;

import lombok.Builder;
import org.example.bankproject.exchange.currency.ExchangeType;

import java.math.BigDecimal;

@Builder
public class ExchangeRequestDto {

    private String fromCurrency;
    private String toCurrency;
    private BigDecimal amount;
    private ExchangeType exchangeType;
}
