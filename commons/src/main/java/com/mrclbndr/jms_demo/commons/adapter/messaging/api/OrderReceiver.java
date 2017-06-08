package com.mrclbndr.jms_demo.commons.adapter.messaging.api;

import java.util.Optional;

public interface OrderReceiver<OrderType> {
    Optional<OrderType> nextOrder(Class<OrderType> orderClazz);
}
