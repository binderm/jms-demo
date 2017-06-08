package com.mrclbndr.jms_demo.commons.adapter.messaging.impl;

abstract class AbstractSubscriptionOrderReceiver extends AbstractOrderReceiver {
    private static final String PROP_SUBSCRIPTION_NAME = "jms_demo.subscription.name";

    String subscriptionName() {
        return System.getProperty(PROP_SUBSCRIPTION_NAME, "sub");
    }
}
