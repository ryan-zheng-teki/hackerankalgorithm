package tutorials;

import java.util.List;

public class StringSubMatch {
    public static String findMatch(List<String> possibleMatches, String crossword) {
        // Write your code here
        for (int i = 0; i < possibleMatches.size(); i++) {
            if(possibleMatches.get(i).length()!= crossword.length()) {
                continue;
            }
            boolean isValidWord = true;
            for (int j = 0; j < crossword.length(); j++) {
                if (crossword.charAt(j) == '.' || theSameChar(crossword.charAt(j),possibleMatches.get(i).charAt(j)))
                {
                    continue;
                } else {
                    isValidWord = false;
                    break;
                }
            }
            if(isValidWord) {
                return possibleMatches.get(i).toLowerCase();
            }
        }
        return "";
    }

    private static  boolean theSameChar(char src, char target) {
        return Character.toLowerCase(src) == Character.toLowerCase(target);
    }
}
