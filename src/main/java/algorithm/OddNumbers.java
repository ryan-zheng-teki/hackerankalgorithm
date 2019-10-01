package algorithm;

import java.util.ArrayList;
import java.util.List;

public class OddNumbers {

    static List<Integer> oddNumbers(int l,int r) {
        List<Integer> oddNumbers = new ArrayList<>();
        for (int i = l; i < r+1;i++) {
            if(i % 2 != 0) {
                oddNumbers.add(i);
            }
        }
        return oddNumbers;
    }



}
