package com.turtleGames.vandalism.tomatos.classes;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {

	public final Vector2 position;
	public Rectangle bounds;

	public GameObject() {
		this.position = new Vector2();
		this.bounds = new Rectangle();
	}
}