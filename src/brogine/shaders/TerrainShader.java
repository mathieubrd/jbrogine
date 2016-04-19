package brogine.shaders;

public class TerrainShader extends Shader {

	private static final String VERTEX_FILE = "src/brogine/shaders/vertex/terrainVertex.txt";
	private static final String GEOMETRY_FILE = "src/brogine/shaders/geometry/terrainGeometry.txt";
	private static final String FRAGMENT_FILE = "src/brogine/shaders/fragment/terrainFragment.txt";
	
	public TerrainShader() {
		super(VERTEX_FILE, GEOMETRY_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void setAttributes() {
		setAttribute(0, "inVertex");
		setAttribute(1, "inTexCoords");
	}

	@Override
	protected void getUniformLocations() {
		getUniformLocation("model");
		getUniformLocation("view");
		getUniformLocation("projection");
		getUniformLocation("lightPosition");
		getUniformLocation("lightColor");
	}

	
	
}
