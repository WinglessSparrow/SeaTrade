package Ship.API;

import Logger.Logger;
import Ship.BusinessLogic.ShipController;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class ShipAPI extends Thread {

    private final ShipController controller;

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

                new ShipConnection(connection, controller).start();

                Logger.log("New Ship connected", this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
