package org.example.bankproject.exchange.exchangeImpl;

import org.example.bankproject.exchange.model.ExchangeRate;
import org.example.bankproject.exchange.model.Rate;
import org.example.bankproject.exchange.model.RateTable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class NbpExchangeRateClient implements ExternalApiClient {

    private static final Set<String> SUPPORTED_CURRENCIES = Set.of("USD", "EUR", "GBP", "CHF");
    private final RestClient restClient;

    public NbpExchangeRateClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public Map<String, ExchangeRate> getRates() {
        RateTable[] rateTables = restClient.get()
                .uri("/exchangerates/tables/C/?format=json")
                .retrieve()
                .body(RateTable[].class);

        if (rateTables == null || rateTables.length == 0) {
            return Map.of();
        }

        List<Rate> rates = rateTables[0].getRates();

        return rates.stream()
                .filter(rate -> SUPPORTED_CURRENCIES.contains(rate.getCode()))
                .collect(Collectors.toMap(
                        Rate::getCode,
                        rate -> new ExchangeRate(rate.getBid(), rate.getAsk())
                ));
    }
}
