package org.example.bankproject.exchange.api;

import lombok.RequiredArgsConstructor;
import org.example.bankproject.exchange.model.ExchangeRate;
import org.example.bankproject.exchange.ExchangeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    @GetMapping("/rates")
    public Map<String, ExchangeRate> getRates() {
        return exchangeService.getRates();
    }
}
