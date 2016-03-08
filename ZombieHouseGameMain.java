
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.paint.Color;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.stage.Stage;



public class ZombieHouseGameMain extends Application 
{
  private int currentLevelIndex;
  private LevelGraphics currentLevelGraphics;
  private GameState currentGameState;
  private GameState currentGameStatecopy;
  private Controller controller;
  private Player player;
  private Group root;
  private StlLoader stlLoader;
  
  private ArrayList<GraphicsZombie> lineZombies= new ArrayList<GraphicsZombie>(); 
  private ArrayList<GraphicsZombie> randZombies= new ArrayList<GraphicsZombie>();
  private GraphicsZombie master;
  private MeshView zombieMeshes = new MeshView();
   
  public void start(Stage primaryStage)
  {
	  
	primaryStage.setFullScreen(true);  
	
	StlLoader stl = new StlLoader();
    root= new Group();
	
	Scene scene = new Scene(root,500,500,true);
	scene.setFill(Color.BLACK);
	
	primaryStage.setTitle("Zombie house");
	
	startLevel(root);
	player= new Player(currentLevelGraphics.playerStartX,
			currentLevelGraphics.playerStartZ, currentGameState.getPlayerCurrentRow(),currentGameState.getPlayerCurrentCol());
	player.setGameState(currentGameState);
	controller= new Controller(player);
	
	controller.handleKeyboard(scene, root);
	controller.handleMouse(scene, root);
	
	controller.setWalls(currentLevelGraphics.walls);
	scene.setCamera(player.playerCamera);
	
	primaryStage.setTitle("Zombie House");
	primaryStage.setScene(scene);
	primaryStage.show();
    MainGameLoop gameloop= new MainGameLoop();
    gameloop.start();
  }
  private void addZombies(Group root)
	{
	  	
	   double tmpx,tmpz;
		for(Point2D lineZSpawn : currentLevelGraphics.lineZombieSpawns)
		{	
			tmpx=lineZSpawn.getX();
			tmpz=lineZSpawn.getY();
			
			GraphicsZombie lineZtmp = new GraphicsZombie(tmpx,tmpz,ZombieConstants.LINE_ZOMBIE);
			lineZombies.add(lineZtmp);
		
		}
		ArrayList<Point2D> lineIJs= currentLevelGraphics.lineZombieSpawnsIJ;
		
		for(int i = 0; i< lineZombies.size(); i++)
		{
			GraphicsZombie lineTmp = lineZombies.get(i);
			Point2D lineIJ= lineIJs.get(i);
			lineTmp.setIJ( (int)lineIJ.getX(), (int)lineIJ.getY() );
		}
		
		for(Point2D randZSpawn : currentLevelGraphics.randomZombieSpawns)
		{	
			tmpx=randZSpawn.getX();
			tmpz=randZSpawn.getY();
			
			GraphicsZombie randZtmp = new GraphicsZombie(tmpx,tmpz,ZombieConstants.RANDOM_ZOMBIE);
			randZombies.add(randZtmp);
			
			
		}
		ArrayList<Point2D> randIJs= currentLevelGraphics.randomZombieSpawnsIJ;
		for(int i = 0; i< randZombies.size(); i++)
		{
			GraphicsZombie randTmp = randZombies.get(i);
			Point2D randIJ= randIJs.get(i);
			randTmp.setIJ( (int)randIJ.getX(), (int)randIJ.getY() );
		}
		
		master= new GraphicsZombie(currentLevelGraphics.masterZombieX,currentLevelGraphics.masterZombieZ,ZombieConstants.MASTER_ZOMBIE);
		
		setZombieId();
		int totalZombie = lineZombies.size() + randZombies.size();
		System.out.println("number of Zombies "+ totalZombie);
		
		
		root.getChildren().addAll(randZombies);
		root.getChildren().addAll(lineZombies);
		root.getChildren().add(master);
	}
  
  private void setZombieId()
  {
	 int lZs = currentGameState.getLineZombieList().size();
	 int rZs = currentGameState.getRandZombieList().size();
	System.out.println(rZs);  
	
	master.setGameStateZombie(currentGameState.getMaster());
	for(GraphicsZombie lineZombie: lineZombies)
	{
		for(Zombie gameStateZombie : currentGameState.getLineZombieList())
		{
			if((lineZombie.getIpos() == gameStateZombie.getStartRow()) 
					&&(lineZombie.getJpos() == gameStateZombie.getStartCol()))
					{
						System.out.println("matched up");	
						lineZombie.setId(gameStateZombie.getGraphicsID());
						lineZombie.setGameStateZombie(gameStateZombie);
						break;
					}
			
		}
		
	}
	
	for(GraphicsZombie randZombie: randZombies)
	{
		for(Zombie gameStateZombie : currentGameState.getRandZombieList())
		{
			if((randZombie.getIpos() == gameStateZombie.getStartRow()) 
					&&(randZombie.getJpos() == gameStateZombie.getStartCol()))
					{
						System.out.println("matched rand");
						randZombie.setId(gameStateZombie.getGraphicsID());
						randZombie.setGameStateZombie(gameStateZombie);
						break;
					}
			
		}
		
	}
	  
  }
  
