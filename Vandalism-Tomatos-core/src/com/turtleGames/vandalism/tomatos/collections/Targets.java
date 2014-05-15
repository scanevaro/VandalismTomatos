package com.turtleGames.vandalism.tomatos.collections;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.turtleGames.vandalism.tomatos.entities.Target;

public class Targets {

	public Array<Target> targets;
	Animation animation;

	public Targets(int howMany, Animation animation) {

		this.animation = animation;
		targets = new Array<Target>();
		float x = 0, y = 0;
		Random rand = new Random();

		for (int i = 0; i < howMany; i++) {

			if (rand.nextFloat() > 0.5f)
				x = 640;
			else
				x = 0;

			y = rand.nextFloat() * 250;

			Target target = new Target(x, y, animation.getKeyFrame(0)
					.getRegionWidth(), animation.getKeyFrame(0)
					.getRegionHeight());
			targets.add(target);
		}
	}

	public void render(SpriteBatch batcher) {
		for (Target target : targets) {
			batcher.draw(animation.getKeyFrame(target.stateTime, true),
					target.spacePos.x, target.spacePos.y, target.dimensions.x,
					target.dimensions.y);
		}
	}

	public void update(float delta) {
		for (Target target : targets) {
			target.update(delta);
		}
	}
}