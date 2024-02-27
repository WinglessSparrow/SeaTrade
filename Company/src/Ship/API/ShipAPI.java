package Ship.API;

import Logger.Logger;
import Ship.BusinessLogic.ShipController;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;

public class ShipAPI extends Thread implements Closeable {

    private final ShipController controller;

    private ServerSocket socket;

    public ShipAPI(ShipController controller, int port) {
        this.controller = controller;

        try {
            socket = new ServerSocket(port);

        } catch (IOException e) {
            Logger.logErr(e.toString());
        }

        Logger.logGreen("SHIP API working");
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                var connection = socket.accept();

                new ShipConnection(connection, controller).start();

                Logger.log("New Ship connected");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
