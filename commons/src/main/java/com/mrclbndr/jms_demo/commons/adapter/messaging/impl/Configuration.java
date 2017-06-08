package com.mrclbndr.jms_demo.commons.adapter.messaging.impl;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

import static java.lang.String.format;

@Named
@ApplicationScoped
public class Configuration {
    private static final String ENV_PROP_CONFIG_PATH = "jms_demo_configPath";
    private static final String PROP_DESTINATION_TYPE = "jms_demo.destinationType";
    private static final String DESTINATION_TYPE_QUEUE = "queue";
    private static final String DESTINATION_TYPE_TOPIC = "topic";
    private static final String PROP_SUBSCRIPTION_DURABLE = "jms_demo.subscription.durable";
    private static final String PROP_SUBSCRIPTION_SHARED = "jms_demo.subscription.shared";
    private static final String PROP_RECEIVER_TIMEOUT = "jms_demo.receiver.timeout";
    private static final String PROP_SUBSCRIPTION_NAME = "jms_demo.subscription.name";

    private Properties properties;

    @PostConstruct
    public void loadConfiguration() {
        String configPath = System.getenv(ENV_PROP_CONFIG_PATH);
        if (configPath == null) {
            throw new IllegalArgumentException(format("Required environment property '%s' not specified.%n", ENV_PROP_CONFIG_PATH));
        }

        properties = new Properties();
        try (InputStream in = new FileInputStream(configPath)) {
            properties.load(in);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public DestinationType destinationType() {
        switch (properties.getProperty(PROP_DESTINATION_TYPE)) {
            default:
            case DESTINATION_TYPE_QUEUE:
                return DestinationType.QUEUE;
            case DESTINATION_TYPE_TOPIC:
                return DestinationType.TOPIC;
        }
    }

    public boolean isDurableSubscription() {
        return Boolean.parseBoolean(properties.getProperty(PROP_SUBSCRIPTION_DURABLE));
    }

    public boolean isSharedSubscription() {
        return Boolean.parseBoolean(properties.getProperty(PROP_SUBSCRIPTION_SHARED));
    }

    public long receiverTimeout() {
        return Long.parseLong(properties.getProperty(PROP_RECEIVER_TIMEOUT, "500"));
    }

    public String subscriptionName() {
        return properties.getProperty(PROP_SUBSCRIPTION_NAME, "sub");
    }
}
