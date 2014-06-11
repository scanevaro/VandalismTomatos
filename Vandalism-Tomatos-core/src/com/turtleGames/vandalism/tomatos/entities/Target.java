package com.turtleGames.vandalism.tomatos.entities;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.turtleGames.vandalism.tomatos.Tomatos;
import com.turtleGames.vandalism.tomatos.classes.Dynamic3DGameObject;

public class Target extends Dynamic3DGameObject {

	public static final int TARGET1 = 0;
	public static final int TARGET2 = 1;
	public static final int DOG = 2;
	public static final int CAT = 3;

	public static final int WALKING = 0;
	public static final int HIT = 1;
	public static final int REMOVE = 2;

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
		setState(WALKING);

		instantiate();
	}

	private void instantiate() {
		if (rand == null)
			rand = new Random();

		setVelocity();
		setSpacePos();
		setDimensions();
		setZ();
		setBounds();
	}

	private void setVelocity() {
		switch (type) {
			case TARGET1:
				if (spacePos.x <= 0)
					velocity.set(30, 0);
				else
					velocity.set(-30, 0);
				break;
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
		}
	}

	private void setSpacePos() {
		float x = 0, y;

		y = rand.nextFloat() * 250;

		if (spacePos.x == 0) {
			if (rand.nextInt(2) == 0)
				x = Gdx.graphics.getWidth();
			else
				x = 0;
		} else {
			spacePos.y = y;
			spacePos.z = y;
			return;
		}

		spacePos.set(x, y, y);
	}

	private void setDimensions() {
		TextureRegion texture;
		switch (type) {
			case TARGET1:
				texture = game.assets.getAnimation(TARGET1).getKeyFrame(0);

				width = texture.getRegionWidth();
				height = texture.getRegionHeight();
				break;
			case TARGET2:
				texture = game.assets.getAnimation(TARGET2).getKeyFrame(0);

				width = texture.getRegionWidth();
				height = texture.getRegionHeight();
				break;
			case DOG:
				texture = game.assets.getAnimation(DOG).getKeyFrame(0);

				width = texture.getRegionWidth();
				height = texture.getRegionHeight();
				break;
			case CAT:
				texture = game.assets.getAnimation(CAT).getKeyFrame(0);

				width = texture.getRegionWidth();
				height = texture.getRegionHeight();
				break;
		}
	}

	private void setZ() {
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
	}

	private void setBounds() {
		bounds.set(spacePos.x - width / 2, spacePos.y - height / 2, width,
				height);
	}

	public void update(float delta) {
		switch (state) {
			case WALKING:
				spacePos.add(velocity.x * delta, velocity.y * delta, 0);
				bounds.set(spacePos.x - width / 2, spacePos.y - height / 2,
						width, height);

				if (spacePos.x <= 0 - dimensions.x
						|| spacePos.x >= Gdx.graphics.getWidth()) {
					instantiate();
				}
				break;
			case HIT:
				if (stateTime >= 2f)
					setState(REMOVE);
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