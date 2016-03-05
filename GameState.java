import java.util.ArrayList;
import java.util.Random;
//James Perry
//2-18-2016
public class GameState 
{
  private ArrayList<RandomZombie> randZombieList = new ArrayList<>();
  private ArrayList<LineZombie> lineZombieList = new ArrayList<>();
  private int[][] floorPlan;
  public int[][] getFloorPlan(){return this.floorPlan;}
  
  private int playerSight;
  private int playerHearing;
  private double playerSpeed;
  private double playerStamina;
  private double playerRegen;
  private double zombieSpawn;
  private static double zombieSpeed;
  private static double zombieSmell;
  private static double zombieDecisionRate;
  private int currentLevel;
  
  private MasterZombie master;
  
  private int playerCurrentRow;
  private int playerCurrentCol;
  private int numRows;
  private int numCols;
  private Random rand;
  
  private Graph graph;
  
  public synchronized void setPlayerSight(int sight){this.playerSight = sight;}
  public int getPlayerSight(){return this.playerSight;}
  
  public synchronized void setPlayerHearing(int hear){this.playerHearing = hear;}
  public int getPlayerHearing(){return this.playerHearing;}
  
  public synchronized void setPlayerSpeed(double speed){this.playerSpeed = speed;}
  public double getPlayerSpeed(){return this.playerSpeed;}
  
  public synchronized void setPlayerStamina(double stamina){this.playerStamina = stamina;}
  public double getPlayerStamina(){return this.playerStamina;}
  
  public synchronized void setPlayerRegen(double regen){this.playerRegen = regen;}
  public double getPlayerRegen(){return this.playerRegen;}
  
  public int getPlayerCurrentRow(){return this.playerCurrentRow;}
  public int getPlayerCurrentCol(){return this.playerCurrentCol;}
  
  public double getZombieDecisionRate(){return this.zombieDecisionRate;}
  public void setZombieDecisionRate(double x){this.zombieDecisionRate = x;}
  
  public GameState(int level)
  {
    LevelGenerator levelGen = new LevelGenerator();
    rand = new Random();
    playerCurrentRow = levelGen.getPlayerStartRow();
    playerCurrentCol = levelGen.getPlayerStartCol();
    floorPlan = levelGen.getGraph();
    numRows = ZombieConstants.NUM_ROWS;
    numCols = ZombieConstants.NUM_COLS;
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
    for(int i = 0;i<numRows;i++)
    {
      for(int j = 0;j<numCols;j++)
      {
        if (!levelGen.isInHallway(i, j)) continue;
        if(rand.nextFloat()<zombieSpawn)
        {
          float lineOrRandom = rand.nextFloat();
          if(lineOrRandom<0.5)
          {
            lineZombieList.add(new LineZombie(i,j));
            floorPlan[i][j] = ZombieConstants.LINE_ZOMBIE;
          }
          else
          {
            randZombieList.add(new RandomZombie(i,j));
            floorPlan[i][j] = ZombieConstants.RANDOM_ZOMBIE;
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
        master = new MasterZombie(masterStartRow,masterStartCol);
        floorPlan[masterStartRow][masterStartCol] = ZombieConstants.MASTER_ZOMBIE;
      }
    }  
    
    graph = new Graph(floorPlan);
  }
  
  public void makeZombieDecisions()
  {
    boolean alertMaster = false;
    int row;
    int col;
    int[] newHeading;
    for(int i = 0;i<randZombieList.size();i++)
    {
      row = randZombieList.get(i).getCurrentRow();
      col = randZombieList.get(i).getCurrentCol();
      if(graph.euclideanDistance(playerCurrentRow, playerCurrentCol, row, col) <= zombieSmell)
      {
        graph.pathFinding(row, col, playerCurrentRow, playerCurrentCol);
        newHeading = graph.getHeading();
        randZombieList.get(i).setHeadingRow(newHeading[0]);
        randZombieList.get(i).setHeadingCol(newHeading[1]);
      }
      
    }
    
  }
  
  
  public void moveZombies()
  {
    int row;
    int col;
    int nextRow;
    int nextCol;
    for(int i = 0;i<randZombieList.size();i++)
    {
      row = randZombieList.get(i).getCurrentRow();
      col = randZombieList.get(i).getCurrentCol();
      nextRow = randZombieList.get(i).getHeadingRow();
      nextCol = randZombieList.get(i).getHeadingCol();
      if(floorPlan[nextRow][nextCol] == 0)
      {
        floorPlan[nextRow][nextCol] = ZombieConstants.RANDOM_ZOMBIE;
        floorPlan[row][col] = 0;
      }
      else if(floorPlan[nextRow][nextCol] != 0)
      {
        randZombieList.get(i).setCollided(true);
      }
    }
    
    for(int i = 0;i<lineZombieList.size();i++)
    {
      row = lineZombieList.get(i).getCurrentRow();
      col = lineZombieList.get(i).getCurrentCol();
      nextRow = lineZombieList.get(i).getHeadingRow();
      nextCol = lineZombieList.get(i).getHeadingCol();
      if(floorPlan[nextRow][nextCol] == 0)
      {
        floorPlan[nextRow][nextCol] = ZombieConstants.LINE_ZOMBIE;
        floorPlan[row][col] = 0;
      }
    }
    
    row = master.getCurrentRow();
    col = master.getCurrentCol();
    nextRow = master.getHeadingRow();
    nextCol = master.getHeadingCol();
    if(floorPlan[nextRow][nextCol] == 0)
    {
      floorPlan[nextRow][nextCol] = ZombieConstants.MASTER_ZOMBIE;
      floorPlan[row][col] = 0;
    }
    
  }
  
  
  public void initilizeBackupGame(GameState game,GameState duplicate)
  {
    
  }
  
  public static void main(String[] args)
  {
    GameState game = new GameState(1); 
    GameState duplicate = new GameState(1);
    game.initilizeBackupGame(game,duplicate);
    
  }
  
}
