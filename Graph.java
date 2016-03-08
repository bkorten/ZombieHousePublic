import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Graph 
{
  private Tile[][] graph;
  private int[] heading = new int[2];
  public int getHeadingRow(){return this.heading[0];}
  public int getHeadingCol(){return this.heading[1];}
  
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
    double distance = rowDistance+colDistance;
    return Math.sqrt(distance);
  }
  
  public void printGraph()
  {
    System.out.println();
    for(int i = 0;i<ZombieConstants.NUM_ROWS;i++)
    {
      for(int j = 0;j<ZombieConstants.NUM_COLS;j++)
      {
        if(graph[i][j].getPathMarker() == 3) System.out.print("3"+" ");
        else System.out.print(graph[i][j].getType()+" ");
      }
      System.out.println();
    }
  }
  
  public void reconstructPath(int startRow, int startCol, int goalRow, int goalCol)
  {
    Tile tmp = graph[goalRow][goalCol];
    Tile start = graph[startRow][startCol];
    while(!(tmp.equalsTile(start)))
    {
      if(tmp.getPrevious().equalsTile(start)) break;
      tmp = tmp.getPrevious();
    };
    heading[0] =Math.abs(start.getRow()-tmp.getRow());
    heading[1] = Math.abs(start.getCol()-tmp.getCol());    
  }
  
  public void pathFinding(int startRow,int startCol,int goalRow, int goalCol)
  {
    for(int i = 0;i<ZombieConstants.NUM_ROWS;i++)
    {
      for(int j = 0;j<ZombieConstants.NUM_COLS;j++)
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
      Iterator<Tile> it = frontier.iterator();
      Tile tmp;
      closedList.add(current);
      if(current.equalsTile(graph[goalRow][goalCol])) 
      {
        graph[goalRow][goalCol].setPrevious(current);
        graph[startRow][startCol].setPrevious(null);
        reconstructPath(startRow,startCol,goalRow,goalCol);
        return;
      }
      
      ArrayList<Tile> neighbors = getNeighbors(current.getRow(),current.getCol());
      
      for(int i = 0;i<neighbors.size();i++)
      {
        adj = neighbors.get(i);
        if(adj.getType() == ZombieConstants.WALL ||
           adj.getType() == ZombieConstants.LINE_ZOMBIE) continue;
        newGScore = current.getGValue()+adj.getType()+1;
        if(adj.equalsTile(graph[goalRow][goalCol]))
        {
          adj.setPrevious(current);
          reconstructPath(startRow,startCol,goalRow,goalCol);
          return;
        }
        
        if(closedList.contains(adj) && newGScore > adj.getGValue()) continue;
        
        else if(!(frontier.contains(adj)) || newGScore<adj.getGValue())
        {       
          newHScore = euclideanDistance(adj.getRow(),adj.getCol(),goalRow,goalCol);
          adj.setFValue(newGScore+newHScore);
          adj.setGValue(newGScore);
          adj.setHValue(newHScore);
          adj.setPrevious(current);
          if(!(frontier.contains(adj))) frontier.add(adj);
        }
        
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
  
  public Graph(int[][] floorPlan)
  {
    graph = new Tile[ZombieConstants.NUM_ROWS][ZombieConstants.NUM_COLS];
    for(int i = 0;i<ZombieConstants.NUM_ROWS;i++)
    {
      for(int j = 0;j<ZombieConstants.NUM_COLS;j++)
      {
        System.out.println(floorPlan[i][j]);
        graph[i][j] = new Tile(i,j,floorPlan[i][j]);
      }
    }
  }
  
  public static void main(String[] args)
  {
    GameState g = new GameState(1);
   
    Graph h = new Graph(g.getFloorPlan());
    h.printGraph();
    
  }
  
  
  
}
