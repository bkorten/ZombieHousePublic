
import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;


public class LevelGraphics extends Group
{
  int map[][];
  int mapWidth,mapHeight,mapCenterX,mapCenterY,level;
  int tilecount=1;
  
  double playerStartX,playerStartZ;
  double masterZombieX, masterZombieZ;
  
  
  ArrayList<Wall> walls= new ArrayList<Wall>();
  ArrayList<Floor> floor= new ArrayList<Floor>();
  ArrayList<Floor> ceiling= new ArrayList<Floor>();
  ArrayList<Obstacle> obstacles= new ArrayList<Obstacle>();
  ArrayList<GraphicsFireTrap> fireTraps= new ArrayList<GraphicsFireTrap>();
  ArrayList<Door> exit =new ArrayList<Door>(2);
  ArrayList<Point2D> randomZombieSpawns= new ArrayList<Point2D>();
  ArrayList<Point2D> randomZombieSpawnsIJ = new ArrayList<Point2D>();
  
  ArrayList<Point2D> lineZombieSpawns= new ArrayList<Point2D>();
  ArrayList<Point2D> lineZombieSpawnsIJ= new ArrayList<Point2D>();
  
  double compsize = GraphicsComponent.COMPSIZE;
  
  
  
  public LevelGraphics(int map[][],int mapWidth,int mapHeight,int level)
  {
	super();  
	this.map=map;
	this.mapWidth=mapWidth;
	this.mapHeight=mapHeight;
	this.mapCenterX=mapWidth/2;
	this.mapCenterY=mapHeight/2;
	this.level=level - 1;
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
			if(tilecount==5)
			{tilecount=1;}
			  
		    Floor tmp = new Floor(i,j,tilecount);
		    Floor tmp2= new Floor(i,j,tilecount);
		 
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
		    tilecount++;
		    break;
		    
		  
		  
		  case 2: 
			  if(tilecount==5)
				{tilecount=1;}
			  
			    Floor roomtmp = new Floor(i,j,tilecount);
			    Floor roomtmp2= new Floor(i,j,tilecount);
			 
			    roomtmp.setSceneLocation(mapCenterX,mapCenterY);
			    roomtmp2.setSceneLocation(mapCenterX, mapCenterY);
			  
			    roomtmp.setRotationAxis(Rotate.X_AXIS);
			    roomtmp.setRotate(90);
			    roomtmp.setTranslateY(GraphicsComponent.COMPSIZE/2);
			  
			    roomtmp2.setRotationAxis(Rotate.X_AXIS);
			    roomtmp2.setRotate(90);
			    roomtmp2.setTranslateY(-GraphicsComponent.COMPSIZE/2);
			    GraphicsFireTrap fireTmp = new GraphicsFireTrap(i,j);
			    
			    fireTmp.setSceneLocation(mapCenterX, mapCenterY);
			    fireTmp.setTranslateY(compsize/4);
			    
			    fireTraps.add(fireTmp);
			    floor.add(roomtmp);
			    ceiling.add(roomtmp2);
			    tilecount++;
			    break;
		 
		  case 3: 
			  if(tilecount==5)
				{tilecount=1;}
			  
			    Floor randomZombiespawntmp = new Floor(i,j,tilecount);
			    Floor randomZombiespawntmp2= new Floor(i,j,1);
			 
			    randomZombiespawntmp.setSceneLocation(mapCenterX,mapCenterY);
			    randomZombiespawntmp2.setSceneLocation(mapCenterX, mapCenterY);
			  
			    randomZombiespawntmp.setRotationAxis(Rotate.X_AXIS);
			    randomZombiespawntmp.setRotate(90);
			    randomZombiespawntmp.setTranslateY(GraphicsComponent.COMPSIZE/2);
			  
			    randomZombiespawntmp2.setRotationAxis(Rotate.X_AXIS);
			    randomZombiespawntmp2.setRotate(90);
			    randomZombiespawntmp2.setTranslateY(-GraphicsComponent.COMPSIZE/2);
			    floor.add(randomZombiespawntmp);
			    ceiling.add(randomZombiespawntmp2);
			    
			    Point2D randomzombiePosition = new Point2D(randomZombiespawntmp.renderSpaceX,randomZombiespawntmp.renderSpaceZ);
			    randomZombieSpawns.add(randomzombiePosition);
			    
			    Point2D randzombieIJ= new Point2D(i ,j);
			    randomZombieSpawnsIJ.add(randzombieIJ);
			    
			    tilecount++;
			    break;
		  
		  case 4: 
			  	  Wall walltmp =new Wall(i,j,level); 
		  	      walltmp.setSceneLocation(mapCenterX,mapCenterY); 
		  	      walls.add(walltmp); 
		  	      break;
		  
		  case 5: Door exitTmp = new Door(i,j); 
		          exitTmp.setSceneLocation(mapCenterX, mapCenterY);
		          exit.add(exitTmp);
		          break;
		  
		  case 6:
			  if(tilecount==5)
				{tilecount=1;}
			    Floor lineZombiespawntmp = new Floor(i,j,tilecount);
			    Floor lineZombiespawntmp2= new Floor(i,j,tilecount);
			 
			    lineZombiespawntmp.setSceneLocation(mapCenterX,mapCenterY);
			    lineZombiespawntmp2.setSceneLocation(mapCenterX, mapCenterY);
			  
			    lineZombiespawntmp.setRotationAxis(Rotate.X_AXIS);
			    lineZombiespawntmp.setRotate(90);
			    lineZombiespawntmp.setTranslateY(GraphicsComponent.COMPSIZE/2);
			  
			    lineZombiespawntmp2.setRotationAxis(Rotate.X_AXIS);
			    lineZombiespawntmp2.setRotate(90);
			    lineZombiespawntmp2.setTranslateY(-GraphicsComponent.COMPSIZE/2);
			    
			    floor.add(lineZombiespawntmp);
			    ceiling.add(lineZombiespawntmp2);
			    
			    Point2D linezombiePosition = new Point2D(lineZombiespawntmp.renderSpaceX,lineZombiespawntmp.renderSpaceZ);
			    Point2D linezombieIJ= new Point2D(i ,j);
			    
			    lineZombieSpawns.add(linezombiePosition);
			    lineZombieSpawnsIJ.add(linezombieIJ);
			    tilecount++;
			  break;
		  
		  case 7:
			  if(tilecount==5)
				{tilecount=1;}
			    Floor mastertmp = new Floor(i,j,tilecount);
			    Floor mastertmp2= new Floor(i,j,tilecount);
			 
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
			    
			    tilecount++;
			    
			    break;
		  
		  case 8: 
			  if(tilecount==5)
				{tilecount=1;}
			  	Floor playertmp = new Floor(i,j,tilecount);
			    Floor playertmp2= new Floor(i,j,tilecount);
			 
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
			    
			    tilecount++;
			    break;
		  
		  case 9:
			  if(tilecount==5)
				{tilecount=1;}
			  Floor obstacleSpawntmp = new Floor(i,j,tilecount);
		      Floor obstacleSpawntmp2= new Floor(i,j,tilecount);
		      
		      Obstacle ob = new Obstacle(i,j);
		      
		      ob.setSceneLocation(mapCenterX, mapCenterY);
		      ob.setTranslateY(compsize/4);
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
		      tilecount++;
		      break;
		  }
		  
		  
	  }
	  	 
	}
	
	this.getChildren().addAll(floor);
	this.getChildren().addAll(walls);
	this.getChildren().addAll(ceiling);
	this.getChildren().addAll(obstacles);
	this.getChildren().addAll(fireTraps);
	this.getChildren().addAll(exit);
	
  
  }
}
