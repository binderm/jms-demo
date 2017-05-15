package com.mrclbndr.jms_demo.synchronous.core.impl;

import com.mrclbndr.jms_demo.synchronous.adapter.messaging.api.AddressValidator;
import com.mrclbndr.jms_demo.synchronous.core.api.AddressBoundary;
import com.mrclbndr.jms_demo.synchronous.domain.Address;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@Stateless
@Transactional(REQUIRES_NEW)
public class AddressBoundaryImpl implements AddressBoundary {
    @Inject
    private AddressValidator addressValidator;

    @Override
    public void blockForReply() {
        Address toBeValidated = new Address("Hötzlstraße 5");
        Address validated = addressValidator.validate(toBeValidated);
        System.out.println(validated);
    }

    @Override
    public Address validate(Address toBeValidated) {
        return new Address(toBeValidated.getStreet().replace("ö", "oe").replace("ß", "ss"));
    }
}
