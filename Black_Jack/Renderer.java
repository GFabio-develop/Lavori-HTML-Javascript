package graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Renderer {

    public static final float VIRTUAL_WIDTH = 1280;
    public static final float VIRTUAL_HEIGHT = 720;

    private final SpriteBatch batch;

    private final OrthographicCamera camera;
    private final ExtendViewport viewport;

    private final Texture tableTexture;
    private final Texture cardTexture;

    private final BitmapFont pixelFont;

    private final Stage stage;

    public Renderer() {

        batch = new SpriteBatch();

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

        tableTexture = new Texture("table/table_green.png");
        cardTexture = new Texture("cards/card_back.png");

        // Pixel perfect filter
        tableTexture.setFilter(
                Texture.TextureFilter.Nearest,
                Texture.TextureFilter.Nearest
        );

        cardTexture.setFilter(
                Texture.TextureFilter.Nearest,
                Texture.TextureFilter.Nearest
        );

        pixelFont = new BitmapFont(
                Gdx.files.internal("fonts/pixel_font.fnt")
        );

        stage = new Stage(viewport, batch);

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = pixelFont;
        style.fontColor = Color.WHITE;

        Label title = new Label(
                "BLACKJACK BALATRO",
                style
        );
}