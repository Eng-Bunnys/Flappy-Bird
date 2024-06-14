package Game;

import java.io.*;

public class HighScore {
    // The .dat file that will store the user scores
    private static final String HIGH_SCORE_FILE = "highscore.dat";

    public static int getHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGH_SCORE_FILE))) {
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            return 0; // Return 0 if the file doesn't exist or there's an error
        }
    }

    public static void setHighScore(int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIGH_SCORE_FILE))) {
            writer.write(String.valueOf(score));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}