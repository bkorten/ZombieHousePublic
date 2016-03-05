//James Perry
//2/12/2016
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class LevelGenerator
{

  private int numRows = ZombieConstants.NUM_ROWS;
  private int numCols = ZombieConstants.NUM_COLS;
  private int playerStartRow = 0;
  public int getPlayerStartRow(){return this.playerStartRow;}
  private int playerStartCol = 0;
  public int getPlayerStartCol(){return this.playerStartCol;}
  
  //exit is two tiles wide
  private int exitRow1;
  private int exitRow2;
  private int exitCol1;
  private int exitCol2;
  
  private int numObstacles = 12;
  private int numFireTraps = 3;
  
  
  private int[][] graph = new int[numRows][numCols];
  public int[][] getGraph(){return this.graph;}
  private Random rand = new Random();
  public void printGraph()
  {
    for(int i = 0;i<numRows;i++)
    {
      for(int j = 0;j<numCols;j++)
      {
        System.out.print(graph[i][j]+" ");
      }
      System.out.println();
    }       
  }
  
  public LevelGenerator()
  {
  for(int i = 0;i<numRows;i++)
  {
    for(int j = 0;j<numCols;j++)
    {
      graph[i][j] = 4;
    }
  } 
    for(int i = 1;i<numRows-1;i++)
    {
      for(int j = 1;j<numCols-1;j++)
      {
        graph[i][j] = 0;
      }
    }   
    
  
  }
  public void carveRowFromLeft(int row)
  {
    int colPasses = rand.nextInt(2)+4;
    for(int i = 1;i<numCols-1;i++)
    {
      graph[row][i] = 4;
      if((graph[row-1][i] == 4) && (graph[row+1][i]==4))
      {
        colPasses--;
      }
      if(colPasses == 0) break;
    }
  }
  
  public void carveRowFromRight(int row)
  {
    int colPasses = rand.nextInt(2)+4;
    for(int i = numCols-1;i>1;i--)
    {
      graph[row][i] = 4;
      if((graph[row-1][i] == 4) && (graph[row+1][i]==4))
      {
        colPasses--;
      }
      if(colPasses == 0) break;
    }
  }
  
  
  
  public void carveCol(int col)
  {
    for(int i = 1;i<numRows-1;i++)
    {
      graph[i][col] = 4;
    }
    
  }
  
  
  public void carveColFromTop(int col)
  {
    int rowPasses = rand.nextInt(2)+4;
    for(int i = 1;i<numRows-1;i++)
    {
      graph[i][col] = 4;
      if((graph[i-1][col] == 4) && (graph[i][col+1]==4))
      {
        rowPasses--;
      }
      if(rowPasses == 0) break;
      
    }
    
  }
  public void carveColFromBottom(int col)
  {
    int rowPasses = rand.nextInt(2)+3;
    for(int i = numRows-1;i>=1;i--)
    {
      graph[i][col] = 4;
      if((graph[i-1][col] == 4) && (graph[i][col+1]==4))
      {
        rowPasses--;
      }
      if(rowPasses == 0) break;
      
    }
    
  }
  public boolean isInHallway(int row, int col)
  {
    int westCount = 0;
    int eastCount = 0;
    int northCount = 0;
    int southCount = 0;
    while(graph[row][col-westCount] == 0) westCount++; 
    while(graph[row][col+eastCount] == 0) eastCount++; 
    while(graph[row-northCount][col] == 0) northCount++;
    while(graph[row+southCount][col] == 0) southCount++;
    if(westCount+eastCount == 4) return true;
    if(northCount+southCount == 4) return true;
    return false;
  }
  
  public void clearBoxedAreas()
  {
    for(int i = 0;i<numRows-4;i++)
    {
      for(int j = 1;j<numCols-4;j++)
      {
        if(graph[i][j] == 4)
        {
          if(graph[i+1][j] == 4 &&
             graph[i+2][j] == 4 &&
             graph[i+3][j] == 4 &&
             graph[i][j+1] == 4 &&
             graph[i][j-1] == 4 &&
             graph[i+4][j+1] == 4 &&
             graph[i+4][j-1] == 4 
             )
          {
            graph[i+1][j] = 0;
            graph[i+2][j] = 0;
            graph[i+3][j] = 0;
          }
        }
      }
    }
  }
  
  public void clearVerticalParallelHalls()
  {
    for(int i = 1;i<numRows-13;i++)
    {
      for(int j = 1;j<numCols-1;j++)
      {
        if(graph[i][j] == 4 &&
           graph[i+1][j] == 0 &&
           graph[i+2][j] == 0 &&
           graph[i+3][j] == 0 &&
           graph[i+4][j] == 4 &&
           graph[i+5][j] == 0 &&
           graph[i+6][j] == 0 &&
           graph[i+7][j] == 0 &&
           graph[i+8][j] == 4 &&
           graph[i+9][j] == 0 &&
           graph[i+10][j] == 0 &&
           graph[i+11][j] == 0)
        {
           graph[i+4][j] = 0;
        }
           
      }
    }
  }
  
  public void clearHorizontalParallelHalls()
  {
    for(int i = 1;i<numRows-2;i++)
    {
      for(int j = 1;j<numCols-13;j++)
      {
        if(graph[i][j] == 4 &&
           graph[i][j+1] == 0 &&
           graph[i][j+2] == 0 &&
           graph[i][j+3] == 0 &&
           graph[i][j+4] == 4 &&
           graph[i][j+5] == 0 &&
           graph[i][j+6] == 0 &&
           graph[i][j+7] == 0 &&
           graph[i][j+8] == 4 &&
           graph[i][j+9] == 0 &&
           graph[i][j+10] == 0 &&
           graph[i][j+11] == 0)
        {
           graph[i][j+4] = 0;
        }
           
      }
    }
  }
  
  
  public void bisectLargerVerticalAreas()
  {
    for(int i = 0;i<numRows-1;i++)
    {
      for(int j = 0;j<numCols-1;j++)
      {
        if(graph[i][j] == 4 &&
           graph[i+1][j] == 4 &&
           graph[i][j+1] != 4)
        {
          int count = 1;
          while(graph[i+count][j+count] == 0)
          {
            count++;
            
          }
          int splitCol;
          if(count>10) splitCol = j+6;
          else if(count>13) splitCol = j+8;
          else continue;
          int rowCounter = i;
          while(graph[rowCounter][splitCol] == 0)
          {
            graph[rowCounter][splitCol] = 4;
            rowCounter++;
          }         
        }
           
      }
    }
  }
  
  public boolean isFilled()
  {
    for(int i = 0;i<numRows;i++)
    {
      for(int j = 0;j<numCols;j++)
      {
        if(graph[i][j] == 0) return false;
      }
    }
    
    return true;
  }
  
  public boolean isBadWall(int wall, int startRow, int startCol, int endRow, int endCol)
  {
    //north
    if(wall == 1 && startRow == 0) return true;
    //west
    if(wall == 2 && startCol == 0) return true;
    //south
    if(wall == 3 && endRow == numRows-1) return true;
    if(wall == 3 && endRow == numRows-2) return true;
    //east
    if(wall == 4 && endCol == numCols-1) return true;
    if(wall == 4 && endCol == numCols-2) return true;
    return false;
  }
  
  public void initializeRoomDoorways()
  {
    while(!isFilled())
    {
      int row = rand.nextInt(numRows-2)+1;
      int col = rand.nextInt(numCols-2)+1;
      if(graph[row][col] != 0) continue;
      int eastCounter = 0;
      int westCounter = 0;
      int northCounter = 0;
      int southCounter = 0;
      while(graph[row+northCounter][col] == 0) northCounter--;
      while(graph[row+southCounter][col] == 0) southCounter++;
      while(graph[row][col+westCounter] == 0) westCounter--;
      while(graph[row][col+eastCounter] == 0) eastCounter++;
      int startRow = row+northCounter;
      int startCol = col+westCounter;
      int endRow = startRow+(Math.abs(northCounter)+southCounter);
      int endCol = startCol+(Math.abs(westCounter)+eastCounter);
      
      
      int numExits = rand.nextInt(3)+1;
      ArrayList<Integer> wallsUsed = new ArrayList<>();
      while(numExits>0)
      {
        int wall = rand.nextInt(4)+1;
        
        if(wallsUsed.contains(wall)) wall = rand.nextInt(4)+1;
       
        while(isBadWall(wall,startRow,startCol,endRow,endCol)) wall = rand.nextInt(4)+1;
        switch(wall)
        {
          //add an exit to the North wall
          case 1:
          int col1 = rand.nextInt(endCol-startCol-1)+startCol+1;
          int col2;
          if(col1<endCol-1) col2 = col1+1;
          else col2 = col1-1;
          graph[startRow][col1] = 6;
          graph[startRow][col2] = 6;
          wallsUsed.add(1);
          numExits--;
          break;
          //add an exit to the West wall
          case 2:
          int row1 = rand.nextInt(endRow-startRow-1)+startRow+1;
          int row2;
          if(row1<endRow-1) row2= row1+1;
          else row2 = row1-1;
          graph[row1][startCol] = 6;
          graph[row2][startCol] = 6;
          wallsUsed.add(2);
          numExits--;
          break;
          //add an exit to the South wall
          case 3:
          int southCol1 = rand.nextInt(endCol-startCol-1)+startCol+1;
          int southCol2;
          if(southCol1<endCol-1) southCol2 = southCol1+1;
          else southCol2 = southCol1-1;
          graph[endRow][southCol1] = 6;
          graph[endRow][southCol2] = 6;
          wallsUsed.add(3);
          numExits--;
          break;
          //add an exit to the East wall
          case 4:
          int eastRow1 = rand.nextInt(endRow-startRow-1)+startRow+1;
          int eastRow2;
          if(eastRow1<endRow-1) eastRow2= eastRow1+1;
          else eastRow2 = eastRow1-1;
          graph[eastRow1][endCol] = 6;
          graph[eastRow2][endCol] = 6;
          wallsUsed.add(4);
          numExits--;
          break;   
        }
        
      }
      
      for(int i = startRow+1;i<endRow;i++)
      {
        for(int j = startCol+1;j<endCol;j++)
        {
          graph[i][j] = 3;
        }
      }
      graph[row][col] = 5;
    }
    
    
   
  }
  
  public void initializeExit()
  {
    int exit = rand.nextInt(4);
    int adjRow1 = 0;
    int adjCol1 = 0;
    int adjRow2= 0;
    int adjCol2 = 0;
    switch(exit)
    {
      case 0:
        //exit is going to be on the left side of the map
        exitRow1 = rand.nextInt(numRows);
        if(exitRow1>0) exitRow2 = exitRow1-1;
        else exitRow2 = exitRow1+1;
        exitCol1 = 0;
        exitCol2 = 0;  
        adjRow1 = exitRow1;
        adjRow2 = exitRow2;
        adjCol1 = 1;
        adjCol2 = 1;
        break;
      case 1:
        //exit is going to be on the top of the map 
        exitCol1 = rand.nextInt(numCols);  
        exitRow1 = 0;
        exitRow2 = 0;
        if(exitCol1>0) exitCol2 = exitCol1-1;
        else exitCol2 = exitCol1+1;
        adjRow1 = 1;
        adjRow2 = 1;
        adjCol1 = exitCol1;
        adjCol2 = exitCol2; 
        break;
      case 2:
        //exit is going to be on the right side of the map  
        exitRow1 = rand.nextInt(numRows);
        if(exitRow1>0) exitRow2 = exitRow1-1;
        else exitRow2 = exitRow1+1;
        exitCol1 = numCols-1;
        exitCol2 = numCols-1;
        adjRow1 = exitRow1;
        adjRow2 = exitRow2;
        adjCol1 = numCols-2;
        adjCol2 = numCols-2;
        break;
      //exit is going to be on the bottom of the map
      case 3:
        exitCol1 = rand.nextInt(numCols);
        exitRow1 = numRows-1;
        exitRow2 = numRows-1;
        if(exitCol1>0) exitCol2 = exitCol1-1;
        else exitCol2 = exitCol1+1;
        adjRow1 = numRows-2;
        adjRow2 = numRows-2;
        adjCol1 = exitCol1;
        adjCol2 = exitCol2;
        
        break;
      }
    
    graph[exitRow1][exitCol1] = ZombieConstants.EXIT;
    graph[exitRow2][exitCol2] = ZombieConstants.EXIT;
    graph[adjRow1][adjCol1] = 0;
    graph[adjRow2][adjCol2] = 0;
    
    
  }
  
  public void initializeStart()
  {
    boolean exit = false;
    double distance = 0;
    while(exit == false)  
    {
      playerStartRow = rand.nextInt(numRows-3)+1;
      playerStartCol = rand.nextInt(numRows-3)+1;
      while(graph[playerStartRow][playerStartCol] != 0)
      {
        playerStartRow = rand.nextInt(numRows - 3) + 1;
        playerStartCol = rand.nextInt(numCols - 3) + 1;
      }
      
      if(!isInHallway(playerStartRow, playerStartCol)) continue;
      distance = 0;
      double rowDistance = (exitRow1 - playerStartRow);
      rowDistance = Math.pow(rowDistance, 2);
      double colDistance = (exitCol1 - playerStartCol);
      colDistance = Math.pow(colDistance, 2);
      distance = Math.sqrt(rowDistance + colDistance);
      int requiredDistance = 0;
      if(exitCol1 == 0 || exitCol1 == numCols-1) requiredDistance = 60;
      else requiredDistance = 36;
      if(distance>requiredDistance) exit = true;
    }
    graph[playerStartRow][playerStartCol] = 5;
  }
  
  public boolean isColFull(int col)
  {
    for(int i = 0;i<numRows-1;i++)
    {
      if(graph[i][col] != 4) return false;   
    }
    return true;
  }
  
  
  public void breakFullCols()
  {
    for(int i = 1;i<numCols-1;i++)
    {
      if(isColFull(i))
      {
        int randRow = rand.nextInt(numRows-2);
        int randRow2 = randRow+1;
        graph[randRow][i] = 0;
        graph[randRow2][i] = 0;
      }
    }
  }
  public void initializeObstacles()
  {
    while(numObstacles>0)
    {
      int row = rand.nextInt(numRows-8)+1;
      int col = rand.nextInt(numCols-2)+1;
      if(graph[row][col] != 0) continue;
      if(graph[row-1][col] != 0 || graph[row-2][col] != 0) continue;
      
      int len = rand.nextInt(2)+4;
      int count = 0;
      int tmp = row;
      while(graph[tmp][col] == 0 &&
          graph[tmp][col-1] == 0 &&
          graph[tmp][col+1] == 0 &&
          graph[tmp][col-2] == 0 &&
          graph[tmp][col+2] == 0)
      {
        count++;
        tmp++;
      }
      if(graph[row+len][col] != 0 || graph[row+len+1][col] != 0) continue;
      if(count >= len)
      {
        for(int i = row;i<row+len;i++)
        {
          graph[i][col] = 9;
        }
      }
      else continue;  
      numObstacles--;
    }
  }
  public void initializeFireTraps()
  {
    while(numFireTraps>0)
    {
      int row = rand.nextInt(numRows-4)+1;
      int col = rand.nextInt(numCols-2)+1;
      if(graph[row][col] != 0) continue;
      if(isInHallway(row,col)) continue;
      
      graph[row][col] = ZombieConstants.FIRE_TRAP;
      graph[row+1][col] = ZombieConstants.FIRE_TRAP;
      graph[row+2][col] = ZombieConstants.FIRE_TRAP;
      
      numFireTraps--;
    }
  }
  
  
  public void carveMap()
  {
    ArrayList<Integer> rowCandidates = new ArrayList<>();
    ArrayList<Integer> colCandidates = new ArrayList<>();

    for(int i = 1;i<numRows-1;i++)
    {
      if(i%4 == 0) 
      {
        rowCandidates.add(i); 
      }
      
    }
    for(int j = 1;j<numCols-1;j++)
    {
      if(j%4 == 0)
      {
        colCandidates.add(j);
      }
    }
    Collections.shuffle(rowCandidates);
    Collections.shuffle(colCandidates);
    int randomColBreaks = rand.nextInt(4)+7;
    while(randomColBreaks>0)
    {
      int breakPoint = rand.nextInt(colCandidates.size());
      while(breakPoint==0) breakPoint = rand.nextInt(colCandidates.size());
      int topOrBottom = rand.nextInt(2);
      if(topOrBottom == 1) carveColFromTop(colCandidates.get(breakPoint));
      else carveColFromBottom(colCandidates.get(breakPoint));
      colCandidates.remove(breakPoint);
      randomColBreaks--;
    }
    
    
    int randomRowBreaks = rand.nextInt(3)+5;
    while(randomRowBreaks>0)
    {
      int breakPoint = rand.nextInt(rowCandidates.size());
      while(breakPoint==0) breakPoint = rand.nextInt(rowCandidates.size());
      
      int leftOrRight = rand.nextInt(2);
      if(leftOrRight == 1) carveRowFromLeft(rowCandidates.get(breakPoint));
      else carveRowFromRight(rowCandidates.get(breakPoint));
      rowCandidates.remove(breakPoint);
      randomRowBreaks--;
    }
    clearBoxedAreas(); 
    clearVerticalParallelHalls();
    clearHorizontalParallelHalls();
    bisectLargerVerticalAreas();
    initializeRoomDoorways();
    for(int i = 0;i<numRows;i++)
    {
      for(int j = 0;j<numCols;j++)
      {
        if(graph[i][j] == 3 ||
           graph[i][j] == 6 ||
           graph[i][j] == 5) graph[i][j] = 0;
      }
    }
    
    for(int i = 0;i<numRows;i++)
    {
      graph[i][0] = 4;
      graph[i][numCols-1] = 4;
    }
    
    for(int i = 0;i<numCols;i++)
    {
      graph[0][i] = 4;
      graph[numRows-1][i] = 4;
    }
    
    initializeExit();
    initializeStart();
    breakFullCols();
    initializeObstacles();
    initializeFireTraps();
  }
  
  
  public static void main(String[] args) 
  {
      LevelGenerator g = new LevelGenerator();
      g.carveMap();
      g.printGraph();

  }

}
