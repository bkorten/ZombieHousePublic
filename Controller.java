import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;


public class Controller 
{
    Player player;
    ArrayList<Wall> walls;
    private int change = 50;
   
    Rotate yRotate= new Rotate(0,0,0,0,Rotate.Y_AXIS);
    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;
    double transX, transZ;
    double sightX,sightZ;
    
    GameState gameState;
    
    
    public Controller(Player player)
    {
    	this.player=player;
    	this.player.playerCamera.getTransforms().add(yRotate);
    	this.player.playerLight.getTransforms().add(yRotate);
    }
    
    public void setWalls(ArrayList<Wall> walls)
    {
    	this.walls=walls;
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
               
               double yawRotate = (yRotate.getAngle() + (mouseDeltaX*1.01));
        
               //player.translatePlayer(10, 10);
               yRotate.setAngle(yawRotate);
                      
             //  System.out.println("Rotate angle "+ yRotate.getAngle());
              
            }
        });
    }
	
	public void handleKeyboard(Scene scene, final Node root) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
         
         
          
          private boolean isCollision()
          {
        	  
        	  double distance;
        	  for(Wall wall : walls)
        	  {
        		  
        		  distance = 
        				 
        						  Math.pow( ((player.playerCurX + transX) - wall.renderSpaceX),2) 
        				  + 
        				  		  Math.pow(((player.playerCurZ +transZ)- wall.renderSpaceZ), 2);
        	     
        		  
        		  if(distance  <  Math.pow(100,2))
        		  {
        			System.out.println("Collision at "+wall.renderSpaceX+" "+wall.renderSpaceZ);  
        		    return true;
        		  }
        	  }
        	  return false;
        	  
          }
          
          private void forward()
          {
        	 
        	  double rotateAngle = yRotate.getAngle();
        	     
        	  double ra = Math.toRadians(rotateAngle);   
        	  
        	    
        	  transX =   Math.sin(ra) * change ;
              
              transZ =   Math.cos(ra) * change ;
        	  
             //if(!isCollision())
              
             player.translatePlayer(transX, transZ);
             
            
             
          }
          
          private void backward()
          {
        	  double rotateAngle = yRotate.getAngle();
        	  double ra = Math.toRadians(rotateAngle);    
              
        	  transX =   Math.sin(ra) * -change;
              transZ =   Math.cos(ra) * -change;
        	
             // if(!isCollision()) 
             player.translatePlayer(transX, transZ);
           	
          }
          
          private void left()
          {
        	  double rotateAngle = yRotate.getAngle();
        	double ra = Math.toRadians(rotateAngle-90);    
          	transX =   Math.sin(ra) * change;
          	transZ =   Math.cos(ra) * change;
          	
          	   // if(!isCollision())
          		player.translatePlayer(transX, transZ);
        	 
          }
          
          private void right()
          {
        	  double rotateAngle = yRotate.getAngle();
        	   double ra = Math.toRadians(rotateAngle-90);    
               transX =   Math.sin(ra) * change;
               transZ =   Math.cos(ra) * change;
          	
          	
               //if(!isCollision()) 
          		player.translatePlayer(-transX, -transZ);
        	 
          
          }
        	@Override
            public void handle(KeyEvent event) 
        	{
             
        		if(event.isShiftDown())
        			change = 75;
        		else
        			change = 20; 
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
