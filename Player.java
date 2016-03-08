import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;


public class Player 
{
  Cylinder boundingCylinder;	
  PerspectiveCamera playerCamera;
  PointLight playerLight;
  Color lightColor= new Color(0.01,0.01,0.01,.01);
  Rotate yRotate;
  int startRow;
  int startCol;
  int oldRow;
  int oldCol;
  int curRow;
  int curCol;
  
  int boundingRadius = 50;
  int boundingHeight = 250;
  
  GameState gameState;
  
  
  double pivotX, pivotZ;
  double playerStartZ,playerStartX,playerOldX,playerOldZ,playerCurZ,playerCurX;
  double nearClip = 0.1;
  double farClip = 1000;
  double feildOfView = 35;
  final double playerY=-300;
  
  public Player(double playerStartX,double playerStartZ, int startRow,int startCol)
  {
	    
	  this.playerStartZ=playerStartZ;
	  this.playerStartX=playerStartX;
	  this.playerCurX=playerStartX;
	  this.playerCurZ=playerStartZ;
	  
	  this.startCol= startCol;
	  this.startRow=startRow;
	  
	  this.curRow= startRow;
	  this.curCol= startCol;
	  
	  
	  //camera init
	  this.playerCamera= new PerspectiveCamera(false);
	  
	  this.playerCamera.setTranslateX(playerStartX);
	  this.playerCamera.setTranslateZ(playerStartZ);
	  this.playerCamera.setTranslateY(playerY);
	 
	  this.playerCamera.setNearClip(nearClip);
	  this.playerCamera.setFarClip(farClip);
      this.playerCamera.setFieldOfView(feildOfView);
	   
	  //light init
	  this.playerLight= new PointLight();
	  this.playerLight.setColor(lightColor);
	  this.playerLight.setTranslateX(playerStartX);
	  this.playerLight.setTranslateZ(playerStartZ);
	  this.playerLight.setTranslateY(playerY);
	  
	  //bounding cylinder
	  this.boundingCylinder=new Cylinder(boundingRadius,boundingHeight);
	  this.boundingCylinder.setTranslateX(playerStartX);
	  this.boundingCylinder.setTranslateY(playerY);
	  this.boundingCylinder.setTranslateZ(playerStartZ);
	  
	  
	 	  
	  //setPivots();
  }
  
  public void setGameState(GameState gameState)
  {
	  this.gameState=gameState;
	  
  }
  public void setRow(int row)
  {
	  this.oldRow=this.curRow;
	  this.curRow =row;
  }
  
  public void setCol(int col)
  {
	  this.oldCol=this.curCol;
	  this.curCol = col; 
  }
  
  public void translatePlayer(double transX, double transZ)
  {
	 this.playerOldX =this.playerCurX;
	 this.playerOldZ =this.playerCurZ;
	 
	 this.playerCurX += transX;
	 this.playerCurZ += transZ;
	 
	 
	 this.playerCamera.setTranslateX(playerCurX);
	 this.playerCamera.setTranslateZ(playerCurZ);
	 
	 
	 this.playerLight.setTranslateX(playerCurX);
	 this.playerLight.setTranslateZ(playerCurZ);
	 
	 this.boundingCylinder.setTranslateX(playerCurX);
	 this.boundingCylinder.setTranslateZ(playerCurZ);
	
	  setRow(this.xToj());
	  setCol(this.yToi());
	  
	 gameState.movePlayer(oldRow, oldCol, curRow, curCol);
	 
	 
  }
  public int xToj()
  {
	  int j=0;
	  
	  if(this.playerCurX< 0)
	  {
		  j= (int) (-this.playerCurX/GraphicsComponent.COMPSIZE);
	  }
	  
	  if(this.playerCurX > 0)
	  {
		  j= (int) (this.playerCurX/GraphicsComponent.COMPSIZE);
	  }
	  
	  return j;
  }
  
  public int yToi()
  {
	  int i = 0;
	  
	  if(this.playerCurZ < 0)
	  {
		  i= (int) (-this.playerCurX/GraphicsComponent.COMPSIZE);
	  }
	  
	  if(this.playerCurZ > 0)
	  {
		  i= (int) (this.playerCurX/GraphicsComponent.COMPSIZE);
	  }
	  
	  return i;
  }

  
  
  
  public double getPlayerX()
  {
	  return this.playerCurX;
  }
  public double getPlayerZ()
  {
	  return this.playerCurZ;
  }
  
}
