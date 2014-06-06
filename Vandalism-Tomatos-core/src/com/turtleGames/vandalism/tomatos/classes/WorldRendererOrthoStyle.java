package com.turtleGames.vandalism.tomatos.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.turtleGames.vandalism.tomatos.Tomatos;
import com.turtleGames.vandalism.tomatos.entities.Projectile;
import com.turtleGames.vandalism.tomatos.entities.Target;

public class WorldRendererOrthoStyle {

	Tomatos game;
	WorldOrthoStyle world;
	OrthographicCamera orthoTargetsCam;
	OrthographicCamera orthoBackgroundCam;
	SpriteBatch spriteBatcher;
	ModelBatch modelBatcher;

	public WorldRendererOrthoStyle(SpriteBatch spriteBatcher,
			ModelBatch modelBatcher, WorldOrthoStyle world, Tomatos game) {
		this.game = game;
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
			/* getAnimation(type, dimensions); */
			Animation animation = game.assets.getAnimation(target.type);
			TextureRegion texture = animation.getKeyFrame(target.stateTime,
					true);

			spriteBatcher.draw(texture, target.spacePos.x, target.spacePos.y,
					target.dimensions.x, target.dimensions.y);
		}
		spriteBatcher.end();
	}

	private void renderProjectile() {
		spriteBatcher.setProjectionMatrix(orthoBackgroundCam.combined);
		spriteBatcher.enableBlending();
		spriteBatcher.begin();

		Projectile projectile = world.projectile;
		if (projectile.update) {
			Vector3 position = projectile.spacePos;
			Vector2 dimensions = projectile.dimensions;
			float stateTime = projectile.stateTime;
			Animation animation = game.assets.projectileAnimation;
			TextureRegion texture = animation.getKeyFrame(stateTime, true);

			spriteBatcher.draw(texture, position.x - dimensions.x / 2,
					position.y - dimensions.y / 2, dimensions.x, dimensions.y);
		}

		spriteBatcher.end();
	}

	private void renderBushKid() {
		spriteBatcher.setProjectionMatrix(orthoTargetsCam.combined);
		spriteBatcher.enableBlending();
		spriteBatcher.begin();
		spriteBatcher.draw(
				(Texture) world.game.assetManager.get("data/bush.png"), 0, 0,
				Gdx.graphics.getWidth(), 200);
		spriteBatcher.draw(
				(Texture) world.game.assetManager.get("data/kid6464.png"),
				Gdx.graphics.getWidth() / 2 - 25, 0, 64, 64);
		spriteBatcher.end();
	}
}