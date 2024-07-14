package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class GameManager extends JPanel implements ActionListener {
    // Game Dimensions
    public static int GameWidth = 340;
    public static int GameHeight = 640;

    // Game Assets
    public static Image BackgroundImage, BirdImage, TopPipeImage, BottomPipeImage;
    Bird PlayerBird;
    ArrayList<Pipe> AvailablePipes;

    // Timers
    Timer GameLoop;
    Timer PipeRenderer;

    // Game Events
    public static boolean GameEnd = false;

    // Score
    Score score;

    private GameEndListener listener;

    public GameManager() {
        setPreferredSize(new Dimension(GameWidth, GameHeight));

        IconLoader();
        RunBirdLogic();
        RunPipeLogic();

        score = new Score();

        setFocusable(true);
    }

    private void IconLoader() {
        BackgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Resources/flappybirdbg.png")))
                .getImage();
        BirdImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Resources/flappybird.png")))
                .getImage();
        TopPipeImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Resources/toppipe.png")))
                .getImage();
        BottomPipeImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Resources/bottompipe.png")))
                .getImage();
    }

    private void RunBirdLogic() {
        PlayerBird = new Bird(BirdImage);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                PlayerBird.keyPressed(e);
                super.keyPressed(e);
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                PlayerBird.Flap();
                super.mousePressed(e);
            }
        });
    }

    private void RunPipeLogic() {
        AvailablePipes = new ArrayList<>();
    }

    public void RenderPipes() {
        int RandomPipeY = (int) (Pipe.PipeY - (double) Pipe.ScaledPipeHeight / 4 - Math.random()
                * ((double) Pipe.ScaledPipeHeight / 2));

        int OpeningSpace = GameManager.GameHeight / 4;

        Pipe TopPipe = new Pipe(TopPipeImage);
        TopPipe.y = RandomPipeY;
        AvailablePipes.add(TopPipe);

        Pipe BottomPipe = new Pipe(BottomPipeImage);
        BottomPipe.y = TopPipe.y + Pipe.ScaledPipeHeight + OpeningSpace;
        AvailablePipes.add(BottomPipe);
    }

    public void StartGame() {
        GameEnd = false;
        AvailablePipes.clear();
        PlayerBird.y = PlayerBird.BirdY;
        PlayerBird.VelocityY = -9;
        score.resetScore();

        GameLoop = new Timer(1000 / 60, this);
        PipeRenderer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RenderPipes();
            }
        });

        PipeRenderer.start();
        GameLoop.start();
    }

    public boolean Collision(Bird Player, Pipe pipe) {
        return Player.x < pipe.x + pipe.Width &&
                Player.x + Player.Width > pipe.x &&
                Player.y < pipe.y + pipe.Height &&
                Player.y + Player.Height > pipe.y;
    }

    public void GameMove() {
        PlayerBird.BirdMove();

        for (Pipe pipe : AvailablePipes) {
            pipe.MovePipe();

            if (Collision(PlayerBird, pipe)) {
                GameEnd = true;
            }

            if (!pipe.BirdPassed && pipe.x + pipe.Width < PlayerBird.x) {
                score.incrementScore();
                pipe.BirdPassed = true;
            }
        }

        AvailablePipes.removeIf(pipe -> pipe.x + pipe.Width < 0);
    }

    public void Render(Graphics window) {
        window.drawImage(BackgroundImage, 0, 0, GameWidth, GameHeight, null);
        window.drawImage(PlayerBird.BirdImage, PlayerBird.x, PlayerBird.y, PlayerBird.Width, PlayerBird.Height, null);

        for (Pipe pipe : AvailablePipes) {
            window.drawImage(pipe.PipeImage, pipe.x, pipe.y, pipe.Width, pipe.Height, null);
        }

        score.RenderScore(window, GameWidth, GameHeight);
    }

    public void paintComponent(Graphics window) {
        super.paintComponent(window);
        Render(window);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameMove();
        repaint();

        if (GameEnd) {
            PipeRenderer.stop();
            GameLoop.stop();
            updateHighScore();
            showGameOverDialog();
        }
    }

    private void updateHighScore() {
        int CurrentScore = (int) score.getGameScore();
        int CurrentHighScore = HighScore.getHighScore();
        if (CurrentScore > CurrentHighScore)
            HighScore.setHighScore(CurrentScore);
    }

    private void showGameOverDialog() {
        int PromptOptions = JOptionPane.showConfirmDialog(
                this,
                "Game Over! Your score: " + (int) score.getGameScore() + "\nHigh score: " + HighScore.getHighScore()
                        + "\nDo you want to play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (PromptOptions == JOptionPane.YES_OPTION) {
            StartGame();
        } else {
            if (listener != null)
                listener.onGameEnd();
        }
    }

    public void setGameEndListener(GameEndListener listener) {
        this.listener = listener;
    }

    public interface GameEndListener {
        void onGameEnd();
    }
}
