package com.turtleGames.vandalism.tomatos.classes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Dynamic3DGameObject extends DynamicGameObject {

	public final Vector3 spacePos;
	public final Vector2 dimensions;
	public float width;
	public float height;

	public Dynamic3DGameObject() {
		super();

		spacePos = new Vector3();
		dimensions = new Vector2();
	}
}