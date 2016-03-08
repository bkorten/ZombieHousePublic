/*
 * CS 351: Project-Zombie House
 * James Perry
 * This class is used to move all of the
 * different zombies every 1/zombieSpeed seconds.
*/
public class ZombieMoveWorker extends Thread
{
  private boolean isGameRunning = false;
  public void setRunning(boolean b){isGameRunning = b;}

  private boolean terminate = false;
  public void setTerminate(boolean b){this.terminate = b;}

  private double zombieSpeed;
  public void setZombieSpeed(double x){this.zombieSpeed = x;}

  private double waitTime;
  private long waitTimeToLong;

  private GameState game;
  /*
   * @param GameState game The game from which we will grab the zombieSpeed 
   *                       variable and call move methods.
   */
  public ZombieMoveWorker(GameState game)
  {
    this.game = game;
    this.zombieSpeed = game.getZombieSpeed();
  }

  @Override
  public void run()
  {
    while(!terminate)
    {
      while(isGameRunning)
      {
        game.moveLineZombies();
        game.moveRandomZombies();
        game.moveMasterZombie();
        try
        {
          waitTime = (1/zombieSpeed);
          waitTime*= 1000;
          waitTimeToLong = (long) waitTime;
          sleep(waitTimeToLong);
        }
        catch(InterruptedException e)
        {}
      }
      //while game isn't running, wait
      try
      {
        sleep(500);
      } 
      catch(InterruptedException e)
      {}
    }
  }
}
