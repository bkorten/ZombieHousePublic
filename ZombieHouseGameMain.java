
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class ZombieHouseGameMain extends Application 
{
  private int currentLevelIndex;
  private LevelGraphics currentLevelGraphics;
  private GameState currentGameState;
  private GameState currentGameStatecopy;
  private Controller controller;
  private Player player;
  
  
  private ArrayList<GraphicsZombie> zombies= new ArrayList<GraphicsZombie>(); 
  
  public void start(Stage primaryStage)
  {
	primaryStage.setFullScreen(true);  
	
	Group root= new Group();
	
	Scene scene = new Scene(root,500,500,true);
	scene.setFill(Color.BLACK);
	
	primaryStage.setTitle("Zombie house");
	startLevel();
	buildLevelGraphics(root);
	addZombies(root);
	player= new Player(currentLevelGraphics.playerStartX,currentLevelGraphics.playerStartZ);
	controller= new Controller(player);
	controller.setWalls(currentLevelGraphics.walls);
	controller.handleKeyboard(scene, root);
	controller.handleMouse(scene, root);
	scene.setCamera(player.playerCamera);
	
	primaryStage.setTitle("Zombie House");
	primaryStage.setScene(scene);
	primaryStage.show();
  
  }
  private void addZombies(Group root)
	{
		double tmpx,tmpz;
		for(Point2D lineZSpawn : currentLevelGraphics.lineZombieSpawns)
		{	
			tmpx=lineZSpawn.getX();
			tmpz=lineZSpawn.getY();
			
			GraphicsZombie lineZtmp = new GraphicsZombie(tmpx,tmpz,ZombieConstants.LINE_ZOMBIE);
			zombies.add(lineZtmp);
		}
		
		for(Point2D randZSpawn : currentLevelGraphics.randomZombieSpawns)
		{	
			tmpx=randZSpawn.getX();
			tmpz=randZSpawn.getY();
			
			GraphicsZombie lineZtmp = new GraphicsZombie(tmpx,tmpz,ZombieConstants.RANDOM_ZOMBIE);
			zombies.add(lineZtmp);
		}
		root.getChildren().addAll(zombies);
	}
  private void startLevel()
  {
	  currentLevelIndex=0;
	  currentGameState = new GameState(currentLevelIndex);
	  
	  currentGameStatecopy=new GameState(currentLevelIndex);
	  //currentGameState.initilizeBackupGame(currentGameState, currentGameStatecopy);
  }
  
  private void nextLevel()
  {
    currentLevelIndex++;
    currentGameState = new GameState(currentLevelIndex);
    currentGameState.initilizeBackupGame(currentGameState, currentGameStatecopy);
  }
  
  private void buildLevelGraphics(Group root)
  {
		currentLevelGraphics = new LevelGraphics(currentGameState.getFloorPlan(),
				ZombieConstants.NUM_COLS ,ZombieConstants.NUM_ROWS,currentLevelIndex);
		
		root.getChildren().add(currentLevelGraphics);
  }
  
  private void renderLevel()
  {
	  
  }
  private void restartLevel()
  {
	  currentGameState = currentGameStatecopy;
	  currentGameState.initilizeBackupGame(currentGameState, currentGameStatecopy);
  }
  class MainGameLoop extends AnimationTimer
  {
	
	
	  
	private void moveZombies()
	{
		for(GraphicsZombie zombie : zombies)
		{
			// get zombie heading form game state.
			zombie.translate(1,0);
			
		}
	}
	
	
	
	@Override
	public void handle(long arg0) 
	{
		// TODO Auto-generated method stub
		moveZombies();
		
	}
  
  }
 
  public static void main(String args[])
  {
	  launch(args);
	  
  }
  
  
  
  
}
