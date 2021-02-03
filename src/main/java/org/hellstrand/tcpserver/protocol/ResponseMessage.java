package org.hellstrand.tcpserver.protocol;

/**
 * @author (Mats Richard Hellstrand)
 * @version (13th of July, 2018)
 */
public class ResponseMessage {
    private String statusLine;
    private String date;            //    Date: Sun, 18 Oct 2012 10:36:20 GMT
    private String server;          //    Server: Apache/2.2.14 (Win32)
    private String contentLength;
    private String contentType;     //    Content-Type: text/html; charset=iso-8859-1
    private String connection;
    private String entityBody;

    public ResponseMessage(String statusCode) {
        switch (statusCode) {
            case Protocol.OK:
                statusLine = Protocol.ALLOWED_STATUS_LINES[0];
                break;
            case Protocol.NOT_FOUND:
                statusLine = Protocol.ALLOWED_STATUS_LINES[2];
                break;
            case Protocol.METHOD_NOT_ALLOWED:
                statusLine = Protocol.ALLOWED_STATUS_LINES[3];
                break;
            case Protocol.UNSUPPORTED_MEDIA_TYPE:
                statusLine = Protocol.ALLOWED_STATUS_LINES[4];
                break;
            case Protocol.FORBIDDEN:
            default:
                statusLine = Protocol.ALLOWED_STATUS_LINES[1];
        }
    }

    public void setStatusLine(String statusLine) {
        this.statusLine = statusLine;
    }

    public String getStatusLine() {
        return statusLine;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getServer() {
        return server;
    }

    public void setContentLength(String contentLength) {
        this.contentLength = contentLength;
    }

    public String getContentLength() {
        return contentLength;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setConnection(int pValue, String connection) {
        this.connection = connection;
    }

    public String getConnection() {
        return connection;
    }

    public void setEntityBody(String entityBody) {
        this.entityBody = entityBody;
    }

    public String getEntityBody() {
        return entityBody;
    }
}
