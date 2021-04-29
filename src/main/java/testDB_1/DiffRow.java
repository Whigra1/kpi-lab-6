package testDB_1;

import java.util.List;

class DiffRow {
    List<Difference> differences;
    int row;

    DiffRow(List<Difference> differences, int row) {
        this.differences = differences;
        this.row = row;
    }

    @Override
    public String toString() {
        return "DiffRow{" +
                "differences=" + differences +
                '}';
    }
}

