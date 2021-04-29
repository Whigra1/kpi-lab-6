package testDB_1;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PGConnector {
    Connection con;
    public PGConnector(String user, String pass, String dbName) throws SQLException {
        con= DriverManager.getConnection("jdbc:postgresql://localhost/" + dbName +"?user=" + user + "&password=" + pass);
    }
    public Connection getConnection () {
        return con;
    }

    public Statement createStatement() {
        try {
            return con.createStatement();
        } catch (java.sql.SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet executeQuery (String sql) {
        try {
            return createStatement().executeQuery(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public void executeQueryToFile (String sql, String header, String fileName) {
        List<String> result = new ArrayList<>();
        result.add(header);
        int colNum = header.split(",").length;
        try {
            ResultSet resultSet = executeQuery(sql);
            while (resultSet.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= colNum; i++) {
                    row.append(resultSet.getString(i)).append(",");
                }
                result.add(row.toString());
            }
            Path file = Paths.get(fileName);
            Files.write(file, result);
        } catch (SQLException | IOException e) {
            System.out.println(e);
        }
    }

    public void close () {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
