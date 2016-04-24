import javafx.scene.image.Image;

public class Door extends GraphicsComponent
{
	final static Image DOORIMAGE = new Image("door.jpeg");
	int gridPosx,gridPosy;
	public Door(int gridPosx,int gridPosy)
	{
	  super(gridPosx,gridPosy);
	  this.setTexture(DOORIMAGE);
  	  this.gridPosx=gridPosx;
	  this.gridPosy=gridPosy;
	  this.setDepth(COMPSIZE);

	}
}
