package com.turtleGames.vandalism.tomatos.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
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
				(Texture) game.assetManager.get("data/background.png"));
	}

	private void initiateTargetsArray() {
		targets = new Array<Target>();
	}

	private void initiateTargets() {
		for (int i = 0; i < 4; i++) {
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
		updateCamera();
		checkCollitions();
		checkTargets();
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

	private void checkCollitions() {
		if (impactSetter.isShooting() && projectile.stateTime >= 0.5f
				&& projectile.state == Projectile.FLYING) {
			for (int i = 0; i < targets.size; i++) {
				Target target = targets.get(i);
				if (target.spacePos.z <= projectile.spacePos.z + 8
						&& target.spacePos.z >= projectile.spacePos.z - 8)
					if (Intersector.overlaps(projectile.bounds, target.bounds))
						if (target.spacePos.z <= projectile.spacePos.z + 32
								&& target.spacePos.z >= projectile.spacePos.z - 32) {
							target.setState(Target.HIT);
							projectile.setState(Projectile.HIT);
						}
			}
		}
	}

	private void checkTargets() {
		if (targets.size == 0) {
			initiateTargets();
		}
	}

	private void updateGameOver() {

	}

	public void dispose() {
		impactSetter.dispose();
	}
}