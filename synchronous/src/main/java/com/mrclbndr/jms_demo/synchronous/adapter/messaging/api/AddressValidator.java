package com.mrclbndr.jms_demo.synchronous.adapter.messaging.api;

import com.mrclbndr.jms_demo.synchronous.domain.Address;

public interface AddressValidator {
    Address validate(Address originalAddress);
}
