package Database;

import Logger.Log;

import java.sql.SQLException;

public class DB {

    private final DBShip ship = new DBShip();
    private final DBCargo cargo = new DBCargo();
    private final DBCompany company = new DBCompany();
    private final DBGetter getter = new DBGetter(this);
    private final DBHarbour harbour = new DBHarbour();

    public void clearDB() {
        Log.log("Clearing the DB");

        ship.clearData();
        cargo.clearData();
        harbour.clearData();
        company.clearData();
    }

    public void startTransaction() {
        try {
            DBConnectionSingleton.getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void commitTransaction() {
        try {
            DBConnectionSingleton.getConnection().commit();
            DBConnectionSingleton.getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            try {
                DBConnectionSingleton.getConnection().rollback();
            } catch (SQLException ex) {
                Log.logErr(ex.toString());
                throw new RuntimeException(ex);
            }
        }
    }

    public DBShip getShip() {
        return ship;
    }

    public DBCargo getCargo() {
        return cargo;
    }

    public DBCompany getCompany() {
        return company;
    }

    public DBGetter getGetter() {
        return getter;
    }

    public DBHarbour getHarbour() {
        return harbour;
    }
}
