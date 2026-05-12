import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import game.BlackjackGame;

public class Main {

    public static void main(String[] args) {

        Lwjgl3ApplicationConfiguration config =
                new Lwjgl3ApplicationConfiguration();

        config.setTitle("Blackjack Balatro");

        // Risoluzione iniziale
        config.setWindowedMode(1280, 720);

        // Resize dinamico
        config.setResizable(true);

        // FPS e VSync
        config.useVsync(true);
        config.setForegroundFPS(144);

        new Lwjgl3Application(
                new BlackjackGame(),
                config
        );
    }
}