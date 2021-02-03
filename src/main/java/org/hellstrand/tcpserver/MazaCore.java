package org.hellstrand.tcpserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLConnection;
import java.util.Optional;
import java.util.StringTokenizer;

import org.hellstrand.tcpserver.protocol.*;
import org.hellstrand.tcpserver.stream.*;
import org.hellstrand.tcpserver.selector.*;

/**
 * @author (Mats Richard Hellstrand)
 * @version (13th of July, 2018)
 */
public class MazaCore {
    private Optional<Protocol> protocol;
    //private Optional<Service> service;
    private Optional<Stream> stream;
    private Optional<ResponseMessage> responseMessage;

    private int port;
    private Optional<ServerSocket> serverSocket;
    private Optional<Socket> socket;
    private Optional<URLConnection> urlConnection;
    private Optional<URL> url;

    private StringTokenizer stringTokenizer;
    private String requestLine;
    private String method;
    private String uri;
    private String addressField;
    private String filename;

    public MazaCore() {
        this(80);
    }

    public MazaCore(int port) {
        this.port = port;
    }

    public void listen() {
        try {
            protocol = Optional.of(ProtocolSelector.select(Selector.Server.WINDOWS_SERVER));
            protocol.get().setCommunicationProtocol(Protocol.HTTP_PROTOCOL);
            service = Optional.of(ServiceSelector.select(Server.WINDOWS_SERVER));
            serverSocket = Optional.of(new ServerSocket(port));

            while (true) {
                socket = serverSocket.get().accept();

                if (socket.isPresent()) {
                    System.out.println("¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤");
                    clearData();
                    prepareStream();

                    requestLine = stream.getBufferedReader().readLine();
                    stringTokenizer = new StringTokenizer(requestLine);
                    method = stringTokenizer.nextToken();

                    if (protocol.requestMethodVerified(method)) {
                        processRequestedCall();
                    } else {
                        prepareResponseMessage(Protocol.METHOD_NOT_ALLOWED, false);
                    }

                    closeStream();
                    socket.close();
                    displayServerDetails();
                    System.out.println("¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤");
                }
            }
        } catch (IOException ioException) {
            System.err.println(ioException);
        }
    }

    private void processRequestedCall() throws IOException {
        uri = stringTokenizer.nextToken();
        stream.setPath(Paths.get(uri));

        if (false) {
            // 404 handling
        } else {
            Path path = stream.getPath();
            filename = path.getFileName().toString();

            if (Files.isDirectory(path)) {
                prepareResponseMessage(Protocol.FORBIDDEN, false);
            } else if (Files.exists(path)) {
                String extension = stream.getFileExtension(filename.toLowerCase());

                if (extension.equals(Protocol.UNSUPPORTED_MEDIA_TYPE)) {
                    prepareResponseMessage(Protocol.UNSUPPORTED_MEDIA_TYPE, false);
                } else {
                    addressField = protocol.getCommunicationProtocol() + path.getFileName();//socket.getInetAddress() + filename;

                    try {
                        url = new URL(addressField);
                        urlConnection = url.openConnection();
                        urlConnection.setRequestProperty("Content-Type", stream.getContentType(filename.toLowerCase()));
                        urlConnection.setRequestProperty("Connection", "Close");
                    } catch (MalformedURLException malformedURLException) {
                        System.err.println(malformedURLException);
                    }

                    stream.setFileInputStream(new FileInputStream(filename));
                    prepareResponseMessage(Protocol.OK, true);
                    streamData(true);
                }
            } else {
                // ENDPOINTS
                //service.process(path);
            }
        }
    }

