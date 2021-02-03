package org.hellstrand.tcpserver.stream;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * @author (Mats Richard Hellstrand)
 * @version (13th of July, 2018)
 */
public class Stream implements Extensions {
    private Path path;
    private FileInputStream fileInputStream;
    private InputStream inputStream;
    private DataOutputStream dataOutputStream;
    private BufferedReader bufferedReader;

    public void setPath(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    public void setFileInputStream(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public FileInputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }
}
