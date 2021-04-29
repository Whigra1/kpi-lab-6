package testDB_1;

import static org.junit.Assert.*;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class Compare2Csv {
	@Test
	public void equalityTestReadingFromDb() throws SQLException {
		PGConnector pgConnector = new PGConnector("postgres", "postgres", "kpi_lab_6");
		ResultSet resultSet = pgConnector.executeQuery("SELECT * from \"Games\"");
		List<DiffRow> diffRows = Comparer.GetDifference(resultSet, "expectedGames.csv", new String[] { "id", "name", "price" });
		pgConnector.close();
		Assert.assertTrue(diffRows != null && diffRows.size() == 0);
	}
	@Test
	public void equalityTestReading() throws SQLException {
		PGConnector pgConnector = new PGConnector("postgres", "postgres", "kpi_lab_6");
		pgConnector.executeQueryToFile("SELECT * from \"Games\"", "id, name, price", "migration.csv");
		List<DiffRow> diffRows = Comparer.GetDifference("migration.csv", "expectedGames.csv", new String[] { "id", "name", "price" });
		pgConnector.close();
		Assert.assertTrue(diffRows != null && diffRows.size() == 0);
	}
	@Test
	public void DifferenceFile() throws SQLException {
		PGConnector pgConnector = new PGConnector("postgres", "postgres", "kpi_lab_6");
		pgConnector.executeQueryToFile("SELECT * from \"Games\" order by price desc", "id, name, price", "migrationOrderedExpected.csv");
		List<DiffRow> diffRows = Comparer.GetDifference("migrationOrderedExpected.csv", "migrationOrdered.csv", new String[] { "id", "name", "price" });
		pgConnector.close();
		if (diffRows != null)
			LoggerWriter.WriteDifferencesToLog(diffRows, "game.log");
		Assert.assertTrue("Files not equals!", diffRows != null && diffRows.size() == 0);
	}
}
