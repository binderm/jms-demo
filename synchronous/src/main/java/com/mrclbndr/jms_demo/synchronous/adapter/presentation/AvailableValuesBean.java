package com.mrclbndr.jms_demo.synchronous.adapter.presentation;

import com.mrclbndr.jms_demo.synchronous.domain.Destination;
import com.mrclbndr.jms_demo.synchronous.domain.MessageType;
import com.mrclbndr.jms_demo.synchronous.domain.Selectable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@Named("values")
@ApplicationScoped
public class AvailableValuesBean {
    public Map<String, Object> getAvailableDestinations() {
        return forEnum(Destination.class);
    }

    public Map<String, Object> getAvailableMessageTypes() {
        return forEnum(MessageType.class);
    }

    public Map<String, Object> getAvailableTimeouts() {
        return Collections.unmodifiableMap(new HashMap<String, Long>() {
            {
                put("No wait", 0L);
                put("1s", 1000L);
                put("2s", 2000L);
                put("4s", 4000L);
                put("8s", 8000L);
            }
        });
    }

    private <EnumConstant extends Enum<EnumConstant> & Selectable> Map<String, Object> forEnum(Class<EnumConstant> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(enumConstant -> (Selectable) enumConstant)
                .collect(Collectors.toMap(Selectable::getLabel, identity()));
    }
}
