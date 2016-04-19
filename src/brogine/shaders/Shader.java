package brogine.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public abstract class Shader {

	private static final FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	private int programID;
	private int vertexShaderID;
	private int geometryShaderID;
	private int fragmentShaderID;
	private Map<String, Integer> uniformLocations;
	
	public Shader(String vertexShaderFile, String fragmentShaderFile) {
		this(vertexShaderFile, null, fragmentShaderFile);
	}
	
	public Shader(String vertexShaderFile, String geometryShaderFile, String fragmentShaderFile) {
		vertexShaderID = loadShader(vertexShaderFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentShaderFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		uniformLocations = new HashMap<String, Integer>();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		
		// Optional geometry shader
		if (geometryShaderFile != null) {
			geometryShaderID = loadShader(geometryShaderFile, GL32.GL_GEOMETRY_SHADER);
			GL20.glAttachShader(programID, geometryShaderID);
		}
		
		setAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getUniformLocations();
	}
	
	protected abstract void setAttributes();
	protected abstract void getUniformLocations();
	
	protected void setAttribute(int attribute, String attributeName) {
		GL20.glBindAttribLocation(programID, attribute, attributeName);
	}
	
	protected void getUniformLocation(String uniformName) {
		uniformLocations.put(uniformName, GL20.glGetUniformLocation(programID, uniformName));
	}
	
	public void loadFloat(float value, String uniformName) {
		GL20.glUniform1f(uniformLocations.get(uniformName), value);
	}
	
	public void loadVector3(Vector3f vector, String uniformName) {
		GL20.glUniform3f(uniformLocations.get(uniformName), vector.x, vector.y, vector.z);
	}
	
	public void loadMatrix4(Matrix4f matrix, String uniformName) {
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4(uniformLocations.get(uniformName), false, matrixBuffer);
	}
	
	public void start() {
		GL20.glUseProgram(programID);
	}
	
	public void stop() {
		GL20.glUseProgram(0);
	}
	
	public void clean() {
		stop();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}
	
	private static int loadShader(String file, int type) {
		StringBuilder shaderSource = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Could not read file");
			e.printStackTrace();
			System.exit(1);
		}
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader");
			System.exit(1);
		}
		return shaderID;
	}
	
}
