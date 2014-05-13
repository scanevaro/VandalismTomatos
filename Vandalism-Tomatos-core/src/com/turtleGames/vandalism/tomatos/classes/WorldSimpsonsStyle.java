package com.turtleGames.vandalism.tomatos.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.turtleGames.vandalism.tomatos.Tomatos;
import com.turtleGames.vandalism.tomatos.collections.Targets;
import com.turtleGames.vandalism.tomatos.entities.Background;
import com.turtleGames.vandalism.tomatos.entities.ImpactSetter;
import com.turtleGames.vandalism.tomatos.entities.Projectile;
import com.turtleGames.vandalism.tomatos.entities.Target;

public class WorldSimpsonsStyle {

	public enum WorldState {
		READY, RUNNING, GAME_OVER
	}

	public interface WorldListener {
		public void hitTarget();

		public void hitGround();
	}

	Tomatos game;

	public WorldRendererSimpsonsStyle worldRenderer;

	public Array<ModelInstance> instances = new Array<ModelInstance>();
	Model floorModel;
	Background background;
	public Targets targets;
	public ImpactSetter impactSetter;
	public Projectile projectile;

	public final WorldListener listener;
	Vector3 touchPoint;
	public int state;

	public WorldSimpsonsStyle(Tomatos game, WorldListener listener) {
		this.game = game;
		this.listener = listener;
		this.touchPoint = new Vector3();

		initiateBackground();
		initiateFloorModel();
		initiateTargets();
		initiateImpactSetter();
		initiateProjectile();

		state = WorldState.READY.ordinal();
	}

	private void initiateBackground() {
		background = new Background(0, 30,
				(Texture) game.assetManager.get("data/springGrass.png"));
	}

	private void initiateFloorModel() {
		ModelBuilder builder = new ModelBuilder();
		builder.begin();
		MeshPartBuilder part = builder.part("floor", GL20.GL_TRIANGLES,
				Usage.Position | Usage.TextureCoordinates | Usage.Normal,
				new Material());
		part.rect(-100, 0, -200, -100, 0, 200, 100, 0, 200, 100, 0, -200, 0, 1,
				0);
		floorModel = builder.end();

		floorModel.materials.get(0).set(
				TextureAttribute.createDiffuse(game.assetManager.get(
						"data/grassStripe.png", Texture.class)));
		instances.add(new ModelInstance(floorModel));
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
		if (state == WorldState.READY.ordinal()) {
			updateReady(deltaTime);
		} else if (state == WorldState.RUNNING.ordinal()) {
			updateRunning(deltaTime);
		} else if (state == WorldState.GAME_OVER.ordinal()) {
			updateGameOver();
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

			worldRenderer.perspCam.position.x += 0.5f;
		} else if (projectile.stateTime >= 0) {
			worldRenderer.orthoTargetsCam.zoom += 0.01f;
			worldRenderer.orthoTargetsCam.position.y -= 0.5f;

			worldRenderer.perspCam.position.x -= 0.5f;
		} else {
			worldRenderer.orthoTargetsCam.zoom = 1;
			worldRenderer.orthoTargetsCam.position.y = Gdx.graphics.getHeight() / 2;

			worldRenderer.perspCam.position.x = -60;
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
		floorModel.dispose();
	}
}