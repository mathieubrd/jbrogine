package brogine.entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private static final float SPEED = 0.005f;
	private static final float MIN_ZOOM = 5;
	private static final float MAX_ZOOM = 50;
	
	private Vector3f center;
	private float radius;
	private float longitude;
	private float colatitude;
	
	public Camera(Vector3f center, float radius, float longitude, float colatitude) {
		this.center = center;
		this.radius = radius;
		this.longitude = longitude;
		this.colatitude = colatitude;
	}
	
	public void move() {
		radius += Mouse.getDWheel() * SPEED;
		radius = Math.max(radius, MIN_ZOOM);
		radius = Math.min(radius, MAX_ZOOM);
		
		if (Mouse.isButtonDown(0)) {
			colatitude += Mouse.getDY() * SPEED;
			colatitude = (float) Math.min(colatitude, Math.toRadians(80));
			colatitude = (float) Math.max(colatitude, Math.toRadians(20));
			longitude += Mouse.getDX() * SPEED;
		}
	}
	
	public Vector3f getCenter() {
		return center;
	}
	
	public Vector3f getPosition() {
		float x = (float) (radius * Math.cos(colatitude) * Math.cos(longitude));
		float y = (float) (radius * Math.sin(colatitude));
		float z = (float) (radius * Math.cos(colatitude) * Math.sin(longitude));
		
		return new Vector3f(x, y, z);
	}
	
}
