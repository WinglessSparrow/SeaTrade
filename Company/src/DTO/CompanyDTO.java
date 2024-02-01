package DTO;

public record CompanyDTO(String name, int deposit, int mapSize, int id) {
    public CompanyDTO(CompanyDTO company, int deposit) {
        this(company.name(), deposit, company.mapSize(), company.id());
    }
}
