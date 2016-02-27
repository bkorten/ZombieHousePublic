
public class Tile implements Comparable<Tile>
{
  private int row;
  public int getRow(){return this.row;}
  private int col;
  public int getCol(){return this.col;}
  
  private Tile previous;
  public Tile getPrevious(){return this.previous;}
  public void setPrevious(Tile x){this.previous = x;}
  private int type;
  public int getType(){return this.type;}
  //movement cost fron startTile to this
  private int g_value;
  public int getGValue(){return this.g_value;}
  public void setGValue(int x){this.g_value = x;}
  //estimated cost from this to goal
  private int h_value;
  public int getHValue(){return this.h_value;}
  public void setHValue(int x){this.h_value = x;}
  

  public boolean equalsTile(Tile other)
  {
    if(this.col != other.getCol()) return false;
    else if(this.row != other.getRow()) return false;
    return true;
  }
  
  
  public Tile(int row, int col, int type)
  {
    this.row = row;
    this.col = col;
    this.type = type;
  }
  @Override
  public int compareTo(Tile o) 
  {
    if(this.col > o.getCol()) return 1;
    else if(this.row > o.getRow()) return 2;
    else if(this.row < o.getRow()) return -1;
    return 0;
  }
  
}
