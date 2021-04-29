package testDB_1;

class Difference {
    private String expected;
    private String colName;
    private String got;
    private int row;
    public Difference(String expected, String got, String colName, int row){
        this.row = row;
        this.got = got;
        this.expected = expected;
        this.colName = colName;
    }

    public String getExpected() {
        return expected;
    }

    public int getRow() {
        return row;
    }

    public String getGot() {
        return got;
    }

    @Override
    public String toString() {
        return "Difference{" +
                "expected='" + expected + '\'' +
                ", colName='" + colName + '\'' +
                ", got='" + got + '\'' +
                ", row=" + row +
                '}';
    }
}

