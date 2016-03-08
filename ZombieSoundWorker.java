/**
 * This class is used to hold a single sound file and play it 
 * whenever GameState sets the play boolean equal to true.
 */
import java.net.URL;
import javafx.scene.media.AudioClip;
public class ZombieSoundWorker extends Thread
{
  //holds the sound
  final AudioClip sound;
  //terminate the thread
  private boolean terminate;
  public void setTerminate(boolean b){this.terminate = b;}
  
  private double volume;
  public void setVolume(double x){this.volume = x;}
  
  private double balance;
  public void setBalance(double x){this.balance = x;}
  
  private double rate; 
  public void setRate(double x){this.rate = x;}
  
  private double pan;
  public void setPan(double x){this.pan = x;}
  
  
  private boolean play;
  public void setPlay(boolean b){this.play = b;}
  
  /**
   * 
   * @param s URL to the sound file
   */
  public ZombieSoundWorker(String s)
  {
    final URL path = this.getClass().getResource(s);
    sound = new AudioClip(path.toString()); 
    terminate = false;
    play = false;
  }
  
  @Override
  public void run()
  {
    while(!terminate)
    {
      try 
      {
        Thread.sleep(100);
      } 
      catch (InterruptedException e){}
      if(play)
      {
        playSound(volume,balance,rate,pan,1);
        play = false;
      }  
    }           
  }
  
  /**
   * Plays the sound
   * @param volume Volume of the sound from 0.0 to 1.0
   * @param balance Balance of the sound from -1.0 to 1.0
   * @param rate How fast to play the sound, 1.0 is default
   * @param pan "Center" of sound from -1.0 to 1.0 
   */
  public void playSound(double volume, double balance, double rate, 
                        double pan, int priority)
  {
    System.out.println(balance);
    sound.play(volume, balance, rate, rate, 1);
    while(sound.isPlaying())
    {}
  }
}
