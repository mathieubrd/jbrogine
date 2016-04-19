package brogine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import brogine.entities.Camera;
import brogine.entities.Light;
import brogine.entities.Water;
import brogine.shaders.WaterShader;

public abstract class WaterRenderer {

	public static void render(Water water, WaterShader shader, Matrix4f viewMatrix, Matrix4f projectionMatrix, Light light, Camera camera) {
		Mesh mesh = water.getMesh();
		Texture texture = mesh.getTexture();
		
		// Calcul de la matrice de transformation
		Matrix4f modelMatrix = new Matrix4f();
		float x = -(water.getWidth() * Scene.POLY_SIZE) / 2;
		float z = -(water.getHeight() * Scene.POLY_SIZE) / 2;
		modelMatrix.translate(new Vector3f(x, 0, z));
		
		// Activation du shader
		shader.start();
		
		// Envoi de la matrice de transformation
		shader.loadMatrix4(modelMatrix, "model");
		// Envoi de la matrice de vue
		shader.loadMatrix4(viewMatrix, "view");
		// Envoi de la matrice de projection
		shader.loadMatrix4(projectionMatrix, "projection");
		// Envoi de temps de vague
		shader.loadFloat(water.getWaveTime(), "waveTime");
		// Envoi de la position de la lumière
		shader.loadVector3(light.getPosition(), "lightPosition");
		// Envoi de la couleur de la lumière
		shader.loadVector3(light.getColor(), "lightColor");
		// Envoi du taux d'absorption du matériau
		shader.loadFloat(mesh.getShineDamper(), "shineDamper");
		// Envoi du taux de reflection du matériau
		shader.loadFloat(mesh.getReflectivity(), "reflectivity");
		
		// Active le VAO
		GL30.glBindVertexArray(mesh.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		// Activation de la texture
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		
		// Active le VBO des indices
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getVboIndicesID());
		
		// Trace la forme
		GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndicesCount(), GL11.GL_UNSIGNED_INT, 0);
		
		// Désactive le VBO et le VAO
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getVboIndicesID());
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		
		GL30.glBindVertexArray(0);
		
		// Désactivation du shader
		shader.stop();
	}
	
}
