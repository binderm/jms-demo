package com.mrclbndr.jms_demo.synchronous.adapter.presentation;

import com.mrclbndr.jms_demo.synchronous.core.api.AddressBoundary;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("requestor")
@RequestScoped
public class RequestorBean {
    @Inject
    private AddressBoundary addressBoundary;

    public void request() {
        addressBoundary.blockForReply();
    }
}
