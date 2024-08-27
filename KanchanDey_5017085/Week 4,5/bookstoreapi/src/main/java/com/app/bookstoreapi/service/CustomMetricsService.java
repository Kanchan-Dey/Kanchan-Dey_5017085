package com.app.bookstoreapi.service;
import org.springframework.stereotype.Service;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Service
public class CustomMetricsService {
    private final Counter customCounter;
    public CustomMetricsService(MeterRegistry meterRegistry){
        this.customCounter=meterRegistry.counter("custom.metric.counter");
    }
    
    public void incrementCounter(){
        customCounter.increment();
    }
}
