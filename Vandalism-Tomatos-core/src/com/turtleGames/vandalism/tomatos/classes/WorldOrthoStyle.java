package com.turtleGames.vandalism.tomatos.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.turtleGames.vandalism.tomatos.Tomatos;
import com.turtleGames.vandalism.tomatos.entities.Background;
import com.turtleGames.vandalism.tomatos.entities.ImpactSetter;
import com.turtleGames.vandalism.tomatos.entities.Projectile;
import com.turtleGames.vandalism.tomatos.entities.Target;
import com.turtleGames.vandalism.tomatos.screens.GameOrthoStyleScreen.GameState;

public class WorldOrthoStyle {

	public interface WorldListener {
		public void hitTarget();

		public void hitGround();

		public void throwTomato();
	}

	Tomatos game;

	public WorldRendererOrthoStyle worldRenderer;

	Background background;
	public ImpactSetter impactSetter;
	public Projectile projectile;

	public final WorldListener listener;
	Vector3 touchPoint;
	public int state;

	Array<Target> targets;
	private int hit;

	public WorldOrthoStyle(Tomatos game, WorldListener listener) {
		this.game = game;
		this.listener = listener;
		this.touchPoint = new Vector3();

		initiateBackground();
		initiateTargetsArray();
		initiateTargets();
		initiateImpactSetter();
		initiateProjectile();

		setHit(0);
		state = GameState.READY.ordinal();
	}

	private void initiateBackground() {
		background = new Background(0, 0,
				(Texture) game.assetManager.get("data/backgroundPixel.png"));
	}

	private void initiateTargetsArray() {
		targets = new Array<Target>();
	}

	private void initiateTargets() {

		switch (game.level) {
			case 1:
				Target target = new Target(game, Target.WALKING, null);
				targets.add(target);

				for (int i = 0; i < 5; i++) {
					target = new Target(game);
					targets.add(target);
				}
				break;
			case 2:
				// TODO
				break;
			case 3:
				// TODO
				break;
		}
	}

	private void initiateImpactSetter() {
		impactSetter = new ImpactSetter();
	}

	private void initiateProjectile() {
		projectile = new Projectile(game);
	}

	public void update(float deltaTime) {
		if (state == GameState.READY.ordinal())
			updateReady(deltaTime);
		else if (state == GameState.RUNNING.ordinal())
			updateRunning(deltaTime);
		else if (state == GameState.GAME_OVER.ordinal())
			updateGameOver();
		else if (state == GameState.WIN.ordinal())
			updateWin();
	}

	private void updateReady(float deltaTime) {

	}

	private void updateRunning(float delta) {
		updateImpactSetter(delta);
		updateProjectile(delta);
		updateTargets(delta);
		// updateCamera();
		checkCollisions();

		if (state == GameState.GAME_OVER.ordinal())
			return;

		checkTargetsState();
	}

	private void updateImpactSetter(float delta) {
		impactSetter.update(delta);
	}

	private void updateProjectile(float delta) {
		projectile.update(delta);
	}

	private void updateTargets(float delta) {
		for (int i = 0; i < targets.size; i++)
			targets.get(i).update(delta);
	}

	private void updateCamera() {
		if (projectile.state == Projectile.FLYING) {
			worldRenderer.orthoTargetsCam.zoom -= 0.01f;
			worldRenderer.orthoTargetsCam.position.y += 0.5f;
		} else if (projectile.state == Projectile.HIT
				|| projectile.state == Projectile.GROUND) {

		} else if (projectile.stateTime >= 0) {
			worldRenderer.orthoTargetsCam.zoom += 0.01f;
			worldRenderer.orthoTargetsCam.position.y -= 0.5f;
		} else {
			worldRenderer.orthoTargetsCam.zoom = 1;
			worldRenderer.orthoTargetsCam.position.y = Gdx.graphics.getHeight() / 2;
		}
	}

	private void checkCollisions() {
		if (impactSetter.isShooting()
				&& projectile.stateTime >= projectile.flyghtTime / 2
				&& projectile.state == Projectile.FLYING) {
			for (int i = 0; i < targets.size; i++) {
				Target target = targets.get(i);

				if (target.spacePos.z > projectile.impactSpot.x)
					continue;

				if (target.spacePos.z + target.bounds.width > projectile.spacePos.z
						+ projectile.bounds.radius
						&& target.spacePos.z - target.bounds.width < projectile.spacePos.z
						&& target.bounds.contains(projectile.spacePos.x,
								projectile.spacePos.y)
						&& target.getState() == Target.WALKING) {
					target.setState(Target.HIT);
					projectile.setState(Projectile.HIT);
					setHit(hit + 1);
					game.score += 10;

					if (target.type == Target.COP)
						state = GameState.GAME_OVER.ordinal();
				}
			}
		}
	}

	private void checkTargetsState() {
		if (hit == game.getHitsToPolice())
			targets.add(new Target(game, 4));
		else if (hit == game.getHitsToWin())
			state = GameState.WIN.ordinal();

		for (int i = 0; i < targets.size; i++) {
			Target target = targets.get(i);
			if (target.type != Target.COP)
				if (target.getState() == Target.WALKING)
					return;
		}
	}

	private void updateGameOver() {
		// TODO
	}

	private void updateWin() {
		// TODO
	}

	public void dispose() {
		impactSetter.dispose();
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}
}