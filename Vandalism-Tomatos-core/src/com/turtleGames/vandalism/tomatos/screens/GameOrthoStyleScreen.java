package com.turtleGames.vandalism.tomatos.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.turtleGames.vandalism.tomatos.Tomatos;
import com.turtleGames.vandalism.tomatos.classes.WorldOrthoStyle;
import com.turtleGames.vandalism.tomatos.classes.WorldRendererOrthoStyle;
import com.turtleGames.vandalism.tomatos.entities.ImpactSetter;
import com.turtleGames.vandalism.tomatos.entities.Projectile;

public class GameOrthoStyleScreen implements Screen {

	public enum GameState {
		READY, RUNNING, GAME_OVER, WIN
	}

	Tomatos game;

	private int state;

	OrthographicCamera guiCam;
	SpriteBatch spriteBatcher;
	ModelBatch modelBatcher;
	WorldOrthoStyle world;
	WorldOrthoStyle.WorldListener worldListener;
	WorldRendererOrthoStyle worldRenderer;

	BitmapFont font;

	Music music;

	private String gameOver = "Game Over";
	private String win = "Level Complete";
	private String score = "Score ";
	private String touch = "Touch to continue";
	private String level = "Level ";

	@SuppressWarnings("deprecation")
	public GameOrthoStyleScreen(Tomatos game) {
		this.game = game;

		guiCam = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		guiCam.position.set(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, 0);

		spriteBatcher = new SpriteBatch();
		modelBatcher = new ModelBatch();

		worldListener = new WorldOrthoStyle.WorldListener() {

			@Override
			public void hitTarget() {
				// TODO Auto-generated method stub
			}

			@Override
			public void hitGround() {
				// TODO Auto-generated method stub
			}

			@Override
			public void throwTomato() {
				// TODO Auto-generated method stub

			}
		};
		world = new WorldOrthoStyle(game, worldListener);
		worldRenderer = new WorldRendererOrthoStyle(spriteBatcher,
				modelBatcher, world, game);
		world.worldRenderer = worldRenderer;

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
				Gdx.files.internal("data/wonder.ttf"));
		font = generator.generateFont(20);
		generator.dispose();

		// Random rand = new Random();
		// int value = rand.nextInt(3);
		// if (value == 0) {
		// music = Gdx.audio.newMusic(Gdx.files.internal("data/ken.mp3"));
		// music.play();
		// } else if (value == 1) {
		// music = Gdx.audio.newMusic(Gdx.files.internal("data/guile.mp3"));
		// music.play();
		// } else {
		// music = Gdx.audio.newMusic(Gdx.files.internal("data/ryu.mp3"));
		// music.play();
		// }

