package Types;

public record DBDump(
        Company company,
        Ship[] ships,
        Cargo[] cargos,
        Harbour[] harbour
) {
}
