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

	public ImpactSetter() {
		shapeRenderer = new ShapeRenderer();

		position = new Vector3();
	}

	public void update(float delta) {
		if (activate) {
			position.add(-delta * 4 * 20, -delta * 8 * 20, -delta * 5.6f * 20);

			if (position.y < -80)
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
					Gdx.graphics.getWidth() / 2 - position.x,
					Gdx.graphics.getHeight() / 2 - position.y,
					// right point
					Gdx.graphics.getWidth() / 2 + position.x,
					Gdx.graphics.getHeight() / 2 - position.y,
					// top point
					Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2
							- position.z);
			shapeRenderer.end();
		}
	}

	public void dispose() {
		shapeRenderer.dispose();
	}

	public void setActivate(boolean activate) {
		this.activate = activate;

		position.set(90, 110, 60);
	}

	public boolean isActivate() {
		return activate;
	}

	public boolean isShooting() {
		return shooting;
	}

	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}
}