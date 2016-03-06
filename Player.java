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
  Rotate xRotate,yRotate;
  
  
  int boundingRadius = 50;
  int boundingHeight = 250;
  
  double pivotX, pivotZ;
  double playerStartZ,playerStartX,playerOldX,playerOldZ,playerCurZ,playerCurX;
  double nearClip = 0.1;
  double farClip = 1000;
  double feildOfView = 45;
  final double playerY=-300;
  
  public Player(double playerStartX,double playerStartZ)
  {
	  
	  
	  
	  
	  this.playerStartZ=playerStartZ;
	  this.playerStartX=playerStartX;
	  this.playerCurX=playerStartX;
	  this.playerCurZ=playerStartZ;
	  yRotate= new Rotate(0,0,0,0,Rotate.Y_AXIS);
	  
	  
	  
	  //camera init
	  this.playerCamera= new PerspectiveCamera(false);
	  
	  this.playerCamera.setTranslateX(playerStartX);
	  this.playerCamera.setTranslateZ(playerStartZ);
	  this.playerCamera.setTranslateY(playerY);
	
	  this.playerCamera.setNearClip(nearClip);
	  this.playerCamera.setFarClip(farClip);
      this.playerCamera.setFieldOfView(feildOfView);
	  this.playerCamera.getTransforms().add(yRotate);
	  
	  //light init
	  this.playerLight= new PointLight();
	  this.playerLight.setColor(lightColor);
	  this.playerLight.setTranslateX(playerStartX);
	  this.playerLight.setTranslateZ(playerStartZ);
	  this.playerLight.setTranslateY(playerY);
	  this.playerLight.getTransforms().add(yRotate);
	  
	  //bounding cylinder
	  this.boundingCylinder=new Cylinder(boundingRadius,boundingHeight);
	  this.boundingCylinder.setTranslateX(playerStartX);
	  this.boundingCylinder.setTranslateY(playerY);
	  this.boundingCylinder.setTranslateZ(playerStartZ);
	  
	  //setPivots();
  }
  
  public void rotatePlayer(double rotateAngle)
  {
	  
	  playerCamera.setRotationAxis(Rotate.Y_AXIS);
	  playerCamera.setRotate(rotateAngle);
  }
  
  public void setPivots()
  {
	 
	  
	  this.yRotate.setPivotX(this.playerCurX);
	  this.yRotate.setPivotZ(this.playerCurZ);
  }
  
  public void translatePlayer(double transX, double transZ)
  {
	 this.playerOldX =this.playerCurX;
	 this.playerOldZ =this.playerCurZ;
	 
	 this.playerCurX = transX;
	 this.playerCurZ = transZ;
	 
	 
	 this.playerCamera.setTranslateX(playerCurX);
	 this.playerCamera.setTranslateZ(playerCurZ);
	 
	 
	 this.playerLight.setTranslateX(playerCurX);
	 this.playerLight.setTranslateZ(playerCurZ);
	 
	 this.boundingCylinder.setTranslateX(playerCurX);
	 this.boundingCylinder.setTranslateZ(playerCurZ);
	 
	 
  }
  
}
