import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * There is a huge difference on the memory efficiency due to the different ways of arrangement.
 */
public class Solution {
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
    static class CrosswordPlaceHolder {
        protected Position startPosition;
        protected Position endPosition;
        protected int wordIndex;
        protected int length;
        protected boolean rowWord = false;

        public CrosswordPlaceHolder(Position startPosition, Position endPosition,boolean rowWord) {
            this.startPosition = startPosition;
            this.endPosition = endPosition;
            this.rowWord = rowWord;
        }

        public int length() {
            if(rowWord) {
                return endPosition.columnIndex - startPosition.columnIndex +1;
            }
            return endPosition.rowIndex - startPosition.rowIndex +1;
        }
    }


    private static final Scanner scanner = new Scanner(System.in);
    // Complete the crosswordPuzzle function below.
    private static String[] crosswordPuzzle(String[] crossword, String words,String seperator) {
        String[] individualWords = words.split(seperator);
        List<String> wordIndexesPermutations = getIndexPermutationsFrom(getWordIndexString(individualWords.length));

        int rowCount = crossword.length;
        int columnCount = crossword[0].length();

        List<CrosswordPlaceHolder> rowwordsPlaceHolders = new ArrayList<>();
        List<CrosswordPlaceHolder> columnwordsPlaceHolders = new ArrayList<>();

        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++)
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {

                if (isBeginningOfRowwordPlaceHolder(rowIndex, columnIndex, crossword, columnCount)) {
                    Position startPosition = new Position(rowIndex, columnIndex);

                    Position endPosition = findRowwordPlaceHolderEndPosition(startPosition, crossword, columnCount);
                    CrosswordPlaceHolder rowword = new CrosswordPlaceHolder(startPosition, endPosition,true);
                    rowwordsPlaceHolders.add(rowword);
                    // set the columnIndex to endPosition columnIndex to speed up the search
                    columnIndex = endPosition.columnIndex;
                }

                if (isBeginningOfColumnwordPlaceHolder(rowIndex, columnIndex, crossword, rowCount)) {
                    Position startPosition = new Position(rowIndex, columnIndex);
                    Position endPosition = findColumnwordPlaceHolderEndPosition(startPosition, crossword, rowCount);
                    CrosswordPlaceHolder columnWord = new CrosswordPlaceHolder(startPosition, endPosition,false);
                    columnwordsPlaceHolders.add(columnWord);
                }
            }

