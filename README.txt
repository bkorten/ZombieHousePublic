# ZombieHouse
ZombieHouse is the first group project for CS 351. This repository is the implementation by the "noobs" group, James Perry and Burton Korten. Thien dropped the class, so all of his points are to be distributed to James and Burt, preferibly with a hefty amount of extra credit for the added emotional and physical trauma.

James was in charge of everything related to the internal computations, and Burton was responsible for the graphic and control components of this game. 

CLASS DESCRIPTIONS:

LevelGenerator.java
-This class is used to create a 2D floorplan of the house for a given level. It takes care of all of the logic pertaining to how the map should be set up, generates hallways,rooms, the exit along the wall, and calculates a player start point that an appropriate distance away from the exit. LevelGenerator does not contain any logic pertaining to spawning zombies or anything for moving a zombie or the player. LevelGenerator is instantiated by every GameState object that gets created by ZombieHouseGameMain.java

GameState.java 
-This class is used for maintaining most of the internal game state, including the positions of the zombies and the player, and maintaining the headings of all of the zombies. It creates a levelGenerator object in its constructor, grabs all of the necessary data from the level generator, and isn't called until the main game loop wants the zombies to make decisions and move their positions. Gamestate also instantiates a Graph object, which is used for its pathfinding capabilites for calculating the zombie headings.
Whenever a zombie or player changes his/her position, the 2D floorPlan and Graph object are both updated to reflect the changes.

Zombie.Java 
-This class is used to hold the state of a zombie when it is instantiated by GameState, i.e. currentRow, currentCol, currentHeadingRow, currentHeadingCol, etc. The move functionality is NOT contained in this class due to the fact that the Zombie class does not contain any references to the graph object or 2D floorplan. GameState takes care of moving the zombie and updating its heading according to the rules set forth by Joel in the lecture slides. The zombie class is able to pick a random heading. The classes that extend this class are MasterZombie.java, LineZombie.java, and RandomZombie.java

ZombieConstants.java
-This class contains static constants used by any class that deals with the graph or floorPlan objects.

GraphicsComponent.java(Burton Korten)
-this class extends Box.  this is the parent class for Floor.java, Wall.java, and FireTrap.  This class has a function that takes the grid position of and object and translates it to 3D space. 

Floor.java(Burton Korten)
-this class extends GraphicsComponent.  This is used for the floor and the ceiling of a level. 

LevelGraphics.java (Burton Korten)
-This class creates a group that contains all the static graphics for a level.  this includes walls obstacles firetraps and the exit

Controller.java
-This class is the controller for the player.  It processes input from the mouse and keyboard and passes it to the Player class. 

LevelGraphics.java
-This class creates the 3D level by parsing the 2d map made by the level generator class. 
Player.java
-This class contains the camera and and light and interfaces with the Controller class. 

StlLoader.java
-This class loads and renders the 3D models for the zombies using predefined methods. 



 
