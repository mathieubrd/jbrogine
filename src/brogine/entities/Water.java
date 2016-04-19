package brogine.entities;

import java.util.ArrayList;
import java.util.List;

import brogine.Mesh;
import brogine.Scene;
import brogine.utils.Loader;

public class Water {
	
	private float waveTime;
	private int width;
	private int height;
	private float y;
	private Mesh mesh;
	
	public Water(int width, int height, float y) {
		waveTime = 0;
		this.width = width;
		this.height = height;
		this.y = y;
		mesh = generateWater();
		mesh.setShineDamper(50);
		mesh.setReflectivity(0.5f);
	}
	
	public void tick() {
		waveTime += 0.01f;
	}
	
	public Mesh generateWater() {	
		List<Float> vertices = new ArrayList<Float>();
		List<Float> texCoords = new ArrayList<Float>();
		List<Integer> indices = new ArrayList<Integer>();
		
		for (int i = 0; i <= height; i++) {
			for (int j = 0; j <= width; j++) {
				vertices.add((float) (j * Scene.POLY_SIZE));
				vertices.add(y);
				vertices.add((float) (i * Scene.POLY_SIZE));
				
				texCoords.add((float) (j % 2) * Scene.POLY_SIZE);
				texCoords.add((float) (i % 2) * Scene.POLY_SIZE);
			}
		}
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				indices.add(j + (i * (width + 1)));
				indices.add(j + (i * (width + 1)) + width + 2);
				indices.add(j + (i * (width + 1)) + 1);
				
				indices.add(j + (i * (width + 1)));
				indices.add(j + (i * (width + 1)) + width + 1);
				indices.add(j + (i * (width + 1)) + width + 2);
			}
		}
		
		float[] vertexArray = new float[vertices.size()];
		float[] texCoordsArray = new float[texCoords.size()];
		int[] indexArray = new int[indices.size()];
		for (int i = 0; i < vertices.size(); i++) {
			vertexArray[i] = vertices.get(i);
		}
		for (int i = 0; i < texCoords.size(); i++) {
			texCoordsArray[i] = texCoords.get(i);
		}
		for (int i = 0; i < indices.size(); i++) {
			indexArray[i] = indices.get(i);
		}
		
		return Loader.loadMesh(vertexArray, texCoordsArray, indexArray, "water");
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public float getY() {
		return y;
	}

	public Mesh getMesh() {
		return mesh;
	}
	
	public float getWaveTime() {
		return waveTime;
	}
	
}
