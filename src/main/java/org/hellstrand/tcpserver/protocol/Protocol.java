package org.hellstrand.tcpserver.protocol;

/**
 * @author (Mats Richard Hellstrand)
 * @version (10th of November, 2018)
 */
public interface Protocol {
    int HTTP_PROTOCOL = 0;
    String OK = "200";
    String FORBIDDEN = "403";
    String NOT_FOUND = "404";
    String METHOD_NOT_ALLOWED = "405";
    String UNSUPPORTED_MEDIA_TYPE = "415";

    String[] ALLOWED_COMMUNICATION_PROTOCOLS = {
        "http://"
    };

    String[] ALLOWED_REQUEST_METHODS = {
        "GET",
        "HEAD"
    };

    String[] ALLOWED_STATUS_CODES = {
        "200",
        "403",
        "404",
        "405",
        "415"
    };

    String[] ALLOWED_STATUS_LINES = {
        "HTTP/1.1 200 OK",
        "HTTP/1.1 403 Forbidden",
        "HTTP/1.1 404 Not Found",
        "HTTP/1.1 405 Method Not Allowed",
        "HTTP/1.1 415 Unsupported Media Type"
    };

    boolean requestMethodVerified(String method);

    void setCommunicationProtocol(int select);

    String getCommunicationProtocol();
}
