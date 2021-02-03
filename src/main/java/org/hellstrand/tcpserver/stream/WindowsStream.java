package org.hellstrand.tcpserver.stream;

import org.hellstrand.tcpserver.exception.UnacceptableSizeException;

import java.util.Arrays;

/**
 * @author (Mats Richard Hellstrand)
 * @version (13th of July, 2018)
 */
public class WindowsStream extends StreamHandler {
    public WindowsStream(String newLine) {
        super(newLine);

        try {
            if (allowedContentTypes.size() != allowedExtensions.size()) {
                throw new UnacceptableSizeException();
            }
        } catch (UnacceptableSizeException e) {
            System.err.println(e);
            System.exit(0);
        }
    }

    public String getContentType(String filename) {
        String extension = getFileExtension(filename);
        return ALLOWED_CONTENT_TYPES[allowedExtensions.indexOf(extension)];
    }

    public String getFileExtension(String filename) {
        return Arrays.stream(ALLOWED_EXTENSIONS)
            .filter(e -> filename.endsWith(e))
            .findFirst()
            .orElse("INVALID - REPLACE");
    }
}
