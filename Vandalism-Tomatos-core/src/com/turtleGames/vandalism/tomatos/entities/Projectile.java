package com.turtleGames.vandalism.tomatos.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;
import com.turtleGames.vandalism.tomatos.classes.Dynamic3DGameObject;

public class Projectile extends Dynamic3DGameObject {

	private final float gravity = 350;

	private Vector3 velocity;
	public Circle bounds;
	private boolean update;
	private Texture texture;
	private float impactSpot;
	public float stateTime;
	private float flyghtTime;

	public Projectile(Texture texture) {
		super(Gdx.graphics.getWidth() / 2, 0, texture.getWidth(), texture
				.getHeight());
		velocity = new Vector3();
		this.texture = texture;
		bounds = new Circle(spacePos.z, spacePos.y, texture.getWidth());
		flyghtTime = 1;
		stateTime = 0;
	}

	public void draw(SpriteBatch batcher) {
		if (update)
			batcher.draw(texture, spacePos.x - texture.getWidth() / 2,
					spacePos.y - texture.getHeight() / 2, texture.getWidth(),
					texture.getHeight());
	}

	public void update(float delta) {
		if (update) {
			stateTime += delta;

			spacePos.z = velocity.z * stateTime + 0;
			spacePos.y = (float) (-0.5 * gravity * stateTime * stateTime
					+ velocity.y * stateTime + 0);

			bounds.set(spacePos.x - dimensions.x / 2, spacePos.y - dimensions.y
					/ 2, dimensions.x);

			if (spacePos.y < 150)
				dimensions.set(width, height);
			else if (spacePos.y > 150 && spacePos.y < 250) {
				width = width / 2;
				height = height / 2;
				dimensions.set(width, height);
			} else if (spacePos.y > 250 && spacePos.y < 350) {
				width = width / 3;
				height = height / 3;
				dimensions.set(width, height);
			} else {
				width = width / 4;
				height = height / 4;
				dimensions.set(width, height);
			}

			if (stateTime > flyghtTime)
				setUpdate(false);
		} else {
			stateTime -= delta;
		}
	}

	public void prepare() {
		spacePos.set(Gdx.graphics.getWidth() / 2, 0, 0);

		velocity.z = (impactSpot - spacePos.z) / flyghtTime;
		velocity.y = (float) ((impactSpot + 0.5 * gravity * flyghtTime
				* flyghtTime - spacePos.y) / flyghtTime);
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;

		spacePos.set(0, 0, 0);
		bounds.set(0, 0, 0);

		if (update)
			stateTime = 0;
	}

	public float getImpactSpot() {
		return impactSpot;
	}

	public void setImpactSpot(Vector3 impactPos) {
		impactSpot = (impactPos.z - impactPos.y) / 2 + impactPos.y;
	}
}