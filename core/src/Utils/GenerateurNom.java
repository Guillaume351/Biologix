package Utils;

import java.util.Random;

public class GenerateurNom {
    public static String genererNomPropre(Random r) {
        int lenght = 2 + r.nextInt(10);
        String[] consonants = {"b", "c", "d", "d'", "f", "g", "h", "j", "k", "l", "m", "l", "n", "p", "q", "r", "s", "sh", "zh", "t", "v", "w", "x", "ph", "th", "l'", "d'"};
        String[] vowels = {"a", "e", "i", "o", "u", "ae", "y", "yo", "yu", "yi", "ya"};
        String Name = "";
        String first = consonants[r.nextInt(consonants.length)];
        if (first.length() == 1) {
            Name += first.toUpperCase();
        } else {
            Name += (String.valueOf(first.charAt(0))).toUpperCase() + first.charAt(1);
        }
        Name += vowels[r.nextInt(vowels.length)];
        int b = 2;
        while (b < lenght) {
            Name += consonants[r.nextInt(consonants.length)];
            b++;
            Name += vowels[r.nextInt(vowels.length)];
            b++;
        }
        return Name;
    }
}
