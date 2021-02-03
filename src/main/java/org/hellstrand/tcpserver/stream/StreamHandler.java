package org.hellstrand.tcpserver.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author (Mats Richard Hellstrand)
 * @version (13th of July, 2018)
 */
public abstract class StreamHandler extends Stream {
    List<String> allowedContentTypes;
    List<String> allowedExtensions;

    private String contentTypes;
    private String extensions;
    private String newLine;

    public StreamHandler(String newLine) {
        this.newLine = newLine;

        allowedContentTypes = new ArrayList<>();
        allowedContentTypes.addAll(Arrays.asList(ALLOWED_CONTENT_TYPES));
        contentTypes = concateData(allowedContentTypes);

        allowedExtensions = new ArrayList<>();
        allowedExtensions.addAll(Arrays.asList(ALLOWED_EXTENSIONS));
        extensions = concateData(allowedExtensions);
    }

    private String concateData(List<String> allowedValues) {
        return String.join("; ", allowedValues) + newLine;
    }

    public String displayContentTypes() {
        return contentTypes;
    }

    public String displayExtensions() {
        return extensions;
    }

    public abstract String getContentType(String filename);

    public abstract String getFileExtension(String filename);
}
