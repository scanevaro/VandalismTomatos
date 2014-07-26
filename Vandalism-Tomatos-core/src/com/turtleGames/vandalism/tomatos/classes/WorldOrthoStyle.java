package com.turtleGames.vandalism.tomatos.classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.turtleGames.vandalism.tomatos.Tomatos;
import com.turtleGames.vandalism.tomatos.entities.Background;
import com.turtleGames.vandalism.tomatos.entities.ImpactSetter;
import com.turtleGames.vandalism.tomatos.entities.Projectile;
import com.turtleGames.vandalism.tomatos.entities.Target;

public class WorldOrthoStyle {

	public final static int READY = 0;
	public final static int RUNNING = 1;
	public final static int GAME_OVER = 2;

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

	public WorldOrthoStyle(Tomatos game, WorldListener listener) {
		this.game = game;
		this.listener = listener;
		this.touchPoint = new Vector3();

		initiateBackground();
		initiateTargetsArray();
		initiateTargets();
		initiateImpactSetter();
		initiateProjectile();

		state = READY;
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

		switch (state) {
			case READY:
				updateReady(deltaTime);
				break;
			case RUNNING:
				updateRunning(deltaTime);
				break;
			case GAME_OVER:
				updateGameOver();
				break;
		}
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

	}

	public void dispose() {
		impactSetter.dispose();
	}
}