
import java.util.ArrayList;

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
			currentLevelGraphics.playerStartZ);
	
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
		ArrayList<Point2D> randIJs= currentLevelGraphics.lineZombieSpawnsIJ;
		for(int i = 0; i< randZombies.size(); i++)
		{
			GraphicsZombie randTmp = randZombies.get(i);
			Point2D randIJ= randIJs.get(i);
			randTmp.setIJ( (int)randIJ.getX(), (int)randIJ.getY() );
		}
		
		setZombieId();
		int totalZombie = lineZombies.size() + randZombies.size();
		System.out.println("number of Zombies "+ totalZombie);
		
		
		root.getChildren().addAll(randZombies);
		root.getChildren().addAll(lineZombies);
		
	}
  
  private void setZombieId()
  {
	 int lZs = currentGameState.getLineZombieList().size();
	 int rZs = currentGameState.getRandZombieList().size();
	//System.out.println(lZs+rZs);  
	for(GraphicsZombie lineZombie: lineZombies)
	{
		for(Zombie gameStateZombie : currentGameState.getLineZombieList())
		{
			if((lineZombie.getIpos() == gameStateZombie.getCurrentRow()) 
					&&(lineZombie.getJpos() == gameStateZombie.getCurrentCol()))
					{
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
			if((randZombie.getIpos() == gameStateZombie.getCurrentRow()) 
					&&(randZombie.getJpos() == gameStateZombie.getCurrentCol()))
					{
						randZombie.setId(gameStateZombie.getGraphicsID());
						randZombie.setGameStateZombie(gameStateZombie);
						break;
					}
			
		}
		
	}
	  
  }
  
  private void startLevel(Group root)
  {
	  currentLevelIndex=1;
	  currentGameState = new GameState(currentLevelIndex);
	  buildLevelGraphics(root);
	  addZombies(root);

  }
  
  private void nextLevel(Group root)
  {

	currentLevelIndex++;
    currentGameState = new GameState(currentLevelIndex);
    root.getChildren().remove(currentLevelGraphics);
    root.getChildren().removeAll(lineZombies);
    root.getChildren().removeAll(randZombies);
    buildLevelGraphics(root);
    player.playerCamera.setTranslateX(currentLevelGraphics.playerStartX);
    player.playerCamera.setTranslateZ(currentLevelGraphics.playerStartZ);
    addZombies(root);
    controller.setWalls(currentLevelGraphics.walls);
    
    
    
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
	  
	  
	  //currentGameStatecopy=currentGameState.backupGame(currentGameState);
  }
  
  private boolean eaten()
  {
	  for(GraphicsZombie zombie : lineZombies)
	  {
		//check to see if the zombie co
	  }
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
	  if(distance < 150)
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
		  //moveGraphicsZombies();	
	      if(eaten())
	      {
	    	  restartLevel();
	      }
		
		  if(levelbeaten())
		  { 
			nextLevel(root);  	  
		  }
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
