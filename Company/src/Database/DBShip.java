package Database;

import Types.Cargo;
import Types.Direction;
import Types.Harbour;
import Types.Ship;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBShip {
    public void update(Ship ship) {
    }

    public void add(Ship ship) {

    }

    public Ship delete(String shipId) {
        return new Ship("Name", new Point(0, 0), Direction.DOWN, null, null);
    }

    public Ship get(String name) {
        return new Ship("Name", new Point(0, 0), Direction.DOWN, null, null);
    }

    public Ship[] getAllShips() {
        String sql = """
                select S.name      as name,
                       S.id        as id,
                       S.pos_x     as pos_x,
                       S.pos_x     as pos_y,
                       S.direction as direction,
                       C.id        as cargo_id,
                       H.name      as harbour_name
                from Ship S
                         left join seatrade.Cargo C on S.cargo = C.id
                         left join seatrade.Harbour H on H.id = S.harbour;
                     """;

        var ships = new ArrayList<Ship>();

        var con = DBConnectionSingleton.getConnection();

        try {
            var st = con.prepareStatement(sql);
            st.execute();

            var set = st.getResultSet();

            while (set.next()) {
                ships.add(new Ship(
                        set.getString("name"), new Point(set.getInt("pos_x"), set.getInt("pos_y")),
                        Direction.valueOf(set.getString("direction")), new Harbour(0, set.getString("harbour_name"), null),
                        new Cargo(null, null, set.getInt("cargo_id"), 0)
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ships.toArray(new Ship[0]);
    }
}
