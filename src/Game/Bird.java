package Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Bird {
    // Bird Initial Position

    private final int BirdX = GameManager.GameWidth / 8;
    final int BirdY = GameManager.GameHeight / 2;

    private final int BirdWidth = 34;
    private final int BirdHeight = 24;

    int x = BirdX;
    int y = BirdY;

    int Width = BirdWidth;
    int Height = BirdHeight;

    Image BirdImage;

    int VelocityY = -9;
    int Gravity = 1;

    Bird(Image BirdImage) {
        this.BirdImage = BirdImage;
    }

    public void Flap() {
        VelocityY = -9;
    }

    public void BirdMove() {
        VelocityY += Gravity;
        this.y += VelocityY;

        this.y = Math.max(this.y, 0);

        // If the player goes under the map
        if (this.y > GameManager.GameHeight)
            GameManager.GameEnd = true;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            Flap();
    }
}