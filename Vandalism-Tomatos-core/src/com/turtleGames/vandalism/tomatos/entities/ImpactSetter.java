package com.turtleGames.vandalism.tomatos.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

public class ImpactSetter {

	ShapeRenderer shapeRenderer;
	public Vector3 position;
	public boolean activate;
	private boolean shooting;
	private float addedX, addedY, addedPos;
	private float x, y;

	public ImpactSetter() {
		shapeRenderer = new ShapeRenderer();

		position = new Vector3();

		x = 0;
		y = 0;
		addedX = 0;
		addedY = 0;
		addedPos = 0;
	}

	public void update(float delta) {
		if (activate) {
			addedPos = Gdx.graphics.getHeight() / 100 * (delta * 40 / 0.5f);

			position.add(0, addedPos, addedPos);

			x = delta * 40 / 0.5f;
			x = x * 5 / 40;

			y = delta * 40 / 0.5f;
			y = y * 5 / 40;

			addedX = addedX - Gdx.graphics.getWidth() / 100 * x;
			addedY = addedY - Gdx.graphics.getHeight() / 100 * y;

			if (position.y > Gdx.graphics.getHeight() * 0.8f || addedX <= 0
					|| addedY <= 0)
				activate = false;
		}
	}

	public void draw() {
		if (activate) {
			// draw line that sets the collision
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1, 1, 0, 1);
			shapeRenderer.triangle(
			// left poing
					position.x - addedX, position.y,
					// right point
					position.x + addedX, position.y,
					// top point
					position.x, position.y + addedY);
			shapeRenderer.end();
		}
	}

	public void dispose() {
		shapeRenderer.dispose();
	}

	public void setActivate(boolean activate) {
		this.activate = activate;

		position.set(Gdx.graphics.getWidth() / 2, 0, 0);
		addedX = Gdx.graphics.getWidth() * 0.1f;
		addedY = Gdx.graphics.getHeight() * 0.1f;
	}

	public boolean isActivate() {
		return activate;
	}

	public boolean isShooting() {
		return shooting;
	}

	public void setShooting(boolean shooting) {
		this.shooting = shooting;

		position.y = position.y + addedY / 2;
	}
}