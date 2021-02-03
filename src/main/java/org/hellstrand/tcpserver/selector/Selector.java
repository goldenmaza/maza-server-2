package org.hellstrand.tcpserver.selector;

/**
 * @author (Mats Richard Hellstrand)
 * @version (13th of July, 2018)
 */
public interface Selector {
    String CR = "\r";
    String LF = "\n";
    String CRLF = CR + LF;

    enum Server {
        WINDOWS_SERVER
    }
}
