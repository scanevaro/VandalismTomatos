package com.turtleGames.vandalism.tomatos.classes;

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
		for (int i = 0; i < 9; i++) {
			Target target = new Target(game);

			targets.add(target);
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
	}

	private void updateReady(float deltaTime) {

	}

	private void updateRunning(float delta) {
		updateImpactSetter(delta);
		updateProjectile(delta);
		updateTargets(delta);
		// updateCamera();
		checkCollisions();
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

	// private void updateCamera() {
	// if (projectile.state == Projectile.FLYING) {
	// worldRenderer.orthoTargetsCam.zoom -= 0.01f;
	// worldRenderer.orthoTargetsCam.position.y += 0.5f;
	// } else if (projectile.state == Projectile.HIT
	// || projectile.state == Projectile.GROUND) {
	//
	// } else if (projectile.stateTime >= 0) {
	// worldRenderer.orthoTargetsCam.zoom += 0.01f;
	// worldRenderer.orthoTargetsCam.position.y -= 0.5f;
	// } else {
	// worldRenderer.orthoTargetsCam.zoom = 1;
	// worldRenderer.orthoTargetsCam.position.y = Gdx.graphics.getHeight() / 2;
	// }
	// }

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
								projectile.spacePos.y)) {
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
		for (int i = 0; i < targets.size; i++) {
			Target target = targets.get(i);
			if (target.getState() == Target.REMOVE) {
				targets.removeIndex(i);

				targets.add(new Target(game));
			}
		}
	}

	private void updateGameOver() {
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

		if (hit == 1)
			targets.add(new Target(game, 4));
	}
}