import java.io.File;

import com.interactivemesh.jfx.importer.stl.StlMeshImporter;

import javafx.application.Application;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Mesh;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class StlLoader 
{

  private static final String MESH_FILENAME =
    "/home/burton/Desktop/Zombie_Fantasy_figure/giant_zombie.stl";

  
  
  public static Mesh zMesh;
  public static MeshView  zMeshView;
  
  public StlLoader() 
  {
    File file = new File(MESH_FILENAME);
    StlMeshImporter importer = new StlMeshImporter();
    importer.read(file);
    zMesh = importer.getImport();
    
    zMeshView = new MeshView(zMesh);
  }

  
  
} 
