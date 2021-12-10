package com.pinzen.puzzle4;

public class Board {

    private String[][] cellValues;
    private Boolean[][] cellValidated;
    public Boolean completed;

    public Board(String[] inputLines) {
        cellValues = new String[5][5];
        cellValidated = new Boolean[5][5];
        this.completed = false;

        int iy = 0;
        for (String line : inputLines) {
            int ix = 0;
            for (String value : line.replace("  ", " ").split(" ")) {
                if (!value.equals("")) {
                    cellValues[iy][ix] = value;
                    cellValidated[iy][ix] = false;
                    ix++;
                }
            }
            iy++;
        }
    }

    private boolean isCompleted() {
        for (int i = 0; i < cellValues.length; i++) {
            boolean line = cellValidated[0][i] && cellValidated[1][i] && cellValidated[2][i] && cellValidated[3][i]
                    && cellValidated[4][i];
            boolean column = cellValidated[i][0] && cellValidated[i][1] && cellValidated[i][2] && cellValidated[i][3]
                    && cellValidated[i][4];
            if (line || column)
                return true;
        }
        return false;
    }

    public boolean addValue(String value) {
        if (this.completed)
            return true;

        boolean found = false;
        for (int iy = 0; iy < cellValues.length; iy++) {
            String[] line = cellValues[iy];
            for (int ix = 0; ix < cellValues.length; ix++) {
                if (line[ix].equals(value)) {
                    cellValidated[iy][ix] = true;
                    found = true;
                }
            }
        }
        this.completed = found && this.isCompleted();
        return this.completed;
    }

    public void print() {
        for (int iy = 0; iy < cellValues.length; iy++) {
            String[] line = cellValues[iy];
            Boolean[] lineValidated = cellValidated[iy];
            String toPrint = "";
            for (int ix = 0; ix < line.length; ix++) {
                String toAdd = lineValidated[ix] ? "X" : line[ix];
                toPrint += toAdd + (toAdd.length() == 2 ? " " : "  ");
            }
            System.out.println(toPrint);
        }
    }

    public int getScore(String currentValue) {
        int sum = 0;
        for (int iy = 0; iy < cellValues.length; iy++) {
            Boolean[] line = cellValidated[iy];
            for (int ix = 0; ix < line.length; ix++) {
                if (!line[ix]) {
                    sum += Integer.parseInt(cellValues[iy][ix]);
                }
            }
        }
        return Integer.parseInt(currentValue) * sum;
    }
}
