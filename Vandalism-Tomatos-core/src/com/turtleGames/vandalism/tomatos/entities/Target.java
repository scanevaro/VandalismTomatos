package com.turtleGames.vandalism.tomatos.entities;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.turtleGames.vandalism.tomatos.Tomatos;
import com.turtleGames.vandalism.tomatos.classes.Dynamic3DGameObject;

public class Target extends Dynamic3DGameObject {

	public static final int TARGET2 = 0;
	public static final int DOG = 1;
	public static final int CAT = 2;
	public static final int PIXEL_TARGET_1 = 3;
	public static final int COP = 4;

	public static final int WALKING = 0;
	public static final int HIT = 1;
	public static final int REMOVE = 2;
	public static final int IDLE = 3;

	private Random rand;

	private Tomatos game;

	public float stateTime;
	private int state;
	public int type;

	public Target(Tomatos game) {
		super();

		this.game = game;

		if (rand == null)
			rand = new Random();

		stateTime = 0;
		type = rand.nextInt(4);

		if (rand.nextFloat() < 0.1f)
			setState(WALKING);
		else
			setState(IDLE);

		instantiate();
	}

	public Target(Tomatos game, int state, String type) {
		super();

		this.game = game;

		if (rand == null)
			rand = new Random();

		setState(state);

		instantiate();
	}

	public Target(Tomatos game, int type) {
		super();

		this.game = game;
		this.type = type;

		if (rand == null)
			rand = new Random();

		stateTime = 0;

		if (rand.nextFloat() < 0.1f)
			setState(WALKING);
		else
			setState(IDLE);

		instantiate();
	}

	private void instantiate() {
		if (rand == null)
			rand = new Random();

		setSpacePos();
		setVelocity();
		setDimensions();
		setBounds();
	}

	private void setVelocity() {
		switch (type) {
			case TARGET2:
				if (spacePos.x <= 0)
					velocity.set(25, 0);
				else
					velocity.set(-25, 0);
				break;
			case DOG:
				if (spacePos.x <= 0)
					velocity.set(40, 0);
				else
					velocity.set(-40, 0);
				break;
			case CAT:
				if (spacePos.x <= 0)
					velocity.set(43, 0);
				else
					velocity.set(-43, 0);
				break;
			case PIXEL_TARGET_1:
				if (spacePos.x <= 0)
					velocity.set(43, 0);
				else
					velocity.set(-43, 0);
				break;
			case COP:
				if (spacePos.x <= 0)
					velocity.set(43, 0);
				else
					velocity.set(-43, 0);
				break;
		}
	}

	private void setSpacePos() {
		float x = 0, y = 0;

		y = rand.nextFloat();
		while (y > 0.6f || y < 0.4f)
			y = rand.nextFloat();

		y = y * Gdx.graphics.getHeight();

		if (spacePos.x == 0) {
			if (rand.nextInt(2) == 0)
				x = Gdx.graphics.getWidth();
			else
				x = 0;
		} else {
			spacePos.y = y;
			spacePos.z = y * 2;
			return;
		}

		spacePos.set(x, y, y * 2);
	}

	private void setDimensions() {
		TextureRegion texture = game.assets.getAnimation(type).getKeyFrame(0);
		width = texture.getRegionWidth();
		height = texture.getRegionHeight();

		float dimensionPercentage = 1 - ((spacePos.y - (Gdx.graphics
				.getHeight() * 0.4f)) / ((Gdx.graphics.getHeight() * 0.6f) - (Gdx.graphics
				.getHeight() * 0.35f)));
		width = width * dimensionPercentage;
		height = height * dimensionPercentage;
		dimensions.set(width, height);
	}

	private void setBounds() {
		bounds.set(spacePos.x, spacePos.y, width, height);
	}

	public void update(float delta) {
		switch (state) {
			case WALKING:
				spacePos.add(velocity.x * delta, velocity.y * delta, 0);
				bounds.set(spacePos.x, spacePos.y, width, height);

				if (spacePos.x <= 0 - dimensions.x
						|| spacePos.x >= Gdx.graphics.getWidth())
					setState(IDLE);
				break;
			case HIT:
				if (stateTime >= 2f)
					setState(REMOVE);
				break;
			case IDLE:
				if (stateTime > 3f) {
					setState(WALKING);
					instantiate();
				}
				break;
		}

		stateTime += delta;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;

		stateTime = 0;
	}
}