import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class Controller 
{
	private static final double CAMERA_INITIAL_DISTANCE = -1500;
	private static final double CAMERA_INITIAL_X_ANGLE = 70.0;
	private static final double CAMERA_INITIAL_Y_ANGLE = 320.0;
	private static final double CAMERA_NEAR_CLIP = 0.1;
	private static final double CAMERA_FAR_CLIP = 10000.0;
	private static final double CONTROL_MULTIPLIER = 0.1;
    private static final double SHIFT_MULTIPLIER = 10.0;
    private static final double MOUSE_SPEED = 0.1;
    private static final double ROTATION_SPEED = 2.0;
    private static final double TRACK_SPEED = 0.3;
    Player player;
    private int change = 10;
    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;
    double transX, transZ;
    
    public Controller(Player player)
    {
    	this.player=player;
    }
    
	public void handleMouse(Scene scene, final Node root) {
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent me) {
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();
            }
        });
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent me) {
                mouseOldX = mousePosX;
                mouseOldY = mousePosY;
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseDeltaX = (mousePosX - mouseOldX); 
                
               double yawRotate = player.yRotate.getAngle() + (mouseDeltaX/5);
               player.yRotate.setAngle(yawRotate);
               
               
                
            }
        });
    }
	
	public void handleKeyboard(Scene scene, final Node root) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
          double rotateAngle = player.yRotate.getAngle();
         
          private void forward()
          {
        	double ra = (rotateAngle/180 * 3.14);    
        	transX = Math.cos(ra) * 2;
        	transZ = Math.sin(ra) * 2;
        	
            player.translatePlayer(transX, transZ);
        	  
          }
          
          private void backward()
          {
        	   transZ= -change;
               player.translatePlayer(transX, transZ);
          }
          
          private void left()
          {
        	double ra = (rotateAngle/180 * 3.14);    
          	transX = Math.cos(ra) * 10;
          	transZ = Math.sin(ra) * 10;
            player.translatePlayer(transX, transZ);
          }
          
          private void right()
          {
        	double ra = (rotateAngle/180 * 3.14);    
          	transX = Math.cos(ra) * -10;
          	transZ = Math.sin(ra) * -10;
            player.translatePlayer(transX, transZ);
          
          }
        	@Override
            public void handle(KeyEvent event) {
             
                
            	switch (event.getCode()) 
                {
                case W: forward();break;
                
                case A: left(); break;
                
                case S: backward();break;
                
                case D: right(); break;

                }
                   
            }
        });
    }
}
