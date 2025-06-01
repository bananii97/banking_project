package org.example.bankproject.exchange;

import org.example.bankproject.exchange.model.ExchangeRate;
import org.example.bankproject.exchange.model.Rate;
import org.example.bankproject.exchange.model.RateTable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExchangeService {

    private static final Set<String> SUPPORTED_CURRENCIES = Set.of("USD", "EUR", "GBP", "CHF");

    private final WebClient webClient;

    public ExchangeService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Map<String, ExchangeRate> getRates() {
        List<RateTable> rateTable = webClient.get()
                .uri("/exchangerates/tables/C/?format=json")
                .retrieve()
                .bodyToFlux(RateTable.class)
                .collectList()
                .block();

        List<Rate> rates = rateTable.get(0).getRates();

        return rates.stream().
                filter(rate -> SUPPORTED_CURRENCIES.contains(rate.getCode()))
                .collect(Collectors.toMap(
                        Rate::getCode,
                        rate -> new ExchangeRate(rate.getBid(), rate.getAsk())
                ));
    }
}
