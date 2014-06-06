package com.turtleGames.vandalism.tomatos.classes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.turtleGames.vandalism.tomatos.entities.Target;

public class Assets {
	public Animation targetAnimation;
	public Animation target2Animation;
	public Animation dogAnimation;
	public Animation catAnimation;
	public Animation projectileAnimation;

	public Animation getAnimation(int type) {
		switch (type) {
			case Target.TARGET1:
				return targetAnimation;
			case Target.TARGET2:
				return target2Animation;
			case Target.DOG:
				return dogAnimation;
			case Target.CAT:
				return catAnimation;
			default:
				return null;
		}
	}
}
