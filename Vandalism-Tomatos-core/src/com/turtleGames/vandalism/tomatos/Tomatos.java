package com.turtleGames.vandalism.tomatos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.FPSLogger;
import com.turtleGames.vandalism.tomatos.classes.Assets;
import com.turtleGames.vandalism.tomatos.screens.LoadingScreen;

public class Tomatos extends Game {

	public static FPSLogger fps;
	public AssetManager assetManager;
	public Assets assets;
	public int score;
	public int level;

	@Override
	public void create() {
		assetManager = new AssetManager();
		assets = new Assets();

		setScreen(new LoadingScreen(this));

		fps = new FPSLogger();
	}

	@Override
	public void dispose() {
		super.dispose();
		getScreen().dispose();
	}

	@Override
	public void render() {
		super.render();
		fps.log();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}