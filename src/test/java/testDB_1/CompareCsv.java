package testDB_1;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompareCsv {
	@Test
	public void equalityTestReadingFromDb() throws SQLException {
		PGConnector pgConnector = new PGConnector("postgres", "postgres", "kpi_lab_6");
		ResultSet resultSet = pgConnector.executeQuery("SELECT * from \"Company\"");
		List<DiffRow> diffRows = Comparer.GetDifference(resultSet, "expectedCompany.csv", new String[] { "id", "name", "employeescount" });
		pgConnector.close();
		Assert.assertTrue(diffRows != null && diffRows.size() == 0);
	}
	@Test
	public void equalityTestReading() throws SQLException {
		PGConnector pgConnector = new PGConnector("postgres", "postgres", "kpi_lab_6");
		pgConnector.executeQueryToFile("SELECT * from \"Company\"", "id, name, employeesnumber", "migrationCompany.csv");
		List<DiffRow> diffRows = Comparer.GetDifference("expectedCompany.csv", "migrationCompany.csv", new String[] { "id", "name", "employeescount" });
		pgConnector.close();
		Assert.assertTrue(diffRows != null && diffRows.size() == 0);
	}
	@Test
	public void DifferenceFile() throws SQLException {
		PGConnector pgConnector = new PGConnector("postgres", "postgres", "kpi_lab_6");
		pgConnector.executeQueryToFile("SELECT count(id) from \"Company\" group by employeesnumber", "groupBy", "groupByCompanyMigration.csv");
		List<DiffRow> diffRows = Comparer.GetDifference("groupByCompanyMigrationExpected.csv", "groupByCompanyMigration.csv", new String[] { "id", "name", "employeescount" });
		pgConnector.close();
		if (diffRows != null)
			LoggerWriter.WriteDifferencesToLog(diffRows, "company.log");
		Assert.assertTrue("Files not equals!", diffRows != null && diffRows.size() == 0);
	}
}
