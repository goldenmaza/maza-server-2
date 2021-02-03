package org.hellstrand.tcpserver.selector;

import org.hellstrand.tcpserver.stream.Stream;
import org.hellstrand.tcpserver.stream.WindowsStream;

/**
 * @author (Mats Richard Hellstrand)
 * @version (13th of July, 2018)
 */
public class StreamSelector implements Selector {
    private StreamSelector() {}

    public static Stream select(Server server) {
        switch (server) {
            case WINDOWS_SERVER:
            default:
                return new WindowsStream(CRLF);
        }
    }
}
