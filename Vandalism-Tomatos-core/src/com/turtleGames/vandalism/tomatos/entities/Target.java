package com.turtleGames.vandalism.tomatos.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.turtleGames.vandalism.tomatos.classes.Dynamic3DGameObject;

public class Target extends Dynamic3DGameObject {

	public float stateTime;
	private Animation animation;

	public Target(float x, float y, float width, float height,
			Animation animation) {
		super(x, y, width, height);

		stateTime = 0;

		setZ();

		bounds = new Rectangle(x - width / 2, y - height / 2, width, height);

		this.setAnimation(animation);
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

		stateTime += delta;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
}