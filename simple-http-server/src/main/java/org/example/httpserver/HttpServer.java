package org.example.httpserver;

import org.example.httpserver.config.Configuration;
import org.example.httpserver.config.ConfigurationManager;
import org.example.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private final static Logger logger= LoggerFactory.getLogger(HttpServer.class);
    public static void main(String[] args) {

        System.out.println("Server starting ...");
        logger.info("Server starting ...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration configuration=ConfigurationManager.getInstance().getCurrentConfiguration();
        System.out.println("Using port: "+ configuration.getPort());
        System.out.println("Using webroot: "+ configuration.getWebroot());
        logger.info("Using port: "+ configuration.getPort());
        logger.info("Using webroot: "+ configuration.getWebroot());

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(configuration.getPort(), configuration.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
