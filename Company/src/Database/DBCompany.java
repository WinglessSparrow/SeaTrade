package Database;

import Types.Company;

import java.awt.*;
import java.sql.SQLException;

public class DBCompany {
    public void update(Company company) {
        String sql = "update company set deposit = ?, map_height = ?, map_width = ?, name = ? where id = ?";

        var con = DBConnectionSingleton.getConnection();

        try {
            var st = con.prepareStatement(sql);

            st.setInt(1, company.deposit());
            st.setInt(2, company.mapSize().height);
            st.setInt(3, company.mapSize().width);
            st.setString(4, company.name());
            st.setInt(5, company.id());

            st.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Company company) {
        String sql = "insert into company (name, deposit, map_height, map_width) values(?, ?, ?, ?)";
        var con = DBConnectionSingleton.getConnection();

        try {
            var st = con.prepareStatement(sql);

            st.setString(1, company.name());
            st.setInt(2, company.deposit());
            st.setInt(3, company.mapSize().height);
            st.setInt(4, company.mapSize().width);

            st.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Company get() {
        Company company;

        var con = DBConnectionSingleton.getConnection();

        try {

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
