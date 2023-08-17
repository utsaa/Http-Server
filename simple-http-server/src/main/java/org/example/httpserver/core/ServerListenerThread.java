package org.example.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerListenerThread  extends Thread{

    private final Logger logger= LoggerFactory.getLogger(ServerListenerThread.class);
    private int port;
    private String webroot;
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    public ServerListenerThread(int port , String webroot) throws IOException {
        this.port=port;
        this.webroot=webroot;
        serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(this.port));
serverSocketChannel.configureBlocking(false);
selector=Selector.open();
serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("The http server is ready");
    }

    @Override
    public void run() {
        ExecutorService service= Executors.newFixedThreadPool(8);

        try {

            while (true){
int select=selector.select(2000);
if (select==0) continue;
Set<SelectionKey> selectionKeySet=selector.selectedKeys();
                Iterator<SelectionKey> iterator=selectionKeySet.iterator();
while (iterator.hasNext()){
    SelectionKey key= iterator.next();

    if (key.isValid()&& key.isAcceptable()){
        System.out.println("channel open "+key.channel().isOpen());
        SocketChannel channel= serverSocketChannel.accept();
        System.out.println("Connected: "+channel);
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);


    }
    if (key.isValid()) {
        HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(key);
        service.submit(workerThread);
    }

        iterator.remove();

}
            }


    } catch (ClosedChannelException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                System.out.println("here code");
                selector.close();
                serverSocketChannel.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
