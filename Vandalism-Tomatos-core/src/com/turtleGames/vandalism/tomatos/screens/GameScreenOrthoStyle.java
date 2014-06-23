package com.turtleGames.vandalism.tomatos.screens;

import java.util.Random;

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

public class GameScreenOrthoStyle implements Screen {

	private enum GameState {
		READY, RUNNING, GAME_OVER
	}

	Tomatos game;

	int state;
	OrthographicCamera guiCam;
	SpriteBatch spriteBatcher;
	ModelBatch modelBatcher;
	WorldOrthoStyle world;
	WorldOrthoStyle.WorldListener worldListener;
	WorldRendererOrthoStyle worldRenderer;

	BitmapFont font;

	Music music;

	@SuppressWarnings("deprecation")
	public GameScreenOrthoStyle(Tomatos game) {
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

		state = GameState.READY.ordinal();

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
				Gdx.files.internal("data/wonder.ttf"));
		font = generator.generateFont(20);
		generator.dispose();

		Random rand = new Random();
		int value = rand.nextInt(3);
		if (value == 0) {
			music = Gdx.audio.newMusic(Gdx.files.internal("data/ken.mp3"));
			music.play();
		} else if (value == 1) {
			music = Gdx.audio.newMusic(Gdx.files.internal("data/guile.mp3"));
			music.play();
		} else {
			music = Gdx.audio.newMusic(Gdx.files.internal("data/ryu.mp3"));
			music.play();
		}
	}

	@Override
	public void render(float delta) {
		update(delta);
		draw();
	}

	public void update(float delta) {
		// if (deltaTime > 0.1f)
		// deltaTime = 0.1f;

		if (state == GameState.READY.ordinal()) {
			updateReady(delta);
		} else if (state == GameState.RUNNING.ordinal()) {
			updateRunning(delta);
		} else if (state == GameState.GAME_OVER.ordinal()) {
			updateGameOver(delta);
		}

		world.update(delta);
	}

	private void updateReady(float delta) {
		if (Gdx.input.justTouched()) {
			// guiCam.unproject(touchPoint.set(Gdx.input.getX(),
			// Gdx.input.getY(),
			// 0));
			state = GameState.RUNNING.ordinal();
			world.state = WorldOrthoStyle.RUNNING;
		}
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

	private void updateGameOver(float delta) {

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

		if (state == GameState.READY.ordinal()) {
			presentReady();
		} else if (state == GameState.RUNNING.ordinal()) {
			presentRunning();
		} else if (state == GameState.GAME_OVER.ordinal()) {
			presentGameOver();
		}

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
	}

	private void presentGameOver() {
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
}