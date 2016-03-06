package resources.sound;
import java.net.URI;
import java.net.URL;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;


public class ZombieSoundWorker extends Thread
{
  
  final AudioClip sound;
  private boolean terminate;
  private double volume;
  private double balance;
  private double rate; 
  private double pan;
  public void setTerminate(boolean b){this.terminate = b;}
  
  private boolean play;
  public void setPlay(boolean b){this.play = b;}
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
        Thread.sleep(500);
      } 
      catch (InterruptedException e){}
      if(play)
      {
        
      }
    
    }
    
    
    
    
  }
  

  public void playSound() 
  {
    sound.play();
    while(sound.isPlaying()){}
  }
  
  public void playSound(double volume, double balance, double rate, 
                        double pan, int priority)
  {
    sound.play(volume, balance, rate, rate, priority);
    while(sound.isPlaying()){}
  }
  
  
  public static void main(String[] args) 
  {
    ZombieSoundWorker s = new ZombieSoundWorker("FootStep.wav");
    ZombieSoundWorker t = new ZombieSoundWorker("Growl_01.wav");
    ZombieSoundWorker u = new ZombieSoundWorker("Growl_02.wav");
    ZombieSoundWorker v = new ZombieSoundWorker("Growl_03.wav");
    ZombieSoundWorker w = new ZombieSoundWorker("Growl_04.wav");
  }


}
