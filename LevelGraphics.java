
import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;


public class LevelGraphics extends Group
{
  int map[][];
  int mapWidth,mapHeight,mapCenterX,mapCenterY,level;
  Door exit;
  
  double playerStartX,playerStartZ;
  double masterZombieX, masterZombieZ;
  
  
  ArrayList<Wall> walls= new ArrayList<Wall>();
  ArrayList<Floor> floor= new ArrayList<Floor>();
  ArrayList<Floor> ceiling= new ArrayList<Floor>();
  ArrayList<Obstacle> obstacles= new ArrayList<Obstacle>();
  ArrayList<Point2D> randomZombieSpawns= new ArrayList<Point2D>();
  ArrayList<Point2D> lineZombieSpawns= new ArrayList<Point2D>();
  
  double compsize = GraphicsComponent.COMPSIZE;
  
  
  
  public LevelGraphics(int map[][],int mapWidth,int mapHeight,int level)
  {
	super();  
	this.map=map;
	this.mapWidth=mapWidth;
	this.mapHeight=mapHeight;
	this.mapCenterX=mapWidth/2;
	this.mapCenterY=mapHeight/2;
	this.level=level;
	this.initialize();
	
  }

  public double getPlayerStartX()
  {
    return playerStartX;
  }
  
  public double getPlayerStartZ()
  {
    return playerStartZ;
  }
  
