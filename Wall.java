import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;



import javafx.scene.image.Image;


public class Wall extends GraphicsComponent
{
  
  final static Image WALLIMAGE0 = new Image("level0walltexture.jpeg");
  final static Image WALLIMAGE1 = new Image("level1walltexture.jpg");
  final static Image WALLIMAGE2 = new Image("level2walltexture.jpg");
  final static Image WALLIMAGE3 = new Image("level3walltexture.jpg");
  final static Image WALLIMAGE4 = new Image("level4walltexture.jpg");
  int gridPosx,gridPosy,level;
  
  public Wall(int gridPosx, int gridPosy,int level)
  {
	
	super(gridPosx,gridPosy);
	this.gridPosx=gridPosx;
	this.gridPosy=gridPosy;
	this.setDepth(COMPSIZE);
	this.level=level;
	this.setWalltexture();

  }
  
  public void setWalltexture()
  {
	  Image wallTexture=null;
	  switch(level)
	  {
	  case 0: wallTexture= WALLIMAGE0;break;
	  case 1: wallTexture= WALLIMAGE1;break;
	  case 2: wallTexture= WALLIMAGE2;break;
	  case 3: wallTexture= WALLIMAGE3;break;
	  case 4: wallTexture= WALLIMAGE4;break;
	  }
	  
	  this.setTexture(wallTexture);
  }  
  
 
}
