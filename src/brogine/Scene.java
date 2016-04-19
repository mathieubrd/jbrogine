package brogine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import brogine.entities.Camera;
import brogine.entities.Entity;
import brogine.entities.Light;
import brogine.entities.Terrain;
import brogine.entities.Water;
import brogine.shaders.TerrainShader;
import brogine.shaders.WaterShader;
import brogine.utils.Loader;
import brogine.utils.MathUtils;

public class Scene {
	
	public static final float POLY_SIZE = 1f;
	
	private static final float FOV = 70;
	private static final float ASPECT = (float) DisplayManager.WIDTH / (float) DisplayManager.HEIGHT;
	private static final float NEAR = 0.1f;
	private static final float FAR = 1000;

	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	
	private WaterShader waterShader;
	private TerrainShader terrainShader;
	
	private Water water;
	private Terrain terrain;
	
	private Camera camera;
	private Light light;
	
	private Entity entity;
	
	public Scene() {
		projectionMatrix = MathUtils.createProjectionMatrix(FOV, ASPECT, NEAR, FAR);
		viewMatrix = new Matrix4f();
		
		waterShader = new WaterShader();
		terrainShader = new TerrainShader();
		
		terrain = new Terrain("heightmap");

		water = new Water(terrain.getWidth(), terrain.getHeight(), 1f);
		
		camera = new Camera(new Vector3f(0, 0, 0), 10f, 0f, 0f);
		light = new Light(new Vector3f(0, 50, 0), new Vector3f(1, 1, 1));	
	}
	
	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glCullFace(GL11.GL_BACK);
		
		// Déplacement de la caméra
		camera.move();
		
		// Mouvement de l'eau
		water.tick();
		
		// Calcul de la matrice de vue
		viewMatrix = MathUtils.createViewMatrix(camera);
		
		TerrainRenderer.render(terrain, light, terrainShader, viewMatrix, projectionMatrix);
		WaterRenderer.render(water, waterShader, viewMatrix, projectionMatrix, light, camera);
		
		DisplayManager.update();
	}
	
	public void clean() {
		waterShader.clean();
		Loader.clean();
	}
	
}
