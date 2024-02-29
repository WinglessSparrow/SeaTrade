package Main;

import DataClasses.Harbour;
import DataClasses.Ship;

import java.awt.*;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws UnknownHostException, IOException {

        Ship ship = new Ship();
        Scanner sc = new Scanner(System.in);
        System.out.println("SeaTrade-Port eingeben: ");
        int sPort = sc.nextInt();
        System.out.println("SeaTrade-Host eingeben: ");
        String sHost = sc.next();
        System.out.println("Company-Port eingeben: ");
        int cPort = sc.nextInt();
        System.out.println("Company-Host eingeben: ");
        String cHost = sc.next();
        ship.createApi(cPort, cHost, sPort, sHost);

        sc.nextLine();

        ship.getController().getApi().launch(new Harbour(-1, "halifax", new Point(3, 2)), "Titanic", "East India Trading Company");

    }

}
