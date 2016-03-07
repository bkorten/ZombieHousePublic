
public class ZombieDecisionWorker extends Thread
{
  private boolean isGameRunning = false;
  public void setRunning(boolean b){isGameRunning = b;}
  
  private boolean terminate = false;
  public void setTerminate(boolean b){this.terminate = b;}
  
  private double zombieDecisionRate;
  public void setZombieDecisionRate(double x){this.zombieDecisionRate = x;}
  private GameState game;
  public ZombieDecisionWorker(GameState game)
  {
    this.game = game;
    this.zombieDecisionRate = game.getZombieDecisionRate();
  }
  
  @Override
  public void run()
  {
    while(!terminate)
    {
      while(isGameRunning)
      {
        game.makeZombieDecisions();
        try
        {
          sleep((long) zombieDecisionRate);
        }
        catch(InterruptedException e){}
      }
      
      try
      {
        sleep(500);
      } 
      catch(InterruptedException e)
      {}
    }
  }

}
