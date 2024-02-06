package SeaTrade.DTO.Parsers;

import DTO.CompanyDTO;
import SeaTrade.DTO.Response.SeaTradeResponseDTO;

import java.awt.*;

public class CompanyParser {
    public static CompanyDTO parseResponse(SeaTradeResponseDTO company, String name) {
        return new CompanyDTO(name, company.DEPOSIT(), new Dimension(company.SIZE().WIDTH(), company.SIZE().HEIGHT()), 0);
    }
}
