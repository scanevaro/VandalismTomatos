package com.turtleGames.vandalism.tomatos.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.turtleGames.vandalism.tomatos.Tomatos;
import com.turtleGames.vandalism.tomatos.collections.Targets;
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
	public Targets targets;
	public ImpactSetter impactSetter;
	public Projectile projectile;

	public final WorldListener listener;
	Vector3 touchPoint;
	public int state;

	public WorldOrthoStyle(Tomatos game, WorldListener listener) {
		this.game = game;
		this.listener = listener;
		this.touchPoint = new Vector3();

		initiateBackground();
		initiateTargets();
		initiateImpactSetter();
		initiateProjectile();

		state = READY;
	}

	private void initiateBackground() {
		background = new Background(0, 0,
				(Texture) game.assetManager.get("data/background.png"));
	}

	private void initiateTargets() {
		targets = new Targets(7, (Texture) game.assetManager.get("data/kid50.png"));
	}

	private void initiateImpactSetter() {
		impactSetter = new ImpactSetter();
	}

	private void initiateProjectile() {
		projectile = new Projectile(game.assetManager.get("data/projectile.png",
				Texture.class));
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
	}

	private void updateImpactSetter(float delta) {
		impactSetter.update(delta);
	}

	private void updateProjectile(float delta) {
		projectile.update(delta);
	}

	private void updateTargets(float delta) {
		targets.update(delta);
	}

	private void updateCamera() {
		if (projectile.isUpdate()) {
			worldRenderer.orthoTargetsCam.zoom -= 0.01f;
			worldRenderer.orthoTargetsCam.position.y += 0.5f;
		} else if (projectile.stateTime >= 0) {
			worldRenderer.orthoTargetsCam.zoom += 0.01f;
			worldRenderer.orthoTargetsCam.position.y -= 0.5f;
		} else {
			worldRenderer.orthoTargetsCam.zoom = 1;
			worldRenderer.orthoTargetsCam.position.y = Gdx.graphics.getHeight() / 2;
		}
	}

	private void checkCollitions() {
		if (impactSetter.isShooting() && projectile.stateTime >= 0.5f) {
			for (int i = 0; i < targets.targets.size; i++) {
				Target target = targets.targets.get(i);
				if (target.spacePos.z <= projectile.spacePos.z + 8
						&& target.spacePos.z >= projectile.spacePos.z - 8)
					if (Intersector.overlaps(projectile.bounds, target.bounds))
						if (target.spacePos.z <= projectile.spacePos.z + 32
								&& target.spacePos.z >= projectile.spacePos.z - 32) {
							targets.targets.removeIndex(i);
							projectile.setUpdate(false);
						}
			}
		}
	}

	private void updateGameOver() {

	}

	public void dispose() {
		impactSetter.dispose();
	}
}