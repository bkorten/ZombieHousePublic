import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;



import javafx.scene.image.Image;


public class Wall extends GraphicsComponent
{
  
  final static Image WALLIMAGE = new Image("level1walltexture.jpeg");
  
  public Wall(int posx, int posy)
  {
	super(posx,posy,WALLIMAGE);
	
  }

  
  
  
}
