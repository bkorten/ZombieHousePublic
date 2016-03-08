import java.util.ArrayList;
import java.util.Random;

import resources.sound.ZombieSoundWorker;
//James Perry
//2-18-2016
public class GameState 
{
  private ArrayList<RandomZombie> randZombieList = new ArrayList<>();
  public ArrayList<RandomZombie> getRandZombieList(){return this.randZombieList;}
  
  private ArrayList<LineZombie> lineZombieList = new ArrayList<>();
  public ArrayList<LineZombie> getLineZombieList(){return this.lineZombieList;}
  private int[][] floorPlan;
  public int[][] getFloorPlan(){return this.floorPlan;}
  
  private boolean alertMaster = false;
  
  private int playerSight;
  private int playerHearing;
  private double playerSpeed;
  private double playerStamina;
  private double playerRegen;
  private double zombieSpawn;
  private double zombieSpeed;
  public double getZombieSpeed(){return this.zombieSpeed;}
  
  private static double zombieSmell;
  private static double zombieDecisionRate;
  
  private double cameraAngle;
  public void setCameraAngle(double b){this.cameraAngle = b;}
  
  
  private ZombieSoundWorker step;
  private ZombieSoundWorker scream;
  private ZombieSoundWorker growl1;
  private ZombieSoundWorker growl2;
  private ZombieSoundWorker growl3;
  private ZombieSoundWorker growl4;
  
  private ZombieDecisionWorker decisionMaker;
  private ZombieMoveWorker zombieMover;
  
  private int currentLevel;
  public int getCurrentLevel(){return this.currentLevel;}

  private MasterZombie master;
  
  private int playerCurrentRow;
  private int playerCurrentCol;
  
  public MasterZombie getMaster(){return this.master;}
  
  private int numRows;
  private int numCols;
  
    
  private Random rand;
  
  private Graph graph;
  
  public void setPlayerSight(int sight){this.playerSight = sight;}
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
  public void setZombieDecisionRate(double x)
  {
    this.zombieDecisionRate = x;
    decisionMaker.setZombieDecisionRate(x);
  }
  
  
  public void setGameRunning(boolean b)
  {
    decisionMaker.setRunning(b);
    zombieMover.setRunning(b);
  }

  
  public void terminateThreads()
  {
    step.setTerminate(true);
    scream.setTerminate(true);
    growl1.setTerminate(true);
    growl2.setTerminate(true);
    growl3.setTerminate(true);
    growl4.setTerminate(true);
    decisionMaker.setRunning(false);
    zombieMover.setRunning(false);
    decisionMaker.setTerminate(true);
    zombieMover.setTerminate(true);
    while(step.isAlive() || scream.isAlive() || growl1.isAlive()
     || growl2.isAlive() || growl3.isAlive() || growl4.isAlive()
     || decisionMaker.isAlive())
    {
      try{Thread.sleep(500);}
      catch(InterruptedException e){}
    } 
  }
  
  public void initializeThreads()
  {
    step = new ZombieSoundWorker("FootStep.wav");
    scream = new ZombieSoundWorker("Scream.wav");
    growl1 = new ZombieSoundWorker("Growl_01.wav");
    growl2 = new ZombieSoundWorker("Growl_02.wav");
    growl3 = new ZombieSoundWorker("Growl_03.wav");
    growl4 = new ZombieSoundWorker("Growl_04.wav");
    
    decisionMaker = new ZombieDecisionWorker(this);
    zombieMover = new ZombieMoveWorker(this);
    
    step.start();
    scream.start();
    growl1.start();
    growl2.start();
    growl3.start();
    growl4.start();
    
    
    decisionMaker.start();
    zombieMover.start();
    
  }
  
  
  public GameState(int level)
  {
    LevelGenerator levelGen = new LevelGenerator();
    levelGen.carveMap();
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
      playerSpeed = 1.0;
      playerStamina = 5.0;
      playerRegen = 0.2;
      zombieSpawn = 0.01;
      zombieSpeed = 1.0;
      zombieDecisionRate = 2.0;
      zombieSmell = 15.0;
      break;
      case 2:
      playerSight = 5;
      playerHearing = 16;
      playerSpeed = 1.75;
      playerStamina = 4.0;
      playerRegen = 0.2;
      zombieSpawn = 0.02;
      zombieSpeed = 0.65;
      zombieDecisionRate = 2.5;
      zombieSmell = 20.0;
      break;
      case 3:
      playerSight = 4;
      playerHearing = 13;
      playerSpeed = 1.60;
      playerStamina = 3.0;
      playerRegen = 0.175;
      zombieSpawn = 0.03;
      zombieSpeed = 0.75;
      zombieDecisionRate = 2.5;
      zombieSmell = 25.0;
      break;
      case 4:
      playerSight = 3;
      playerHearing = 10;
      playerSpeed = 1.50;
      playerStamina = 2.0;
      playerRegen = 0.175;
      zombieSpawn = 0.04;
      zombieSpeed = 0.85;
      zombieDecisionRate = 2.5;
      zombieSmell = 30.0;
      break;
      case 5:
      playerSight = 3;
      playerHearing = 8;
      playerSpeed = 1.25;
      playerStamina = 2.0;
      playerRegen = 0.135;
      zombieSpawn = 0.05;
      zombieSpeed = 1;
      zombieDecisionRate = 2.5;
      zombieSmell = 40.0;
      break;
    }
    currentLevel = level;
    int idCounter = 1;
    
