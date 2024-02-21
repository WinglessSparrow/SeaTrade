package Database;

import Types.Cargo;
import Types.Direction;
import Types.Harbour;
import Types.Ship;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBShip {

    private final String fullShipSQL = """ 
            select S.name      as name,
                   S.id        as id,
                   S.pos_x     as pos_x,
                   S.pos_x     as pos_y,
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

    public void update(Ship ship) {
    }

    public void add(Ship ship) {

    }

    public void delete(String shipId) {

    }

    public Ship get(String shipId) {

        var con = DBConnectionSingleton.getConnection();
        Ship ship;

        try {
            var st = con.prepareStatement(fullShipSQL + " where S.id = ?;");
            st.setString(1, shipId);
            st.execute();

            var resultSet = st.getResultSet();
            resultSet.next();

            ship = parseToShip(resultSet);

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
                ships.add(parseToShallowShip(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ships.toArray(new Ship[0]);
    }

    private Ship parseToShallowShip(ResultSet result) throws SQLException {
        var destHarbour = new Harbour(-1, result.getString("dest_name"), null);
        var srcHarbour = new Harbour(-1, result.getString("src_name"), null);

        Cargo cargo = null;

        if (result.getInt("cargo_id") > 0) {
            cargo = new Cargo(destHarbour, srcHarbour, result.getInt("cargo_id"), result.getInt("cargo_value"));
        }

        Harbour currHarbour = null;
        if (result.getString("harbour_name") != null) {
            currHarbour = new Harbour(-1, result.getString("harbour_name"), null);
        }

        var pos = new Point(result.getInt("pos_x"), result.getInt("pos_y"));
        var dir = Direction.valueOf(result.getString("direction"));

        return new Ship(result.getString("name"), pos, dir, currHarbour, cargo);
    }

    private Ship parseToShip(ResultSet result) throws SQLException {

        Cargo cargo = null;

        if (result.getString("cargo_id") != null) {
            var destPos = new Point(result.getInt("dest_x"), result.getInt("dest_y"));
            var destHarbour = new Harbour(result.getInt("dest_id"), result.getString("dest_name"), destPos);

            var srcPos = new Point(result.getInt("src_x"), result.getInt("src_y"));
            var srcHarbour = new Harbour(result.getInt("src_id"), result.getString("src_name"), srcPos);
            cargo = new Cargo(destHarbour, srcHarbour, result.getInt("cargo_id"), result.getInt("cargo_value"));
        }

        Harbour currHarbour = null;

        if (result.getString("harbour_name") != null) {
            var harbourPos = new Point(result.getInt("harbour_x"), result.getInt("harbour_y"));
            currHarbour = new Harbour(result.getInt("harbour_id"), result.getString("harbour_name"), harbourPos);
        }

        var pos = new Point(result.getInt("pos_x"), result.getInt("pos_y"));
        var dir = Direction.valueOf(result.getString("direction"));

        return new Ship(result.getString("name"), pos, dir, currHarbour, cargo);
    }
}
