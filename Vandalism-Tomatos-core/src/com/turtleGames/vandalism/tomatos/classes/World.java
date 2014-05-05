package com.turtleGames.vandalism.tomatos.classes;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.turtleGames.vandalism.tomatos.Tomatos;
import com.turtleGames.vandalism.tomatos.collections.Targets;
import com.turtleGames.vandalism.tomatos.entities.Background;
import com.turtleGames.vandalism.tomatos.entities.ImpactSetter;
import com.turtleGames.vandalism.tomatos.entities.Projectile;

public class World {

	private enum WorldState {
		READY, RUNNING, GAME_OVER
	}

	public interface WorldListener {
		public void hitTarget();

		public void hitGround();
	}

	Tomatos game;

	public Array<ModelInstance> instances = new Array<ModelInstance>();
	Model floorModel;
	Background background;
	Targets targets;
	ImpactSetter impactSetter;
	Projectile projectile;

	public final WorldListener listener;
	Vector3 touchPoint;
	int state;

	public World(Tomatos game, WorldListener listener) {
		this.game = game;
		this.listener = listener;
		this.touchPoint = new Vector3();

		initiateBackground();
		initiateFloorModel();
		initiateTargets();
		initiateImpactSetter();
		initiateProjectile();

		generateRound();

		state = WorldState.READY.ordinal();
	}

	private void initiateBackground() {
		background = new Background(0, 30,
				(Texture) game.assets.get("data/springGrass.png"));
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
				TextureAttribute.createDiffuse(game.assets.get(
						"data/grassStripe.png", Texture.class)));
		instances.add(new ModelInstance(floorModel));
	}

	private void initiateTargets() {
		targets = new Targets(7, (Texture) game.assets.get("data/kid50.png"));
	}

	private void initiateImpactSetter() {
		impactSetter = new ImpactSetter();
	}

	private void initiateProjectile() {
		projectile = new Projectile(game.assets.get("data/projectile.png",
				Texture.class));
	}

	private void generateRound() {

	}

	public void update(float deltaTime) {
		if (state == WorldState.READY.ordinal()) {
			stateReady(deltaTime);
		} else if (state == WorldState.RUNNING.ordinal()) {
			stateRunning(deltaTime);
		} else if (state == WorldState.GAME_OVER.ordinal()) {
			stateGameOver();
		}
	}

	private void stateReady(float deltaTime) {

	}

	private void stateRunning(float deltaTime) {

	}

	private void stateGameOver() {

	}

	public void dispose() {
		impactSetter.dispose();
		floorModel.dispose();
	}
}