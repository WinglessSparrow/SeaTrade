package Ship.API;

import Logger.Logger;
import Ship.BusinessLogic.ShipController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ShipAPI extends Thread {

    private final ShipController controller;

    private final List<ShipConnection> connections = new ArrayList<>();

    private ServerSocket socket;

    public ShipAPI(ShipController controller, int port) {
        this.controller = controller;

        try {
            socket = new ServerSocket(port);

        } catch (IOException e) {
            Logger.logErr(e.toString(), this);
        }

        Logger.logGreen("SHIP API working", this);
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try (var connection = socket.accept()) {

                connections.add(new ShipConnection(connection, controller));

                Logger.log("New Ship connected", this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
