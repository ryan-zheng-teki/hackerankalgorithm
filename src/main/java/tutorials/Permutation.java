package tutorials;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Permutation {
    public static void main(String[] args) {
        String input = "abc";

        System.out.println(getPermutationsFrom(input));
    }

    //permutation is acutally very common statistics problem. How to program permutation
    public static List<String> getPermutationsFrom(String characters) {
        //end condition

        if(characters.length() == 1) {
            return Collections.singletonList(characters);
        }

        List<String> allPermutations = new ArrayList<>();

        for (int i = 0; i < characters.length(); i++) {
            StringBuilder remainingCharacters = new StringBuilder();

            remainingCharacters.append(characters.substring(0,i));
            remainingCharacters.append(characters.substring(i+1,characters.length()));

            List<String> subPermutations = getPermutationsFrom(remainingCharacters.toString());

            for (String permutation : subPermutations) {
                StringBuilder result = new StringBuilder();
                result.append(characters.charAt(i));
                result.append(permutation);
                allPermutations.add(result.toString());
            }
        }

        return allPermutations;
    }

}

