package SeaTrade.DTO.Parsers;

import Types.Company;
import SeaTrade.DTO.Response.SeaTradeCargoArrayDTO;

import java.awt.*;

public class CompanyParser {
    public static Company parseResponse(SeaTradeCargoArrayDTO company, String name) {
        return new Company(name, company.DEPOSIT(), new Dimension(company.SIZE().WIDTH(), company.SIZE().HEIGHT()), 0);
    }
}
