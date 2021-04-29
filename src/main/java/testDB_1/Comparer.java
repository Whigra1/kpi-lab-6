package testDB_1;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Comparer {
    public static List<DiffRow> GetDifference(ResultSet resultSet, String fileName, String[] colNames) throws SQLException {
        List<DiffRow> result = new ArrayList<>();
        List<String[]> r;
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            r = reader.readAll();
        } catch (IOException | CsvException e) {
            return null;
        }
        if (r.size() == 0) return null;
        int row = 1;

        while(resultSet.next()) {
            List<Difference> differences = new ArrayList<>();
            String[] fileRow = r.get(row);
            for (int col = 0; col < colNames.length; col++) {
                if (!fileRow[col].equals(resultSet.getString(col + 1))) {
                    differences.add(
                            new Difference(
                                fileRow[col],
                                resultSet.getString(col + 1),
                                colNames[col],
                            row + 1)
                    );
                }

            }
            if (differences.size() > 0) {
                result.add(new DiffRow(differences, row + 1));
            }
            row++;
        }
        return result;
    }
    public static List<DiffRow> GetDifference(String expectedResFile, String migrationFileName, String [] colNames) throws SQLException {
        List<DiffRow> result = new ArrayList<>();
        List<String[]> migrationFileRows, expectedResFileRows;
        try (CSVReader reader = new CSVReader(new FileReader(expectedResFile))) {
            expectedResFileRows = reader.readAll();
        } catch (IOException | CsvException e) {
            return null;
        }
        try (CSVReader reader = new CSVReader(new FileReader(migrationFileName))) {
            migrationFileRows = reader.readAll();
        } catch (IOException | CsvException e) {
            return null;
        }
        if (expectedResFileRows.size() == 0 || migrationFileRows.size() == 0) return null;
        if (expectedResFileRows.size() != migrationFileRows.size()) return null;
        for (int row = 0; row < migrationFileRows.size(); row++) {
            List<Difference> differences = new ArrayList<>();
            String[] migrationFileRow = migrationFileRows.get(row);
            String[] expectedResFileRow = expectedResFileRows.get(row);
            for (int col = 0; col < migrationFileRow.length; col++) {
                if (!expectedResFileRow[col].equals(migrationFileRow[col])) {
                    differences.add(new Difference(expectedResFileRow[col], migrationFileRow[col], colNames[col] ,row + 1));
                }
            }
            if (differences.size() > 0) {
                result.add(new DiffRow(differences, row + 1));
            }
        }
        return result;
    }
}
