//James Perry
//2-18-2016
import java.util.ArrayList;
import java.util.Random;



public class Zombie 
{
  private int currentRow;
  private int currentCol;
  private int headingRow;
  private int headingCol;
  
  public void move()
  {
    
  }
  
  public Zombie(int startRow,int startCol)
  {
    this.currentRow = startRow;
    this.currentCol = startCol;
    Random rand = new Random();
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
  
  public void pathFinding(int startRow,int startCol,int goalRow, int goalCol)
  {
   
  }
  
  
}
