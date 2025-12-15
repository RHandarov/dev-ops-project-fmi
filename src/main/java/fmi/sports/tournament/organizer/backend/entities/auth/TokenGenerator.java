package fmi.sports.tournament.organizer.backend.entities.auth;

import java.util.Random;

public class TokenGenerator {
    private static final Random generator = new Random();

    public static String generateRandomToken(int tokenLength) {
        StringBuilder token = new StringBuilder(tokenLength);

        Random generator = new Random();
        for (int i = 0; i < tokenLength; ++i) {
            token.append(generateNextSymbol());
        }

        return token.toString();
    }

    private static char generateNextSymbol() {
        boolean isDigit = generator.nextBoolean();
        if (isDigit) {
            return generateRandomDigit();
        }

        return generateRandomSmallLetter();
    }

    private static char generateRandomDigit() {
        return (char)(generator.nextInt(9) + '0');
    }

    private static char generateRandomSmallLetter() {
        return (char)(generator.nextInt(26) + 'a');
    }
}
