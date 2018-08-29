package com.sevak_avet.flume;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

/**
 * Created by savetisyan on 20/12/2017
 */
public class FlumeClient implements AutoCloseable {
    private Writer out;
    private Socket socket;

    public FlumeClient(String hostname, int port) throws IOException {
        this.socket = new Socket(hostname, port);
        this.out = new BufferedWriter(new OutputStreamWriter(
                this.socket.getOutputStream(), "UTF8"));
    }

    public void send(String data) {
        try {
            out.append(data).append("\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
