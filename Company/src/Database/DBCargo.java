package Database;

import Types.Cargo;
import Types.Harbour;

import java.sql.SQLException;
import java.util.ArrayList;

public class DBCargo {
    public void update(Cargo cargo) {

    }

    public void add(Cargo cargo) {

    }

    public void addBulk(Cargo[] cargos) {

    }

    public void delete(String cargoId) {

    }

    public Cargo get(String cargoId) {
        return new Cargo(null, null, 0, 1);
    }

    public Cargo[] getAllCargo() {

        final var cargos = new ArrayList<Cargo>();

        String sql = """
                select H1.name as destination, H2.name as source, C.id as id, value
                from Cargo C
                         join seatrade.Harbour H1 on H1.id = C.destination
                         join seatrade.Harbour H2 on H2.id = C.source;
                """;

        try {
            var con = DBConnectionSingleton.getConnection();

            var st = con.prepareStatement(sql);
            st.execute();
            var set = st.getResultSet();

            while (set.next()) {
                cargos.add(new Cargo(new Harbour(0, set.getString("source"), null), new Harbour(0, set.getString("destination"), null), set.getInt("id"), set.getInt("value")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cargos.toArray(new Cargo[0]);
    }
}
