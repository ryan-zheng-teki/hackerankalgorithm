package algorithm;

public class LongestCommonSubsequence {


    static int[][] commonSequenceCache;

    public static void main(String[] args) {
        String text1 = "abcdelreipgfsgrtpsgpoipofsdgf", text2 = "acoptripovfsgpipdsafdasfdre";
        commonSequenceCache = new int[text2.length()][text1.length()];
        for (int i = 0; i < text2.length(); i++) {
            for (int j = 0; j < text1.length(); j++) {
                commonSequenceCache[i][j] = -1;
            }
        }

        //recursion
        StopWatch sw = new StopWatch();
        for (int i = 0; i < text2.length(); i++) {
            longestCommonSequenceStartingWithFirstIndex(text2, i, text1, 0);
        }
        sw.getElapsedTime();


        StopWatch sw2 = new StopWatch();
        for (int i = 0; i < text2.length(); i++) {
            longestCommonSequenceMemoization(text2, i, text1, 0);
        }
        sw.getElapsedTime();

    }


    /*Meoization
     */
    static int longestCommonSequenceStartingWithFirstIndex(String firstString, int firstIndex, String secondString, int secondIndex) {
        if (firstIndex == firstString.length() || secondIndex == secondString.length()) {
            return 0;
        }

        int commonSequenceCount = 0;
        for (int i = secondIndex; i < secondString.length(); i++) {
            if (firstString.charAt(firstIndex) == secondString.charAt(i)) {
                for (int j = firstIndex + 1; j < firstString.length(); j++) {
                    //use the max of the seb common sequence.
                    //Find all the largest common subsequences starting of firstString starting with firstIndex.
                    //the commonSequence is not correct.
                    commonSequenceCount = Math.max(commonSequenceCount, longestCommonSequenceStartingWithFirstIndex(firstString, j, secondString, i + 1));
                }
                return commonSequenceCount + 1;
            }
        }
        return 0;
    }


    //optimization memoization.
    /*Meoization
     */
    static int longestCommonSequenceMemoization(String firstString, int firstIndex, String secondString, int secondIndex) {
        if (firstIndex == firstString.length() || secondIndex == secondString.length()) {
            return 0;
        }

        int commonSequenceCount = 0;
        for (int i = secondIndex; i < secondString.length(); i++) {
            if (firstString.charAt(firstIndex) == secondString.charAt(i)) {
                for (int j = firstIndex + 1; j < firstString.length(); j++) {
                    //use the max of the seb common sequence.
                    //Find all the largest common subsequences starting of firstString starting with firstIndex.
                    //the commonSequence is not correct.
                    if (commonSequenceCache[j][i + 1] != -1) {
                        commonSequenceCount = Math.max(commonSequenceCount, commonSequenceCache[j][i + 1]);
                    } else {
                        commonSequenceCount = Math.max(commonSequenceCount, longestCommonSequenceStartingWithFirstIndex(firstString, j, secondString, i + 1));
                    }
                }
                return commonSequenceCount + 1;
            }
        }
        return 0;
    }

    //optimization tabularization
    static int longestCommonSequenceTabularization(String firstString, int firstIndex, String secondString, int secondIndex) {
        for (int i = firstString.length() - 1; i >= 0; i--) {
            for (int j = secondString.length() - 1; j >= 0; j--) {
                if (j == secondString.length() && i == firstString.length()) {
                    if (firstString.charAt(i) == secondString.charAt(j)) {
                        commonSequenceCache[j][i] = 1;
                    } else {
                        commonSequenceCache[j][i] = 0;
                    }
                } else {
                    commonSequenceCache[j][i] = commonSequenceCache[j + 1][i];
                }
            }

        }
        return 0;
    }

    //a simple algorithm all beginns with the way of thinking. So strange.
    static int lcs(String firstString, int firstIndex, String secondString, int secondIndex) {
        if (firstIndex == 0 || secondIndex == 0) {
            return 0;
        }

        if (firstString.charAt(firstIndex - 1) == secondString.charAt(secondIndex - 1)) {
            return 1 + lcs(firstString, firstIndex - 1, secondString, secondIndex - 1);
        }

        return Math.max(lcs(firstString, firstIndex - 1, secondString, secondIndex)
                , lcs(firstString, firstIndex, secondString, secondIndex - 1));
    }

    //tabularization
    //tabularization is like the direct translation of the recursion.The bottom up
    //approach is very interesting.
    static int lcsTabularization(String firstString, String secondString) {
        int firstSize = firstString.length();
        int secondSize = secondString.length();

        int[][] lookupTable = new int[firstSize + 1][secondSize + 1];

        for (int i = 0; i <= firstSize; i++) {
            for (int j = 0; j <= secondSize; j++) {
                if (i == 0 || j == 0) {
                    lookupTable[i][j] = 0;
                }
                if (firstString.charAt(i - 1) == secondString.charAt(j - 1)) {
                    lookupTable[i][j] = 1 + lookupTable[i - 1][j - 1];
                } else {
                    lookupTable[i][j] = Math.max(lookupTable[i][j - 1], lookupTable[i - 1][j]);
                }

            }
        }
        return lookupTable[firstSize][secondSize];
    }
}
