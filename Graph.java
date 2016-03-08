/*
*James Perry
*CS 351: Project: Zombie House 
*This class is used to represent the 2D floorPlan
*as an array of objects to make A* pathfinding easier
*to calculate. GameState instantiates this class once in its
*own constructor, and everytime a change is made to the 2D floorPlan
*the change is also reflected in the Graph object. This class also contains
*the A* pathfinding algorithm, and maintains a global heading which is copied
*into a given zombie's heading whenever pathFinding() is called for that zombie.
*/

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
public class Graph 
{
  private Tile[][] graph;
  
  //[row]
  //[col]
  private int[] heading = new int[2];
  public int getHeadingRow(){return this.heading[0];}
  public int getHeadingCol(){return this.heading[1];}
  
  /*
   * This method is used to update the position of anything on the graph that has changed,
   * so a zombie or the player. value tells us who changed their position.
   * @param prevRow The row from which a position was changed on the floorPlan
   * @param prevCol The col from which a position was changed on the floorPlan
   * @param newRow The row which is being traveled to.
   * @param newCol The col which is being traveled to.
   * @param value The type of game object that is changing position.
  */
  public void updatePosition(int prevRow, int prevCol, int newRow, int newCol, int value)
  {
    graph[prevRow][prevCol].setType(0);
    graph[newRow][newCol].setType(value);
  }
  /*
   * This method is used by pathFinding() in order to get the neighboring
   * cells for a particular cell. We ignore cells in the border.
   * @param row The row of the cell who's neighbors we want to look at
   * @param col The col of the cell who's neighbors we want to look at
  */
  public ArrayList<Tile> getNeighbors(int row, int col)
  {
    ArrayList<Tile> tiles = new ArrayList<>();
    if(row>1){tiles.add(graph[row-1][col]);}
    if(col>1){tiles.add(graph[row][col-1]);}
    if(row<ZombieConstants.NUM_ROWS-1){tiles.add(graph[row+1][col]);}
    if(col<ZombieConstants.NUM_COLS-1){tiles.add(graph[row][col+1]);}
    return tiles;
  }
  /*
   * This method is called by pathFinding as well as the make*ZombieDecision functions
   * in GameState. It returns a double representing the Euclidean Distance between two 
   * points on the graph.
   * @param row1 The row of point one
   * @param col1 The col of point one
   * @param row2 The row of point two
   * @param col2 The col of point two
   */
  public double euclideanDistance(int row1, int col1, int row2, int col2)
  {
    double rowDistance = row2-row1;
    double colDistance = col2-col1;
    rowDistance = Math.pow(rowDistance, 2);
    colDistance = Math.pow(colDistance, 2);
    double distance = rowDistance+colDistance;
    return Math.sqrt(distance);
  }
  /*
   * This method is called whenever pathFinding finds a path between two points
   * on the graph. reconstructPath() starts by traversing the graph from the
   * goal tile of the path, until it gets back to the tile before the starting tile.
   * When it arrives there, it finds the distance between itself and the starting tile
   * and loads that distance into the global heading value for GameState to use.
   * @param startRow Starting row on the path
   * @param startCol Starting col on the path
   * @param goalRow Ending row n the path
   * @param goalCol Ending col on the path
  */
  public void reconstructPath(int startRow, int startCol, int goalRow, int goalCol)
  {
    Tile tmp = graph[goalRow][goalCol];
    Tile start = graph[startRow][startCol];
    while(!(tmp.equalsTile(start)))
    {
      if(tmp.getPrevious().equalsTile(start)) break;   
      tmp = tmp.getPrevious();
      tmp.setPathMarker(3);
    };
    heading[0] = tmp.getRow()-start.getRow();
    heading[1] = tmp.getCol()-start.getCol();    
  }
  /*
   * This method is called by GameState with a position of a zombie as the startRow
   * and startCol, and the position of the player as the goalRow and the goalCol.
   * It uses A* pathfinding, and calls reconstructPath() whenever it finds a path to 
   * the player.
  */
  public void pathFinding(int startRow,int startCol,int goalRow, int goalCol)
  {
    //housekeeping
    for(int i = 1;i<ZombieConstants.NUM_ROWS-1;i++)
    {
      for(int j = 1;j<ZombieConstants.NUM_COLS-1;j++)
      {
        graph[i][j].setPrevious(null);
        graph[i][j].setGValue(0);
        graph[i][j].setFValue(0);
        graph[i][j].setHValue(0);
        graph[i][j].setPathMarker(0);
      }
    }
    PriorityQueue<Tile> frontier = new PriorityQueue<>();
    ArrayList<Tile> closedList = new ArrayList<>();
    Tile start = graph[startRow][startCol];
    Tile current;
    start.setPrevious(null);
    frontier.add(start);
    double newGScore, newHScore;
    Tile adj;
    while(!frontier.isEmpty())
    {
      current = frontier.poll();
      closedList.add(current);
      //are we at the goal?
      if(current.equalsTile(graph[goalRow][goalCol])) 
      {
        graph[goalRow][goalCol].setPrevious(current);
        graph[startRow][startCol].setPrevious(null);
        reconstructPath(startRow,startCol,goalRow,goalCol);
        return;
      }   
      ArrayList<Tile> neighbors = getNeighbors(current.getRow(),current.getCol());
      //look at neighbors
      for(int i = 0;i<neighbors.size();i++)
      {
        adj = neighbors.get(i);
        if(adj.getType() == ZombieConstants.WALL ||
           adj.getType() == ZombieConstants.LINE_ZOMBIE) continue;
        newGScore = current.getGValue()+adj.getType()+1;
        if(adj.equalsTile(graph[goalRow][goalCol]))
        {
          adj.setPrevious(current);
          graph[startRow][startCol].setPrevious(null);
          reconstructPath(startRow,startCol,goalRow,goalCol);
          return;
        }
        //already looked at this tile
        if(closedList.contains(adj) && newGScore > adj.getGValue()) continue;
        //this is worth adding to the frontier
        else if(!(frontier.contains(adj)) || newGScore<adj.getGValue())
        {       
          newHScore = euclideanDistance(adj.getRow(),adj.getCol(),goalRow,goalCol);
          adj.setFValue(newGScore+newHScore);
          adj.setGValue(newGScore);
          adj.setHValue(newHScore);
          adj.setPrevious(current);
          if(!(frontier.contains(adj))) frontier.add(adj);
        }
        //see if this gives us a better heuristic score, if so
        //make it part of the path
        else if(frontier.contains(adj))
        {
          newGScore = current.getGValue()+adj.getType()+1;
          newHScore = euclideanDistance(adj.getRow(),adj.getCol(),goalRow,goalCol);
          if((newGScore+newHScore)<adj.getFValue())
          {
            adj.setFValue(newGScore+newHScore);
            adj.setGValue(newGScore);
            adj.setPrevious(current);
          }
        }  
      }    
    }
  }
  /*
   * Constructor for a graph object needs a completely finished floorPlan from gameState
   * in order to work properly. Using a floorPlan from LevelGenerator wouldn't take into account
   * the zombies on the graph.
   * @param int[][] floorPlan The floorPlan created by LevelGenerator, modified by GameState 
   */
  public Graph(int[][] floorPlan)
  {
    graph = new Tile[ZombieConstants.NUM_ROWS][ZombieConstants.NUM_COLS];
    for(int i = 0;i<ZombieConstants.NUM_ROWS;i++)
    {
      for(int j = 0;j<ZombieConstants.NUM_COLS;j++)
      {
        //tile takes a row, col and value in its constructor
        graph[i][j] = new Tile(i,j,floorPlan[i][j]);
      }
    }
  } 
}
