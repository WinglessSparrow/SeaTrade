package SeaTrade.DTO.Parsers;

import DTO.HarbourDTO;
import SeaTrade.DTO.Response.HarbourResponseDTO;
import SeaTrade.DTO.Response.PositionDTO;

import java.awt.*;

public class HarbourParser {
    public static HarbourResponseDTO parse(HarbourDTO harbour) {
        return new HarbourResponseDTO(harbour.name(), new PositionDTO(harbour.pos().x, harbour.pos().y));
    }

    public static HarbourDTO parseResponse(HarbourResponseDTO harbour) {
        return new HarbourDTO(0, harbour.name(), new Point(harbour.pos().X(), harbour.pos().Y()));
    }

    public static HarbourDTO[] parserResponseArr(HarbourResponseDTO[] harbours) {
        var harbourArr = new HarbourDTO[harbours.length];

        for (int i = 0; i < harbours.length; i++) {
            harbourArr[i] = parseResponse(harbours[i]);
        }

        return harbourArr;
    }
}
