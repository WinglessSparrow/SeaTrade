package Web.API;

import Logger.Logger;
import Web.Controller.WebController;
import Web.DTO.DBDump;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WebAPI extends Thread {

    private ServerSocket serverSocket;

    private final WebController controller;

    public WebAPI(WebController controller, int port) {
        this.controller = controller;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            Logger.logErr(e.toString(), this);
        }

        Logger.log("Web API is running", this);
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                var connection = serverSocket.accept();

                Logger.log("New Web Connection", this);

                new Thread(new WebConnectionHandler(connection, controller)).start();

            } catch (IOException e) {
                Logger.logErr(e.toString(), this);
            }
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            Logger.logErr(e.toString(), this);
        }
    }
}
