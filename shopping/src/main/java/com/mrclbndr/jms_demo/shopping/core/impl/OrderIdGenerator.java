package com.mrclbndr.jms_demo.shopping.core.impl;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.format;

@Named
@ApplicationScoped
public class OrderIdGenerator {
    private int appInstanceId;
    private AtomicInteger lastOrderId;

    @PostConstruct
    public void generateAppInstanceId() {
        Random random = new Random();
        appInstanceId = random.nextInt(10);
        lastOrderId = new AtomicInteger();
    }

    public synchronized String generateOrderId() {
        return format("%02d-%d", appInstanceId, lastOrderId.incrementAndGet());
    }
}
