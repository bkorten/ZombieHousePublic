import java.util.Random;
//James Perry
//2/12/2016
public class LevelGenerator 
{
  private int numRows = 20;
  private int numCols = 30;
  
  private int exitRow1;
  private int exitRow2;
  private int exitCol1;
  private int exitCol2;
  
  private int playerStartRow;
  private int playerStartCol;
  
  private int numRooms = 2;
  private int roomMinTiles = 25;
  
  private int numHallways = 6;
  private int numObstacles = 12;
  

  private final int FLOOR = 0;
  private final int ROOM = 2;
  private final int WALL = 4;
  private final int PLAYER = 8;
  private final int EXIT = 16;
  
  private int[][] map;
  private Random rand;
  
  public int[][] getMap(){return this.map;}
  
  public void printMap()
  {
    for(int i = 0;i<numRows;i++)
    {
      for(int j = 0;j<numCols;j++)
      {
        System.out.print(map[i][j]+" ");	  
      }
      System.out.println();
    }	  
  }
  

  public void initializeRooms()
  {
    while(numRooms>0)
    {
      int roomRows = rand.nextInt(2)+6;
      int roomCols = rand.nextInt(2)+6;
      while(roomRows == roomCols) roomCols = rand.nextInt(2)+5;
      int startRow = rand.nextInt(numRows-2)+1;
      int startCol = rand.nextInt(numCols-2)+1;
      int rowOffset = startRow+roomRows;
      int colOffset = startCol+roomCols;
      if(rowOffset>(numRows-2)) continue;
      else if(colOffset>(numCols-2)) continue;
      
      boolean badArea = false;
      for(int i = startRow;i<rowOffset;i++)
      {
        for(int j = startCol;j<colOffset;j++)
        {
          if(map[i][j] != FLOOR) badArea = true;
        }
      }     
      if(badArea == true) continue;  
      
      for(int i = startRow;i<rowOffset;i++)
      {
        for(int j = startCol;j<colOffset;j++)
        {
          map[i][j] = ROOM;
        }
      }  
      
      for(int i = startRow;i<rowOffset;i++)
      {
        map[i][startCol] = WALL;
        map[i][startCol-1] = WALL;
        map[i][colOffset] = WALL;
      }
      
      for(int i = startCol;i<=colOffset;i++)
      {
        map[startRow][i] = WALL; 
        map[startRow-1][i] = WALL;
        map[rowOffset][i] = WALL;
      }
     
      numRooms--;    
    }
  
  }
  
  public void initializeExit()
  {
    int exit = rand.nextInt(4);
    switch(exit)
    {
      case 0:
        //exit is going to be on the left side of the map
        exitRow1 = rand.nextInt(numRows);
        if(exitRow1>0) exitRow2 = exitRow1-1;
        else exitRow2 = exitRow1+1;
        exitCol1 = 0;
        exitCol2 = 0;
        break;
      case 1:
        //exit is going to be on the top of the map 
        exitCol1 = rand.nextInt(numCols);  
        exitRow1 = 0;
        exitRow2 = 0;
        if(exitCol1>0) exitCol2 = exitCol1-1;
        else exitCol2 = exitCol1+1;
        break;
      case 2:
        //exit is going to be on the right side of the map  
        exitRow1 = rand.nextInt(numRows);
        if(exitRow1>0) exitRow2 = exitRow1-1;
        else exitRow2 = exitRow1+1;
        exitCol1 = numCols-1;
        exitCol2 = numCols-1;
        break;
      //exit is going to be on the bottom of the map
      case 3:
        exitCol1 = rand.nextInt(numCols);
        exitRow1 = numRows-1;
        exitRow2 = numRows-1;
        if(exitCol1>0) exitCol2 = exitCol1-1;
        else exitCol2 = exitCol1+1;
        break;
      }
    
    map[exitRow1][exitCol1] = EXIT;
    map[exitRow2][exitCol2] = EXIT;
    
    
  }
  
  public void initializeStart()
  {
    
    playerStartRow = rand.nextInt(numRows-2)+1;
    playerStartCol = rand.nextInt(numCols-2)+1;    
    double distance = 0;
    while(distance<10)
    {
      double rowDistance = (exitRow1-playerStartRow);  
      rowDistance = Math.pow(rowDistance,2);
      double colDistance = (exitCol1-playerStartCol);
      colDistance = Math.pow(colDistance,2);
      
      distance = Math.sqrt(rowDistance+colDistance);
      if(distance>5 && map[playerStartRow][playerStartCol]!= WALL) break;
      else
      {
        playerStartRow = rand.nextInt(numRows-2)+1;
        playerStartCol = rand.nextInt(numCols-2)+1;
      }
      
    }
    
    map[playerStartRow][playerStartCol] = PLAYER;
  }
  
  
  public LevelGenerator(int currentLevel)
  {
    map = new int[numRows][numCols];  
    rand = new Random(System.currentTimeMillis()^50);
    
    for(int i = 0;i<numRows;i++)
    {
      map[i][0] = WALL;
      map[i][numCols-1] = WALL;
    }
    
    for(int i = 0;i<numCols;i++)
    {
      map[0][i] = WALL;
      map[numRows-1][i] = WALL;
    }
   
    initializeRooms();
    initializeExit();
    initializeStart();
    
  }
  
  public static void main(String[] args)
  {
    LevelGenerator l = new LevelGenerator(1);	
    l.printMap();

  }

}
