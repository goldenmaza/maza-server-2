package org.hellstrand.tcpserver.protocol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author (Mats Richard Hellstrand)
 * @version (10th of November, 2018)
 */
public abstract class ProtocolHandler implements Protocol {
    List<String> allowedCommunicationProtocols;
    List<String> allowedRequestMethods;
    List<String> allowedStatusCodes;
    List<String> allowedStatusLines;
    String communicationProtocol;

    private String communicationProtocols;
    private String requestMethods;
    private String statusCodes;
    private String statusLines;
    private String newLine;

    public ProtocolHandler(String newLine) {
        this.newLine = newLine;

        allowedCommunicationProtocols = new ArrayList<>();
        allowedCommunicationProtocols.addAll(Arrays.asList(ALLOWED_COMMUNICATION_PROTOCOLS));
        communicationProtocols = concateData(allowedCommunicationProtocols);

        allowedRequestMethods = new ArrayList<>();
        allowedRequestMethods.addAll(Arrays.asList(ALLOWED_REQUEST_METHODS));
        requestMethods = concateData(allowedRequestMethods);

        allowedStatusCodes = new ArrayList<>();
        allowedStatusCodes.addAll(Arrays.asList(ALLOWED_STATUS_CODES));
        statusCodes = concateData(allowedStatusCodes);

        allowedStatusLines = new ArrayList<>();
        allowedStatusLines.addAll(Arrays.asList(ALLOWED_STATUS_LINES));
        statusLines = concateData(allowedStatusLines);
    }

    private String concateData(List<String> allowedValues) {
        return String.join("; ", allowedValues) + newLine;
    }

    public String displayCommunicationProtocols() {
        return communicationProtocols;
    }

    public String displayRequestMethods() {
        return requestMethods;
    }

    public String displayStatusCodes() {
        return statusCodes;
    }

    public String displayStatusLines() {
        return statusLines;
    }

    public abstract boolean requestMethodVerified(String method);

    public abstract void setCommunicationProtocol(int select);

    public abstract String getCommunicationProtocol();
}
