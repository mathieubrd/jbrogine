package brogine.utils;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import brogine.entities.Camera;

public abstract class MathUtils {

	public static Matrix4f createProjectionMatrix(float fov, float aspect, float near, float far) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(fov / 2f))) * aspect);
		float x_scale = y_scale / aspect;
		float frustum_length = far - near;

		matrix.m00 = x_scale;
		matrix.m11 = y_scale;
		matrix.m22 = -((far + near) / frustum_length);
		matrix.m23 = -1;
		matrix.m32 = -((2 * near * far) / frustum_length);
		matrix.m33 = 0;
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(Camera camera) {
		Vector3f eye = camera.getPosition();
		Vector3f lookAt = camera.getCenter();
		Vector3f up = new Vector3f(0, 1, 0);
		
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		
		// Create the basis vectors
		Vector3f forwards = Vector3f.sub(eye, lookAt, null);
		forwards.normalise();
		
		Vector3f right = Vector3f.cross(up, forwards, null);
		right.normalise();
		
		Vector3f actualUp = Vector3f.cross(forwards, right, null);
		actualUp.normalise();
		
		// Right vector across the top
		matrix.m00 = right.x;
		matrix.m10 = right.y;
		matrix.m20 = right.z;
		
		// Up vector across the middle row
		matrix.m01 = actualUp.x;
		matrix.m11 = actualUp.y;
		matrix.m21 = actualUp.z;
		
		// Forwards vector across the bottom row
		matrix.m02 = forwards.x;
		matrix.m12 = forwards.y;
		matrix.m22 = forwards.z;
		
		// Negative translation in the last column
		Matrix4f translation = new Matrix4f();
		translation.setIdentity();
		translation.translate(new Vector3f(-eye.x, -eye.y, -eye.z));
		
		return Matrix4f.mul(matrix, translation, null);
	}
	
}
