package me.vlobanov.mustache;

public class Scanner {
    public static final String TAGS = "#^>!/{&?";

    String template;
    int idx;
    int currLine;

    public Scanner(String template) {
        this.template = template;
        idx = 0;
        currLine = 1;
    }

    public boolean eos() { // end of string
        return idx == template.length() - 1;
    }

    private void addNewLines(String s) {
        for( int i=0; i<s.length(); i++ ) {
            if( s.charAt(i) == '\n' ) {
                currLine++;
            }
        }
    }

    public String scanUtil(String patten) {
        int pos = template.indexOf(patten, idx);
        if (pos == -1) {
            String match = template.substring(idx);
            idx = template.length() - 1;
            addNewLines(match);
            return match;
        } else if (pos == 0) {
            idx += patten.length();
            return null;
        } else {
            String match = template.substring(idx, pos);
            idx = pos + patten.length();
            addNewLines(match);
            return match;
        }
    }

    public void skipeWhiteSpace() {
        while (Character.isWhitespace(next()) && !eos()) {
            ++idx;
        }
    }

    private char next() {
        return template.charAt(idx);
    }

    public void pushBack(int count) {
        idx -= count;
    }

    public int getCurrentLine() {
        return currLine;
    }

    public char nextType() {
        skipeWhiteSpace();
        if (!eos()) {
            char c = next();
            if (TAGS.indexOf(c) != -1) {
                idx += 1;
                if (c == Token.TRUE) {
                    System.err.println("WARN: use nonstandard ?");
                }
                return c;
            } else {
                return Token.NAME;
            }
        }
        return 0;
    }
}
