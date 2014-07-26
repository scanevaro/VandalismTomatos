package com.turtleGames.vandalism.tomatos.classes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.turtleGames.vandalism.tomatos.entities.Target;

public class Assets {
	public Animation targetAnimation;
	public Animation target2Anim;
	public Animation dogAnim;
	public Animation catAnim;
	public Animation projectileAnim;
	public Animation girlAnim;
	public Animation copAnim;

	public Animation getAnimation(int type) {
		switch (type) {
			case Target.TARGET1:
				return targetAnimation;
			case Target.TARGET2:
				return target2Anim;
			case Target.DOG:
				return dogAnim;
			case Target.CAT:
				return catAnim;
			case Target.PIXEL_TARGET_1:
				return girlAnim;
			default:
				return null;
		}
	}
}
