package Database.ORMapping;

import Types.Harbour;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class HarbourMapping {

    public static int ID = 0, NAME = 1, POS_X = 2, POS_Y = 3;
    public static String DEFAULT_COLUMNS = "id, name, pos_x, pos_y";

    public static int numCols = DEFAULT_COLUMNS.split(",").length;

    public static Harbour mapHarbour(ResultSet result, String[] columns) throws SQLException {

        var cols = (columns != null) ? columns : DEFAULT_COLUMNS.split(",");

        if (cols.length < numCols)
            throw new SQLException("Harbour require " + numCols + " columns: in the following order: " + DEFAULT_COLUMNS);

        var pos = new Point(result.getInt(cols[POS_X]), result.getInt(cols[POS_Y]));
        return new Harbour(result.getInt(cols[ID]), result.getString(cols[NAME]), pos);
    }

    public static Harbour mapHarbourShallow(ResultSet result, String[] columns) throws SQLException {
        var cols = (columns != null) ? columns : Arrays.copyOfRange(DEFAULT_COLUMNS.split(","), ID, NAME);

        if (cols.length < 2)
            throw new SQLException("Shallow Harbour require 2 columns: in the following order: id, name");

        return new Harbour(result.getInt(cols[0]), result.getString(cols[1]), null);
    }

}
