package Game;

import java.awt.*;

public class Pipe {
    final int PipeX = GameManager.GameWidth;
    static final int PipeY = 0;

    final int ScaledPipeWidth = 64;
    static final int ScaledPipeHeight = 512;

    int x = PipeX;
    int y = PipeY;
    int Width = ScaledPipeWidth;
    int Height = ScaledPipeHeight;

    Image PipeImage;

    boolean BirdPassed = false;

    // To simulate bird motion in the x-axis, we move the pipe in the negative
    // x-axis

    int VelocityX = -4;

    public Pipe(Image PipeImage) {
        this.PipeImage = PipeImage;
    }

    public void MovePipe() {
        x += VelocityX;
    }
}