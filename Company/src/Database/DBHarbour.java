package Database;

import Database.ORMapping.HarbourMapping;
import Types.Harbour;

import java.sql.SQLException;
import java.util.ArrayList;

public class DBHarbour {
    public void addBulk(Harbour[] harbours) {

        String sql = "insert into Harbour (name, pos_x, pos_y) values (?, ?, ?)";

        var con = DBConnectionSingleton.getConnection();

        try {
            con.setAutoCommit(false);

            var st = con.prepareStatement(sql);

            for (var harbour : harbours) {
                st.setString(1, harbour.name());
                st.setInt(2, harbour.pos().x);
                st.setInt(2, harbour.pos().y);
            }

            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Harbour[] getAllHarbours() {
        var con = DBConnectionSingleton.getConnection();

        var harbours = new ArrayList<Harbour>();

        try {
            var st = con.prepareStatement("select * from Harbour");
            st.execute();
            var set = st.getResultSet();

            while (set.next()) {
                harbours.add(HarbourMapping.mapHarbour(set, null));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return harbours.toArray(new Harbour[0]);
    }
}
