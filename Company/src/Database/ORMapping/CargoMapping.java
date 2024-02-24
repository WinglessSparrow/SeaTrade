package Database.ORMapping;

import Types.Cargo;
import Types.Harbour;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class CargoMapping {
    public static Cargo mapCargo(ResultSet result, String[] columns) throws SQLException {
        var cols = (columns != null) ? columns : "id, value, dest_id, dest_name, dest_x, dest_y, src_id, src_name, src_x, src_y".split(",");

        if (cols.length < 10)
            throw new SQLException("Cargo require 10 columns: in the following order: id, value, dest_id, dest_name, dest_x, dest_y, src_id, src_name, src_x, src_y");


        var dest = HarbourMapping.mapHarbour(result, Arrays.copyOfRange(cols, 2, 5));
        var src = HarbourMapping.mapHarbour(result, Arrays.copyOfRange(cols, 6, 9));

        return new Cargo(dest, src, result.getInt(cols[0]), result.getInt(cols[1]));
    }

    public static Cargo mapCargoShallow(ResultSet result, String[] columns) throws SQLException {
        var cols = (columns != null) ? columns : "id, value".split(",");

        if (cols.length < 2) throw new SQLException("Cargo require 10 columns: in the following order: id, value");

        return new Cargo(null, null, result.getInt(cols[0]), result.getInt(cols[1]));
    }
}
