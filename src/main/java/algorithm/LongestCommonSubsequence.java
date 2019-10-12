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
}
