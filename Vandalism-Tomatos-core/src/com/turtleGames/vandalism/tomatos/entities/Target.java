package com.turtleGames.vandalism.tomatos.entities;

import com.badlogic.gdx.math.Rectangle;
import com.turtleGames.vandalism.tomatos.classes.Dynamic3DGameObject;

public class Target extends Dynamic3DGameObject {

	public Target(float x, float y, float width, float height) {
		super(x, y, width, height);

		setZ();

		bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
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

	public void update(float delta) {
		spacePos.add(velocity.x * delta, 0, 0);
		bounds.set(spacePos.x - width / 2, spacePos.y - height / 2, width,
				height);
	}
}