    for(int i = 1;i<numRows-1;i++)
    {
      for(int j = 1;j<numCols-1;j++)
      {
        if (levelGen.isInHallway(i, j)) continue;
        if(rand.nextFloat()<zombieSpawn)
        {
          float lineOrRandom = rand.nextFloat();
          if(lineOrRandom<0.5)
          {
            lineZombieList.add(new LineZombie(i,j,idCounter));
            idCounter++;
            floorPlan[i][j] = ZombieConstants.LINE_ZOMBIE;
          }
          else
          {
            randZombieList.add(new RandomZombie(i,j,idCounter));
            idCounter++;
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
      if(distance>20) 
      {
        exitLoop = true;
        master = new MasterZombie(masterStartRow,masterStartCol,idCounter);
        floorPlan[masterStartRow][masterStartCol] = ZombieConstants.MASTER_ZOMBIE;
      }
    }  
    graph = new Graph(floorPlan);
    
  }
  
  public void printFloorPlan()
  {
    for(int i = 0;i<ZombieConstants.NUM_ROWS;i++)
    {
      for(int j = 0;j<ZombieConstants.NUM_COLS;j++)
      {
        System.out.print(floorPlan[i][j]+" ");
      }
      System.out.println();
    }
  }
  
  
  public void makeRandomZombieDecisions()
  {
    int row;
    int col;
    int newHeadingRow;
    int newHeadingCol;
    for(int i = 0;i<randZombieList.size();i++)
    {
      row = randZombieList.get(i).getCurrentRow();
      col = randZombieList.get(i).getCurrentCol();
      
      if(graph.euclideanDistance(row, col, playerCurrentRow, playerCurrentCol) <= zombieSmell)
      {
        alertMaster = true;
        if(randZombieList.get(i).getCollided() == true)
        {
          randZombieList.get(i).randomizeHeading();
          randZombieList.get(i).setCollided(false);
          continue;
        }
        graph.pathFinding(row, col, playerCurrentRow, playerCurrentCol);
        newHeadingRow = graph.getHeadingRow();
        newHeadingCol = graph.getHeadingCol();
        randZombieList.get(i).setHeadingRow(newHeadingRow);
        randZombieList.get(i).setHeadingCol(newHeadingCol);
      } 
      else 
      {
        randZombieList.get(i).randomizeHeading();
      } 
    }
    
  }
  
  public synchronized void makeLineZombieDecisions()
  {
    int row;
    int col;
    int newHeadingRow;
    int newHeadingCol;
    for(int i = 0;i<lineZombieList.size();i++)
    {
      row = lineZombieList.get(i).getCurrentRow();
      col = lineZombieList.get(i).getCurrentCol();
      if(graph.euclideanDistance(playerCurrentRow, playerCurrentCol, row, col) <= zombieSmell)
      {
        alertMaster = true;
        graph.pathFinding(row, col, playerCurrentRow, playerCurrentCol);
        newHeadingRow = graph.getHeadingRow();
        newHeadingCol = graph.getHeadingCol();
        lineZombieList.get(i).setHeadingRow(newHeadingRow);
        lineZombieList.get(i).setHeadingCol(newHeadingCol);
        continue;
      }
      else if(lineZombieList.get(i).getCollided() == true)
      {
        lineZombieList.get(i).randomizeHeading();
        continue;
      }
    } 
  }
  
  public void moveLineZombies()
  {
    int row;
    int col;
    int nextRow;
    int nextCol;
    for(int i = 0;i<lineZombieList.size();i++)
    {
      row = lineZombieList.get(i).getCurrentRow();
      col = lineZombieList.get(i).getCurrentCol();
      nextRow = row+lineZombieList.get(i).getHeadingRow();
      nextCol = col+lineZombieList.get(i).getHeadingCol();
      if(floorPlan[nextRow][nextCol] == 0)
      {
        floorPlan[nextRow][nextCol] = ZombieConstants.LINE_ZOMBIE;
        floorPlan[row][col] = 0;
        lineZombieList.get(i).setCollided(false);
        lineZombieList.get(i).setCurrentRow(nextRow);
        lineZombieList.get(i).setCurrentCol(nextCol);
        graph.updatePosition(row, col, nextRow, nextCol, ZombieConstants.LINE_ZOMBIE);
        double dist = graph.euclideanDistance(nextRow, nextCol, 
            playerCurrentRow, playerCurrentCol);
            if(dist <= playerHearing)
           {
             playGrowl(dist);
           }
      }
      else if(floorPlan[nextRow][nextCol] != 0)
      {
        lineZombieList.get(i).setCollided(true); 
      }
    }
  }
  
  
  public void makeMasterZombieDecision()
  {
    if(alertMaster == false)
    {
      master.randomizeHeading();
      return;
    }
    int row = master.getCurrentRow();
    int col = master.getCurrentCol();
    int goalRow = playerCurrentRow;
    int goalCol = playerCurrentCol;
    graph.pathFinding(row, col, goalRow, goalCol);
    master.setHeadingRow(graph.getHeadingRow());
    master.setHeadingCol(graph.getHeadingCol());
    alertMaster = false;
  }
  
  
  public void moveMasterZombie()
  {
    int row = master.getCurrentRow();
    int col = master.getCurrentCol();
    int headingRow = master.getHeadingRow();
    int headingCol = master.getHeadingCol();
    int newRow = row+headingRow;
    int newCol = col+headingCol;
    if(floorPlan[newRow][newCol] == 0)
    {
      floorPlan[newRow][newCol] = ZombieConstants.MASTER_ZOMBIE;
      floorPlan[row][col] = 0;
      master.setCurrentRow(newRow);
      master.setCurrentCol(newCol);
      graph.updatePosition(row, col, newRow, newCol, ZombieConstants.MASTER_ZOMBIE);
    }
    
  }
  
  public void moveRandomZombies()
  {
    int row;
    int col;
    int nextRow;
    int nextCol;
    for(int i = 0;i<randZombieList.size();i++)
    {
      row = randZombieList.get(i).getCurrentRow();
      col = randZombieList.get(i).getCurrentCol();
      nextRow = row+randZombieList.get(i).getHeadingRow();
      nextCol = col+randZombieList.get(i).getHeadingCol();
      if(floorPlan[nextRow][nextCol] == 0)
      {
        floorPlan[nextRow][nextCol] = ZombieConstants.RANDOM_ZOMBIE;
        floorPlan[row][col] = 0;
        randZombieList.get(i).setCollided(false);
        randZombieList.get(i).setCurrentRow(nextRow);
        randZombieList.get(i).setCurrentCol(nextCol);
        graph.updatePosition(row, col, nextRow, nextCol, ZombieConstants.RANDOM_ZOMBIE);
      }
      double dist = graph.euclideanDistance(nextRow, nextCol, 
                          playerCurrentRow, playerCurrentCol);
      if(dist <= playerHearing)
      {
        playGrowl(dist);
      }
    }   
  }
  
  public boolean movePlayer(int currentRow, int currentCol, int nextRow, int nextCol)
  {
    if(floorPlan[nextRow][nextCol] == ZombieConstants.FLOOR)
    {
      floorPlan[nextRow][nextCol] = ZombieConstants.PLAYER;
      floorPlan[currentRow][currentCol] = ZombieConstants.FLOOR;
      graph.updatePosition(currentRow, currentCol, 
                              nextRow,    nextCol, 
                           ZombieConstants.PLAYER);
      return true;
    }
    return false;
  }
  
  
  
  public void initializeBackup(GameState game, GameState backup)
  {
    backup.currentLevel = game.currentLevel;
    for(int i = 0;i<ZombieConstants.NUM_ROWS;i++)
    {
      for(int j = 0;j<ZombieConstants.NUM_COLS;j++)
      {
        backup.floorPlan[i][j] = game.floorPlan[i][j];
      }
    }
    backup.getLineZombieList().clear();
    for(int i = 0;i<game.getLineZombieList().size();i++)
    {
      backup.getLineZombieList().add
      (new LineZombie(game.getLineZombieList().get(i).getCurrentRow(),
                        game.getLineZombieList().get(i).getCurrentCol(),
                        game.getLineZombieList().get(i).getGraphicsID()));
    }
    
    backup.getRandZombieList().clear();
    for(int i = 0;i<game.getRandZombieList().size();i++)
    {
      backup.getRandZombieList().add
      (new RandomZombie(game.getRandZombieList().get(i).getCurrentRow(),
                        game.getRandZombieList().get(i).getCurrentCol(),
                        game.getRandZombieList().get(i).getGraphicsID()));
    }
    backup.getMaster().setCurrentRow(game.getMaster().getCurrentRow());
    backup.getMaster().setCurrentCol(game.getMaster().getCurrentCol());
  }
  
  public void playGrowl(double distance)
  {
    int growl = rand.nextInt(4);
    double loss = (1.0/(double) playerHearing);
    loss*= distance;
    double volume = (1.0-loss);
    switch(growl)
    {
      case 0:
      growl1.setVolume(volume);
      growl1.setPlay(true);
      break;
      case 1:
      growl1.setVolume(volume);
      growl1.setPlay(true);
      break;
      case 2:
      growl1.setVolume(volume);
      growl1.setPlay(true);
      break;
      case 3:
      growl1.setVolume(volume);
      growl1.setPlay(true);
      break;
    }
  }
 
  
 
  public static void main(String[] args)
  {
    GameState game = new GameState(1);
    
    game.printFloorPlan();
    game.initializeThreads();
    game.setGameRunning(true);
    game.playGrowl(10);
    while(true)
    {
      game.printFloorPlan();
      try{Thread.sleep(500);}catch(Exception e){}
    }
    //game.terminateThreads();    
  
  }
  
}
