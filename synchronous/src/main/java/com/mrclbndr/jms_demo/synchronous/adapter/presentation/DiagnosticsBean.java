package com.mrclbndr.jms_demo.synchronous.adapter.presentation;

import com.mrclbndr.jms_demo.synchronous.core.api.DiagnosticsBoundary;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("diagnostics")
public class DiagnosticsBean {
    @Inject
    private DiagnosticsBoundary diagnosticsBoundary;

    public List<String> getDeadMessages() {
        return diagnosticsBoundary.getDeadMessages();
    }
}