  private void startLevel(Group root)
  {
	  currentLevelIndex= 1;
	  currentGameState = new GameState(currentLevelIndex);
	  currentGameStatecopy=new GameState(currentLevelIndex);
	  
	  currentGameState.initializeBackup(currentGameState, currentGameStatecopy); 
	  buildLevelGraphics(root);
	  addZombies(root);
	  currentGameState.initializeThreads();

  }
  
  private void nextLevel(Group root)
  {

	currentLevelIndex++;
    currentGameState = new GameState(currentLevelIndex);
    currentGameState.initializeBackup(currentGameState, currentGameStatecopy);
    
    root.getChildren().remove(currentLevelGraphics);
    root.getChildren().removeAll(lineZombies);
    root.getChildren().removeAll(randZombies);
    
    buildLevelGraphics(root);
    player.playerCamera.setTranslateX(currentLevelGraphics.playerStartX);
    player.playerCamera.setTranslateZ(currentLevelGraphics.playerStartZ);
    addZombies(root);
    
    
    
    
  }
  private boolean endGame()
  {
	 if(currentLevelIndex==6)
		 return true;
	 else
		 return false;
  }
  
  private void buildLevelGraphics(Group root)
  {
		currentLevelGraphics = new LevelGraphics(currentGameState.getFloorPlan(),
				ZombieConstants.NUM_COLS ,ZombieConstants.NUM_ROWS,currentLevelIndex);
		root.getChildren().add(currentLevelGraphics);
  }
  
 
  private void restartLevel()
  {
	  
	    player.playerCamera.setTranslateX(currentLevelGraphics.playerStartX);
	    player.playerCamera.setTranslateZ(currentLevelGraphics.playerStartZ);
	    System.out.println("RESTART");
	 
  }
  
  private boolean eaten()
  {
	  
	  int i=0;
	  ArrayList<Double> dists=new ArrayList<Double>();
	  for(GraphicsZombie zombie : lineZombies)
	  {
		  
		double xComp =Math.pow(zombie.currentX -player.playerCurX , 2);
		double zComp =Math.pow(zombie.currentZ -player.playerCurZ , 2);
	    Double distance = Math.sqrt(xComp+zComp);
	    dists.add(distance);
	    
	    if( distance < 350)
	    {
	    	System.out.println("Eaten");
	    	return true;
	    }
	  }
	  
	 
	  for(GraphicsZombie zombie : randZombies)
	  {
		double xComp =Math.pow(zombie.currentX -player.playerCurX , 2);
		double zComp =Math.pow(zombie.currentZ -player.playerCurZ , 2);
	    Double distance = Math.sqrt(xComp+zComp);
	    dists.add(distance);
	    if( distance < 350)
	    {
	    	System.out.println("Eaten");
	    	return true;
	    }
	  }
	   //System.out.println(Collections.min(dists));	
	 
	  return false;
  }
  
  
  private void moveGraphicsZombies()
  {
	  
	  for(GraphicsZombie lZombie: lineZombies)
	  {
		lZombie.headingToRender();
	  }
	  
	  for(GraphicsZombie rZombie: randZombies)
	  {
		  rZombie.headingToRender();
	  }
	  
  }
  private boolean levelbeaten()
  {
	  double exitX=currentLevelGraphics.exit.get(0).renderSpaceX;
	  double exitZ=currentLevelGraphics.exit.get(0).renderSpaceZ;
	  double xComp= Math.pow(( exitX - player.playerCurX ), 2);
	  double yComp= Math.pow(( exitZ - player.playerCurZ ), 2);
	  double distance = Math.sqrt( xComp + yComp );
	  
	  
	  //System.out.println(distance);
	  if(distance < 300)
	  {
		 return true;
	  }
	  return false;
  }
  
  class MainGameLoop extends AnimationTimer
  {
	
	
	
	
	@Override
	public void handle(long arg0) 
	{
		// TODO Auto-generated method stub
		
		
	  if(!endGame())
	  {
		  
		 currentGameState.setGameRunning(false);
		 moveGraphicsZombies();
		  
	      if(eaten())
	      {
	    	  restartLevel();
	      }
		
		  if(levelbeaten())
		  { 
			nextLevel(root);  	  
		  }
		  currentGameState.setGameRunning(true);
	  }	
	  else
		System.out.println("YOU BEAT THE GAME");	
	}
  
  }
 
  public static void main(String[] args)
  {
	  launch(args);
	  
  }
  
  
  
  
}
