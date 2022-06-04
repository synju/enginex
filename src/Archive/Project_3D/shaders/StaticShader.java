package Archive.Project_3D.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import Archive.Project_3D.engineTester.Game;
import Archive.Project_3D.entities.Camera;
import Archive.Project_3D.entities.Light;
import Archive.Project_3D.toolbox.Maths;

import java.util.List;

public class StaticShader extends ShaderProgram {
	public static final int MAX_LIGHTS = 16;

	private static final String VERTEX_FILE = "src/Archive/Project_3D/shaders/vertexShader.vert";
	private static final String FRAGMENT_FILE = "src/Archive/Project_3D/shaders/fragmentShader.frag";

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_currentLightCount;
	private int location_lightPosition[];
	private int location_lightColor[];
	private int location_attenuation[];
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_useFakeLighting;
	private int location_skyColor;
	private int location_density;
	private int location_gradient;
	private int location_numberOfRows;
	private int location_offset;
	private int currentLightCount;

	public Game game;

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	public void setCurrentLightCount(int count) {
		this.currentLightCount = count;
	}

	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_useFakeLighting = super.getUniformLocation("useFakeLighting");
		location_skyColor = super.getUniformLocation("skyColor");
		location_density = super.getUniformLocation("density");
		location_gradient = super.getUniformLocation("gradient");
		location_numberOfRows = super.getUniformLocation("numberOfRows");
		location_offset = super.getUniformLocation("offset");
		location_currentLightCount = super.getUniformLocation("currentLightCount");

		location_lightPosition = new int[MAX_LIGHTS];
		location_lightColor = new int[MAX_LIGHTS];
		location_attenuation = new int[MAX_LIGHTS];

		for(int i = 0; i < MAX_LIGHTS; i++) {
			location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			location_lightColor[i] = super.getUniformLocation("lightColor[" + i + "]");
			location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
		}
	}

	public void loadNumberOfRows(int numberOfRows) {
		super.loadFloat(location_numberOfRows, numberOfRows);
	}

	public void loadOffset(float x, float y) {
		super.load2DVector(location_offset, new Vector2f(x, y));
	}

	public void loadDensityAndGradient(float density, float gradient) {
		super.loadFloat(location_density, density);
		super.loadFloat(location_gradient, gradient);
	}

	public void loadSkyColor(float r, float g, float b) {
		super.loadVector(location_skyColor, new Vector3f(r, g, b));
	}

	public void loadFakeLightingVariable(boolean useFake) {
		super.loadBoolean(location_useFakeLighting, useFake);
	}

	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}

	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoordinates");
		super.bindAttribute(2, "normals");
	}

	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	public void loadCurrentLightCount() {
		super.loadInt(location_currentLightCount, currentLightCount);
	}

	public void loadLights(List<Light> lights) {
		for(int i = 0; i < lights.size(); i++) {
			if(i < MAX_LIGHTS) {
				super.loadVector(location_lightPosition[i], lights.get(i).getPosition());
				super.loadVector(location_lightColor[i], lights.get(i).getColor());
				super.loadVector(location_attenuation[i], lights.get(i).getAttenuation());
			}
		}
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
	}

	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
}
