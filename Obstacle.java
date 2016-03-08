import javafx.scene.image.Image;


public class Obstacle extends GraphicsComponent
{
  int gridPosx,gridPosy;
  final static Image OBSTACLEIMAGE = new Image("obstacleTexture.jpg");
  public Obstacle(int gridPosx, int gridPosy)
  {  
	super(gridPosx,gridPosy);
	this.gridPosx=gridPosx;
    this.gridPosy=gridPosy;
    this.setTexture(OBSTACLEIMAGE);
    this.setHeight(300);
    this.setDepth(COMPSIZE);
  }
}
