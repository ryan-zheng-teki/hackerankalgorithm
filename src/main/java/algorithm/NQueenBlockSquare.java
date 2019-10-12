package algorithm;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class NQueenBlockSquare {
    /**
     * What improvements can be made
     * 1.replace all the equal or not equal operations with bitwise operations
     * 2.replace all the temp variables with cache.
     * 3.reset cache with bitwise operators.
     * 4.Use the bit position to represent the row and column.
     * 5.Compared with memory usage,performance is favored in autonomous driving.
     * <p>
     * <p>
     * <p>
     * 1.M is less than 5,so can be definitely replaced by 1 integer
     * 2.N is less than 50.So can be replaced by one integer as well with 64 bits
     * <p>
     * <p>
     * Refactor version 1:
     * after removing those temporary arrays,test case 0 and test case 1 passed.
     * <p>
     * Refactor version 2:
     * Use bit map to represent row and column position.Use bitmap as index into original
     * board.
     * <p>
     * Refactor version 3:
     * convert recursion into iterations.Recursion always have a iterative variable.And also
     * use memoization to store the already computed configurations.For example find the possible
     * row configurations.
     */

    private static final Scanner scanner = new Scanner(System.in);
    private static int numberOfConfigs;
    private static int[] currentCandidateRowIndexes;
    private static int[] currentRowConfig;
    private static int boardRowCount;
    private static int boardColumnCount;
    private static int[][] previousColumnPlacements;

    static void initialize(String[] board) {
        boardRowCount = board.length;
        boardColumnCount = board[0].length();
        currentCandidateRowIndexes = new int[boardRowCount];
        Arrays.fill(currentCandidateRowIndexes, -1);

        currentRowConfig = new int[boardRowCount];
        Arrays.fill(currentRowConfig, -1);


        //initialize takes time.Do i have to initialize this.Can i take advantage of
        //the default value.
        previousColumnPlacements = new int[boardColumnCount][boardRowCount];
        for (int i = 0; i < boardColumnCount; i++) {
            for (int j = 0; j < boardRowCount; j++) {
                previousColumnPlacements[i][j] = -1;
            }
        }
    }

    /*
     * Complete the queensBoard function below.
     */
    static int queensBoard(String[] board) {
        /*
         * Write your code here.
         */

        initialize(board);

        numberOfConfigs = 0;
        generateAnswer(previousColumnPlacements, 0, board, 0);
        return numberOfConfigs;
    }

    /**
     * Memory wise and operation wise.
     * The target is to print the final board.
     * Previous columns array  contains the row indexes for previous columns.The first
     * index contains the column index. The second index contains the selected rows.
     * We initialize all the row indexes to be -1.Then
     * currentColumn contains the column index of the current column to process.
     *
     * @param previousColumnPlacements
     * @param currentColumn
     * @return
     */
    static void generateAnswer(int[][] previousColumnPlacements, int currentColumn, String[] board, int queensPlaced) {
        int rowCount = board.length;
        int boardColumnCount = board[0].length();

        if (currentColumn == boardColumnCount) {
            //print all the previous column row indexes.PreviousColumnPlacements
            if (queensPlaced != 0) {
                numberOfConfigs++;
            }
            return;
        }


        int validRowCount = 0;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            boolean isValidPlace = true;

            //if the current position at (rowIndex,currentColumn) is #.Then it's not compared.
            if (board[rowIndex].charAt(currentColumn) == '#') {
                continue;
            }

            //check constraint with previous placements
            for (int previousColumnIndex = 0; previousColumnIndex < currentColumn; previousColumnIndex++) {
                int previousColumnRowIndex = 0;
                //we have to check against
                while (true) {
                    int previousColumnRowChoice = previousColumnPlacements[previousColumnIndex][previousColumnRowIndex];
                    if (previousColumnRowChoice == -1) {
                        break;
                    }
                    if (rowIndex == previousColumnRowChoice) {

                        //check all the positions between the previous column and current column
                        int tempColumnIndex = previousColumnIndex + 1;
                        while (tempColumnIndex < currentColumn) {
                            if (board[rowIndex].charAt(tempColumnIndex) != '#') {
                                tempColumnIndex++;
                                continue;
                            } else {
                                break;
                            }
                        }
                        if (tempColumnIndex == currentColumn) {
                            isValidPlace = false;
                        }

                    }
                    //on diagonal.the distance of x coordinate and y coordinates are the same
                    if (Math.abs(rowIndex - previousColumnRowChoice) == Math.abs(currentColumn - previousColumnIndex)) {
                        //check if on the diagonal,there is #,then valid
                        int tempColumnIndex = previousColumnIndex + 1;
                        int tempPreviousRowIndex = previousColumnRowChoice;
                        int count = 1;
                        while (tempColumnIndex < currentColumn) {
                            int diagonalRowIndex = -1;
                            //the current position is below the previous placement.
                            if (rowIndex < tempPreviousRowIndex) {
                                diagonalRowIndex = tempPreviousRowIndex - count;
                            } else {
                                diagonalRowIndex = tempPreviousRowIndex + count;
                            }

                            if (board[diagonalRowIndex].charAt(tempColumnIndex) != '#') {
                                tempColumnIndex++;
                                count++;
                            } else {
                                break;
                            }
                        }
                        if (tempColumnIndex == currentColumn) {
                            isValidPlace = false;
                        }
                    }
                    if (isValidPlace) {
                        previousColumnRowIndex++;
                    } else {
                        break;
                    }
                }
            }
            if (isValidPlace) {
                currentCandidateRowIndexes[validRowCount] = rowIndex;
                validRowCount++;
            }
        }

        List<int[]> result = new ArrayList<>();
        //validRows contains all the valid rows.We have to get all the valid permutations of these rows.
        getValidRowConfigurations(currentRowConfig, currentCandidateRowIndexes, currentColumn, 0, 0, validRowCount, board, result);
        Arrays.fill(currentCandidateRowIndexes, -1);//reset the values since they are not useful anymore
        Arrays.fill(currentRowConfig, -1);

        for (int[] rowConfig : result) {
            int index = 0;
            while (rowConfig[index] != -1) {
                previousColumnPlacements[currentColumn][index] = rowConfig[index];
                index++;
            }
            generateAnswer(previousColumnPlacements, currentColumn + 1, board, queensPlaced + index);
            Arrays.fill(previousColumnPlacements[currentColumn], 0, rowCount, -1);
        }

    }

    //We can divide the column into segments divided by symbol #.For each segments
    //we can select one position.This is exactly what we see.Convert what we see into
    //programs.
    private static void getValidRowConfigurations(int[] rowConfigs, int[] validRows, int columnIndex, int currentRowSelection, int selectedRowCount, int validRowCount, String[] board, List<int[]> result) {
        if (currentRowSelection == validRowCount) {
            result.add(cloneArray(rowConfigs));
            return;
        }

        int count = 0;
        boolean currentRowValid = true;

        while (count < selectedRowCount) {
            boolean isValidSelection = false;
            //check constraint against
            int rowIndexToCheck = rowConfigs[count] + 1;

            //check constraint
            while (rowIndexToCheck < validRows[currentRowSelection]) {
                if (board[rowIndexToCheck].charAt(columnIndex) == '#') {
                    isValidSelection = true;
                    break;
                }
                rowIndexToCheck++;
            }

            if (isValidSelection) {
                count++;
            } else {
                currentRowValid = false;
                break;
            }
        }
        if (currentRowValid) {
            rowConfigs[count] = validRows[currentRowSelection];
            getValidRowConfigurations(rowConfigs, validRows, columnIndex, currentRowSelection + 1, selectedRowCount + 1, validRowCount, board, result);
            //restore the changes
            Arrays.fill(rowConfigs, selectedRowCount, validRowCount, -1);
        }
        //we also need to consider the option that the current row is not selected at all
        getValidRowConfigurations(rowConfigs, validRows, columnIndex, currentRowSelection + 1, selectedRowCount, validRowCount, board, result);
        //restore the changes
        Arrays.fill(rowConfigs, selectedRowCount, validRowCount, -1);
    }

    private static int[] cloneArray(int[] source) {
        int length = source.length;
        int[] newArray = new int[length];
        for (int i = 0; i < length; i++) {
            newArray[i] = source[i];
        }
        return newArray;
    }

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = scanner.nextInt();
        scanner.nextLine();

        for (int tItr = 0; tItr < t; tItr++) {
            String nm = scanner.nextLine();

            int n = Integer.parseInt(nm.split(" ")[0]);

            int m = Integer.parseInt(nm.split(" ")[1]);

            String[] board = new String[n];

            for (int boardItr = 0; boardItr < n; boardItr++) {
                String boardItem = scanner.nextLine();
                board[boardItr] = boardItem;
            }

            int result = queensBoard(board);

            bufferedWriter.write(String.valueOf(result));
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }

}
