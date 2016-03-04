import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;


public class LevelGraphics extends Group
{
  int map[][];
  int mapWidth,mapHeight,mapCenterX,mapCenterY,level;
  Door exit;
  
  double playerStartX,playerStartZ;
  
  ArrayList<Wall> walls= new ArrayList<Wall>();
  ArrayList<Tile> floor= new ArrayList<Tile>();
  ArrayList<Tile> ceiling= new ArrayList<Tile>();
  ArrayList<Obstacle> obstacles= new ArrayList<Obstacle>();
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
			  
		    Tile tmp = new Tile(i,j);
		    Tile tmp2= new Tile(i,j);
		 
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
		    
		  case 1: break;
		  
		  case 2: 
			    Tile roomtmp = new Tile(i,j);
			    Tile roomtmp2= new Tile(i,j);
			 
			    roomtmp.setSceneLocation(mapCenterX,mapCenterY);
			    roomtmp2.setSceneLocation(mapCenterX, mapCenterY);
			  
			    roomtmp.setRotationAxis(Rotate.X_AXIS);
			    roomtmp.setRotate(90);
			    roomtmp.setTranslateY(GraphicsComponent.COMPSIZE/2);
			  
			    roomtmp2.setRotationAxis(Rotate.X_AXIS);
			    roomtmp2.setRotate(90);
			    roomtmp2.setTranslateY(-GraphicsComponent.COMPSIZE/2);
			  
			    floor.add(roomtmp);
			    ceiling.add(roomtmp2);
			    break;
		  case 3: break;
		  
		  case 4: Wall walltmp =new Wall(i,j,level); 
		  	      walltmp.setSceneLocation(mapCenterX,mapCenterY); 
		  	      walls.add(walltmp); 
		  	      break;
		  
		  case 5: exit = new Door(i,j); exit.setSceneLocation(mapCenterX, mapCenterY);break;
		  
		  case 6:
			  Tile hallwaytmp = new Tile(i,j);
		      Tile hallwaytmp2= new Tile(i,j);
			 
		      hallwaytmp.setSceneLocation(mapCenterX,mapCenterY);
		      hallwaytmp2.setSceneLocation(mapCenterX, mapCenterY);
		  
		      hallwaytmp.setRotationAxis(Rotate.X_AXIS);
		      hallwaytmp.setRotate(90);
		      hallwaytmp.setTranslateY(GraphicsComponent.COMPSIZE/2);
		  
		      hallwaytmp2.setRotationAxis(Rotate.X_AXIS);
		      hallwaytmp2.setRotate(90);
		      hallwaytmp2.setTranslateY(-GraphicsComponent.COMPSIZE/2);
		  
		      floor.add(hallwaytmp);
		      ceiling.add(hallwaytmp2); 
			  break;
		  
		  case 7: break;
		  
		  case 8: break;
		  
		  case 9: break;
		  
		  }
		  
		  
	  }
	  	 
	}
	
	this.getChildren().addAll(floor);
	this.getChildren().addAll(walls);
	this.getChildren().addAll(ceiling);
	this.getChildren().add(exit);
  
  
  }
}
