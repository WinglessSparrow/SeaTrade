package Database.ORMapping;

import Types.Cargo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class CargoMapping {

    public static final int ID = 0, VALUE = 1, DEST_ID = 2, DEST_Y = 5, SRC_ID = 6, SRC_Y = 9;
    public static String DEFAULT_COLUMNS = "id,value,dest_id,dest_name,dest_x,dest_y,src_id,src_name,src_x,src_y";

    public static int numCols = DEFAULT_COLUMNS.split(",").length;

    public static Cargo mapCargo(ResultSet result, String[] columns) throws SQLException {
        var cols = (columns != null) ? columns : DEFAULT_COLUMNS.split(",");

        if (cols.length < numCols)
            throw new SQLException("Cargo require " + numCols + " columns: in the following order: " + DEFAULT_COLUMNS);


        var dest = HarbourMapping.mapHarbour(result, Arrays.copyOfRange(cols, DEST_ID, DEST_Y + 1));
        var src = HarbourMapping.mapHarbour(result, Arrays.copyOfRange(cols, SRC_ID, SRC_Y + 1));

        return new Cargo(dest, src, result.getInt(cols[ID]), result.getInt(cols[VALUE]));
    }
}
