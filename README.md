# AI_Maze_Game(Team 16)
Java Project Using Object Oriented Coding

How to run: For the final version of the project, compile all packages and run the class 'Display' found in the 'view' package, to run text based run the file 'Runner' 
-- To run previous demo's, compile the code in that demo's folder then run Display for GUI and Runner for text based.

Updates on the final version of the project
  - Removed all bugs from random Maze generator(so there is always a path from player to goal)
  - constructed packages 
  -  Clean up each class/junit one-by-one (
      i) get rid of code repetition,
      ii) fix variable names/unused variables,
      iii) consistent spacing/if else design**, 
      iv) remove magic numbers, 
      v) each method performs a single task );
      vi) comment everything using java doc*; 
   - images are declared as final variables 
   - Throw Exception error that do not allow user to enter invalid data and gives user easy to understand/fix error messages
   - Window pop-up at the end of the game(TA recommended) that says ‘play again’ or ‘close
   - Seperated GUI and Logic classes into view and logic packages
   - Can save the current state of the game if game is left unfinished and able to load previously saved data 
   - Exceptions are cascaded on the files appropriately
   - Atleast one class runs all the junit methods
   - Make instance variables private, encapsulate and create accessors/mutators 
   - Implement OO design with polymorhphism and abstract class 

 Updates for demo 3
  - added comments and organized
  - changed to a new map generation method
  - reorganized to create some child and parent classes (MapNode & PathNode)
  - added in graphics
  - Fully functioning GUI version.
  - An OO design that uses inheritance.  It is required that you extend one of your own classes, not just Java library classes.  If it is not obvious how to do this, ask your instructor or TA for suggestions.
  - few Privacy leaks removed 
  -  added JUnit tests - for logic classes - passed all tests

 Updates for demo 2:
- added comments and organized
- changed to a new map generation method
- reorganized to create some child and parent classes (MapNode & PathNode)
- added in graphics
- Fully functioning text based version.  (There may be some functionality that can't be implemented text based for some projects.  Discuss with your TA if you think this is the case for your project.)


