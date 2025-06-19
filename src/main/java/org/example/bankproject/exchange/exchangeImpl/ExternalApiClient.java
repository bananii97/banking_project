package org.example.bankproject.exchange.exchangeImpl;

import org.example.bankproject.exchange.model.ExchangeRate;

import java.util.Map;

public interface ExternalApiClient {

    Map<String, ExchangeRate> getRates();
}
