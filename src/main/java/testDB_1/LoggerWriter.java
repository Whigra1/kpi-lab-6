package testDB_1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoggerWriter {
    public static void WriteDifferencesToLog(List<DiffRow> diffRowList, String fileName) {
        List<String> diffs = new ArrayList<>();
        int diffCols = 0;
        for (DiffRow diffRow : diffRowList) {
            diffs.add("Got difference on row " + (diffRow.row - 1));
            for (Difference difference : diffRow.differences) {
                diffs.add("\t" + difference.toString());
            }
            diffCols += diffRow.differences.size();
        }
        if (diffCols > 0) {
            diffs.add("Total column difference: " + diffCols);
        }
        try {
            Files.write(Paths.get(fileName), diffs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
