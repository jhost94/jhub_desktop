package center.jhub.desktop.framework.layout;

import java.util.ArrayList;
import java.util.Arrays;
import lombok.Getter;

@Getter
public class TableLayoutHelper {
    private static final double[] DEFAULT_COLS = {
        5,
        250,
        5,
        TableLayout.FILL,
        5
    };
    private static final double[] DEFAULT_ROWS = {
        2,
        15,
        2,
        15,
        2,
        15,
        2,
        15,
        2,
        15,
        2,
        15,
        2
    };

    private final Integer[] emptyCols;
    private final Integer[] contentCols;

    private final double[] cols;
    private final double[] rows;


    public TableLayoutHelper() {
        this(DEFAULT_COLS, DEFAULT_ROWS);
    }

    public TableLayoutHelper(double [] col, double [] row) {
        // Check parameters
        if (col == null)
            throw new IllegalArgumentException("Parameter col cannot be null");

        if (row == null)
            throw new IllegalArgumentException("Parameter row cannot be null");

        cols = new double[col.length];
        rows = new double[row.length];

        System.arraycopy(col, 0, cols, 0, col.length);
        System.arraycopy(row, 0, rows, 0, row.length);

        ArrayList<Integer> eC = new ArrayList<>();
        ArrayList<Integer> cC = new ArrayList<>();
        for (int i = 0; i < col.length; i++) {
            double c = col[i];
            if (c < 15
                    && c != TableLayout.FILL
                    && c != TableLayout.PREFERRED
                    && c != TableLayout.MINIMUM) {
                eC.add(i);
            } else {
                cC.add(i);
            }
        }

        emptyCols = eC.toArray(new Integer[0]);
        contentCols = cC.toArray(new Integer[0]);
    }

    public String restraintForContent(int contentCol, int row) {
        String seperator = ",";

        if (contentCol > contentCols.length) {
            throw new RuntimeException("Content col: " + contentCol + " does not exit. \nExisting content cols: " + Arrays.toString(contentCols));
        }

        return contentCols[contentCol] + seperator + row;
    }
}
