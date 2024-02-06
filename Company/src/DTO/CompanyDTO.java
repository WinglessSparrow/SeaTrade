package DTO;

import java.awt.*;

public record CompanyDTO(String name, int deposit, Dimension mapSize, int id) {
    public CompanyDTO(CompanyDTO company, int deposit) {
        this(company.name(), deposit, company.mapSize(), company.id());
    }
}
