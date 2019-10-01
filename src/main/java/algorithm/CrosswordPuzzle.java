package tutorials;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

/**
 * The solution has three steps
 * 1. Find all those crossword placeholders and put them in an array
 * 2. Sort those placeholders by length in ascending order. Sort words in ascending order.
 * Then placeHolders and words length are aligned
 * 3.Fill the words back into the crosswords recursively and check constraint
 */
public class CrosswordPuzzle {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        String[] crossword = new String[10];

        for (int i = 0; i < 10; i++) {
            String crosswordItem = scanner.nextLine();
            crossword[i] = crosswordItem;
        }

        String words = scanner.nextLine();
        crossword = crosswordPuzzle(crossword, words);

        for (int i = 0; i < crossword.length; i++) {
            bufferedWriter.write(crossword[i]);
            bufferedWriter.newLine();
        }

        scanner.close();
        bufferedWriter.close();
    }

    // Complete the crosswordPuzzle function below.
    private static String[] crosswordPuzzle(String[] crossword, String words) {
        //Convert the crossword into a matrix
        char[][] corsswordCharMatrix = convertToCharMatrix(crossword);
        List<String> splitedWords = Arrays.asList(words.split(";"));

        int rowCount = crossword.length;
        int columnCount = crossword[0].length();

        List<CrosswordPlaceHolder> crosswordPlaceHolders = new ArrayList<>();

        //Step1:pull out all the crosswords placeholders
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {

                if (isBeginningOfRowwordPlaceHolder(rowIndex, columnIndex, crossword, columnCount)) {
                    Position startPosition = new Position(rowIndex, columnIndex);

                    Position endPosition = findRowwordPlaceHolderEndPosition(startPosition, crossword, columnCount);
                    CrosswordPlaceHolder rowword = new CrosswordPlaceHolder(startPosition, endPosition, true);
                    crosswordPlaceHolders.add(rowword);
                }

                if (isBeginningOfColumnwordPlaceHolder(rowIndex, columnIndex, crossword, rowCount)) {
                    Position startPosition = new Position(rowIndex, columnIndex);
                    Position endPosition = findColumnwordPlaceHolderEndPosition(startPosition, crossword, rowCount);
                    CrosswordPlaceHolder columnWord = new CrosswordPlaceHolder(startPosition, endPosition, false);
                    crosswordPlaceHolders.add(columnWord);
                }
            }
        }

        //Step2:sort both words and placeholders in ascending order by length.
        Collections.sort(crosswordPlaceHolders);
        Collections.sort(splitedWords,(first,second) -> first.length() - second.length());

        //Step3:
        List<char[][]> finalResult  = new ArrayList<>();
        generateAnswer(crosswordPlaceHolders,splitedWords,corsswordCharMatrix,finalResult);

        return convertBackToStringArray(finalResult.get(0));
    }

    /**
     * Clones the provided array
     *
     * @param src
     * @return a new clone of the provided array
     */
    private static char[][] cloneArray(char[][] src) {
        int length = src.length;
        char[][] target = new char[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }

    private static String[] convertBackToStringArray(char[][] crossword) {
        String[] result = new String[crossword.length];
        int rowCount = crossword.length;
        int columnCount = crossword[0].length;

        for (int i = 0; i < rowCount; i++) {
                result[i] = new String(crossword[i]);
        }
        return result;
    }
    private static char[][] convertToCharMatrix(String[] crossword) {
        int rowCount = crossword.length;
        int columnCount = crossword[0].length();
        char[][] crosswordBoard = new char[crossword.length][crossword[0].length()];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                crosswordBoard[i][j] = crossword[i].charAt(j);
            }
        }
        return crosswordBoard;
    }

    private static void generateAnswer(List<CrosswordPlaceHolder> currentPlaceHolders, List<String> currentWords, char[][] crossword,List<char[][]> finalResult) {
        //This is the correct case
        if (currentWords.size() == 0 && currentPlaceHolders.size() == 0) {
            finalResult.add(crossword);
            for(int k = 0; k < crossword.length; k++) {
                for (int l = 0; l < crossword[k].length; l++) {
                    //System.out.print(crossword[k][l]);
                }
                //System.out.println();
            }
            return ;
        }

        for (int i = 0; i < currentWords.size(); i++) {
            List<CrosswordPlaceHolder> tempPlaceHolders = new ArrayList<CrosswordPlaceHolder>(currentPlaceHolders.subList(1,currentPlaceHolders.size()));
            char[][] copiedCrossword = cloneArray(crossword);
            CrosswordPlaceHolder currentPlaceHolder = currentPlaceHolders.get(0);

            if(currentWords.get(i).length() != currentPlaceHolder.length())
            {
                return;
            }

            boolean isWordFitInPlaceHolder = true;
            int currentRowIndex = currentPlaceHolder.startPosition.rowIndex;
            int currentColumnIndex = currentPlaceHolder.startPosition.columnIndex;

            for (int j = 0; j < currentPlaceHolder.length(); j++) {

                //check the constraint.
                if (copiedCrossword[currentRowIndex][currentColumnIndex] == '-'
                        || copiedCrossword[currentRowIndex][currentColumnIndex] == currentWords.get(i).charAt(j)) {
                    copiedCrossword[currentRowIndex][currentColumnIndex] = currentWords.get(i).charAt(j);
                } else {
                    //when cross section character do not match
                    isWordFitInPlaceHolder = false;
                    break;
                }

                if (currentPlaceHolder.isHorizontal) {
                    currentColumnIndex++;
                } else {
                    currentRowIndex++;
                }
            }
            if (isWordFitInPlaceHolder) {
                List<String> tempWords = new ArrayList<>();
                tempWords.addAll(currentWords.subList(0, i));
                tempWords.addAll(currentWords.subList(i+1,currentWords.size()));
                generateAnswer(tempPlaceHolders, tempWords, copiedCrossword,finalResult);
            }
        }
    }

    private static Position findRowwordPlaceHolderEndPosition(Position startPosition, String[] crossword, int columnCount) {
        // until the end of the column or the end of the -
        Position endPosition = new Position();
        int rowIndex = startPosition.rowIndex;
        int columnIndex = startPosition.columnIndex;

        while (columnIndex < columnCount && crossword[rowIndex].charAt(columnIndex) == '-') {
            columnIndex++;
        }
        endPosition.rowIndex = rowIndex;
        endPosition.columnIndex = columnIndex - 1;
        return endPosition;
    }

    private static boolean isBeginningOfRowwordPlaceHolder(int rowIndex, int columnIndex, String[] crossword, int columnCount) {

        if (columnIndex == 0 || crossword[rowIndex].charAt(columnIndex-1) != '-') {
            if (columnIndex+1 < columnCount && crossword[rowIndex].charAt(columnIndex) == '-' && crossword[rowIndex].charAt(columnIndex + 1) == '-') {
                return true;
            }
        }
        return false;
    }

    private static Position findColumnwordPlaceHolderEndPosition(Position startPosition, String[] crossword, int rowCount) {
        // until the end of the column or the end of the -
        Position endPosition = new Position();
        int rowIndex = startPosition.rowIndex;
        int columnIndex = startPosition.columnIndex;

        while (rowIndex < rowCount && crossword[rowIndex].charAt(columnIndex) == '-') {
            rowIndex++;
        }

        endPosition.rowIndex = rowIndex - 1;
        endPosition.columnIndex = columnIndex;
        return endPosition;
    }

    private static boolean isBeginningOfColumnwordPlaceHolder(int rowIndex, int columnIndex, String[] crossword, int rowCount) {
        if (rowIndex == 0 || crossword[rowIndex-1].charAt(columnIndex) != '-') {
            if (rowIndex + 1 < rowCount && crossword[rowIndex].charAt(columnIndex) == '-' && crossword[rowIndex + 1].charAt(columnIndex) == '-') {
                return true;
            }
        }
        return false;
    }


    /**
     * Represent the point in the crossword
     */
    static class Position {
        protected int rowIndex;
        protected int columnIndex;

        public Position(int rowIndex, int columnIndex) {
            this.rowIndex = rowIndex;
            this.columnIndex = columnIndex;
        }
        public Position() {}

    }
    /**
     * Represent a crossword placeholder. The placeHolder contains the startPosition
     * and endPosition. And the final index of word that are put in the placeholder
     */
    static class CrosswordPlaceHolder implements Comparable<CrosswordPlaceHolder> {
        protected Position startPosition;
        protected Position endPosition;
        protected int wordIndex;
        protected boolean isHorizontal = false;


        public CrosswordPlaceHolder(Position startPosition, Position endPosition, boolean isHorizontal) {
            this.startPosition = startPosition;
            this.endPosition = endPosition;
            this.isHorizontal = isHorizontal;
        }

        public int length() {
            if (isHorizontal) {
                return endPosition.columnIndex - startPosition.columnIndex + 1;
            }
            return endPosition.rowIndex - startPosition.rowIndex + 1;
        }

        //used to sort the placeholder by length
        public int compareTo(CrosswordPlaceHolder another) {
            return this.length() - another.length();
        }

    }
}
