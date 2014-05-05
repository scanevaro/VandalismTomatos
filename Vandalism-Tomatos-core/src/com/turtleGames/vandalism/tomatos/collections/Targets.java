package com.turtleGames.vandalism.tomatos.collections;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.turtleGames.vandalism.tomatos.entities.Target;

public class Targets {

	public Array<Target> targets;
	Texture texture;

	public Targets(int howMany, Texture texture) {

		this.texture = texture;
		targets = new Array<Target>();
		float x = 0, y = 0;
		Random rand = new Random();

		for (int i = 0; i < howMany; i++) {

			if (rand.nextFloat() > 0.5f)
				x = 640;
			else
				x = 0;

			y = rand.nextFloat() * 250;

			Target target = new Target(x, y, texture.getWidth(),
					texture.getHeight());
			targets.add(target);
		}
	}

	public void render(SpriteBatch batcher) {
		for (Target target : targets) {
			batcher.draw(texture, target.spacePos.x, target.spacePos.y,
					target.dimensions.x, target.dimensions.y);
		}
	}

	public void update(float delta) {
		for (Target target : targets) {
			target.update(delta);
		}
	}
}