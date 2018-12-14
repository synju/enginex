package testing3d.terrains;

import testing3d.models.RawModel;
import testing3d.renderEngine.Loader;
import testing3d.textures.TerrainTexture;
import testing3d.textures.TerrainTexturePack;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class CustomTerrain {
	public float size = 10;
	public float maxHeight = 40;
	public float maxPixelColor = 256*256*256;

	public float x;
	public float z;

	public RawModel model;
	public TerrainTexturePack terrainTexturePack;
	public TerrainTexture blendMap;

	public float[][] heights;

	public CustomTerrain(int x, int z, Loader loader, TerrainTexturePack terrainTexturePack, TerrainTexture blendMap, String heightMap) {
		this.terrainTexturePack = terrainTexturePack;
		this.blendMap = blendMap;
		this.x = x;
		this.z = z;
		this.model = generateTerrain(loader,heightMap);
	}

	public RawModel generateTerrain(Loader loader, String heightMap) {
		BufferedImage image = null;

		try {
			image = ImageIO.read(new File("src/testing3d/res/" + heightMap + ".png"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}



		return null;
	}
}
