package org.hellstrand.tcpserver.stream;

/**
 * @author (Mats Richard Hellstrand)
 * @version (13th of July, 2018)
 */
public interface Extensions {
    String[] ALLOWED_CONTENT_TYPES = {
        "text/htm",
        "text/html",
        "text/plain",
        "image/gif",
        "image/jpg",
        "image/jpeg",
        "image/png"
    };

    String[] ALLOWED_EXTENSIONS = {
        ".htm",
        ".html",
        ".txt",
        ".gif",
        ".jpg",
        ".jpeg",
        ".png"
    };
}
