package Database;

import Database.ORMapping.ShipMapping;
import Logger.Log;
import Types.*;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class DBShip {

    private final String fullShipSQL = """ 
            select S.name      as name,
                   S.id        as id,
                   S.pos_x     as pos_x,
                   S.pos_y     as pos_y,
                   S.direction as direction,
                   C.id        as cargo_id,
                   C.value     as cargo_value,
                   H.name      as harbour_name,
                   H.pos_x     as harbour_x,
                   H.pos_y     as harbour_y,
                   H.id        as harbour_id,
                   H2.name     as src_name,
                   H2.pos_x    as src_x,
                   H2.pos_y    as src_y,
                   H2.id       as src_id,
                   H3.name     as dest_name,
                   H3.pos_x    as dest_x,
                   H3.pos_y    as dest_y,
                   H3.id       as dest_id
            from Ship S
                    left join seatrade.Cargo C on S.cargo = C.id
                    left join seatrade.Harbour H on H.id = S.harbour
                    left join seatrade.Harbour H2 on H2.id = C.source
                    left join seatrade.Harbour H3 on H3.id = C.destination
                        """;

    public void clearData() {
        var sql = "delete from Ship where id > 0;";

        var con = DBConnectionSingleton.getConnection();

        try {
            var st = con.prepareStatement(sql);

            st.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Ship ship) {
        var sql = "update Ship set direction = ?, cargo = ?, harbour = ?, pos_x = ?, pos_y = ? where id = ?";

        var con = DBConnectionSingleton.getConnection();

        try {
            var st = con.prepareStatement(sql);

            st.setString(1, ship.dir().toString());

            if (ship.heldCargo() == null) st.setNull(2, Types.INTEGER);
            else st.setInt(2, ship.heldCargo().id());


            if (ship.harbour() == null) st.setNull(3, Types.INTEGER);
            else st.setInt(3, ship.harbour().id());

            st.setInt(4, ship.pos().x);
            st.setInt(5, ship.pos().y);

            st.setInt(6, ship.id());

            st.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Ship ship, int companyId) {
        var sql = "insert into Ship (name, pos_x, pos_y, direction, company, harbour, cargo) values (?, ?, ?, ?, ?, ?, NULL);";

        var con = DBConnectionSingleton.getConnection();

        try {
            var st = con.prepareStatement(sql);
            st.setString(1, ship.name());
            st.setInt(2, ship.pos().x);
            st.setInt(3, ship.pos().y);
            st.setString(4, ship.dir().toString());
            st.setInt(5, companyId);
            st.setInt(6, ship.harbour().id());

            st.execute();
        } catch (SQLException e) {
            Log.log(e.toString());
        }
    }

    public void delete(int shipId) {
        var sql = "delete from Ship where id = ?";

        var con = DBConnectionSingleton.getConnection();

        try {
            var st = con.prepareStatement(sql);

            st.setInt(1, shipId);

            st.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Ship get(int shipId) {

        var con = DBConnectionSingleton.getConnection();
        Ship ship = null;

        try {
            var st = con.prepareStatement(fullShipSQL + " where S.id = ?;");
            st.setInt(1, shipId);
            st.execute();

            var resultSet = st.getResultSet();

            if (resultSet.next()) ship = ShipMapping.mapShip(resultSet, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ship;
    }

    public Ship getByName(String shipName) {

        var con = DBConnectionSingleton.getConnection();
        Ship ship = null;

        try {
            var st = con.prepareStatement(fullShipSQL + " where S.name = ?;");
            st.setString(1, shipName);
            st.execute();

            var resultSet = st.getResultSet();

            if (resultSet.next()) ship = ShipMapping.mapShip(resultSet, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ship;
    }

    public Ship[] getAllShips() {
        var ships = new ArrayList<Ship>();

        var con = DBConnectionSingleton.getConnection();

        try {
            var st = con.prepareStatement(fullShipSQL + ";");
            st.execute();

            var resultSet = st.getResultSet();

            while (resultSet.next()) {
                ships.add(ShipMapping.mapShip(resultSet, null));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ships.toArray(new Ship[0]);
    }
}
