package Database;

import Types.Harbour;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBHarbour {
    public void addBulk(Harbour[] harbours) {

    }

    public Harbour[] getAllHarbours() {
        var con = DBConnectionSingleton.getConnection();

        var harbours = new ArrayList<Harbour>();

        try {
            var st = con.prepareStatement("select * from Harbour");
            st.execute();
            var set = st.getResultSet();

            while (set.next()) {
                harbours.add(new Harbour(set.getInt("id"), set.getString("name"), new Point(set.getInt("pos_x"), set.getInt("pos_y"))));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return harbours.toArray(new Harbour[0]);
    }
}