    private void prepareResponseMessage(String statusCode, boolean acceptableRequest) throws IOException {
        responseMessage = new ResponseMessage(statusCode);

//        responseMessage.setStatusLine(Selector.CRLF);
        responseMessage.setDate("DATE");
        responseMessage.setDate("SERVER");
        if (acceptableRequest) {
            responseMessage.setContentLength(stream.getFileInputStream().available());
            responseMessage.setContentType(urlConnection.getRequestProperty("Content-Type"));
            responseMessage.setConnection(urlConnection.getRequestProperty("Connection"));
        } else {
            responseMessage.setContentLength(responseMessage.getEntityBody().length());
            responseMessage.setContentType("text/custom_server_message");
            responseMessage.setConnection("Closed");
        }

        switch (statusCode) {
            case Protocol.OK:
                responseMessage.setEntityBody("### EntityBody ### - 200 (OK): File was found and sent back with the response!");
                break;
            case Protocol.METHOD_NOT_ALLOWED:
                responseMessage.setEntityBody("### EntityBody ### - 405 (METHOD_NOT_ALLOWED): Requested method was NOT allowed: Regardless of what you are trying to do, you are trying to access this server in a manner that has not been accepted! This server currently accept: " + protocol.displayRequestMethods());
                break;
            case Protocol.UNSUPPORTED_MEDIA_TYPE:
                responseMessage.setEntityBody("### EntityBody ### - 415 (UNSUPPORTED_MEDIA_TYPE): You have tried to access a file: '" + filename + "' it's extension makes it currently unavailable on this server!");
                break;
            case Protocol.FORBIDDEN:
            default:
                responseMessage.setEntityBody("### EntityBody ### - 403 (FORBIDDEN): You have tried something forbidden! Like entering a possible folder located on this server!");
        }

        displayResponseMessage(acceptableRequest);
        streamData(false);
    }

    private void displayResponseMessage(boolean acceptableRequest) {
        System.out.println(responseMessage.getStatusLine());
        System.out.println(responseMessage.getDate());
        System.out.println(responseMessage.getServer());
        System.out.println(responseMessage.getContentLength());
        System.out.println(responseMessage.getContentType());
        System.out.println(responseMessage.getConnection());
        System.out.println(Selector.CRLF);
        System.out.println(responseMessage.getEntityBody());
    }

    private void streamData(boolean statusCode) throws IOException {
        stream.getDataOutputStream().writeBytes(responseMessage.getStatusLine());
        if (statusCode) {
            stream.getDataOutputStream().writeBytes(responseMessage.getDate());
            stream.getDataOutputStream().writeBytes(responseMessage.getServer());
            stream.getDataOutputStream().writeBytes(responseMessage.getContentLength());
            stream.getDataOutputStream().writeBytes(responseMessage.getContentType());
            stream.getDataOutputStream().writeBytes(responseMessage.getConnection());
            stream.getDataOutputStream().writeBytes(Selector.CRLF);
            readToWriteBuffer(stream.getFileInputStream(), stream.getDataOutputStream());
        }
        stream.getDataOutputStream().writeBytes(Selector.CRLF);
        if (!statusCode) {
            stream.getDataOutputStream().writeBytes(responseMessage.getEntityBody());
        }
    }

    private static void readToWriteBuffer(FileInputStream fileInputStream, DataOutputStream dataOutputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytes = 0;

        while ((bytes = fileInputStream.read(buffer)) != -1) {
            dataOutputStream.write(buffer, 0, bytes);
        }
    }

    private void clearData() {
        responseMessage = null;
        requestLine = null;
        method = null;
        uri = null;
        addressField = null;
        filename = null;
    }

    private void prepareStream() {
        stream = StreamSelector.select(Server.WINDOWS_SERVER);
        stream.setInputStream(socket.getInputStream());
        stream.setDataOutputStream(new DataOutputStream(socket.getOutputStream()));
        stream.setBufferedReader(new BufferedReader(new InputStreamReader(stream.getInputStream())));
    }

    private void closeStream() {
        stream.getFileInputStream().close();
        stream.getInputStream().close();
        stream.getDataOutputStream().close();
        stream.getBufferedReader().close();
    }

    private void displayServerDetails() {
        System.out.println(protocol.displayCommunicationProtocols());
        System.out.println(protocol.displayRequestMethods());
        System.out.println(protocol.displayStatusCodes());
        System.out.println(protocol.displayStatusLines());
        System.out.println(protocol.displayContentTypes());
        System.out.println(protocol.displayExtensions());
        System.out.println(protocol.getCommunicationProtocol());
    }

    public static void main(String[] args) {
        try {
            new MazaCore().listen();
            //new MazaCore(5000).listen();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
