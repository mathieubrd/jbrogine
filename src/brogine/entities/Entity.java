package brogine.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

import brogine.Mesh;

public class Entity {

	private Mesh mesh;
	private Terrain terrain;
	private Vector3f position;
	private Vector3f rotation;
	private float scale;
	
	public Entity(Mesh mesh, Terrain terrain, Vector3f position, Vector3f rotation, float scale) {
		this.mesh = mesh;
		this.terrain = terrain;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public void move() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.z -= 0.1f;
		} if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.z += 0.1f;
		} if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			position.x -= 0.1f;
		} if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			position.x += 0.1f;
		}
	}
	
	public void increatePosition(Vector3f position) {
		this.position.x += position.x;
		this.position.y += position.y;
		this.position.z += position.z;
	}
	
	public void increaseRotation(Vector3f rotation) {
		this.rotation.x += rotation.x;
		this.rotation.y += rotation.y;
		this.rotation.z += rotation.z;
	}
	
	public Mesh getMesh() {
		return mesh;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}
	
	public float getScale() {
		return scale;
	}
	
	// DEBUG
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
}
