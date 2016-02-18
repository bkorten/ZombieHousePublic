

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;


public class GraphicsComponent extends Box
{
  int posX, posY;	
  final PhongMaterial MAT=new PhongMaterial();
  public GraphicsComponent(int posX, int posY, Image texture)
  {
	super(100,100,1);
	this.posX= posX;
	this.posY= posY;
	this.MAT.setDiffuseMap(texture);
	this.setMaterial(MAT);
  }

  
  
  
}