  private void initialize()
  {
	  
	int mapValue;  
	for(int i = 0; i< mapHeight;i++)
	{
	  
	  for(int j= 0; j<mapWidth; j++)
	  {
		  mapValue = map[i][j];
		  
		  switch(mapValue)
		  {
		  
		  case 0: 
			  
		    Floor tmp = new Floor(i,j);
		    Floor tmp2= new Floor(i,j);
		 
		    tmp.setSceneLocation(mapCenterX,mapCenterY);
		    tmp2.setSceneLocation(mapCenterX, mapCenterY);
		 
		    tmp.setRotationAxis(Rotate.X_AXIS);
		    tmp.setRotate(90);
		    tmp.setTranslateY(GraphicsComponent.COMPSIZE/2);
		  
		    tmp2.setRotationAxis(Rotate.X_AXIS);
		    tmp2.setRotate(90);
		    tmp2.setTranslateY(-GraphicsComponent.COMPSIZE/2);
		  
		    floor.add(tmp);
		    ceiling.add(tmp2);
		    break;
		    
		  
		  
		  case 2: 
			    Floor roomtmp = new Floor(i,j);
			    Floor roomtmp2= new Floor(i,j);
			 
			    roomtmp.setSceneLocation(mapCenterX,mapCenterY);
			    roomtmp2.setSceneLocation(mapCenterX, mapCenterY);
			  
			    roomtmp.setRotationAxis(Rotate.X_AXIS);
			    roomtmp.setRotate(90);
			    roomtmp.setTranslateY(GraphicsComponent.COMPSIZE/2);
			  
			    roomtmp2.setRotationAxis(Rotate.X_AXIS);
			    roomtmp2.setRotate(90);
			    roomtmp2.setTranslateY(-GraphicsComponent.COMPSIZE/2);
			    //add fire trap bullshit
			    floor.add(roomtmp);
			    ceiling.add(roomtmp2);
			    break;
		  case 3: 
			    Floor randomZombiespawntmp = new Floor(i,j);
			    Floor randomZombiespawntmp2= new Floor(i,j);
			 
			    randomZombiespawntmp.setSceneLocation(mapCenterX,mapCenterY);
			    randomZombiespawntmp2.setSceneLocation(mapCenterX, mapCenterY);
			  
			    randomZombiespawntmp.setRotationAxis(Rotate.X_AXIS);
			    randomZombiespawntmp.setRotate(90);
			    randomZombiespawntmp.setTranslateY(GraphicsComponent.COMPSIZE/2);
			  
			    randomZombiespawntmp2.setRotationAxis(Rotate.X_AXIS);
			    randomZombiespawntmp2.setRotate(90);
			    randomZombiespawntmp2.setTranslateY(-GraphicsComponent.COMPSIZE/2);
			    Point2D randomzombiePosition = new Point2D(randomZombiespawntmp.renderSpaceX,randomZombiespawntmp.renderSpaceZ);
			    randomZombieSpawns.add(randomzombiePosition);
			    
			    break;
		  
		  case 4: 
			  	  Wall walltmp =new Wall(i,j,level); 
		  	      walltmp.setSceneLocation(mapCenterX,mapCenterY); 
		  	      walls.add(walltmp); 
		  	      break;
		  
		  case 5: exit = new Door(i,j); 
		          exit.setSceneLocation(mapCenterX, mapCenterY);
		          break;
		  
		  case 6:
			    Floor lineZombiespawntmp = new Floor(i,j);
			    Floor lineZombiespawntmp2= new Floor(i,j);
			 
			    lineZombiespawntmp.setSceneLocation(mapCenterX,mapCenterY);
			    lineZombiespawntmp2.setSceneLocation(mapCenterX, mapCenterY);
			  
			    lineZombiespawntmp.setRotationAxis(Rotate.X_AXIS);
			    lineZombiespawntmp.setRotate(90);
			    lineZombiespawntmp.setTranslateY(GraphicsComponent.COMPSIZE/2);
			  
			    lineZombiespawntmp2.setRotationAxis(Rotate.X_AXIS);
			    lineZombiespawntmp2.setRotate(90);
			    lineZombiespawntmp2.setTranslateY(-GraphicsComponent.COMPSIZE/2);
			    Point2D linezombiePosition = new Point2D(lineZombiespawntmp.renderSpaceX,lineZombiespawntmp.renderSpaceZ);
			    lineZombieSpawns.add(linezombiePosition);
			  break;
		  
		  case 7:
			    Floor mastertmp = new Floor(i,j);
			    Floor mastertmp2= new Floor(i,j);
			 
			    mastertmp.setSceneLocation(mapCenterX,mapCenterY);
			    mastertmp2.setSceneLocation(mapCenterX, mapCenterY);
			 
			    mastertmp.setRotationAxis(Rotate.X_AXIS);
			    mastertmp.setRotate(90);
			    mastertmp.setTranslateY(GraphicsComponent.COMPSIZE/2);
			  
			    mastertmp2.setRotationAxis(Rotate.X_AXIS);
			    mastertmp2.setRotate(90);
			    mastertmp2.setTranslateY(-GraphicsComponent.COMPSIZE/2);
			  
			    floor.add(mastertmp);
			    ceiling.add(mastertmp2);
			    
			    masterZombieX=mastertmp.renderSpaceX;
			    masterZombieZ= mastertmp.renderSpaceZ;
			    
			    break;
		  
		  case 8: 
			  	Floor playertmp = new Floor(i,j);
			    Floor playertmp2= new Floor(i,j);
			 
			    playertmp.setSceneLocation(mapCenterX,mapCenterY);
			    playertmp2.setSceneLocation(mapCenterX, mapCenterY);
			 
			    playertmp.setRotationAxis(Rotate.X_AXIS);
			    playertmp.setRotate(90);
			    playertmp.setTranslateY(GraphicsComponent.COMPSIZE/2);
			  
			    playertmp2.setRotationAxis(Rotate.X_AXIS);
			    playertmp2.setRotate(90);
			    playertmp2.setTranslateY(-GraphicsComponent.COMPSIZE/2);
			  
			    floor.add(playertmp);
			    ceiling.add(playertmp2);
			    
			    playerStartX=playertmp.renderSpaceX;
			    playerStartZ= playertmp.renderSpaceZ;
			    
			    break;
		  
		  case 9:
			  
			  Floor obstacleSpawntmp = new Floor(i,j);
		      Floor obstacleSpawntmp2= new Floor(i,j);
		      Obstacle ob = new Obstacle(i,j);
		      
		      ob.setSceneLocation(mapCenterX, mapCenterY);
		      obstacleSpawntmp.setSceneLocation(mapCenterX,mapCenterY);
		      obstacleSpawntmp2.setSceneLocation(mapCenterX, mapCenterY);
		  
		      obstacleSpawntmp.setRotationAxis(Rotate.X_AXIS);
		      obstacleSpawntmp.setRotate(90);
		      obstacleSpawntmp.setTranslateY(GraphicsComponent.COMPSIZE/2);
		  
		      obstacleSpawntmp2.setRotationAxis(Rotate.X_AXIS);
		      obstacleSpawntmp2.setRotate(90);
		      obstacleSpawntmp2.setTranslateY(-GraphicsComponent.COMPSIZE/2);
		      
		      
		      floor.add(obstacleSpawntmp);
		      ceiling.add(obstacleSpawntmp2);
		      obstacles.add(ob);
		      
		      break;
		  }
		  
		  
	  }
	  	 
	}
	
	this.getChildren().addAll(floor);
	this.getChildren().addAll(walls);
	this.getChildren().addAll(ceiling);
	this.getChildren().addAll(obstacles);
	this.getChildren().add(exit);
  
  
  }
}
