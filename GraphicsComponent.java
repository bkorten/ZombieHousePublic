

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;


/**
 * @author burton
 *
 *Main graphic component for walls floor obstacles
 */
/**
 * @author burton
 *
 */
/**
 * @author burton
 *
 */
public class GraphicsComponent extends Box
{
  int gridposY, gridposX;
  double renderSpaceX, renderSpaceZ;
  final PhongMaterial MAT=new PhongMaterial();
  final static double COMPSIZE=500;
  Image texture;
  
  /**
   * 
   * public constuctor for graphic componenet 
   * takes a grid x and y to determine 3D rendering
 * @param gridposY
 * @param gridposX
 */
public GraphicsComponent(int gridposY, int gridposX)
  {
	super(COMPSIZE,COMPSIZE,1);
	this.gridposX=gridposX;
	this.gridposY=gridposY;
	
  }

  /**
   * this sets the texture image for the component;
   * 
 * @param texture
 */
public void setTexture(Image texture)
  {
	this.texture=texture;
	this.MAT.setDiffuseMap(texture);
	//this.MAT.setSpecularColor(Color.WHITE);
	this.setMaterial(MAT);
	  
  }
  

  /**
   * 
   * this sets the rendering for the translate x and z values based on the grid position
   *
 * @param centerX
 * @param centerY
 */
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

