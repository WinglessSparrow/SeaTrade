package Database.ORMapping;

import Types.Harbour;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HarbourMapping {
    public static Harbour mapHarbour(ResultSet result, String[] columns) throws SQLException {

        var cols = (columns != null) ? columns : new String[]{"id", "name", "pos_x", "pos_y"};

        if (cols.length < 4)
            throw new SQLException("Harbour require 4 columns: in the following order: id, name, pos_x, pos_y");

        var pos = new Point(result.getInt(cols[2]), result.getInt(cols[3]));
        return new Harbour(result.getInt(cols[0]), result.getString(cols[1]), pos);
    }

    public static Harbour mapHarbourShallow(ResultSet result, String[] columns) throws SQLException {
        var cols = (columns != null) ? columns : new String[]{"id", "name"};

        if (cols.length < 2)
            throw new SQLException("Shallow Harbour require 2 columns: in the following order: id, name");

        return new Harbour(result.getInt(cols[0]), result.getString(cols[1]), null);
    }

}
