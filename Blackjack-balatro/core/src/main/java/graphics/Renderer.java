package graphics;

import ai.BotDecision;
import cards.Card;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import game.GameManager;
import players.BotPlayer;
import players.Player;

public class Renderer {

    public static final float VIRTUAL_WIDTH = 1280;
    public static final float VIRTUAL_HEIGHT = 720;

    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private final OrthographicCamera camera;
    private final ExtendViewport viewport;
    private final BitmapFont font;
    private final GlyphLayout layout;

    private GameManager gameManager;

    public Renderer() {
        this(null);
    }

    public Renderer(GameManager gameManager) {
        this.gameManager = gameManager;

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera();

        viewport = new ExtendViewport(
            VIRTUAL_WIDTH,
            VIRTUAL_HEIGHT,
            camera
        );

        viewport.apply();

        camera.position.set(
            VIRTUAL_WIDTH / 2f,
            VIRTUAL_HEIGHT / 2f,
            0
        );

        camera.update();

        font = new BitmapFont();
        font.getData().setScale(1.25f);

        layout = new GlyphLayout();
    }

    public void render() {
        Gdx.gl.glClearColor(0.05f, 0.04f, 0.08f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);

        drawBackground();
        drawTable();
        drawGameData();
    }

    private void drawBackground() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(new Color(0.06f, 0.04f, 0.10f, 1f));
        shapeRenderer.rect(
            0,
            0,
            viewport.getWorldWidth(),
            viewport.getWorldHeight()
        );

        shapeRenderer.end();
    }

    private void drawTable() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(new Color(0.05f, 0.30f, 0.18f, 1f));
        shapeRenderer.rect(
            120,
            120,
            VIRTUAL_WIDTH - 240,
            VIRTUAL_HEIGHT - 220
        );

        shapeRenderer.setColor(new Color(0.10f, 0.65f, 0.35f, 1f));
        shapeRenderer.rect(
            130,
            130,
            VIRTUAL_WIDTH - 260,
            VIRTUAL_HEIGHT - 240
        );

        shapeRenderer.end();
    }

    private void drawGameData() {
        batch.begin();

        font.setColor(Color.WHITE);

        drawCenteredText(
            "BLACKJACK BALATRO",
            VIRTUAL_WIDTH / 2f,
            640
        );

        if (gameManager == null) {
            drawCenteredText(
                "GameManager non collegato",
                VIRTUAL_WIDTH / 2f,
                330
            );

            batch.end();
            return;
        }

        font.setColor(new Color(0.75f, 0.90f, 1f, 1f));
        font.draw(
            batch,
            "State: " + gameManager.getGameState(),
            220,
            615
        );

        drawDealer();
        drawPlayers();

        batch.end();
    }

    private void drawDealer() {
        Player dealer = gameManager.getDealer();

        font.setColor(Color.WHITE);
        font.draw(
            batch,
            dealer.getName()
                + " | Value: " + dealer.calculateHandValue()
                + " | Bust: " + dealer.isBust(),
            220,
            570
        );

        font.setColor(new Color(0.95f, 0.75f, 0.75f, 1f));

        StringBuilder dealerHandText = new StringBuilder();

        for (Card card : dealer.getHand()) {
            dealerHandText.append(card.toString()).append("   ");
        }

        font.draw(
            batch,
            dealerHandText.toString(),
            260,
            535
        );
    }

    private void drawPlayers() {
        float startY = 430;
        float rowSpacing = 120;

        for (int i = 0; i < gameManager.getPlayers().size(); i++) {
            Player player = gameManager.getPlayers().get(i);

            float y = startY - (i * rowSpacing);

            font.setColor(Color.WHITE);

            String playerInfo =
                player.getName()
                    + " | Chips: " + player.getChips()
                    + " | Bet: " + player.getCurrentBet()
                    + " | Value: " + player.calculateHandValue()
                    + " | Standing: " + player.isStanding()
                    + " | Bust: " + player.isBust();

            if (player instanceof BotPlayer) {
                BotPlayer botPlayer = (BotPlayer) player;
                BotDecision decision = botPlayer.makeDecision();

                playerInfo += " | AI: " + decision;
                playerInfo += " | Count: " + botPlayer.getCounter().getRunningCount();
            }

            String result = gameManager.getResultFor(player);

            if (!result.equals("")) {
                playerInfo += " | Result: " + result;
            }

            font.draw(
                batch,
                playerInfo,
                220,
                y
            );

            font.setColor(new Color(0.95f, 0.90f, 0.70f, 1f));

            StringBuilder handText = new StringBuilder();

            for (Card card : player.getHand()) {
                handText.append(card.toString()).append("   ");
            }

            font.draw(
                batch,
                handText.toString(),
                260,
                y - 35
            );
        }
    }

    private void drawCenteredText(String text, float centerX, float y) {
        layout.setText(font, text);

        font.draw(
            batch,
            text,
            centerX - layout.width / 2f,
            y
        );
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }
}
