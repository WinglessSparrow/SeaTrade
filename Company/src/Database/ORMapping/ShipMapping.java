package Database.ORMapping;

import Types.Cargo;
import Types.Direction;
import Types.Harbour;
import Types.Ship;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class ShipMapping {

    public static final int ID = 0, NAME = 1, DIRECTION = 2, POS_X = 3, POS_Y = 4, HARBOUR_ID = 5, HARBOUR_NAME = 6, HARBOUR_Y = 8, CARGO_ID = 9, SRC_Y = 18;
    public static final String DEFAULT_COLUMNS = "id,name,direction,pos_x,pos_y,harbour_id,harbour_name,harbour_x,harbour_y,cargo_id,cargo_value,dest_id,dest_name,dest_x,dest_y,src_id,src_name,src_x,src_y";
    public static final int numCols = DEFAULT_COLUMNS.split(",").length;

    public static Ship mapShip(ResultSet result, String[] columns) throws SQLException {

        var cols = (columns != null) ? columns : DEFAULT_COLUMNS.split(",");

        if (cols.length < numCols)
            throw new SQLException("Harbour require " + numCols + " columns in the following order: " + DEFAULT_COLUMNS);

        Cargo cargo = (result.getString(cols[CARGO_ID]) != null) ? CargoMapping.mapCargo(result, Arrays.copyOfRange(cols, CARGO_ID, SRC_Y + 1)) : null;
        Harbour currHarbour = (result.getString(cols[HARBOUR_NAME]) != null) ? HarbourMapping.mapHarbour(result, Arrays.copyOfRange(cols, HARBOUR_ID, HARBOUR_Y + 1)) : null;

        var pos = new Point(result.getInt(cols[POS_X]), result.getInt(cols[POS_Y]));
        var dir = Direction.valueOf(result.getString(cols[DIRECTION]));

        return new Ship(result.getString(cols[NAME]), result.getInt(cols[ID]), pos, dir, currHarbour, cargo);
    }
}
