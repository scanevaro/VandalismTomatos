package com.turtleGames.vandalism.tomatos.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.turtleGames.vandalism.tomatos.entities.Target;

public class WorldRendererOrthoStyle {

	WorldOrthoStyle world;
	OrthographicCamera orthoTargetsCam;
	OrthographicCamera orthoBackgroundCam;
	SpriteBatch spriteBatcher;
	ModelBatch modelBatcher;

	public WorldRendererOrthoStyle(SpriteBatch spriteBatcher,
			ModelBatch modelBatcher, WorldOrthoStyle world) {
		this.world = world;
		this.spriteBatcher = spriteBatcher;
		this.modelBatcher = modelBatcher;

		orthoBackgroundCam = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		orthoBackgroundCam.position.set(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, 0);
		orthoBackgroundCam.update();

		orthoTargetsCam = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		orthoTargetsCam.position.set(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, 0);
		orthoTargetsCam.update();
	}

	public void render() {
		orthoTargetsCam.update();

		renderBackground();
		renderCollitionSetter();
		renderTargets();
		renderProjectile();
		renderBushKid();
	}

	private void renderBackground() {
		spriteBatcher.setProjectionMatrix(orthoTargetsCam.combined);
		spriteBatcher.disableBlending();
		spriteBatcher.begin();
		spriteBatcher.draw((Texture) world.background.texture,
				world.background.position.x, world.background.position.y,
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spriteBatcher.end();
	}

	private void renderCollitionSetter() {
		world.impactSetter.draw();
	}

	private void renderTargets() {
		spriteBatcher.setProjectionMatrix(orthoTargetsCam.combined);
		spriteBatcher.enableBlending();
		spriteBatcher.begin();
		for (Target target : world.targets) {
			spriteBatcher.draw(
					target.getAnimation().getKeyFrame(target.stateTime, true),
					target.spacePos.x, target.spacePos.y, target.dimensions.x,
					target.dimensions.y);
		}
		spriteBatcher.end();
	}

	private void renderProjectile() {
		spriteBatcher.setProjectionMatrix(orthoBackgroundCam.combined);
		spriteBatcher.enableBlending();
		spriteBatcher.begin();
		world.projectile.draw(spriteBatcher);
		spriteBatcher.end();
	}

	private void renderBushKid() {
		spriteBatcher.setProjectionMatrix(orthoTargetsCam.combined);
		spriteBatcher.enableBlending();
		spriteBatcher.begin();
		spriteBatcher.draw(
				(Texture) world.game.assetManager.get("data/bush.png"), 0, 0,
				Gdx.graphics.getWidth(), 300);
		spriteBatcher.draw(
				(Texture) world.game.assetManager.get("data/kid6464.png"),
				Gdx.graphics.getWidth() / 2 - 25, 0, 64, 64);
		spriteBatcher.end();
	}
}