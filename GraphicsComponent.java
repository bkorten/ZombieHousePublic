

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;


public class GraphicsComponent extends Box
{
  int gridposY, gridposX;
  double renderSpaceX, renderSpaceZ;
  final PhongMaterial MAT=new PhongMaterial();
  final static double COMPSIZE=500;
  Image texture;
  
  public GraphicsComponent(int gridposY, int gridposX)
  {
	super(COMPSIZE,COMPSIZE,1);
	this.gridposX=gridposX;
	this.gridposY=gridposY;
	
  }
  public void setTexture(Image texture)
  {
	this.texture=texture;
	this.MAT.setDiffuseMap(texture);
	//this.MAT.setSpecularColor(Color.WHITE);
	this.setMaterial(MAT);
	  
  }
  
  public  void setSceneLocation(int centerX, int centerY)
  {
	  double transX=0;
	  double transZ=0;
	  
	  if(gridposX > centerX)
	  {
		 transX = Math.abs(gridposX- centerX)*COMPSIZE;
	  }
	  if(gridposX < centerX)
	  {
		 transX = -Math.abs(gridposX- centerX)*COMPSIZE;
	  }
	  if(gridposY > centerY)
	  {
		transZ = Math.abs(gridposY- centerY)*COMPSIZE;
	  }
	  if(gridposY < centerY)
	  {
		transZ = -Math.abs(gridposY- centerY)*COMPSIZE;
	  }
	  
	  this.renderSpaceX=transX;
	  this.renderSpaceZ=transZ;
	  
	  this.setTranslateX(transX);
	  this.setTranslateZ(transZ);  
  }
  
  
}

