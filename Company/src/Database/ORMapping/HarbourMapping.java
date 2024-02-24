package Database.ORMapping;

import Types.Harbour;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HarbourMapping {

    public static final int ID = 0, NAME = 1, POS_X = 2, POS_Y = 3;
    public static final String DEFAULT_COLUMNS = "id,name,pos_x,pos_y";
    public static final int numCols = DEFAULT_COLUMNS.split(",").length;

    public static Harbour mapHarbour(ResultSet result, String[] columns) throws SQLException {

        var cols = (columns != null) ? columns : DEFAULT_COLUMNS.split(",");

        if (cols.length < numCols)
            throw new SQLException("Harbour require " + numCols + " columns: in the following order: " + DEFAULT_COLUMNS);

        var pos = new Point(result.getInt(cols[POS_X]), result.getInt(cols[POS_Y]));
        return new Harbour(result.getInt(cols[ID]), result.getString(cols[NAME]), pos);
    }

}
