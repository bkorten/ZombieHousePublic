import java.util.Random;
//James Perry
//2/12/2016
public class LevelGenerator 
{
  private int numRows = 35;
  public int getNumRows(){return this.numRows;}
  
  private int numCols = 75;
  public int getNumCols(){return this.numCols;}  
  //exit is two tiles wide
  private int exitRow1;
  private int exitRow2;
  private int exitCol1;
  private int exitCol2;
  
  private int playerStartRow = 0;
  public int getPlayerStartRow(){return this.playerStartRow;}
  private int playerStartCol = 0;
  public int getPlayerStartCol(){return this.playerStartCol;}
  private int numRooms = 15;


  private int numObstacles = 12;
  

  private final int FLOOR = 0;
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
      //we need to make an exit     
      boolean exitFound = false;
      int wallForExit;
      while(!exitFound)
      {  
        boolean twoExits = rand.nextBoolean();
        wallForExit = rand.nextInt(4);
        switch(wallForExit)
        {
          //Let's put the exit on the left wall
          case 0:
          if(startCol != 1)
          {
            int doorTile1 = startRow+rand.nextInt(roomRows-3)+1;
            int doorTile2 = doorTile1+1;
            if(map[doorTile1][startCol-1] != 0 
            || map[doorTile2][startCol-1] != 0) break;
            map[doorTile1][startCol] = ZombieConstants.DOORWAY;
            map[doorTile2][startCol] = ZombieConstants.DOORWAY;
            map[doorTile1][startCol-1] = ZombieConstants.DOORWAY;
            map[doorTile2][startCol-1] = ZombieConstants.DOORWAY; 
            //add an extra exit to the right side
            map[doorTile1][colOffset-1] = ZombieConstants.DOORWAY;
            map[doorTile2][colOffset-1] = ZombieConstants.DOORWAY;
            map[doorTile1][colOffset] = ZombieConstants.DOORWAY;
            map[doorTile2][colOffset] = ZombieConstants.DOORWAY;
            if(colOffset<numCols)
            {
              map[doorTile1][colOffset] = ZombieConstants.DOORWAY;
              map[doorTile2][colOffset] = ZombieConstants.DOORWAY;
            }
            
            exitFound = true;
            break;
          }
          else wallForExit = rand.nextInt(4);
          //Exit on the right wall
          case 1:
          if(colOffset != numCols-1)
          {
            int doorTile1 = startRow+rand.nextInt(roomRows-3)+1;
            int doorTile2 = doorTile1+1;
            if(map[doorTile1][colOffset] != 0
            || map[doorTile2][colOffset] != 0) break;
            map[doorTile1][colOffset-1] = ZombieConstants.DOORWAY;
            map[doorTile2][colOffset-1] = ZombieConstants.DOORWAY;
            map[doorTile1][colOffset] = ZombieConstants.DOORWAY;
            map[doorTile2][colOffset] = ZombieConstants.DOORWAY;
            exitFound = true;
            break;
          }
          else wallForExit = rand.nextInt(4);
          //top wall
          case 2:
          if(startRow != 1)
          {
            int doorTile1 = startCol+rand.nextInt(roomCols-3)+1;
            int doorTile2 = doorTile1+1;
            if(map[startRow-1][doorTile1] != 0 
            || map[startRow-1][doorTile2] != 0) break;
            map[startRow][doorTile1] = ZombieConstants.DOORWAY;
            map[startRow][doorTile2] = ZombieConstants.DOORWAY;
            map[startRow-1][doorTile1] = ZombieConstants.DOORWAY;
            map[startRow-1][doorTile2] = ZombieConstants.DOORWAY;
            exitFound = true;
            break;
          }
          else wallForExit = rand.nextInt(4);
          //bottom wall
          case 3:
          if(rowOffset != numRows-1)
          {
            int doorTile1 = startCol+rand.nextInt(roomCols-3)+1;
            int doorTile2 = doorTile1+1;
            if(map[rowOffset][doorTile1] != 0 
            || map[rowOffset][doorTile2] != 0) break;
            map[rowOffset-1][doorTile1] = ZombieConstants.DOORWAY;
            map[rowOffset-1][doorTile2] = ZombieConstants.DOORWAY;
            map[rowOffset][doorTile1] = ZombieConstants.DOORWAY;
            map[rowOffset][doorTile2] = ZombieConstants.DOORWAY;
            if(twoExits == true)
            {
              //add an exit to the right wall if twoExits is true
              if(startRow>numRows/2)
              {
                int col= (colOffset-2)-rand.nextInt(2);
                map[startRow][col] = ZombieConstants.DOORWAY;
                map[startRow][col-1] = ZombieConstants.DOORWAY;
                map[startRow-1][col-1] = ZombieConstants.DOORWAY;
                map[startRow-1][col] = ZombieConstants.DOORWAY;
              }
            }  
            exitFound = true;
            break;
          }
          else wallForExit = rand.nextInt(4);
        }
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
  
  public void expandHallways()
  {
    
  }
  public void truncateVerticalDoorWays()
  {
    for(int i = 2;i<numRows;i++)
    {
      for(int j = 1;j<numCols;j++)
      {
        if(map[i][j] == ZombieConstants.DOORWAY
        && map[i+1][j]==ZombieConstants.DOORWAY
        && map[i+2][j]!= ZombieConstants.WALL
        && map[i-2][j]!= ZombieConstants.WALL)
        {
          map[i][j] = ZombieConstants.FLOOR;
          map[i+1][j] = ZombieConstants.FLOOR;
        } 
      }
    }
  }
  
  public void initializeObstacles()
  {
    while(numObstacles>0)
    {
      numObstacles--;
    }
    
  }
  
  public void initializeStart()
  {
    boolean exit = false;
    double distance = 0;
    while(exit == false)  
    {
      playerStartRow = 0;
      playerStartCol = 0;
      while(map[playerStartRow][playerStartCol] != FLOOR)
      {
        playerStartRow = rand.nextInt(numRows - 3) + 1;
        playerStartCol = rand.nextInt(numCols - 3) + 1;
      }
      distance = 0;
      double rowDistance = (exitRow1 - playerStartRow);
      rowDistance = Math.pow(rowDistance, 2);
      double colDistance = (exitCol1 - playerStartCol);
      colDistance = Math.pow(colDistance, 2);
      distance = Math.sqrt(rowDistance + colDistance);
      if(distance>50) exit = true;
    }
    map[playerStartRow][playerStartCol] = PLAYER;
  }
  
  public boolean isColEmpty(int col)
  {
    for(int i = 1;i<(numRows-1)/2;i++)
    {
      if(map[i][col] != 0) return false;
    }
    return true;
  }
  
  public boolean initializeHalls() 
  {
    for(int i = 1;i<numRows-5;i+=5)
    {
      for(int j = 1;j<numCols-1;j++)
      {
       
      }
      
    }
    
    return false;
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
    //initializeObstacles();
    //initializeHalls();
    //truncateVerticalDoorWays();
    initializeStart();
    for(int i = 0;i<numRows;i++)
    {
      for(int j = 0;j<numCols;j++)
      {
        if(map[i][j] == 3) map[i][j] = 0;
      }
    }
    

  }
  
  

  public static void main(String[] args)
  {
    LevelGenerator l = new LevelGenerator();	
    l.printMap();
    Graph g = new Graph(l.getMap());
  }

}
