package com.turtleGames.vandalism.tomatos.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Background {

	public Vector2 position;
	public Texture texture;

	public Background(float x, float y, Texture texture) {
		this.texture = texture;

		position = new Vector2(x, y);
	}
}
