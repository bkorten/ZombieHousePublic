

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
  public void setType(int x){this.type = x;}
  
  private double f_value;
  public double getFValue(){return this.f_value;}
  public void setFValue(double x){this.f_value = x;}
  
  private double g_value;
  public double getGValue(){return this.g_value;}
  public void setGValue(double x){this.g_value = x;}
  
  private double h_value;
  public double getHValue(){return this.h_value;}
  public void setHValue(double x){this.h_value = x;}
  
  private int pathMarker = 3;
  public void setPathMarker(int x){this.pathMarker = x;}
  public int getPathMarker(){return this.pathMarker;}

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
    if(this.getFValue() > o.getFValue()) return 1;
    else if(this.getFValue() < o.getFValue()) return -1;
    else if(this.getFValue() == o.getFValue()) return 0;
    return 0;
  }
  
}
