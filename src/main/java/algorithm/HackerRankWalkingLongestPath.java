package algorithm;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class HackerRankWalkingLongestPath {
    private static final Scanner scanner = new Scanner(System.in);


    /*
    Dynamic Programming:Bottom Up
     */


    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));


        String line = scanner.nextLine();

        int cityCount = Integer.parseInt(line.split(" ")[0]);
        int roadCount = Integer.parseInt(line.split(" ")[1]);


        int[][] cityMap = new int[cityCount][cityCount];

        for (int i = 0; i < cityCount; i++) {
            for (int j = 0; j < cityCount; j++) {
                cityMap[i][j] = 0;
            }
        }

        //construct the city map
        for (int tItr = 0; tItr < roadCount; tItr++) {
            String nm = scanner.nextLine();

            int firstCity = Integer.parseInt(nm.split(" ")[0]);

            int secondCity = Integer.parseInt(nm.split(" ")[1]);

            cityMap[firstCity - 1][secondCity - 1] = 1;
            cityMap[secondCity - 1][firstCity - 1] = 1;
        }

        bufferedWriter.close();

        scanner.close();
    }
}
