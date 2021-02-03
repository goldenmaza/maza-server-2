package org.hellstrand.tcpserver.exception;

/**
 * @author (Mats Richard Hellstrand)
 * @version (10th of November, 2018)
 */
public class UnacceptableSizeException extends Exception {
    private String DEFAULT_EXCEPTION =
            "\n¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤\n"
            + " EXCEPTION MESSAGE!\n"
            + "¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤";

    public UnacceptableSizeException() {
        super(DEFAULT_IMPLEMENTATION);
    }

    public UnacceptableSizeException(TEMPLATE_TYPES template_types) {
        switch (template_types.value()) {
            case DEFAULT_IMPLEMENTATION:
            default:
                this(DEFAULT_EXCEPTION);
        }
    }
}
