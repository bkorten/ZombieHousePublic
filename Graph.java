import java.util.ArrayList;
import java.util.PriorityQueue;

public class Graph 
{
  private Tile[][] graph;
  
  public void updatePosition(int prevRow, int prevCol, int newRow, int newCol, int value)
  {
    graph[prevRow][prevCol].setType(0);
    graph[newRow][newCol].setType(value);
   
  }
  
  public ArrayList<Tile> getNeighbors(int row, int col)
  {
    ArrayList<Tile> tiles = new ArrayList<>();
    if(row>1){tiles.add(graph[row-1][col]);}
    if(col>1){tiles.add(graph[row][col-1]);}
    if(row<ZombieConstants.NUM_ROWS-1){tiles.add(graph[row+1][col]);}
    if(col<ZombieConstants.NUM_COLS-1){tiles.add(graph[row][col+1]);}
    return tiles;
  }
  
  public double euclideanDistance(int row1, int col1, int row2, int col2)
  {
    double rowDistance = row2-row1;
    double colDistance = col2-col1;
    rowDistance = Math.pow(rowDistance, 2);
    colDistance = Math.pow(colDistance, 2);
    return Math.sqrt((rowDistance+colDistance));
  }
  
  
  
  public void pathFinding(int startRow,int startCol,int goalRow, int goalCol)
  {
    for(int i = 0;i<ZombieConstants.NUM_ROWS;i++)
    {
      for(int j = 0;j<ZombieConstants.NUM_COLS;j++)
      {
        graph[i][j].setPrevious(null);
        graph[i][j].setGValue(0);
        graph[i][j].setHValue(0);
      }
    }
    
    PriorityQueue<Tile> frontier = new PriorityQueue<>();
    ArrayList<Tile> closedList = new ArrayList<>();
    Tile start = graph[startRow][startCol];
    Tile current;
    start.setPrevious(start);
    frontier.add(start);
    int newCost;
    
    
    while(!frontier.isEmpty())
    {
      current = frontier.poll();
      
      closedList.add(current);
      if(current.equalsTile(graph[goalRow][goalCol])) return;
      
      ArrayList<Tile> neighbors = getNeighbors(current.getRow(),current.getCol());
      
      for(int i = 0;i<neighbors.size();i++)
      {
        
        if(closedList.contains(neighbors.get(i))) continue;
        
        else if(!(frontier.contains(neighbors.get(i))))
        { 
         Tile tmp = neighbors.get(i);
         frontier.add(neighbors.get(i));
         newCost = current.getGValue()+neighbors.get(i).getType()+1;
        }
        
        else if(frontier.contains(neighbors.get(i)))
        {
          
        }  
        
      }  
      
    }
    
  }
  
  public Graph(int[][] floorPlan)
  {
    graph = new Tile[ZombieConstants.NUM_ROWS][ZombieConstants.NUM_COLS];
    for(int i = 0;i<ZombieConstants.NUM_ROWS;i++)
    {
      for(int j = 0;j<ZombieConstants.NUM_COLS;j++)
      {
        graph[i][j] = new Tile(i,j,floorPlan[i][j]);
      }
    }
    
    pathFinding(1,1,2,2);
    
  }
}
