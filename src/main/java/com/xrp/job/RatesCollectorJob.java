package com.xrp.job;

import com.xrp.model.Exchange;
import com.xrp.model.dto.ReceivedRatesDTO;
import com.xrp.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RatesCollectorJob {

    private final ExchangeRepository exchangeRepository;
    @Value("${base.url}")
    private String baseUrl;
    @Value("${path1}")
    private String path1;
    @Value("${path2}")
    private String path2;
    @Value("${query.param.name}")
    private String queryParamName;
    @Value("${query.param.value}")
    private String queryParamValue;

    @Autowired
    public RatesCollectorJob(ExchangeRepository exchangeRepository) {
        this.exchangeRepository = exchangeRepository;
    }

    @Scheduled(cron = "${cron.expression}")
    public void pullData() {
        WebClient client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        WebClient.ResponseSpec responseSpec = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path1)
                        .path(path2)
                        .queryParam(queryParamName, queryParamValue)
                        .build())
                .retrieve();

        ReceivedRatesDTO responseBody = responseSpec.bodyToMono(ReceivedRatesDTO.class).block();

        if (responseBody == null || !responseBody.isSuccess()) {
            return;
        }

        List<Exchange> exchanges = responseBody.getRates().entrySet().stream()
                .map(entry -> new Exchange(responseBody.getTimestamp(), responseBody.getDate(), entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        this.exchangeRepository.saveAll(exchanges);
    }
}
