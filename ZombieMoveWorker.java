
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
      game.moveZombies();
      try
      {
        waitTime = 1000*(1/zombieSpeed);
        waitTimeToLong = (long) waitTime;
        sleep(waitTimeToLong);
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
