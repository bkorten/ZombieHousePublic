import java.util.ArrayList;
import java.util.Random;
//James Perry
//2-18-2016
public class GameState 
{
  private ArrayList<RandomZombie> randZombieList = new ArrayList<>();
  private ArrayList<LineZombie> lineZombieList = new ArrayList<>();
  private int[][] floorPlan;
  private int playerSight;
  private int playerHearing;
  private double playerSpeed;
  private double playerStamina;
  private double playerRegen;
  private double zombieSpawn;
  private double zombieSpeed;
  private double zombieSmell;
  private double zombieDecisionRate;
  private int currentLevel;
  
  private int playerCurrentRow;
  private int playerCurrentCol;
  private int numRows;
  private int numCols;
  
  
  public void setPlayerSight(int sight){this.playerSight = sight;}
  public int getPlayerSight(){return this.playerSight;}
  
  public void setPlayerHearing(int hear){this.playerHearing = hear;}
  public int getPlayerHearing(){return this.playerHearing;}
  
  public void setPlayerSpeed(double speed){this.playerSpeed = speed;}
  public double getPlayerSpeed(){return this.playerSpeed;}
  
  public void setPlayerStamina(double stamina){this.playerStamina = stamina;}
  public double getPlayerStamina(){return this.playerStamina;}
  
  public void setPlayerRegen(double regen){this.playerRegen = regen;}
  public double getPlayerRegen(){return this.playerRegen;}
  
  public int getPlayerCurrentRow(){return this.playerCurrentRow;}
  public int getPlayerCurrentCol(){return this.playerCurrentCol;}
  
  
  
  public GameState(int level, LevelGenerator levelGen)
  {
    playerCurrentRow = levelGen.getPlayerStartRow();
    playerCurrentCol = levelGen.getPlayerStartCol();
    floorPlan = levelGen.getMap();
    numRows = levelGen.getNumRows();
    numCols = levelGen.getNumCols();
    switch(level)
    {
      case 1:
      playerSight = 7;
      playerHearing = 20;
      playerSpeed = 2.0;
      playerStamina = 5.0;
      playerRegen = 0.2;
      zombieSpawn = 0.20;
      zombieSpeed = 0.5;
      zombieDecisionRate = 2.0;
      zombieSmell = 15.0;
      break;
      case 2:
      playerSight = 5;
      playerHearing = 16;
      playerSpeed = 1.75;
      playerStamina = 4.0;
      playerRegen = 0.2;
      zombieSpawn = 0.30;
      zombieSpeed = 0.65;
      zombieDecisionRate = 2.5;
      zombieSmell = 15.0;
      break;
      case 3:
      playerSight = 4;
      playerHearing = 13;
      playerSpeed = 1.60;
      playerStamina = 3.0;
      playerRegen = 0.175;
      zombieSpawn = 0.35;
      zombieSpeed = 0.75;
      zombieDecisionRate = 2.5;
      zombieSmell = 15.0;
      break;
      case 4:
      playerSight = 3;
      playerHearing = 10;
      playerSpeed = 1.50;
      playerStamina = 2.5;
      playerRegen = 0.175;
      zombieSpawn = 0.38;
      zombieSpeed = 0.85;
      zombieDecisionRate = 2.5;
      zombieSmell = 15.0;
      break;
      case 5:
      playerSight = 3;
      playerHearing = 8;
      playerSpeed = 1.25;
      playerStamina = 2.0;
      playerRegen = 0.135;
      zombieSpawn = 0.40;
      zombieSpeed = 1;
      zombieDecisionRate = 2.5;
      zombieSmell = 20.0;
      break;
    }
    Random rand = new Random();
    for(int i = 0;i<numRows;i++)
    {
      for(int j = 0;j<numCols;j++)
      {
        if (floorPlan[i][j] != ZombieConstants.ROOM) continue;
        if(rand.nextFloat()<zombieSpawn)
        {
          float lineOrRandom = rand.nextFloat();
          if(lineOrRandom<0.5)
          {
            lineZombieList.add(new LineZombie(i,j));
            floorPlan[i][j] = ZombieConstants.ZOMBIE;
          }
          else
          {
            randZombieList.add(new RandomZombie(i,j));
            floorPlan[i][j] = ZombieConstants.ZOMBIE;
          }
        } 
      }
    }
    
    int masterStartRow = 0;
    int masterStartCol = 0;
    boolean exitLoop = false;
    while(!exitLoop)
    {
      masterStartRow = rand.nextInt(numRows-3)+1;
      masterStartCol = rand.nextInt(numRows-3)+1;
      if(floorPlan[masterStartRow][masterStartCol] != 0) continue;
      double distance = 0;
      double rowDistance = (masterStartRow - playerCurrentRow);
      rowDistance = Math.pow(rowDistance, 2);
      double colDistance = (masterStartCol - playerCurrentCol);
      colDistance = Math.pow(colDistance, 2);
      distance = Math.sqrt(rowDistance + colDistance);
      if(distance>30) 
      {
        exitLoop = true;
        floorPlan[masterStartRow][masterStartCol] = ZombieConstants.MASTER_ZOMBIE;
      }
    }
    
    
   
  }
  
  
  public static void main(String[] args)
  {
    LevelGenerator lg = new LevelGenerator();
    GameState game = new GameState(1,lg); 
    
  }
  
}
