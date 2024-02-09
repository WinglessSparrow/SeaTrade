package SeaTrade.DTO.Parsers;

import Types.Harbour;
import SeaTrade.DTO.Response.HarbourResponseDTO;
import SeaTrade.DTO.Response.PositionDTO;

import java.awt.*;

public class HarbourParser {
    public static HarbourResponseDTO parse(Harbour harbour) {
        return new HarbourResponseDTO(harbour.name(), new PositionDTO(harbour.pos().x, harbour.pos().y));
    }

    public static Harbour parseResponse(HarbourResponseDTO harbour) {
        return new Harbour(0, harbour.name(), new Point(harbour.pos().X(), harbour.pos().Y()));
    }

    public static Harbour[] parserResponseArr(HarbourResponseDTO[] harbours) {
        var harbourArr = new Harbour[harbours.length];

        for (int i = 0; i < harbours.length; i++) {
            harbourArr[i] = parseResponse(harbours[i]);
        }

        return harbourArr;
    }
}
