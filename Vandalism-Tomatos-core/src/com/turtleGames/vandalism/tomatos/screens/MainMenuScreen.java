package com.turtleGames.vandalism.tomatos.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.turtleGames.vandalism.tomatos.Tomatos;
import com.turtleGames.vandalism.tomatos.entities.Background;

public class MainMenuScreen implements Screen {

	Tomatos game;

	OrthographicCamera guiCam;
	Rectangle playBounds;
	Rectangle highScoreBounds;
	Rectangle helpBounds;
	Rectangle quitBounds;
	Vector3 touchPoint;
	SpriteBatch spriteBatcher;

	Texture playButtonTex;
	Texture helpButtonTex;
	Texture highScoreButtonTex;
	Texture quitButtonTex;
	Texture titleTex;
	Background background;

	Texture pixelTarget2;

	int state;

	public MainMenuScreen(Tomatos game) {
		this.game = game;

		guiCam = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		guiCam.position.set(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, 0);

		background = new Background(0, 0,
				(Texture) game.assetManager.get("data/backgroundPixel.png"));

		titleTex = game.assetManager.get("data/title.png", Texture.class);
		playButtonTex = game.assetManager.get("data/play.png", Texture.class);
		quitButtonTex = game.assetManager.get("data/quit.png", Texture.class);
		helpButtonTex = game.assetManager.get("data/help.png", Texture.class);
		highScoreButtonTex = game.assetManager.get("data/highscore.png",
				Texture.class);

		playBounds = new Rectangle(Gdx.graphics.getWidth() / 2
				- playButtonTex.getWidth() / 2,
				Gdx.graphics.getHeight() * 0.35f, playButtonTex.getWidth(),
				playButtonTex.getHeight());
		highScoreBounds = new Rectangle(Gdx.graphics.getWidth() / 2
				- highScoreButtonTex.getWidth() / 2,
				Gdx.graphics.getHeight() * 0.15f,
				highScoreButtonTex.getWidth(), highScoreButtonTex.getHeight());
		helpBounds = new Rectangle(0, 0, helpButtonTex.getWidth(),
				helpButtonTex.getHeight());
		quitBounds = new Rectangle(Gdx.graphics.getWidth()
				- quitButtonTex.getWidth(), 0, quitButtonTex.getWidth(),
				quitButtonTex.getHeight());

		touchPoint = new Vector3();
		spriteBatcher = new SpriteBatch();

		pixelTarget2 = game.assetManager.get("data/entities/pixelTarget2Hit.png",
				Texture.class);
	}

	@Override
	public void render(float delta) {
		update(delta);
		draw();
	}

	public void update(float delta) {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),
					0));

			if (playBounds.contains(touchPoint.x, touchPoint.y)) {
				game.setScreen(new GameOrthoStyleScreen(game));
			}
		}
	}

	public void draw() {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		guiCam.update();

		spriteBatcher.setProjectionMatrix(guiCam.combined);
		spriteBatcher.enableBlending();
		spriteBatcher.begin();

		spriteBatcher.draw((Texture) background.texture, background.position.x,
				background.position.y, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		drawMenu();

		spriteBatcher.end();
	}

	private void drawMenu() {
		spriteBatcher.draw(titleTex,
				Gdx.graphics.getWidth() / 2 - titleTex.getWidth() / 2,
				Gdx.graphics.getHeight() * 0.7f, titleTex.getWidth(),
				titleTex.getHeight());
		spriteBatcher.draw(playButtonTex, playBounds.x, playBounds.y,
				playButtonTex.getWidth(), playButtonTex.getHeight());
		spriteBatcher.draw(helpButtonTex, helpBounds.x, helpBounds.y,
				helpButtonTex.getWidth(), helpButtonTex.getHeight());
		spriteBatcher.draw(highScoreButtonTex, highScoreBounds.x,
				highScoreBounds.y, highScoreButtonTex.getWidth(),
				highScoreButtonTex.getHeight());
		spriteBatcher.draw(quitButtonTex, quitBounds.x, quitBounds.y,
				quitButtonTex.getWidth(), quitButtonTex.getHeight());
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
}