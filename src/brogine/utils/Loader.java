package brogine.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import brogine.Mesh;

public abstract class Loader {

	private static List<Integer> vaoList = new ArrayList<Integer>();
	private static List<Integer> vboList = new ArrayList<Integer>();
	private static List<Integer> textureList = new ArrayList<Integer>();
	
	public static Mesh loadMesh(float[] vertices, float[] texCoords, int[] indices, String textureFile) {
		int vaoID = createVAO();
		bindVAO(vaoID);
		storeDataInVAO(0, 3, vertices, vaoID);
		storeDataInVAO(1, 2, texCoords, vaoID);
		
		int vboIndicesID = createVBO();
		bindVBO(vboIndicesID, GL15.GL_ELEMENT_ARRAY_BUFFER);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(
				GL15.GL_ELEMENT_ARRAY_BUFFER,
				buffer,
				GL15.GL_STATIC_DRAW
		);
		unbindVBO(GL15.GL_ELEMENT_ARRAY_BUFFER);
		unbindVAO();
		
		Texture texture = loadTexture(textureFile);
		
		return new Mesh(vaoID, vboIndicesID, indices.length, texture);
	}
	
	public static void clean() {
		for (int vaoID : vaoList) {
			unbindVAO();
			GL30.glDeleteVertexArrays(vaoID);
		}
		for (int vboID : vboList) {
			unbindVBO(GL15.GL_ARRAY_BUFFER);
			unbindVBO(GL15.GL_ELEMENT_ARRAY_BUFFER);
			GL15.glDeleteBuffers(vboID);
		}
		for (int textureID : textureList) {
			GL11.glDeleteTextures(textureID);
		}
	}
	
	private static Texture loadTexture(String textureFile) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", Loader.class.getResourceAsStream("/res/textures/" + textureFile + ".png"));
		} catch (FileNotFoundException e) {
			System.err.println("Unable to find " + textureFile);
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Unable to read " + textureFile);
			e.printStackTrace();
			System.exit(1);
		}
		textureList.add(texture.getTextureID());
		return texture;
	}
	
	private static void bindVBO(int vboID, int target) {
		GL15.glBindBuffer(target, vboID);
	}
	
	private static void unbindVBO(int target) {
		GL15.glBindBuffer(target, 0);
	}
	
	private static int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaoList.add(vaoID);
		return vaoID;
	}
	
	private static int createVBO() {
		int vboID = GL15.glGenBuffers();
		vboList.add(vboID);
		return vboID;
	}
	
	private static void bindVAO(int vaoID) {
		GL30.glBindVertexArray(vaoID);
	}
	
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}

	private static void storeDataInVAO(int index, int size, float[] data,
			int vaoID) {
		int vboID = createVBO();
		bindVBO(vboID, GL15.GL_ARRAY_BUFFER);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0); 
		unbindVBO(GL15.GL_ARRAY_BUFFER);
	}
	
	private static FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private static IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
}
