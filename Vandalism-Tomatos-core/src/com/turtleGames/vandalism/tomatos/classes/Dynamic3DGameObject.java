package com.turtleGames.vandalism.tomatos.classes;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Dynamic3DGameObject extends DynamicGameObject {

	public final Vector3 spacePos;
	public final Vector2 dimensions;
	public float width;
	public float height;

	public Dynamic3DGameObject(float x, float y, float width, float height) {
		super();

		spacePos = new Vector3(x, y, y);

		setVelocity();
		// setAccel();

		this.width = width;
		this.height = height;

		dimensions = new Vector2(width, height);
	}

	private void setVelocity() {
		Random rand = new Random();

		if (spacePos.x == 0)
			velocity.set(rand.nextFloat() * 15, 0);
		else
			velocity.set(rand.nextFloat() * -15, 0);
	}
}