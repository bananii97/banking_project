package org.example.bankproject.exchange;

import org.example.bankproject.exchange.exchangeImpl.NbpExchangeRateClient;
import org.example.bankproject.exchange.model.ExchangeRate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExchangeServiceTest {

    @Mock
    private NbpExchangeRateClient exchangeRateClient;

    @InjectMocks
    private ExchangeService exchangeService;

    @Test
    void shouldReturnRates() {
        //given
        Map<String, ExchangeRate> rates = Map.of(
                "USD", new ExchangeRate(3.5, 3.6),
                "EUR", new ExchangeRate(4.1, 4.2)
        );
        when(exchangeRateClient.getRates()).thenReturn(rates);

        //when
        Map<String, ExchangeRate> result = exchangeService.getRates();

        //then
        assertEquals(2, result.size());
        assertEquals(3.5, result.get("USD").buy());
    }
}
