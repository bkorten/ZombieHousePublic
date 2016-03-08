/**
 * CS 351: Project-Zombie House
 * James Perry
 * This class is used to create a 2D floorplan for 
 * the zombie house, determine the player starting position,
 * the exit position, and the location of all firetraps, halls
 * walls, and hallways. It is instantiated by GameState every level.
 * This class DOES NOT create or place zombies on the floor plan. 
*/
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
  
  //utility method to print the floorPlan
  public void printFloorPlan()
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
  
  /**
   * Constructor creates a border of wall tiles
   * around an empty floor plan.
   */
  public LevelGenerator()
  {
    for(int i = 0;i<numRows;i++)
    {
      for(int j = 0;j<numCols;j++)
      {
        graph[i][j] = ZombieConstants.WALL;
      }
    } 
    for(int i = 1;i<numRows-1;i++)
    {
      for(int j = 1;j<numCols-1;j++)
      {
        graph[i][j] = ZombieConstants.FLOOR;
      }
    }   
  }
  
  /**
   * This method carves a wall from the left side of the graph   
   * Everytime we carve past a vertical wall, we decrease the 
   * number of colPasses (which are randomly determined). When
   * colPasses is equal to zero, we stop carving from this row.
   * @param row The row from which we will carve across the floor 
   *        plan
   */
  public void carveRowFromLeft(int row)
  {
    int colPasses = rand.nextInt(2)+4;
    for(int i = 1;i<numCols-1;i++)
    {
      graph[row][i] = ZombieConstants.WALL;
      if((graph[row-1][i] == ZombieConstants.WALL) && 
         (graph[row+1][i] == ZombieConstants.WALL))
      {
        colPasses--;
      }
      if(colPasses == 0) break;
    }
  }
  
  /**
   * This method carves a wall from the right side of the graph   
   * Everytime we carve past a vertical wall, we decrease the 
   * number of colPasses (which are randomly determined). When
   * colPasses is equal to zero, we stop carving from this row.
   * @param row The row from which we will carve across the floor 
   *        plan
   */
  public void carveRowFromRight(int row)
  {
    int colPasses = rand.nextInt(2)+4;
    for(int i = numCols-1;i>1;i--)
    {
      graph[row][i] = ZombieConstants.WALL;
      if((graph[row-1][i] == ZombieConstants.WALL) &&
         (graph[row+1][i] == ZombieConstants.WALL))
      {
        colPasses--;
      }
      if(colPasses == 0) break;
    }
  }
  
  /**
   * This method carves a wall from the top of the graph   
   * Everytime we carve past a horizontal wall, we decrease the 
   * number of rowPasses (which are randomly determined). When
   * rowPasses is equal to zero, we stop carving from this row.
   * @param col The col from which we will carve across the floor 
   *        plan
   */
  public void carveColFromTop(int col)
  {
    int rowPasses = rand.nextInt(2)+4;
    for(int i = 1;i<numRows-1;i++)
    {
      graph[i][col] = ZombieConstants.WALL;
      if((graph[i-1][col] == ZombieConstants.WALL) &&
         (graph[i][col+1] == ZombieConstants.WALL))
      {
        rowPasses--;
      }
      if(rowPasses == 0) break;
    } 
  }
  /**
   * This method carves a wall from the bottom of the graph   
   * Everytime we carve past a horizonatl wall, we decrease the 
   * number of rowPasses (which are randomly determined). When
   * rowPasses is equal to zero, we stop carving from this row.
   * @param col The row from which we will carve across the floor 
   *        plan
   */
  public void carveColFromBottom(int col)
  {
    int rowPasses = rand.nextInt(2)+3;
    for(int i = numRows-1;i>=1;i--)
    {
      graph[i][col] = ZombieConstants.WALL;
      if((graph[i-1][col] == ZombieConstants.WALL) && 
         (graph[i][col+1] == ZombieConstants.WALL))
      {
        rowPasses--;
      }
      if(rowPasses == 0) break;
      
    } 
  }
  /**
   * This method is called by several methods in this class, as well
   * as some of the methods in GameState. It returns a boolean if the
   * tile in graph[row][col] is located within a hallway or not.
   * @param row The row corresponding to the tile in question
   * @param col The col corresponding to the tile in question
   * @return true if the tile is located in a hallway, false otherwise
   */
  public boolean isInHallway(int row, int col)
  {
    if(row == 0 || row == numRows-1) return false;
    if(col == 0 || col == numCols-1) return false;
    //we use counters to see how wide/tall the space around the
    //tile is.
    int westCount = 0;
    int eastCount = 0;
    int northCount = 0;
    int southCount = 0;
    //count to the left
    while(graph[row][col-westCount] == 0)
    {
      if(col-westCount<1) break;
      westCount++; 
    }
    //count to the right
    while(graph[row][col+eastCount] == 0)
    {
      if(col+eastCount>=numCols-1) break;
      eastCount++; 
    }
    //count up
    while(graph[row-northCount][col] == 0)
    { 
      if(row-northCount<1) break;
      northCount++;
    }
    //count down
    while(graph[row+southCount][col] == 0)
    {
      if(row+southCount>=numRows-1) break;
      southCount++;
    }
    //we are in a hallway if the total number of free 
    //vertical or horizontal tiles nearby equal 4 
    if(westCount+eastCount == 4) return true;
    if(northCount+southCount == 4) return true;
    //side note:the current tile gets counted in the
    //tally, which is why 4 is the maic number rather
    //than 3
    return false;
  }
  
  /**
   * This method is used after the walls are carved into
   * the floorPlan to fix a periodic occurrance of small
   * 3*3 spaces. They are neither hallways nor rooms so
   * they need to be cleared.
   * 
   */
  public void clearBoxedAreas()
  {
    for(int i = 0;i<numRows-4;i++)
    {
      for(int j = 1;j<numCols-4;j++)
      {
        if(graph[i][j] == 4)
        {
          //I know it's messy, but this is 
          //a specific type of problem that
          //needs to be dealt with through specific
          //checking
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
  /**
   * This is a method called after the walls are carved into the 
   * floorPlan. It fixes an issue where occasionally we end up with
   * three hallways sitting atop one another, because java's
   * random number generator sometimes pulls the same number three 
   * times in a row.
   */
  public void clearVerticalParallelHalls()
  {
    for(int i = 1;i<numRows-13;i++)
    {
      for(int j = 1;j<numCols-1;j++)
      {
        //once again, specific problem needs specific solution
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
  /**
   * This method is called to clear the same issue as the method above, but 
   * deals with horizontal hallways instead of vertical ones.
   */
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
  
  /**
   * This method is called to get rid of abnormally large spaces
   * that occur when a few calls to carveColFrom* and carveRowFrom*
   * end up ignoring the a similar portion of the graph, so we end up
   * with a massive room that typically spans the entire width of the 
   * floorPlan.
   */
  public void bisectLargerVerticalAreas()
  {
    for(int i = 0;i<numRows-1;i++)
    {
      for(int j = 0;j<numCols-1;j++)
      {
        //find a corner
        if(graph[i][j] == 4 &&
           graph[i+1][j] == 4 &&
           graph[i][j+1] != 4)
        {
          //start counting diagonally
          int count = 1;
          while(graph[i+count][j+count] == 0)
          {
            count++; 
          }
          //we split the room vertically every time
          //splitCol is the col index where we will recarve
          //the wall
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
  /**
   * This method is used when we're adding exits to every room and hallway.
   * When we add an exit to a room, we set all of the tiles in that room equal
   * to a number other than ZombieConstants.FLOOR so we don't come back and
   * add more exits. When all of the spaces have exits, we set all of the Floor
   * tiles back to ZombieConstants.FLOOR
   * 
   * When this method returns true, every room and hallway has at least one exit 
   * carved into it
   */
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
  /**
   * This class is used by initializeDoorways()
   * in order to see if adding an exit to a particular
   * room's wall is a bad idea. For example, we wouldn't want to
   * have exits being carved into the outside walls by the rooms 
   * that border them.
   */
  public boolean isBadWall(int wall, int startRow, int startCol, int endRow, int endCol)
  {
    //this room is touching the
    //north wall
    if(wall == 1 && startRow == 0) return true;
    //west wall
    if(wall == 2 && startCol == 0) return true;
    //south wall
    if(wall == 3 && endRow == numRows-1) return true;
    if(wall == 3 && endRow == numRows-2) return true;
    //east wall
    if(wall == 4 && endCol == numCols-1) return true;
    if(wall == 4 && endCol == numCols-2) return true;
    return false;
  }
  /**
   * This method is used to carve doorways into the
   * floorPlan. Once a room has doorways, we set all of the floor tiles
   * equal to an arbitrary number,3. Once all of the floor tiles equal three
   * we know every room has doorways carved into it
   */
  public void initializeDoorways()
  {
    while(!isFilled())
    {
      //we pick rooms by choosing random empty tiles and 
      //counting outwards to get the dimensions
      int row = rand.nextInt(numRows-2)+1;
      int col = rand.nextInt(numCols-2)+1;
      if(graph[row][col] != 0) continue;
      //count in each direction
      int eastCounter = 0;
      int westCounter = 0;
      int northCounter = 0;
      int southCounter = 0;
      while(graph[row+northCounter][col] == 0) northCounter--;
      while(graph[row+southCounter][col] == 0) southCounter++;
      while(graph[row][col+westCounter] == 0) westCounter--;
      while(graph[row][col+eastCounter] == 0) eastCounter++;
      //top left corner
      int startRow = row+northCounter;
      int startCol = col+westCounter;
      //bottom right corner
      int endRow = startRow+(Math.abs(northCounter)+southCounter);
      int endCol = startCol+(Math.abs(westCounter)+eastCounter);
      //pick a number of exits
      int numExits = rand.nextInt(3)+1;
      //we don't want to carve more than one exit
      //into a given wall, so we have to keep track
      //of which walls have exits
      ArrayList<Integer> wallsUsed = new ArrayList<>();
      while(numExits>0)
      {
        //pick random wall
        int wall = rand.nextInt(4)+1; 
        if(wallsUsed.contains(wall)) wall = rand.nextInt(4)+1;
        //make sure we don't catch a wall on the edge of the floor plan
        while(isBadWall(wall,startRow,startCol,endRow,endCol)) wall = rand.nextInt(4)+1;
        //doorways are two tiles wide
        //we can't use zeroes to represent doorways until later
        //so I used the number 6
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
      //we have exits, so fill the room so we don't randomly
      //choose it again
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
  /**
   * This method is used to initialize the exit on the border of the floorPlan
   * it also sets the tiles adjacent to he exit equal to FLOOR in case a wall was carved
   * right next to the border
   */
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
    graph[adjRow1][adjCol1] = ZombieConstants.FLOOR;
    graph[adjRow2][adjCol2] = ZombieConstants.FLOOR;
  }
  
  /**
   * This method is used to initialize the starting position of the player.
   * It needs to be an appropriate distance away from the exit
   */
  public void initializeStart()
  {
    boolean exit = false;
    double distance = 0;
    while(exit == false)  
    {
      playerStartRow = rand.nextInt(numRows-4)+2;
      playerStartCol = rand.nextInt(numRows-4)+2;
      while(graph[playerStartRow][playerStartCol] != ZombieConstants.FLOOR)
      {
        playerStartRow = rand.nextInt(numRows - 4) + 2;
        playerStartCol = rand.nextInt(numCols - 4) + 2;
      }
      //can't spawn in a hall
      if(!isInHallway(playerStartRow, playerStartCol)) continue;
      //make sure we're far away from the exit
      distance = 0;
      double rowDistance = (exitRow1 - playerStartRow);
      rowDistance = Math.pow(rowDistance, 2);
      double colDistance = (exitCol1 - playerStartCol);
      colDistance = Math.pow(colDistance, 2);
      distance = Math.sqrt(rowDistance + colDistance);
      int requiredDistance = 0;
      if(exitCol1 == 0 || exitCol1 == numCols-1) requiredDistance = 50;
      else requiredDistance = 33;
      if(distance>requiredDistance) exit = true;
    }
    graph[playerStartRow][playerStartCol] = ZombieConstants.PLAYER;
  }
  /**
   * This method is called by breakFullCols to see if a given column is 
   * completely carved into a wall.
   * @param col Column in question
   */
  public boolean isColFull(int col)
  {
    for(int i = 0;i<numRows-1;i++)
    {
      if(graph[i][col] != 4) return false;   
    }
    return true;
  }

  /**
   * This method is used to carve doorways into columns that
   * didn't end up being chosen to have exits carved into them
   * by any of their adjacent rooms/halls.
   * 
   */
  public void breakFullCols()
  {
    for(int i = 1;i<numCols-1;i++)
    {
      if(isColFull(i))
      {
        //create a random doorway
        int randRow = rand.nextInt(numRows-2);
        int randRow2 = randRow+1;
        graph[randRow][i] = 0;
        graph[randRow2][i] = 0;
      }
    }
  }
  /**
   * This method is used to randomly place obstacles across the floorPlan
   */
  public void initializeObstacles()
  {
    while(numObstacles>0)
    {
      int row = rand.nextInt(numRows-8)+1;
      int col = rand.nextInt(numCols-2)+1;
      //fnd an empty three tile segment
      if(graph[row][col] != 0) continue;
      if(graph[row-1][col] != 0 || graph[row-2][col] != 0) continue;
      //give it an appropriate length
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
          graph[i][col] = ZombieConstants.OBSTACLE;
        }
      }
      else continue;  
      numObstacles--;
    }
  }
  /**
   * This method is called to randomly place firetraps across the floor
   * plan. They are never placed in hallways
   */
  public void initializeFireTraps()
  {
    while(numFireTraps>0)
    {
      int row = rand.nextInt(numRows-4)+1;
      int col = rand.nextInt(numCols-2)+1;
      if(graph[row][col] != 0) continue;
      if(isInHallway(row,col)) continue;
      //fire traps always have a height of three tiles
      graph[row][col] = ZombieConstants.FIRE_TRAP;
      graph[row+1][col] = ZombieConstants.FIRE_TRAP;
      graph[row+2][col] = ZombieConstants.FIRE_TRAP;
      numFireTraps--;
    }
  }
  /**
  * This method is called to carve the walls into the floor plan
  * and call the utility functions that clean up some situations
  * that are prohibited by the rules.
  */
  public void carveMap()
  {
    //candidates to carve walls from
    ArrayList<Integer> rowCandidates = new ArrayList<>();
    ArrayList<Integer> colCandidates = new ArrayList<>();
    //candidates will always have a minimum of three tiles between them
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
    //we will pick randomly from the candidates
    Collections.shuffle(rowCandidates);
    Collections.shuffle(colCandidates);
    //random col breaks are cols where we will carve walls from
    int randomColBreaks = rand.nextInt(3)+8;
    while(randomColBreaks>0)
    {
      //col to carve from
      int breakPoint = rand.nextInt(colCandidates.size());
      while(breakPoint==0) breakPoint = rand.nextInt(colCandidates.size());
      //carve from top or bottom of floorplan
      int topOrBottom = rand.nextInt(2);
      if(topOrBottom == 1) carveColFromTop(colCandidates.get(breakPoint));
      else carveColFromBottom(colCandidates.get(breakPoint));
      //remove the candidate
      colCandidates.remove(breakPoint);
      randomColBreaks--;
    }
    
    
    int randomRowBreaks = rand.nextInt(2)+8;
    while(randomRowBreaks>0)
    {
      //row to carve from
      int breakPoint = rand.nextInt(rowCandidates.size());
      while(breakPoint==0) breakPoint = rand.nextInt(rowCandidates.size());
      //carve from left or right
      int leftOrRight = rand.nextInt(2);
      if(leftOrRight == 1) carveRowFromLeft(rowCandidates.get(breakPoint));
      else carveRowFromRight(rowCandidates.get(breakPoint));
      //remove candidate
      rowCandidates.remove(breakPoint);
      randomRowBreaks--;
    }
    //call utility functions 
    clearBoxedAreas(); 
    clearVerticalParallelHalls();
    clearHorizontalParallelHalls();
    bisectLargerVerticalAreas();
    initializeDoorways();
    //fix some changes that initializeDoorways() made
    for(int i = 0;i<numRows;i++)
    {
      for(int j = 0;j<numCols;j++)
      {
        if(graph[i][j] == 3 ||
           graph[i][j] == 6 ||
           graph[i][j] == 5) graph[i][j] = ZombieConstants.FLOOR;
      }
    }
    for(int i = 0;i<numRows;i++)
    {
      graph[i][0] = ZombieConstants.WALL;
      graph[i][numCols-1] = ZombieConstants.WALL;
    }
    for(int i = 0;i<numCols;i++)
    {
      graph[0][i] = ZombieConstants.WALL;
      graph[numRows-1][i] = ZombieConstants.WALL;
    }
    //pick a starting point and an exit
    initializeExit();
    initializeStart();
    //utility function call
    breakFullCols();
    //intialize obstacles and traps
    initializeObstacles();
    initializeFireTraps();
  }
}
