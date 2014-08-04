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

	public int getHitsToPolice() {
		int hitsToPolice = 0;

		if (level == 1)
			hitsToPolice = 3;
		if (level == 2)
			hitsToPolice = 2;

		return hitsToPolice;
	}

	public int getHitsToWin() {
		int hitToWin = 0;

		if (level == 1)
			hitToWin = 6;
		else if (level == 2)
			hitToWin = 8;

		return hitToWin;
	}
}