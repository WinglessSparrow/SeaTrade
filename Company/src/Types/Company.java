package Types;

import java.awt.*;

public record Company(String name, int deposit, Dimension mapSize, int id) {
    public Company(Company company, int deposit) {
        this(company.name(), deposit, company.mapSize(), company.id());
    }
}
