package com.blackjack.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import game.GameManager;
import graphics.Renderer;

/** Main LibGDX application shared by all platforms. */
public class BlackJackGame extends ApplicationAdapter {

    private Renderer renderer;
    private GameManager gameManager;

    @Override
    public void create() {
        gameManager = new GameManager();
        gameManager.startDemoGame();

        renderer = new Renderer(gameManager);
    }

    @Override
    public void render() {
        handleInput();
        renderer.render();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            gameManager.playerHit();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            gameManager.playerStand();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            gameManager.startNewRound();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
            gameManager.increasePlayerBet();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            gameManager.decreasePlayerBet();
        }
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
