import javafx.scene.image.Image;


public class Floor extends GraphicsComponent
{
  
  final static Image TILEIMAGE = new Image("level1tiletexture.jpeg");
  final static Image TILE2IMAGE = new Image("level2tiletexture.jpg");
  final static Image TILE3IMAGE = new Image("level3tiletexture.jpg");
  final static Image TILE4IMAGE = new Image("level4tiletexture.jpg");
  public Floor(int posx, int posy, int type)
  {
	super(posx,posy);
	
	switch(type)
	{
	case 1 : this.setTexture(TILEIMAGE); break;
	case 2:  this.setTexture(TILE2IMAGE); break;
	case 3:  this.setTexture(TILE3IMAGE); break;
	case 4:  this.setTexture(TILE4IMAGE); break; 
	}
  }

  
  
  
}
