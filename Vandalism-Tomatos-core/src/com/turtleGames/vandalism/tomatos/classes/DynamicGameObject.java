package com.turtleGames.vandalism.tomatos.classes;

import com.badlogic.gdx.math.Vector2;

public class DynamicGameObject extends GameObject {

	public final Vector2 velocity;
	public final Vector2 accel;

	public DynamicGameObject() {
		super();

		velocity = new Vector2();
		accel = new Vector2();
	}
}
