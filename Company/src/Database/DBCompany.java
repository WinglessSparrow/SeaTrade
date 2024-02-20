package Database;

import Types.Company;

import java.awt.*;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCompany {
    public void update(Company ship) {
    }

    public void add(Company ship) {

    }

    public void delete(Company ship) {

    }

    public Company get() {
        Company company = null;

        try {
            var con = DBConnectionSingleton.getConnection();

            var st = con.prepareStatement("SELECT * from Company");
            st.execute();
            var set = st.getResultSet();
            set.next();

            company = new Company(set.getString("name"), set.getInt("deposit"), new Dimension(set.getInt("map_width"), set.getInt("map_height")), set.getInt("id"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return company;
    }
}
