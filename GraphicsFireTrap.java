import javafx.scene.image.Image;



public class GraphicsFireTrap extends GraphicsComponent  
{
	final static Image FIRETRAPIMAGE = new Image("fireTrapTexture.gif");
	
	public GraphicsFireTrap(int gridX,int gridY)
	{
		super(gridX,gridY);
		this.setHeight(COMPSIZE/2);
		this.setDepth(COMPSIZE/2);
		this.setWidth(COMPSIZE/2);
		this.setTexture(FIRETRAPIMAGE);
	}
	
	
}
