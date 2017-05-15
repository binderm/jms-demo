package com.mrclbndr.jms_demo.synchronous.core.api;

import com.mrclbndr.jms_demo.synchronous.domain.Address;

public interface AddressBoundary {
    void blockForReply();

    Address validate(Address toBeValidated);
}
