import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Mesh;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;




public class GraphicsZombie  extends MeshView
{
  public static Mesh zombieMesh = StlLoader.zMesh;	
  private static PhongMaterial zombieMaterial= new PhongMaterial();
  
  final static Image RANDZOMBIEIMAGE = new Image("randZombieTexture.jpg");
  final static Image LINEZOMBIEIMAGE = new Image("lineZombieTexture.jpg");
  
  Zombie gameStateZombie;
  double startZ,startX, currentZ,currentX;
  int iPos, jPos;
  
  int type;
  int id;
  int scale;
  final PhongMaterial mat = new PhongMaterial(); 
 
  public GraphicsZombie(double startX , double startZ, int type) 
  {
	  
	  super(zombieMesh);
	  this.startX=startX;
	  this.startZ=startZ;
	  this.currentX=startX;
	  this.currentZ=startZ;
	  this.type=type; 
	  this.setStartingPos();
	  
	  switch(type)
	  {
	  case ZombieConstants.LINE_ZOMBIE:
		  zombieMaterial.setDiffuseMap(LINEZOMBIEIMAGE); scale= 2; break;
	  case ZombieConstants.RANDOM_ZOMBIE:
		  zombieMaterial.setDiffuseMap(RANDZOMBIEIMAGE); scale= 2; break;
	  case ZombieConstants.MASTER_ZOMBIE:
	  zombieMaterial.setDiffuseMap(RANDZOMBIEIMAGE); scale= 4;
	  break;
	  	  
	  }
	  
	  this.setMaterial(zombieMaterial);
  }
  public void setGameStateZombie(Zombie gameStateZombie)
  {
	  this.gameStateZombie=gameStateZombie;
	  
  }
  public void setIJ(int iPos, int jPos)
  {
	  this.iPos=iPos;
	  this.jPos=jPos;
  }
  
  public int getIpos()
  {
	  return this.iPos;
  }
  
  public int getJpos()
  {
	 return this.jPos;
	  
  }
  
  private void setStartingPos()
  {
	 
	  	this.setScaleX(2);
	  	this.setScaleY(2);
	  	this.setScaleZ(2);
	  	
		this.setTranslateX(this.startX);
		this.setTranslateY(GraphicsComponent.COMPSIZE/2 - 100);
		this.setTranslateZ(this.startZ);
		
		
	
  }
  
  public void setId(int id)
  {this.id= id; }
  
  public void headingToRender()
  {
	  double transX=0;
	  double transZ=0;
	  
	  int rowHeading =this.gameStateZombie.getHeadingRow();
	  int colHeading =this.gameStateZombie.getHeadingCol();
	  
	  switch(rowHeading)
	  {
	  case -1: transZ = 10; break;
		   
	  case  0: transZ = 0; break;
		  
	  case  1: transZ = -10; break; 
	  }
	  
	  switch(colHeading)
	  {
	  case -1: transX = 10; break;
	   
	  case  0: transX = 0; break;
		  
	  case  1: transX = -10;  break;
	  }
	  
	  
	  this.translate(transX, transZ);
	  
  }
  
  
  public void translate(double transX,double transZ)
  {	
 	  currentX+= transX;
	  currentZ+= transZ;
	 /*
	  this.setTranslateX(currentX);
	  this.setTranslateZ(currentZ);
	  */
	  
			this.setTranslateX(this.currentX);
			
			this.setTranslateZ(this.currentZ);
			
		 
	  
  }
	
}
