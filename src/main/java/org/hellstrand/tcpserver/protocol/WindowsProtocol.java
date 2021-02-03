package org.hellstrand.tcpserver.protocol;

import java.util.Arrays;

import org.hellstrand.tcpserver.exception.UnacceptableSizeException;

/**
 * @author (Mats Richard Hellstrand)
 * @version (13th of July, 2018)
 */
public class WindowsProtocol extends ProtocolHandler {
    public WindowsProtocol(String newline) {
        super(newline);

        try {
            if (allowedCommunicationProtocols.size() == 0
                || allowedRequestMethods.size() == 0
                || allowedStatusCodes.size() != allowedStatusLines.size()) {
                throw new UnacceptableSizeException();
            }
        } catch (UnacceptableSizeException e) {
            System.err.println(e);
            System.exit(0);
        }
    }

    public boolean requestMethodVerified(String method) {
        return Arrays.stream(ALLOWED_REQUEST_METHODS).anyMatch(m -> m.equals(method));
    }

    public void setCommunicationProtocol(int select) {
        communicationProtocol = ALLOWED_COMMUNICATION_PROTOCOLS[select];
    }

    public String getCommunicationProtocol() {
        return communicationProtocol;
    }
}
