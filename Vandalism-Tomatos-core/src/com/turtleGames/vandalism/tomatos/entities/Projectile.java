package com.turtleGames.vandalism.tomatos.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;
import com.turtleGames.vandalism.tomatos.Tomatos;
import com.turtleGames.vandalism.tomatos.classes.Dynamic3DGameObject;

public class Projectile extends Dynamic3DGameObject {

	public static final int IDLE = 0;
	public static final int FLYING = 1;
	public static final int HIT = 2;
	public static final int GROUND = 3;

	private final float gravity = 350;

	private Vector3 velocity;
	public Circle bounds;
	public boolean update;
	private float impactSpot;
	public float stateTime;
	public int state;
	private float flyghtTime;

	public Projectile(Tomatos game) {
		super();

		flyghtTime = 1;
		stateTime = 0;

		spacePos.set(Gdx.graphics.getWidth() / 2, 0, 0);

		TextureRegion texture = game.assets.projectileAnimation.getKeyFrame(0);
		bounds = new Circle(spacePos.z, spacePos.y, texture.getRegionWidth());
		dimensions.set(texture.getRegionWidth(), texture.getRegionHeight());

		velocity = new Vector3();

		setState(IDLE);
	}

	public void update(float delta) {
		if (update) {
			switch (getState()) {
				case FLYING:
					spacePos.z = velocity.z * stateTime + 0;
					spacePos.y = (float) (-0.5 * gravity * stateTime
							* stateTime + velocity.y * stateTime + 0);

					if (spacePos.y < 150)
						bounds.set(spacePos.x - dimensions.x / 2, spacePos.y
								- dimensions.y / 2, dimensions.x);
					else if (spacePos.y > 150 && spacePos.y < 250)
						bounds.set(spacePos.x - dimensions.x / 2 / 2,
								spacePos.y - dimensions.y / 2 / 2, dimensions.x);
					else if (spacePos.y > 250 && spacePos.y < 350)
						bounds.set(spacePos.x - dimensions.x / 3 / 2,
								spacePos.y - dimensions.y / 3 / 2, dimensions.x);
					else
						bounds.set(spacePos.x - dimensions.x / 4 / 2,
								spacePos.y - dimensions.y / 4 / 2, dimensions.x);

					if (stateTime > flyghtTime) {
						setUpdate(false);
						setState(IDLE);
					}
					break;
				case HIT:
					if (stateTime >= 1f)
						setState(IDLE);
					break;
				case GROUND:
					// TODO
					break;
			}

			stateTime += delta;
		} else
			stateTime -= delta;
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

		if (update) {
			setState(FLYING);
		}
	}

	public float getImpactSpot() {
		return impactSpot;
	}

	public void setImpactSpot(Vector3 impactPos) {
		impactSpot = (impactPos.z - impactPos.y) / 2 + impactPos.y;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;

		switch (state) {
			case FLYING:
			case HIT:
				stateTime = 0;
				break;
			case GROUND:
				break;
			case IDLE:
				setUpdate(false);
				break;
		}
	}
}