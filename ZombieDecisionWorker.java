/*
 * CS 351: Project-Zombie House
 * James Perry
 * This class is used to make decisions for all of the
 * different zombies every zombieDecisionRate seconds.
 */
public class ZombieDecisionWorker extends Thread
{
  private boolean isGameRunning = false;
  public void setRunning(boolean b){isGameRunning = b;}
  
  //should this thread terminate itself
  private boolean terminate = false;
  public void setTerminate(boolean b){this.terminate = b;}
  
  private double zombieDecisionRate;
  public void setZombieDecisionRate(double x){this.zombieDecisionRate = x;}
  
  private GameState game;
  /*
  *@param GameState game The GameState object from which we will grab the decision rate.
  *                      and call the decision making methods.
  */
  public ZombieDecisionWorker(GameState game)
  {
    this.game = game;
    this.zombieDecisionRate = game.getZombieDecisionRate()*1000;
  }
  
  @Override
  public void run()
  {
    while(!terminate)
    {
      while(isGameRunning)
      {
        game.makeRandomZombieDecisions();
        game.makeLineZombieDecisions();
        game.makeMasterZombieDecision();
        try
        {
          sleep((long) zombieDecisionRate);
        }
        catch(InterruptedException e)
        {}
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
