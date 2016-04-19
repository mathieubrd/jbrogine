package brogine.shaders;

public class EntityShader extends Shader {

	private static final String VERTEX_FILE = "src/brogine/shaders/vertex/entityVertex.txt";
	private static final String GEOMETRY_FILE = "src/brogine/shaders/geometry/entityGeometry.txt";
	private static final String FRAGMENT_FILE = "src/brogine/shaders/fragment/entityFragment.txt";
	
	public EntityShader() {
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
		getUniformLocation("shineDamper");
		getUniformLocation("reflectivity");
	}

}
