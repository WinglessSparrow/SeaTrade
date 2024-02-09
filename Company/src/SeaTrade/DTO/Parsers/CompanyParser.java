package SeaTrade.DTO.Parsers;

import Types.Company;
import SeaTrade.DTO.Response.SeaTradeResponseDTO;

import java.awt.*;

public class CompanyParser {
    public static Company parseResponse(SeaTradeResponseDTO company, String name) {
        return new Company(name, company.DEPOSIT(), new Dimension(company.SIZE().WIDTH(), company.SIZE().HEIGHT()), 0);
    }
}
