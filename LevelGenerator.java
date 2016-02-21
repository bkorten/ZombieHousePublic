import java.util.Random;
//James Perry
//2/12/2016
public class LevelGenerator 
{
  private int numRows = 25;
  public int getNumRows(){return this.numRows;}
  
  private int numCols = 40;
  public int getNumCols(){return this.numCols;}  
  //exit is two tiles wide
  private int exitRow1;
  private int exitRow2;
  private int exitCol1;
  private int exitCol2;
  
  private int playerStartRow = 0;
  private int playerStartCol = 0;
  
  private int numRooms = 3;
  private int roomMinTiles = 25;
  
  private int numHallways = 6;
  private int numObstacles = 12;
  

  private final int FLOOR = 0;
  private final int HALLWAY = 6;
  private final int ROOM = 2;
  private final int WALL = 4;
  private final int PLAYER = 8;
  private final int EXIT = 5;
  
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
      //get width and height of room
      int roomRows = rand.nextInt(4)+7;
      int roomCols = rand.nextInt(4)+7;
      //can't be a square room
      while(roomRows == roomCols) roomCols = rand.nextInt(2)+7;
      //top left corner of room
      int startRow = rand.nextInt(numRows)+1;
      int startCol = rand.nextInt(numCols)+1;
      //bottom right corner of room
      int rowOffset = startRow+roomRows;
      int colOffset = startCol+roomCols;
      //can't go off the map
      if(rowOffset>(numRows-1)) continue;
      else if(colOffset>(numCols-1)) continue;

      //use this to see if we can't place a room 
      //in a particular spot
      boolean badArea = false;
      //1: is the real estate empty?
      for(int i = startRow;i<rowOffset;i++)
      {
        for(int j = startCol;j<colOffset;j++)
        {
          if(map[i][j] != FLOOR) badArea = true;
              
        }
      }
      
      //Step 2: We can't place a room that leaves one or two tile gaps
      //between itself and something else.
      if(colOffset<numCols-1)
      {
        for(int i = startRow;i<rowOffset;i++)
        {
          if(map[i][colOffset] == 0)
          {
            if(map[i][colOffset+1] != 0)
            { 
              badArea = true;
            }
            else if(map[i][colOffset+2] != 0) badArea = true;
          }
        }  
      }
      
      //continuation of step 2
      if(rowOffset<=numRows-1)
      {
        for(int i = startCol;i<colOffset;i++)
        {
          if(map[rowOffset][i] == 0)
          {
            if(map[rowOffset+1][i] != 0) badArea = true;
            else if((map[rowOffset+1][i] == 0) &&
                     map[rowOffset+2][i] != 0) badArea = true;
          }
        }
      }
      //also step 2
      if(startCol>1)
      {
        for(int i = startRow;i<rowOffset;i++)
        {
          if((map[i][startCol-1] == 0) && 
             (map[i][startCol-2] != 0)) badArea = true;
        }
      }
      //this is also part of step 2
      if(startCol>2)
      {
        for(int i = startRow;i<rowOffset;i++)
        {
          if((map[i][startCol-1] == 0) && 
             (map[i][startCol-2] == 0) && 
              map[i][startCol-3] != 0) badArea = true;
        }
      }
      //again, step 2
      if(startRow>1)
      {
        for(int i = startCol;i<colOffset;i++)
        {
          if(map[startRow-1][i] == 0)
          {
            if(map[startRow-2][i] != 0) badArea = true;
            else if(startRow>2)
            {
              if((map[startRow-2][i] == 0)
               &&(map[startRow-3][i] !=0)) badArea = true;
            }
          }   
          
        }
      }
     
      //real estate isn't good 
      if(badArea == true) continue;  
      //Okay, lets make a room
      for(int i = startRow;i<rowOffset;i++)
      {
        for(int j = startCol;j<colOffset;j++)
        {
          map[i][j] = ROOM;
        }
      }     
      //the outermost cells become the wall
      for(int i = startRow;i<rowOffset;i++)
      {
        map[i][startCol] = WALL;
        map[i][colOffset-1] = WALL;
      }
      
      for(int i = startCol;i<colOffset;i++)
      {
        map[startRow][i] = WALL;
        map[rowOffset-1][i] = WALL;
      }
      
      int wallForExit = rand.nextInt(4);
      switch(wallForExit)
      {
      case 0:
      break;
      case 1:
      break;
      case 2:
      break;
      case 3:
      break;
      
      }
      
      
      
      numRooms--;    
    }
  
  }
  
  public void initializeExit()
  {
    int exit = rand.nextInt(4);
    rand.setSeed(System.currentTimeMillis());
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

    playerStartRow = rand.nextInt(numRows - 2) + 1;
    playerStartCol = rand.nextInt(numCols - 2) + 1;
    double distance = 0;
    while (distance < 10) 
    {
      double rowDistance = (exitRow1 - playerStartRow);
      rowDistance = Math.pow(rowDistance, 2);
      double colDistance = (exitCol1 - playerStartCol);
      colDistance = Math.pow(colDistance, 2);

      distance = Math.sqrt(rowDistance + colDistance);
      if (distance > 5 && map[playerStartRow][playerStartCol] != WALL) break;
      else 
      {
        playerStartRow = rand.nextInt(numRows - 2) + 1;
        playerStartCol = rand.nextInt(numCols - 2) + 1;
      }
    }
    if(map[playerStartRow][playerStartCol] != 0)
    {
      initializeStart();
      return;
    } 
    map[playerStartRow][playerStartCol] = PLAYER;
  }
    
  
  public LevelGenerator()
  {
    map = new int[numRows][numCols];  
    rand = new Random(System.currentTimeMillis()^50);
    //rand = new Random(27);
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
    LevelGenerator l = new LevelGenerator();	
    l.printMap();

  }

}
