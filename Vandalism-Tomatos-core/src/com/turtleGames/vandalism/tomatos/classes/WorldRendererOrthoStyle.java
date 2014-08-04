package com.turtleGames.vandalism.tomatos.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
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
	ShapeRenderer shapeRenderer;

	private int sideX;

	public WorldRendererOrthoStyle(SpriteBatch spriteBatcher,
			ModelBatch modelBatcher, WorldOrthoStyle world, Tomatos game) {
		this.game = game;
		this.world = world;
		this.spriteBatcher = spriteBatcher;
		this.modelBatcher = modelBatcher;
		shapeRenderer = new ShapeRenderer();

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
		// renderShapes();
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

		TextureRegion textureReg = null;
		Texture texture = null;

		for (Target target : world.targets) {

			sideX = target.velocity.x < 0 ? -1 : 1;

			if (target.getState() == Target.WALKING) {
				/* getAnimation(type, dimensions); */
				Animation animation = game.assets.getAnimation(target.type);
				textureReg = animation.getKeyFrame(target.stateTime, true);
				texture = null;
			} else if (target.getState() == Target.HIT) {
				textureReg = null;
				switch (target.type) {
					case Target.TARGET2:
						texture = game.assetManager.get(
								"data/entities/pixelTarget2Hit.png",
								Texture.class);
						break;
					case Target.DOG:
						texture = game.assetManager.get(
								"data/entities/dogHit.png", Texture.class);
						break;
					case Target.CAT:
						texture = game.assetManager.get(
								"data/entities/catHit.png", Texture.class);
						break;
					case Target.PIXEL_TARGET_1:
						texture = game.assetManager.get(
								"data/entities/girlHit.png", Texture.class);
						break;
					case Target.COP:
						texture = game.assetManager.get(
								"data/entities/copHit.png", Texture.class);
						break;
				}
			} else {

			}

			if (target.getState() != Target.IDLE
					&& target.getState() != Target.REMOVE) {
				if (sideX == 1) {
					if (textureReg == null)
						spriteBatcher.draw(texture, target.spacePos.x,
								target.spacePos.y, target.dimensions.x,
								target.dimensions.y);
					else
						spriteBatcher.draw(textureReg, target.spacePos.x,
								target.spacePos.y, target.dimensions.x,
								target.dimensions.y);
				} else {
					if (textureReg == null)
						spriteBatcher.draw(texture, target.spacePos.x
								+ target.bounds.width, target.spacePos.y,
								target.dimensions.x * sideX,
								target.dimensions.y);
					else
						spriteBatcher.draw(textureReg, target.spacePos.x
								+ target.bounds.width, target.spacePos.y,
								target.dimensions.x * sideX,
								target.dimensions.y);
				}
			}
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
			Circle bounds = projectile.bounds;
			float stateTime = projectile.stateTime;
			TextureRegion textureReg = null;
			Texture texture = null;

			if (projectile.getState() == Projectile.FLYING) {
				Animation animation = game.assets.projectileAnim;
				textureReg = animation.getKeyFrame(stateTime, true);
			} else if (projectile.getState() == Projectile.HIT) {
				texture = game.assetManager.get("data/projectileHit.png",
						Texture.class);
				textureReg = null;
			} else {
				texture = game.assetManager.get("data/projectileHit.png",
						Texture.class);
				textureReg = null;
			}

			if (textureReg == null)
				spriteBatcher.draw(texture, position.x - bounds.radius * 2 / 2,
						position.y - bounds.radius * 2 / 2, bounds.radius * 2,
						bounds.radius * 2);
			else
				spriteBatcher.draw(textureReg, position.x - bounds.radius * 2
						/ 2, position.y - bounds.radius * 2 / 2,
						bounds.radius * 2, bounds.radius * 2);

		}

		spriteBatcher.end();
	}

	private void renderBushKid() {
		spriteBatcher.setProjectionMatrix(orthoTargetsCam.combined);
		spriteBatcher.enableBlending();
		spriteBatcher.begin();

		Texture texture = (Texture) world.game.assetManager
				.get("data/pixelBush.png");

		spriteBatcher.draw(texture,
				Gdx.graphics.getWidth() / 2 - texture.getWidth() / 2, 0,
				texture.getWidth(), texture.getHeight());
		spriteBatcher.draw((Texture) world.game.assetManager
				.get("data/entities/pixelKid.png"),
				Gdx.graphics.getWidth() / 2 - 25, 0, 64, 64);
		spriteBatcher.end();
	}

	private void renderShapes() {
		shapeRenderer.setProjectionMatrix(orthoTargetsCam.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(1, 0, 0, 1);

		Circle circle = world.projectile.bounds;
		shapeRenderer.circle(circle.x, circle.y, circle.radius);

		for (int i = 0; i < world.targets.size; i++) {
			Target target = world.targets.get(i);
			sideX = target.velocity.x < 0 ? -1 : 1;
			Rectangle rect = target.bounds;

			if (sideX == -1)
				shapeRenderer.rect(rect.x + rect.width, rect.y, rect.width
						* sideX, rect.height);
			else
				shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}

		shapeRenderer.end();
	}
}