        return crossword;
    }
    
    private static void generateAnswer(List<CrosswordPlaceHolder> remainingRowwordsPlaceHolders,List<CrosswordPlaceHolder> remainingColumnwordsPlaceHolders,
                    List<CrosswordPlaceHolder> currentRowPlaceHolders,List<CrosswordPlaceHolder> currentColumnwordsPlaceHolders,String[] availableWords,int[] usedWordsIndexes) {
        if (remainingRowwordsPlaceHolders.size() == 0) {
            remain
            
        }
    }
    
    private static void fillWordsBackToBoard(String[] crossword,List<CrosswordPlaceHolder> rowwordsPlaceHolders,
                                             List<CrosswordPlaceHolder> columnwordsPlaceHolders) {
        for (int i = 0; i < rowwordsPlaceHolders.size(); i++) {
            Position startPosition = rowwordsPlaceHolders.get(i).startPosition;
            Position endPosition = rowwordsPlaceHolders.get(i).endPosition;

            for(int j=startPosition.columnIndex ; j < endPosition.columnIndex ; j++) {
                //crossword[startPosition.rowIndex]
            }
        }
    }
    /* Check if this word permutation is valid for the board */
    private static boolean isWordIndexPermutationValid(String wordIndexPermutation,String[] words,
                                                List<CrosswordPlaceHolder> rowCrosswords,List<CrosswordPlaceHolder> columnCrosswords) {
        int rowCrosswordsCount = rowCrosswords.size();
        int columnCrosswordsCount = columnCrosswords.size();

        for (int i = 0; i < rowCrosswordsCount; i++) {
            rowCrosswords.get(i).wordIndex = Integer.valueOf(wordIndexPermutation.charAt(i));
        }

        for (int i = 0; i < columnCrosswordsCount; i++) {
            columnCrosswords.get(i).wordIndex = Integer.valueOf(wordIndexPermutation.charAt(i+rowCrosswordsCount));
        }

        for (int i = 0; i < rowCrosswordsCount; i++) {
            CrosswordPlaceHolder rowWord = rowCrosswords.get(i);
            if(rowWord.length() != words[rowWord.wordIndex].length()) {
                return false;
            }
            for (int j = 0; j < columnCrosswordsCount; j++) {
                CrosswordPlaceHolder columnWord = columnCrosswords.get(j);
                if(columnWord.length() != words[columnWord.wordIndex].length()) {
                    return false;
                }
                Position crosssection = findPlaceHolderCrossSection(rowWord, columnWord);
                if(crosssection != null) {
                    if(!isSameCharForCrossSection(rowWord, columnWord, crosssection,words)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean isSameCharForCrossSection(final CrosswordPlaceHolder rowwordPlaceHolder
            , final CrosswordPlaceHolder columnWordPlaceHolder,Position crosssection,String[] words) {
        char rowwordSectionChar = words[rowwordPlaceHolder.wordIndex].charAt(crosssection.columnIndex - rowwordPlaceHolder.startPosition.columnIndex);
        char columnworldeSectionChar = words[columnWordPlaceHolder.wordIndex].charAt(crosssection.rowIndex - columnWordPlaceHolder.startPosition.rowIndex);

        if (rowwordSectionChar == columnworldeSectionChar) {
            return true;
        }
        return false;
    }


    private static String getWordIndexString(int total) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < total; i++) {
            sb.append(i);
        }
        return sb.toString();
    }

    //permutation is acutally very common statistics problem. How to program permutation
    private static List<String> getIndexPermutationsFrom(String characters) {
        //end condition

        if(characters.length() == 1) {
            return Collections.singletonList(characters);
        }

        List<String> allPermutations = new ArrayList<>();

        for (int i = 0; i < characters.length(); i++) {
            StringBuilder remainingCharacters = new StringBuilder();

            remainingCharacters.append(characters.substring(0,i));
            remainingCharacters.append(characters.substring(i+1,characters.length()));

            List<String> subPermutations = getIndexPermutationsFrom(remainingCharacters.toString());

            for (String permutation : subPermutations) {
                StringBuilder result = new StringBuilder();
                result.append(characters.charAt(i));
                result.append(permutation);
                allPermutations.add(result.toString());
            }
        }
        return allPermutations;
    }

    /**
     * Find the possible crosssection position of rowword and columnword.
     * @param rowword
     * @param columnword
     * @return null or Position
     */
    private static Position findPlaceHolderCrossSection(CrosswordPlaceHolder rowword, CrosswordPlaceHolder columnword) {
        if (columnword.startPosition.rowIndex > rowword.startPosition.rowIndex ||
                columnword.endPosition.rowIndex < rowword.startPosition.rowIndex) {
            return null;
        }
        return new Position(rowword.startPosition.rowIndex,columnword.endPosition.columnIndex);
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
        if ((columnIndex == 0 || crossword[rowIndex].charAt(columnIndex - 1) != '-')
                && (columnIndex + 1) != columnCount) {
            if (crossword[rowIndex].charAt(columnIndex) == '-' && crossword[rowIndex].charAt(columnIndex + 1) == '-') {
                return true;
            } else {
                return false;
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
        if ((rowIndex == 0 || crossword[rowIndex - 1].charAt(columnIndex) != '-') && (rowIndex + 1) != rowCount) {
            if (crossword[rowIndex].charAt(columnIndex) == '-' && crossword[rowIndex + 1].charAt(columnIndex) == '-') {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] crossword = new String[10];

        for (int i = 0; i < 10; i++) {
            String crosswordItem = scanner.nextLine();
            crossword[i] = crosswordItem;
        }

        String words = scanner.nextLine();
        String[] result = crosswordPuzzle(crossword, words,"#");

        for (int i = 0; i < result.length; i++) {
            bufferedWriter.write(result[i]);

            if (i != result.length - 1) {
                bufferedWriter.write("\n");
            }
        }
        bufferedWriter.newLine();
        bufferedWriter.close();

        scanner.close();
    }
}
