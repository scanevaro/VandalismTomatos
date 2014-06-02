package com.turtleGames.vandalism.tomatos.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.turtleGames.vandalism.tomatos.Tomatos;
import com.turtleGames.vandalism.tomatos.entities.LoadingBar;

public class LoadingScreen implements Screen {

	Tomatos game;

	private Stage stage;

	private Image logo;
	private Image loadingFrame;
	private Image loadingBarHidden;
	private Image screenBg;
	private Image loadingBg;

	private float startX, endX;
	private float percent;

	private Actor loadingBar;

	public LoadingScreen(Tomatos game) {
		this.game = game;

		// Settings.load();
	}

	@Override
	public void show() {
		// Tell the manager to load assets for the loading screen
		game.assetManager.load("data/loading.pack", TextureAtlas.class);
		// Wait until they are finished loading
		game.assetManager.finishLoading();

		// Initialize the stage where we will place everything
		stage = new Stage();

		// Get our textureatlas from the manager
		TextureAtlas atlas = game.assetManager.get("data/loading.pack",
				TextureAtlas.class);

		// Grab the regions from the atlas and create some images
		logo = new Image(atlas.findRegion("libgdx-logo"));
		loadingFrame = new Image(atlas.findRegion("loading-frame"));
		loadingBarHidden = new Image(atlas.findRegion("loading-bar-hidden"));
		screenBg = new Image(atlas.findRegion("screen-bg"));
		loadingBg = new Image(atlas.findRegion("loading-frame-bg"));

		// Add the loading bar animation
		Animation anim = new Animation(0.05f,
				atlas.findRegions("loading-bar-anim"));
		anim.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
		loadingBar = new LoadingBar(anim);

		// Or if you only need a static bar, you can do
		// loadingBar = new Image(atlas.findRegion("loading-bar1"));

		// Add all the actors to the stage
		stage.addActor(screenBg);
		stage.addActor(loadingBar);
		stage.addActor(loadingBg);
		stage.addActor(loadingBarHidden);
		stage.addActor(loadingFrame);
		stage.addActor(logo);

		// Add everything to be loaded, for instance:
		game.assetManager.load("data/springGrass.png", Texture.class);
		game.assetManager.load("data/grassStripe.png", Texture.class);
		game.assetManager.load("data/grassBackground.png", Texture.class);
		game.assetManager.load("data/kid50.png", Texture.class);
		game.assetManager.load("data/projectile.png", Texture.class);
		game.assetManager.load("data/kid6464.png", Texture.class);
		game.assetManager.load("data/background.png", Texture.class);
		game.assetManager.load("data/bush.png", Texture.class);
		game.assetManager.load("data/target1hit.png", Texture.class);
		game.assetManager.load("data/target2hit.png", Texture.class);
		game.assetManager.load("data/cathit.png", Texture.class);
		game.assetManager.load("data/doghit.png", Texture.class);
		game.assetManager.load("data/projectileHit.png", Texture.class);
		game.assetManager.load("data/targetAnimation.pack", TextureAtlas.class);
		game.assetManager.load("data/target2.pack", TextureAtlas.class);
		game.assetManager.load("data/dogAnim.pack", TextureAtlas.class);
		game.assetManager.load("data/cat.pack", TextureAtlas.class);
		game.assetManager.load("data/projectile.pack", TextureAtlas.class);

		game.assetManager.finishLoading();

		TextureAtlas targetAnimAtlas = game.assetManager.get(
				"data/targetAnimation.pack", TextureAtlas.class);
		game.assets.targetAnimation = new Animation(0.25f,
				targetAnimAtlas.getRegions());

		TextureAtlas target2AnimAtlas = game.assetManager.get(
				"data/target2.pack", TextureAtlas.class);
		game.assets.target2Animation = new Animation(0.25f,
				target2AnimAtlas.getRegions());

		TextureAtlas dogAnimAtlas = game.assetManager.get("data/dogAnim.pack",
				TextureAtlas.class);
		game.assets.dogAnimation = new Animation(0.25f,
				dogAnimAtlas.getRegions());

		TextureAtlas catAnimAtlas = game.assetManager.get("data/cat.pack",
				TextureAtlas.class);
		game.assets.catAnimation = new Animation(0.25f,
				catAnimAtlas.getRegions());

		TextureAtlas projectileAnimAtlas = game.assetManager.get(
				"data/projectile.pack", TextureAtlas.class);
		game.assets.projectileAnimation = new Animation(0.25f,
				projectileAnimAtlas.getRegions());
	}

	@Override
	public void resize(int width, int height) {
		// Set our screen to always be XXX x 480 in size
		// width = 480 * width / height;
		// height = 480;
		stage.getViewport().update(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight(), true);

		// Make the background fill the screen
		screenBg.setSize(width, height);

		// Place the logo in the middle of the screen and 100 px up
		logo.setX((width - logo.getWidth()) / 2);
		logo.setY((height - logo.getHeight()) / 2 + 100);

		// Place the loading frame in the middle of the screen
		loadingFrame.setX((stage.getWidth() - loadingFrame.getWidth()) / 2);
		loadingFrame.setY((stage.getHeight() - loadingFrame.getHeight()) / 2);

		// Place the loading bar at the same spot as the frame, adjusted a few
		// px
		loadingBar.setX(loadingFrame.getX() + 15);
		loadingBar.setY(loadingFrame.getY() + 5);

		// Place the image that will hide the bar on top of the bar, adjusted a
		// few px
		loadingBarHidden.setX(loadingBar.getX() + 35);
		loadingBarHidden.setY(loadingBar.getY() - 3);
		// The start position and how far to move the hidden loading bar
		startX = loadingBarHidden.getX();
		endX = 440;

		// The rest of the hidden bar
		loadingBg.setSize(450, 50);
		loadingBg.setX(loadingBarHidden.getX() + 30);
		loadingBg.setY(loadingBarHidden.getY() + 3);
	}

	@Override
	public void render(float delta) {
		// Clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (game.assetManager.update()) { // Load some, will return true if done
			// loading
			// if (Gdx.input.isTouched()) { // If the
			// screen is touched after the
			// game is done loading, go to the
			// main menu screen
			game.setScreen(new GameScreenOrthoStyle(game));
			// }
		}

		// Interpolate the percentage to make it more smooth
		percent = Interpolation.linear.apply(percent,
				game.assetManager.getProgress(), 0.1f);

		// Update positions (and size) to match the percentage
		loadingBarHidden.setX(startX + endX * percent);
		loadingBg.setX(loadingBarHidden.getX() + 30);
		loadingBg.setWidth(450 - 450 * percent);
		loadingBg.invalidate();

		// Show the loading screen
		stage.act();
		stage.draw();
	}

	@Override
	public void hide() {
		// Dispose the loading assets as we no longer need them
		game.assetManager.unload("data/loading.pack");
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}