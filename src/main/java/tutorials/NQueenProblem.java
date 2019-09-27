package tutorials;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NQueenProblem {

    public static void main(String[] args) {
        List<int[]> list = new ArrayList<>();
        int[] activePath = new int[5];
        Arrays.fill(activePath, -1);
        generateAnswer(activePath,0,5,5);
    }


    /**
     * This solution is pretty good.Backtrack must send the previous result down to
     * the tree.Otherwise there is no way to track.
     * previousRowsTaken are the row indexes of the queens already put.The size of
     * the array equal to the size of the matrix.
     * @param previousColumns
     * @param currentColumn the current column to process
     * @return
     */
    public static void generateAnswer(int[] previousColumns, int currentColumn, int totalRows, int totalColumns) {

        if(currentColumn == totalColumns) {
            StringBuilder sb = new StringBuilder();
            for(int k = 0; k < previousColumns.length ; k++) {
                sb.append(previousColumns[k]);
            }
            System.out.println(sb.toString());
        }
        for (int i = 0; i < totalRows; i++) {
            boolean fitConstraints = true;

            //check constraint
            for (int j = 0; j < currentColumn; j++) {
                if(previousColumns[j] == i) {
                    fitConstraints = false;
                    break;
                }
            }
            if(fitConstraints) {
                previousColumns[currentColumn] = i;
                generateAnswer(previousColumns,currentColumn+1,totalRows,totalColumns);
            }
        }
    }

}
