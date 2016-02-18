import javafx.scene.image.Image;


public class Tile extends GraphicsComponent
{
  
  final static Image TILEIMAGE = new Image("level1tiletexture.jpeg");
  
  public Tile(int posx, int posy)
  {
	super(posx,posy,TILEIMAGE);
	
  }

  
  
  
}
