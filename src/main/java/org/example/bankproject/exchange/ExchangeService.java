package org.example.bankproject.exchange;

import lombok.RequiredArgsConstructor;
import org.example.bankproject.exchange.exchangeImpl.NbpExchangeRateClient;
import org.example.bankproject.exchange.model.ExchangeRate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final NbpExchangeRateClient nbpExchangeRateClient;

    public Map<String, ExchangeRate> getRates() {
        return nbpExchangeRateClient.getRates();
    }
}
