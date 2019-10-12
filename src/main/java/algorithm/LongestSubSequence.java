package algorithm;

import java.util.Arrays;

public class LongestSubSequence {
    //Algorithm is more about how to view the problem


    /**
     * Memoization to optimize the recursion.
     */

    static int[] lookupTable;

    /**
     * The longest sequence is the longest of the previous plus the current or not.We are looking at the
     * later ones first.
     * The problem is find out the longest subsequence in increasing order.The problem is
     * very wierd.
     * (1)convert what u see into variables
     * (2)derive the formula for the algorithm
     * (3)convert the formula "directly" to code.
     */
    static int longestSubSequence(int[] sequence) {
        int[] sequenceCounters = new int[sequence.length];
        Arrays.fill(sequenceCounters, 1);

        //excellent
        for (int i = 1; i < sequence.length; i++) {
            for (int j = 0; j < i; j++) {
                if (sequence[i] > sequence[j]) {
                    sequenceCounters[i] = Math.max(sequenceCounters[i], sequenceCounters[j] + 1);
                }
            }
        }
        return Arrays.stream(sequenceCounters)
                .max().getAsInt();
    }


    //what i did was actually start counting. For each index.I actually recorded
    //all the sequences smaller than the current one.So it's possible that the earlier
    //sequence has bigger count
    public static void main(String[] args) {
        int sequence[] = {7, 8, 9, 10, 1, 2, 3};
        lookupTable = new int[sequence.length];

        longestSubSequence(sequence);

        System.out.println(longestSubSequence(sequence));
    }
}
