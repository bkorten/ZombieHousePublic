package resources.sound;
import java.net.URI;
import java.net.URL;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;


public class ZombieSoundManager 
{
  
  final AudioClip walkingSound;
  AudioClip runningSound;
  private boolean walking = true;
  public void setWalking(boolean b){this.walking = b;}
  
  public ZombieSoundManager(String s)
  {
    final URL path = this.getClass().getResource(s);
    walkingSound = new AudioClip(path.toString());    
  }
  

  public void playSound()
  {
    walkingSound.play();
    while(walkingSound.isPlaying())
    {
      if(walking == false) break;
    }
  }
  public static void main(String[] args) 
  {
    ZombieSoundManager s = new ZombieSoundManager("walking.wav");
    s.playSound();
  }


}
