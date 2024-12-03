public class Parseur {
    private String input; // CHAINE POUR TESTER
    private int position; //POSITION DE CHAINE

    public Parseur(String input) {
        this.input = input;
        this.position = 0;
    }

    private char currentChar() {
        if (position < input.length()) {
            return input.charAt(position);
        }
        return '\0';
    }

    private boolean consume(char expected) {
        if (currentChar() == expected) {
            position++;
            return true;
        }
        return false;
    }

    public boolean parseS() {
        int startPosition = position;

        //  S → bSb
        if (consume('b')) {
            if (parseS() && consume('b')) {
                return true;
            }
            position = startPosition;
        }

        //  S → cAc
        if (consume('c')) {
            if (parseA() && consume('c')) {
                return true;
            }
            position = startPosition;
        }

        return false;
    }

    public boolean parseA() {
        int startPosition = position;

        //  A → bAA
        if (consume('b')) {
            if (parseA() && parseA()) {
                return true; 
            }
            position = startPosition;
        }

        //  A → cASAb
        if (consume('c')) {
            if (parseA() && parseS() && parseA() && consume('b')) {
                return true;
            }
            position = startPosition;
        }

        //  A → dcb
        if (consume('d') && consume('c') && consume('b')) {
            return true;
        }

        return false;
    }

    public boolean parse() {
        return parseS() && position == input.length();
    }

    public static void main(String[] args) {
        String[] testStrings = {
                "cdcbc", "bcdcbcb", "cbdcbdcbc", "ccdcbcdcbcdcbbcr", "cdcbbb", "cdcb", ""
        };

        for (String test : testStrings) {
            Parseur parser = new Parseur(test);
            boolean result = parser.parse();
            System.out.println("Chaîne : \"" + test + "\" -> " + (result ? "Accepte" : "accepte pas"));
        }
    }
}