package Types;

import Types.Cargo;
import Types.Company;
import Types.Harbour;
import Types.Ship;

public record DBDump(
        Company company,
        Ship[] ships,
        Cargo[] cargos,
        Harbour[] harbour
) {
}
