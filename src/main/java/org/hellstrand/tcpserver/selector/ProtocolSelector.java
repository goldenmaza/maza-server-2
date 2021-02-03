package org.hellstrand.tcpserver.selector;

import org.hellstrand.tcpserver.protocol.Protocol;
import org.hellstrand.tcpserver.protocol.WindowsProtocol;

/**
 * @author (Mats Richard Hellstrand)
 * @version (13th of July, 2018)
 */
public class ProtocolSelector implements Selector {
    private ProtocolSelector() {}

    public static Protocol select(Server server) {
        switch (server) {
            case WINDOWS_SERVER:
            default:
                return new WindowsProtocol(CRLF);
        }
    }
}
