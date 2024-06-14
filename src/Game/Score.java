package Game;

import java.awt.*;

public class Score {
    private double GameScore;

    public Score() {
        GameScore = 0;
    }

    public void incrementScore() {
        GameScore += 0.5;
    }

    public double getGameScore() {
        return GameScore;
    }

    public void resetScore() {
        GameScore = 0;
    }

    public void RenderScore(Graphics window, int width, int height) {
        window.setColor(Color.WHITE);
        window.setFont(new Font("Dialog", Font.BOLD, 20));
        window.drawString("" + (int) GameScore, width / 2 - 25, height - 45);
    }
}