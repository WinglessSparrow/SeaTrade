package DataClasses;

import java.awt.Point;
import java.io.IOException;
import java.net.UnknownHostException;

import API.CompanyAPI;
import API.ShipController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Ship {

    private int id = 0;
    private String name = null;
    private Point pos = null;
    private Direction dir = null;
    private Harbour harbour = null;
    private Cargo heldCargo = null;

    private ShipController controller = null;

    public void createApi(int cPort, String cHost, int sPort, String sHost) throws UnknownHostException, IOException {

        CompanyAPI company = new CompanyAPI(cHost, cPort);
        controller = new ShipController(company, this, sPort, sHost);
        company.setController(controller);

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getPos() {
        return pos;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public Harbour getHarbour() {
        return harbour;
    }

    public void setHarbour(Harbour harbour) {
        this.harbour = harbour;
    }

    public Cargo getHeldCargo() {
        return heldCargo;
    }

    public void setHeldCargo(Cargo heldCargo) {
        this.heldCargo = heldCargo;
    }

    public void setController(ShipController controller) {
        this.controller = controller;
    }

    public void setPosDirection(Point pos, Direction dir) {

        this.pos = pos;
        this.dir = dir;

    }

    public void printShip() {
        try {
            System.out.println(new ObjectMapper().writeValueAsString(this));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ShipController getController() {
        return controller;
    }
}
