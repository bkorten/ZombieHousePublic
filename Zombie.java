//James Perry
//2-18-2016
import java.util.ArrayList;
import java.util.Random;



public class Zombie 
{
  private int graphicsID;  
  public int getGraphicsID(){return this.graphicsID;}
  public void setGraphicsID(int x){this.graphicsID = x;}

  private int currentRow;
  private int currentCol;
  public int getCurrentRow(){return this.currentRow;}
  public int getCurrentCol(){return this.currentCol;}
  public void setCurrentRow(int x){this.currentRow = x;}
  public void setCurrentCol(int x){this.currentCol = x;}
  
  private int headingRow;
  public int getHeadingRow(){return this.headingRow;}
  public void setHeadingRow(int x){this.headingRow = x;}  
  private int headingCol;
  public int getHeadingCol(){return this.headingCol;}
  public void setHeadingCol(int x){this.headingCol = x;}
  private static double zombieSmell;
  public void setZombieSmell(double x){this.zombieSmell = x;}
  public double getZombieSmell(){return this.zombieSmell;}
  
  private boolean collided = false;
  public void setCollided(boolean b){this.collided = b;}
  public boolean getCollided(){return this.collided;}
  
  private Random rand;
  
  public Zombie(int startRow,int startCol,int graphicsID)
  {
    this.currentRow = startRow;
    this.currentCol = startCol;
    this.graphicsID = graphicsID;
    rand = new Random();
    int heading = rand.nextInt(4);
    switch(heading)
    {
      case 0:
      headingRow = 0;
      headingCol = 1;
      break;
      case 1:
      headingRow = 1;
      headingCol = 0;
      break;
      case 2:
      headingRow = -1;
      headingCol = 0;
      break;
      case 3:
      headingRow = 0;
      headingCol = -1;
      break;
    
    }
    
  }
 
  
  public void randomizeHeading()
  {
    int heading = rand.nextInt(4);
    int headingRow = 0;
    int headingCol = 0;
    switch(heading)
    {
      case 0:
      headingRow = 0;
      headingCol = 1;
      break;
      case 1:
      headingRow = 1;
      headingCol = 0;
      break;
      case 2:
      headingRow = -1;
      headingCol = 0;
      break;
      case 3:
      headingRow = 0;
      headingCol = -1;
      break;
    }
    this.headingRow = headingRow;
    this.headingCol = headingCol;
  }
  
  
}
