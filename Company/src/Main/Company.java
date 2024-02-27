package Main;

import Database.*;
import Logger.Logger;
import SeaTrade.BusinessLogic.SeaTradeController;
import Ship.BusinessLogic.ShipController;
import Web.Controller.WebController;
import Web.Server.WebServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

import UI.UI;

public class Company {

    private static boolean running = true;

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        var ui = new UI();
        ui.setVisible(true);

        while (running) {
            running = !scanner.nextLine().equals("shutdown");
        }
    }
}