package Database;

import Database.ORMapping.CargoMapping;
import Types.Cargo;

import java.sql.SQLException;
import java.util.ArrayList;

public class DBCargo {

    private static final String sqlFullCargo = """
            select C.id     as id,
                        C.value  as value,
                        H.id     as dest_id,
                        H.name   as dest_name,
                        H.pos_x  as dest_x,
                        H.pos_y  as dest_y,
                        H2.id    as src_id,
                        H2.name  as src_name,
                        H2.pos_x as src_x,
                        H2.pos_y as src_y
                 from Cargo C
                          join seatrade.Harbour H on H.id = C.destination
                          join seatrade.Harbour H2 on H2.id = C.source
            """;

    public void clearData() {
        var sql = "delete from Cargo where id > 0;";

        var con = DBConnectionSingleton.getConnection();

        try {
            var st = con.prepareStatement(sql);

            st.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Cargo cargo) {
        var sql = "insert into Cargo values(?, ?, ?, ?);";

        var con = DBConnectionSingleton.getConnection();

        try {
            var st = con.prepareStatement(sql);

            st.setInt(1, cargo.id());
            st.setInt(2, cargo.value());
            st.setInt(3, cargo.src().id());
            st.setInt(4, cargo.dest().id());

            st.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        var sql = "delete from cargo where id = ?;";

        var con = DBConnectionSingleton.getConnection();

        try {
            var st = con.prepareStatement(sql);

            st.setInt(1, id);

            st.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addBulk(Cargo[] cargos) {
        var sql = "insert into Cargo values(?, ?, ?, ?);";

        var con = DBConnectionSingleton.getConnection();

        try {
            con.setAutoCommit(false);

            var st = con.prepareStatement(sql);

            for (var cargo : cargos) {
                st.setInt(1, cargo.id());
                st.setInt(2, cargo.value());
                st.setInt(3, cargo.src().id());
                st.setInt(4, cargo.dest().id());

                st.addBatch();
            }

            st.executeBatch();

            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Cargo get(int cargoId) {
        var sql = sqlFullCargo + " where C.id = ?;";

        var con = DBConnectionSingleton.getConnection();

        Cargo cargo;

        try {
            var st = con.prepareStatement(sql);

            st.setInt(1, cargoId);

            st.execute();

            var result = st.getResultSet();

            result.next();

            cargo = CargoMapping.mapCargo(result, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cargo;
    }

    public Cargo[] getAllCargo() {

        final var cargos = new ArrayList<Cargo>();

        try {
            var con = DBConnectionSingleton.getConnection();

            var st = con.prepareStatement(sqlFullCargo + ";");
            st.execute();
            var set = st.getResultSet();

            while (set.next()) {
                cargos.add(CargoMapping.mapCargo(set, null));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cargos.toArray(new Cargo[0]);
    }
}
