package SeaTrade.DTO.Parsers;

import Types.Harbour;
import SeaTrade.DTO.Response.HarbourDTO;
import SeaTrade.DTO.Response.PositionDTO;

import java.awt.*;

public class HarbourParser {
    public static HarbourDTO parse(Harbour harbour) {
        return new HarbourDTO(harbour.name(), new PositionDTO(harbour.pos().x, harbour.pos().y, "NONE"));
    }

    public static Harbour parseResponse(HarbourDTO harbour) {
        return new Harbour(0, harbour.name(), new Point(harbour.pos().X(), harbour.pos().Y()));
    }

    public static Harbour[] parserResponseArr(HarbourDTO[] harbours) {
        var harbourArr = new Harbour[harbours.length];

        for (int i = 0; i < harbours.length; i++) {
            harbourArr[i] = parseResponse(harbours[i]);
        }

        return harbourArr;
    }
}
