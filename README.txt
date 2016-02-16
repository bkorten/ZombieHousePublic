# ZombieHouse
James Perry on 02/12/2016:

Procedural Level generation is almost done
-Expect a 2D array of ints for the floor plan
-Might make a constants class, not sure if it will be really necessary 
 
-A big thing one of you guys can start on is setting up the 3D environment with the camera and gathering textures.
-I'm gonna tackle the audio stuff after I'm done with the level generation, and then
 I'm going to create a GameState class that will get updated every time something happens.

Preliminary Plan (edit as you guys see fit):

Audio:
-I (James) make custom audio files that deal with varying degrees of stereo effects. 
-Plan on finding the angle between a player's forward direction and a zombie position, and triggering the sound file for that range of angle.(Do this in the player class)
-Player footsteps/running will be the same sound file no matter what, only the zombie sounds will need to trigger specific files.

Level Generation
-I'll take care of all of this.
-Different map for each level (map topography won't play into difficulty, we'll use constraints on attributes and
                               increase the number of zombies to do that)
-Like I said, 2D array of ints for 2D map.
-I take care of player start position and exit. They'll be stored in LevelGenerator.java if you need to access them for your classes.

Zombie and Player class:
-Position
-Zombie class needs a euclidean distance and actual distance function
-Zombie class needs a makeDecision() method, with pathfinding to the player.
-smell() function.

GUI
-NO BUTTONS,PANELS,SLIDERS,LISTVIEWS,NONE OF THAT 
-We do a canvas bound to the primary stage (maybe a layout if need be) with attribute controls mapped to keyboard inputs,
 and attribute values displayed overlaying the bottom of the screen (I'm sure you've seen some FPS that has done this).
 
 For instance, "p" increments player sight
               "o" to decraments player sight
               Press Spacebar to play
 
GUI Threadworker class keeps track of keyboard input and update the attributes accordingly.
-Use a switch instead of a bunch of if statements (its faster).
-keyCodes are useful.
-GUI main thread just has to render based on a constantly updating GameState object

Start menu comes up first, and we'll have an outro to finish out the last level.

For everything:
-Longer variable names are my preference
-We need the gamestate to be able to update quickly if the GUI is going to be able to run at 60fps.
-We're using ints/int arrays because they're fast, low level writes.
-So before you push anything new, test it to make sure it's fast enough to not drag our FPS down.

Int operations are fast, and the 2D array shouldn't be bigger than 25*30, so it won't take a lot of time to look through.

If you can think of a simpler/faster way to do anything, feel free to try it on your own, see if its a noticible improvement, then suggest it. The first sprint is all about getting a full credit version written/tested. We get this done, save the full-credit working version in a zip file somewhere, then we go hard for extra credit if there's time.

Sound like a plan?
-James










 
