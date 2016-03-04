import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;


public class Player 
{
  PerspectiveCamera playerCamera;
  PointLight playerLight;
  Color lightColor= new Color(0.01,0.01,0.01,.01);
  Rotate xRotate,yRotate;
  
  double playerStartZ,playerStartX,playerCurZ,playerCurX;
  double nearClip = 0.1;
  double farClip = 1000;
  double feildOfView = 45;
  final double playerY=-300;
  
  public Player(double playerStartZ,double playerStartX)
  {
	  this.xRotate=new Rotate(0,0,0,0,Rotate.X_AXIS);
	  this.yRotate=new Rotate(0,0,0,0,Rotate.Y_AXIS);
	  
	  this.playerStartZ=playerStartZ;
	  this.playerStartX=playerStartX;
	  this.playerCurX=playerStartX;
	  this.playerCurZ=playerStartZ;
	  
	  //camera init
	  this.playerCamera= new PerspectiveCamera(false);
	  this.playerCamera.getTransforms().add(xRotate);
	  this.playerCamera.getTransforms().add(yRotate);
	  this.playerCamera.setTranslateX(playerStartX);
	  this.playerCamera.setTranslateZ(playerStartZ);
	  this.playerCamera.setTranslateY(playerY);
	  this.playerCamera.setNearClip(nearClip);
	  this.playerCamera.setFarClip(farClip);
      this.playerCamera.setFieldOfView(feildOfView);
	  this.playerCamera.setRotationAxis(Rotate.Y_AXIS);
	  
	  //light init
	  this.playerLight= new PointLight();
	  this.playerLight.setColor(lightColor);
	  this.playerLight.setTranslateX(playerStartX);
	  this.playerLight.setTranslateZ(playerStartZ);
	  this.playerLight.setTranslateY(playerY);
	  this.playerLight.getTransforms().addAll(xRotate,yRotate);
  }
  
  public void translatePlayer(double transX, double transZ)
  {
	 this.playerCurX += transX;
	 this.playerCurZ += transZ;
	 
	 this.playerCamera.setTranslateX(playerCurX);
	 this.playerCamera.setTranslateZ(playerCurZ);
	 
	 this.playerLight.setTranslateX(playerCurX);
	 this.playerLight.setTranslateZ(playerCurZ);
  }
  
}
