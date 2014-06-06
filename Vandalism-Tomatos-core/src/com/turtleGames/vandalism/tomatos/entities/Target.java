package com.turtleGames.vandalism.tomatos.entities;

import java.util.Random;

import com.badlogic.gdx.math.Rectangle;
import com.turtleGames.vandalism.tomatos.classes.Dynamic3DGameObject;

public class Target extends Dynamic3DGameObject {

	public static final int TARGET1 = 0;
	public static final int TARGET2 = 1;
	public static final int DOG = 2;
	public static final int CAT = 3;

	public static Random rand;

	public float stateTime;
	public int type;

	public Target(float x, float y, float width, float height, int type) {
		super(x, y, width, height);

		if (rand == null)
			rand = new Random();

		stateTime = 0;

		setZ();

		bounds = new Rectangle(x - width / 2, y - height / 2, width, height);

		this.type = type;
		setVelocity();
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

	private void setVelocity() {
		switch (type) {
			case TARGET1:
				if (spacePos.x == 0)
					velocity.set(rand.nextFloat() * 20, 0);
				else
					velocity.set(rand.nextFloat() * -20, 0);
				break;
			case TARGET2:
				if (spacePos.x == 0)
					velocity.set(rand.nextFloat() * 15, 0);
				else
					velocity.set(rand.nextFloat() * -15, 0);
				break;
			case DOG:
				if (spacePos.x == 0)
					velocity.set(rand.nextFloat() * 30, 0);
				else
					velocity.set(rand.nextFloat() * -30, 0);
				break;
			case CAT:
				if (spacePos.x == 0)
					velocity.set(rand.nextFloat() * 33, 0);
				else
					velocity.set(rand.nextFloat() * -33, 0);
				break;
		}
	}

	public void update(float delta) {
		spacePos.add(velocity.x * delta, velocity.y * delta, 0);
		bounds.set(spacePos.x - width / 2, spacePos.y - height / 2, width,
				height);

		stateTime += delta;
	}
}