package com.max.compiler.scanner;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ScannerTest {


    @Test
    public void generateTokens() throws IOException {

        try (InputStream inStream = ScannerTest.class.getResourceAsStream("/program.txt");
             InputStreamReader inReader = new InputStreamReader(inStream);
             BufferedReader reader = new BufferedReader(inReader)) {

            List<String> actualTokens = readAllTokens(reader);

            List<String> expectedTokens = Arrays.asList(
                    "int", "x", "=", "10", ";",
                    "int", "y", "=", "20", ";",
                    "int", "z", "=", "0", ";",
                    "if", "(", "x", ">=", "y", ")", "{",
                    "z", "=", "x", ";", "}",
                    "else", "{",
                    "z", "=", "y", ";",
                    "}",
                    "sout", "(", "z", ")", ";"
            );

            assertEquals(expectedTokens, actualTokens);
        }
    }

    private static List<String> readAllTokens(BufferedReader reader) throws IOException {
        final Scanner scanner = new Scanner(reader);

        List<String> tokens = new ArrayList<>();

        for (String token = scanner.next(); token != null; token = scanner.next()) {
            tokens.add(token);
        }

        return tokens;
    }


}
