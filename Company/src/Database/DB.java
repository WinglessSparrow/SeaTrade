package Database;

import java.sql.SQLException;

public class DB {

    private DBShip ship = new DBShip();
    private DBCargo cargo = new DBCargo();
    private DBCompany company = new DBCompany();
    private DBGetter getter = new DBGetter();

    public void clearDB() {

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
}
