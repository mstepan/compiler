package com.max.compiler.scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

final class Scanner implements Iterator<String> {

    private static final Set<String> KEYWORDS = new HashSet<>();

    private static final char EMPTY_CHAR = '\0';

    static {
        // control flows
        KEYWORDS.add("if");
        KEYWORDS.add("else");
        KEYWORDS.add("while");
        KEYWORDS.add("for");

        // types
        KEYWORDS.add("long");
        KEYWORDS.add("int");
        KEYWORDS.add("short");
        KEYWORDS.add("char");
        KEYWORDS.add("byte");
        KEYWORDS.add("double");
        KEYWORDS.add("float");
    }

    private final BufferedReader reader;

    private char charVal = EMPTY_CHAR;

    Scanner(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public String next() {
        try {
            if (charVal == EMPTY_CHAR) {

                int intVal = reader.read();

                if (intVal == -1) {
                    return null;
                }

                charVal = (char) intVal;
            }

            if (Character.isLetter(charVal)) {
                return readTillLetterOrDigit();
            }
            else if (Character.isDigit(charVal)) {
                return readTillDigit();
            }
            else if (charVal == ';' || charVal == '(' || charVal == ')' || charVal == '{' || charVal == '}') {
                String token = String.valueOf(charVal);
                charVal = EMPTY_CHAR;
                return token;
            }
            else if (charVal == '>' || charVal == '<' || charVal == '=') {

                StringBuilder token = new StringBuilder(2);
                token.append(charVal);

                int intValue = reader.read();

                if (intValue == -1) {
                    return token.toString();
                }

                charVal = (char) intValue;

                if (charVal == '=') {
                    token.append("=");
                }

                charVal = EMPTY_CHAR;

                return token.toString();

            }
            else if (isSkipCharacter(charVal)) {
                return skipAndReadNextToken();
            }
        }
        catch (IOException ioEx) {
            throw new IllegalStateException(ioEx);
        }

        return null;
    }

    private String readTillLetterOrDigit() throws IOException {
        StringBuilder token = new StringBuilder(10);
        token.append(charVal);

        for (int intValue = reader.read(); intValue != -1; intValue = reader.read()) {

            charVal = (char) intValue;

            if (Character.isLetter(charVal) || Character.isDigit(charVal)) {
                token.append(charVal);
            }
            else {
                break;
            }
        }

//                if (KEYWORDS.contains(token.toString())) {
//                    token.append("[keyword]");
//                }

        return token.toString();
    }

    private String readTillDigit() throws IOException {
        StringBuilder token = new StringBuilder(10);
        token.append(charVal);

        for (int intValue = reader.read(); intValue != -1; intValue = reader.read()) {
            charVal = (char) intValue;

            if (Character.isDigit(charVal)) {
                token.append(charVal);
            }
            else {
                break;
            }
        }

        return token.toString();
    }

    private String skipAndReadNextToken() throws IOException {
        for (int intValue = reader.read(); intValue != -1; intValue = reader.read()) {
            charVal = (char) intValue;

            if (!isSkipCharacter(charVal)) {
                return next();
            }
        }

        return null;
    }

    private static boolean isSkipCharacter(char ch) {
        return ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n';
    }
}
