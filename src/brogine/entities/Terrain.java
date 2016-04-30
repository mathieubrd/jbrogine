package brogine.entities;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import brogine.Mesh;
import brogine.Scene;
import brogine.utils.Loader;

public class Terrain {

	private static final float MAX_HEIGHT = 12f;
	private static final float MAX_PIXEL_COLOUR = 256 * 256 * 256;
	
	private Mesh mesh;
	private float x;
	private float z;
	private int width;
	private int height;
	
	public Terrain(String heightMap) {
		mesh = generateTerrain(heightMap);
		x = 0;
		z = 0;
	}
	
	public Mesh generateTerrain(String heightMap) {
		InputStream stream = this.getClass().getResourceAsStream("/res/" + heightMap + ".png");
		BufferedImage image = null;
		try {
			image = ImageIO.read(stream);
		} catch (IOException e) {
			System.err.println("Unable to read " + heightMap);
			e.printStackTrace();
			System.exit(1);
		}
		
		width = image.getWidth();
		height = image.getHeight();
		
		List<Float> vertices = new ArrayList<Float>();
		List<Float> texCoords = new ArrayList<Float>();
		List<Integer> indices = new ArrayList<Integer>();
		
		for (int i = 0; i <= height; i++) {
			for (int j = 0; j <= width; j++) {
				vertices.add((float) (j * Scene.POLY_SIZE));
				float height = getHeight(j, i, image);
				if (i == 0 || j == 0) {
					if (height < 0) {
						height = 0;
					}
				}
				vertices.add(height);
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
		
		return Loader.loadMesh(vertexArray, texCoordsArray, indexArray, "terrainTexture");
	}
	
	private float getHeight(int x, int z, BufferedImage image) {
		if (x < 0 || x >= image.getHeight() || z < 0 || z >= image.getHeight()) {
			return 0;
		}
		float height = image.getRGB(x, z);
		height += MAX_PIXEL_COLOUR / 2f;
		height /= MAX_PIXEL_COLOUR / 2f;
		height *= MAX_HEIGHT;
		return Math.max(height, -2);
	}

	public Mesh getMesh() {
		return mesh;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

}