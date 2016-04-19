package brogine;

import org.newdawn.slick.opengl.Texture;

public class Mesh {

	private int vaoID;
	private int vboIndicesID;
	private int indicesCount;
	private Texture texture;
	private float reflectivity;
	private float shineDamper;
	
	public Mesh(int vaoID, int vboIndicesID, int indicesCount, Texture texture) {
		this.vaoID = vaoID;
		this.vboIndicesID = vboIndicesID;
		this.indicesCount = indicesCount;
		this.texture = texture;
		reflectivity = 1f;
		shineDamper = 10f;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVboIndicesID() {
		return vboIndicesID;
	}

	public int getIndicesCount() {
		return indicesCount;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public float getReflectivity() {
		return reflectivity;
	}
	
	public float getShineDamper() {
		return shineDamper;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}
	
}
