import java.util.*;

public class AffineCipher {

    public static final int KEY_A = 5;
    public static final int KEY_B = 8;
    public static final int NUMBER_OF_LETTER = 26;
    public static final List<Character> LETTER_FREQUENCY = Arrays.asList('E','T','A','O','I','N','S','H','R','D'
                            ,'L','C','U','M','W','F','G','Y','P','B','V','K','J','X','Q','Z');

    public static void main(String[] args) {

        StringBuilder plainText = new StringBuilder("one way to solve an encrypted message, if we know its language, is to find a different plaintext of the same language long enough to fill one sheet or so, and then we count the occurrences of each letter. we call the most frequently occurring letter the 'first', the next most occurring letter the 'second' the following most occurring letter the 'third', and so on, until we account for all the different letters in the plaintext sample. then we look at the cipher text we want to solve and we also classify its symbols. we find the most occurring symbol and change it to the form of the 'first' letter of the plaintext sample, the next most common symbol is changed to the form of the 'second' letter, and the following most common symbol is changed to the form of the 'third' letter, and so on, until we account for all symbols of the cryptogram we want to solve");
        test(plainText);

        plainText = new StringBuilder("Professor Fabio Di Troia");
        test(plainText);

        plainText = new StringBuilder("In cryptanalysis, frequency analysis (also known as counting letters) is the study of the frequency of letters or groups of letters in a ciphertext. The method is used as an aid to breaking classical ciphers.");
        test(plainText);

        plainText = new StringBuilder("i ate eat toe");
        test(plainText);

    }

    public static void test(StringBuilder plainText){
        StringBuilder cipherText = encryption(plainText);
        System.out.println("\nENCRYPTION");
        System.out.println("Plain Text: " + plainText);
        System.out.println("Cipher Text: " + cipherText);

        System.out.println("\nDECRYPTION");
        System.out.println("Cipher Text: " + cipherText);
        System.out.println("Plain Text: " + decryption(cipherText));
    }

    /**
     * Transform each of the letters in the plaintext to cipher text using Affine Cipher
     * @param plaintext
     * @return ciphertext
     */
    public static StringBuilder encryption(StringBuilder plaintext){
        StringBuilder cipherText = new StringBuilder();
        for (Character p : plaintext.toString().toUpperCase().toCharArray()){
            if (Character.isLetter(p)) {
                char cipherChar = (char) ((KEY_A * (p - 'A') + KEY_B) % NUMBER_OF_LETTER + 'A');
                cipherText.append(cipherChar);
            }else cipherText.append(p);
        }
        return cipherText;
    }

    /**
     * Transform each of the letters in the ciphertext to plaintext using Affine Cipher
     * @param cipherText
     * @return
     */
    public static String decryption(StringBuilder cipherText){
        HashMap<Character, Integer> cipherCount = new HashMap<Character, Integer>();
        populateMap(cipherCount);

        for (Character c : cipherText.toString().toUpperCase().toCharArray()){
            if (Character.isLetter(c)) {
                if(cipherCount.containsKey(c)) {
                    int newCount = cipherCount.get(c) + 1;
                    cipherCount.put(c, newCount);
                }
            } else cipherText.append(c);
        }

        cipherCount = sortHasHMap(cipherCount);
        System.out.println("Letter Frequency: ");
        iterate(cipherCount);

        List<Character> sortedCipher = new ArrayList<Character>(cipherCount.keySet());
        String plainText = "";
        String old = cipherText.toString();
        for (int i = 0; i < 26; i++) {
            String oldLetter = String.valueOf(sortedCipher.get(i));
            String newLetter = String.valueOf(LETTER_FREQUENCY.get(i));
            plainText = old.replaceAll(oldLetter, newLetter.toLowerCase());
            old = plainText;
        }
        return plainText.toUpperCase();
    }

    /**
     * Sort the each letter (key) based on its frequency counter (value) in the HashMap using Comparator
     * @param unsortMap
     * @return sortedMap
     */
    private static HashMap<Character, Integer> sortHasHMap(HashMap<Character, Integer> unsortMap)
    {
        List<Map.Entry<Character, Integer>> list = new LinkedList<Map.Entry<Character, Integer>>(unsortMap.entrySet());

        // Sorting the list based on letter frequency of each character
        Collections.sort(list, new Comparator<Map.Entry<Character, Integer>>()
        {
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2)
            {
                    return o2.getValue().compareTo(o1.getValue()); //Sort in descending order
            }
        });

        //Repopulate new sorted HashMap
        HashMap<Character, Integer> sortedHashMap = new LinkedHashMap<Character, Integer>();
        for (Map.Entry<Character, Integer> element : list)
        {
            sortedHashMap.put(element.getKey(), element.getValue());
        }
        return sortedHashMap;
    }

    /**
     * Populate a hashmap with 26 key (from A to Z)
     * @param cipherCount
     */
    public static void populateMap(HashMap<Character, Integer> cipherCount){
        for (int i = 0; i < 26; i++){
            cipherCount.put((char) (65+i), 0);
        }
    }

    /**
     * Iterate through HashMap to display key and value
     * @param cipherCount
     */
    public static void iterate(HashMap<Character, Integer> cipherCount) {
        Iterator it = cipherCount.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.print(pair.getKey() + " = " + pair.getValue() +", ");
        }
        System.out.print("\n");
    }

}
