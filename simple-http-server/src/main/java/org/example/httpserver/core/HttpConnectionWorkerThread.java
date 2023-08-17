package org.example.httpserver.core;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class HttpConnectionWorkerThread extends Thread{
    private final Logger logger= LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private Socket socket;
private SelectionKey key;
    public HttpConnectionWorkerThread(SelectionKey key) {
        this.key=key;
    }
    @Override
    public void run() {

        if (key.isValid() && key.isReadable() && key.channel().isOpen()){
            SocketChannel channel=null;
            try {
                 channel = (SocketChannel) key.channel();

                String html = "<html><head><title>Simple java " +
                        "Http server</title></head><body><h1>This page was served using simple java http server</h1></body></html>";
                final String CRLF = "\n\r";//13, 10 ASCII
                String response =
                        "HTTP/1.1 200 OK " + CRLF + //Status Line: HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                                "Content-Length: " + html.getBytes().length + CRLF + // HEADER
                                CRLF +
                                html +
                                CRLF + CRLF;
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                channel.write(byteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8)));
//                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (channel!=null && channel.isOpen()) {
                    try {
                        System.out.println("closing channel");
                        channel.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }




}
}
