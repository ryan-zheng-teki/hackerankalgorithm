package tutorials;

import java.io.*;
import java.util.Scanner;

public class HackerRankInputOutput {
        public static void main(String args[] ) throws Exception {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line = br.readLine();
            int N = Integer.parseInt(line);
            for (int i = 0; i < N; i++) {
                System.out.println("hello world");
            }
        }


        public static void readOneInteger() {
            Scanner scanner = new Scanner(System.in);
            String myString = scanner.next();
            int myInt = scanner.nextInt();
            scanner.close();

            System.out.println("myString is: " + myString);
            System.out.println("myInt is: " + myInt);
        }


        public static void readMultipleIntegers() throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

            Integer l = Integer.parseInt(bufferedReader.readLine().trim());
            Integer r = Integer.parseInt(bufferedReader.readLine().trim());

        }
}