		setState(GameState.READY.ordinal());
	}

	@Override
	public void render(float delta) {
		update(delta);
		draw();
	}

	public void update(float delta) {
		// if (deltaTime > 0.1f)
		// deltaTime = 0.1f;

		if (getState() == GameState.READY.ordinal())
			updateReady(delta);
		else if (getState() == GameState.RUNNING.ordinal())
			updateRunning(delta);
		else if (getState() == GameState.GAME_OVER.ordinal())
			updateGameOver();
		else if (getState() == GameState.WIN.ordinal())
			updateWin();

		world.update(delta);

		if (world.state == GameState.GAME_OVER.ordinal())
			setState(GameState.GAME_OVER.ordinal());
	}

	private void updateReady(float delta) {
		if (Gdx.input.justTouched())
			setState(GameState.RUNNING.ordinal());
	}

	private void updateRunning(float delta) {
		if (Gdx.input.justTouched()) {
			Projectile projectile = world.projectile;
			ImpactSetter impactSetter = world.impactSetter;

			if (!projectile.isUpdate())
				impactSetter.setShooting(false);

			if (!impactSetter.isActivate() && !impactSetter.isShooting())
				impactSetter.setActivate(true);
			else if (!impactSetter.isShooting()) {
				impactSetter.setShooting(true);
				projectile.setImpactSpot(impactSetter.position);
				projectile.setUpdate(true);
				projectile.prepare();
				impactSetter.setActivate(false);
			}
		}
	}

	private void updateGameOver() {
		if (Gdx.input.justTouched())
			game.setScreen(new MainMenuScreen(game));
	}

	private void updateWin() {
		// TODO
	}

	public void draw() {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		worldRenderer.render();

		guiCam.update();

		spriteBatcher.setProjectionMatrix(guiCam.combined);
		spriteBatcher.enableBlending();
		spriteBatcher.begin();

		presentUI();

		if (getState() == GameState.READY.ordinal())
			presentReady();
		else if (getState() == GameState.RUNNING.ordinal())
			presentRunning();
		else if (getState() == GameState.GAME_OVER.ordinal())
			presentGameOver();
		else if (getState() == GameState.WIN.ordinal())
			presentWin();

		spriteBatcher.end();
	}

	private void presentUI() {
		// batcher.draw(texture, x, y, width, height);
	}

	private void presentReady() {
		font.setColor(Color.WHITE);
		font.setScale(0.5f, 0.5f);
		font.draw(spriteBatcher, "READY?", Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);
		font.draw(spriteBatcher, "Touch Screen", Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - 20);
	}

	private void presentRunning() {
		score = "Score " + String.valueOf(game.score);
		level = "Level " + String.valueOf(game.level);

		font.setColor(Color.RED);
		font.setScale(1.0f, 1.0f);
		font.draw(spriteBatcher, score, 0, font.getCapHeight());
		font.draw(
				spriteBatcher,
				level,
				Gdx.graphics.getWidth() - font.getSpaceWidth()
						* (level.length() * 2), font.getCapHeight());
	}

	private void presentGameOver() {
		score = "Score " + String.valueOf(game.score);

		font.setColor(Color.RED);
		font.setScale(3.0f, 3.0f);
		font.draw(
				spriteBatcher,
				gameOver,
				Gdx.graphics.getWidth() / 2 - font.getSpaceWidth()
						* gameOver.length(),
				(Gdx.graphics.getHeight() / 2) + (Gdx.graphics.getHeight() / 4)
						+ ((Gdx.graphics.getHeight() / 4) / 2));
		font.setScale(2.8f, 2.8f);
		font.draw(
				spriteBatcher,
				score,
				Gdx.graphics.getWidth() / 2 - font.getSpaceWidth()
						* score.length(), Gdx.graphics.getHeight() / 2
						+ ((Gdx.graphics.getHeight() / 4) / 2));
		font.setColor(Color.BLUE);
		font.setScale(2.0f, 2.0f);
		font.draw(
				spriteBatcher,
				touch,
				Gdx.graphics.getWidth() / 2 - font.getSpaceWidth()
						* (touch.length() - 2), Gdx.graphics.getHeight() / 3);
	}

	private void presentWin() {
		score = "Score " + String.valueOf(game.score);

		font.setColor(Color.RED);
		font.setScale(3.0f, 3.0f);
		font.draw(
				spriteBatcher,
				win,
				Gdx.graphics.getWidth() / 2 - font.getSpaceWidth()
						* win.length(),
				(Gdx.graphics.getHeight() / 2) + (Gdx.graphics.getHeight() / 4)
						+ ((Gdx.graphics.getHeight() / 4) / 2));
		font.setScale(2.8f, 2.8f);
		font.draw(
				spriteBatcher,
				score,
				Gdx.graphics.getWidth() / 2 - font.getSpaceWidth()
						* score.length(), Gdx.graphics.getHeight() / 2
						+ ((Gdx.graphics.getHeight() / 4) / 2));
		font.setColor(Color.BLUE);
		font.setScale(2.0f, 2.0f);
		font.draw(
				spriteBatcher,
				touch,
				Gdx.graphics.getWidth() / 2 - font.getSpaceWidth()
						* (touch.length() - 2), Gdx.graphics.getHeight() / 3);
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
		spriteBatcher.dispose();
		modelBatcher.dispose();
		world.dispose();
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;

		world.state = state;
	}
}