package com.turtleGames.vandalism.tomatos.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.turtleGames.vandalism.tomatos.Tomatos;
import com.turtleGames.vandalism.tomatos.classes.Dynamic3DGameObject;

public class Projectile extends Dynamic3DGameObject {

	public static final int IDLE = 0;
	public static final int FLYING = 1;
	public static final int HIT = 2;
	public static final int GROUND = 3;

	private float gravity;

	private Vector3 velocity;
	public Circle bounds;
	public boolean update;
	public Vector2 impactSpot;
	public float stateTime;
	public int state;
	public float flyghtTime;
	private float impactTime;

	public Projectile(Tomatos game) {
		super();

		stateTime = 0;
		gravity = 0;

		spacePos.set(Gdx.graphics.getWidth() / 2, 0, 0);

		TextureRegion texture = game.assets.projectileAnim.getKeyFrame(0);
		bounds = new Circle(spacePos.z, spacePos.y,
				texture.getRegionWidth() / 2);
		dimensions.set(texture.getRegionWidth() / 2,
				texture.getRegionHeight() / 2);

		velocity = new Vector3();
		impactSpot = new Vector2();

		setState(IDLE);
	}

	public void update(float delta) {
		if (update) {
			stateTime += delta;
			switch (getState()) {
				case FLYING:
					spacePos.z = velocity.z * stateTime + 0;
					spacePos.y = (float) (-0.5 * gravity * stateTime
							* stateTime + velocity.y * stateTime + 0);

					if (spacePos.z < Gdx.graphics.getHeight() * 0.25f * 2)
						bounds.set(spacePos.x, spacePos.y, dimensions.x / 2);
					else if (spacePos.z > Gdx.graphics.getHeight() * 0.25f * 2
							&& spacePos.z < Gdx.graphics.getHeight() * 0.5f * 2)
						bounds.set(spacePos.x, spacePos.y, dimensions.x / 2 / 2);
					else if (spacePos.z > Gdx.graphics.getHeight() * 0.5f * 2
							&& spacePos.z < Gdx.graphics.getHeight() * 0.75f * 2)
						bounds.set(spacePos.x, spacePos.y, dimensions.x / 3 / 2);
					else
						bounds.set(spacePos.x, spacePos.y, dimensions.x / 4 / 2);

					if (stateTime >= impactTime)
						setState(GROUND);
					break;
				case HIT:
					if (stateTime >= 1f)
						setState(IDLE);
					break;
				case GROUND:
					if (stateTime >= 1f)
						setState(IDLE);
					break;
			}
		} else
			stateTime -= delta;
	}

	public Vector2 getImpactSpot() {
		return impactSpot;
	}

	public void setImpactSpot(Vector3 impactPos) {
		impactSpot.set(impactPos.y * 2, impactPos.y);
	}

	public void prepare() {
		spacePos.set(Gdx.graphics.getWidth() / 2, 0, 0);

		impactTime = (float) (impactSpot.x * 1.0f / (Gdx.graphics.getHeight() * 0.8));
		flyghtTime = 100 * impactTime / 75;

		float funtionY = impactSpot.y + impactSpot.y * 1 / 4;

		gravity = funtionY
				/ ((-0.5f * flyghtTime / 2 * flyghtTime / 2) + 0.5f
						* flyghtTime * flyghtTime / 2);

		velocity.z = (impactSpot.x - spacePos.z) / impactTime;
		velocity.y = (float) ((0.5 * gravity * flyghtTime * flyghtTime - spacePos.y) / flyghtTime);
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;

		spacePos.set(0, 0, 0);
		bounds.set(0, 0, 0);

		if (update)
			setState(FLYING);
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
				stateTime = 0;
				break;
			case IDLE:
				setUpdate(false);
				break;
		}
	}
}