# Knots and Crosses AI
In this Cross and Knots(TicTacToe) game I experimented by developing an AI strategy from scratch. The algorithm proved effective.

## My Attempt at a TicTacToe AI

During my journey as a software developer I’ve always tried to set apart some time to focus on hobby projects or interesting problems. This is the first post of what I hope will be a series. I’ve decided to revisit some of these hobby projects and see if I could make an interesting read out of them.

This post is not a tutorial or a reference for best practices but simply an explanation on how I approached implementing an AI algorithm for a TicTacToe game.

TicTacToe is a game that’s familiar to most of us. But have you ever wondered how a computer would approach the problem. Would you explain to the computer what to do with an unending list of if else conditions.


Or is there some wonderful mathematical equation that allows the computer to pick the optimal move. Both could be possible but I decided to approach it a different way. I started out designing the ‘A “not so” I’ to place “X” randomly on an empty spot.

 When I started out I designed the algorithm mainly in Java. With the random algorithm the Computer had no strategies and was making very obvious blunders. Next I proceeded to make the random AI play against itself to test what the X wins to O wins ratio was. As you may be able to predict, the ratio was close to 1. In other words each side won around 50% of the time.

To keep the article concise I won’t go into all the details of how I improved the algorithm. However, I will walk you through how the final algorithm works.

We start with a game board which is a 3x3 matrix. The init board method initializes the board to values of -1 representing that those spots are unfilled.
```
public void initBoard() {
   for (int i = 0; i < board.length; i++) {
       for (int j = 0; j < board[i].length; j++) {
           board[i][j] = -1;
       }
   }
   move = 0;
   winner = -1;


}
```

“move” counts the number of the move.
“winner” is initially set to -1 representing that no one has won. 0 would represent X’s victory and 1 would represent O’s victory.

The findEmpty() method indicates whether there is an empty cell on the board:
```
//finds the first empty location on the board
public boolean findEmpty(GameEngine ttt) {

   for (int i = 0; i < 3; i++) {
       for (int j = 0; j < 3; j++) {
           if (ttt.board[i][j] == -1) {
               return true;
           }
       }
   }
   return false;
}
```
The winner method evaluates who has won.
```
public int winner() {

   if (winner != -1)
       return winner;

   //Determine if the row wins
   for (int i = 0; i < board.length; i++) {
       int prev = board[i][0];
       int count = 0;
       for (int j = 0; j < board[i].length; j++) {

           if (prev == board[i][j]) {
               count++;
               prev = board[i][j];
           } else {
               break;
           }
       }
       if (count == 3) {
           if (prev != -1) {
               winner = prev;
               return prev;
           }
       }
   }

   //Determine if column wins
   for (int i = 0; i < 3; i++) {
       int prev = board[0][i];
       int count = 0;
       for (int j = 0; j < 3; j++) {

           if (prev == board[j][i]) {
               count++;
               prev = board[j][i];
           } else {
               break;
           }
       }
       if (count == 3) {
           if (prev != -1) {
               winner = prev;
               return prev;
           }
       }
   }

   if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
       if (board[0][0] != -1) {
           winner = board[0][0];
           return board[0][0];
       }
   }


   if (board[2][0] == board[1][1] && board[1][1] == board[0][2]) {
       if (board[2][0] != -1) {
           winner = board[2][0];
           return board[2][0];
       }
   }
   winner = -1;
   return -1;
}
```
The following move generator calculates the AI’s moves
```
   //This is the move Generator for the main game
   public List<Integer> moveGeneratorMain( int userX, int userY) {



       Random rx = new Random();
       Random ry = new Random();
       double difficultyRate = 0.9;
//the times the performance of the AI was blow the difficulty rate
       int lowWinProbabilityCount = 0;
       double tempMaxDifficulty =0.0;
       int tempX=-1 ,tempY=-1;
//The x and y values finally selected
       int lastX=0,lastY=0;
       int x = 0, y = 0;
       //This variable indicates that the game is not complete
       boolean complete = false;
       if (findEmpty(this)) {
           if (userX != -1) {//if userX is -1 means that it’s the computers turn
               board[userX][userY] = 1;
               lastX = userX ;
               lastY = userY;
           } else {

               //Checks if move is complete

               while (complete == false) {
                   x = rx.nextInt(3);
                   y = ry.nextInt(3);
                   //If position is not empty then loop again
                   if (board[x][y] != -1) {
                       continue;
                   } else {
                       //If it's the computer's turn
                       if (move % 2 == 0) {
                           //difficulty generated by the computer through simulations
                           double difficultyGenerated = movePredictor(x, y);
                           //Checks if the computer has a high chance of winning
                           if (difficultyGenerated>=difficultyRate)//If yes
                           {
                               lastX = x;
                               lastY = y;
                               board[x][y] = move % 2;
                               complete = true;
                               System.out.println("movePredictorTrue");

                           } else//If it does not have a high chance of winning
                           {
                               if(difficultyGenerated>=tempMaxDifficulty)//Play the move with the highest generated difficulty out of all moves
                               {
                                   tempX = x;
                                   tempY = y;
                                   tempMaxDifficulty = difficultyGenerated;
                               }

                               lowWinProbabilityCount++;
                               if (lowWinProbabilityCount > 1000)//If it's unable to get a high chance of winning even in a 1000 attempts
                               {
                                   lastX = tempX;
                                   lastY = tempY;
                                   board[tempX][tempY] = move % 2;//Pick the last coordinates and complete the move (This can be improved to the move that got the max wins
                                   complete = true;
                               } else

                                   complete = false;
                           }

                       }


                   }
               }
           }
       }


       List<Integer> coord = new ArrayList<>();

       coord.add(lastX);
       coord.add(lastY);
       move++;
       return coord;


   }
```
The comments in the code give a high level overview of the functionality of different methods and variables.

Move predictor is an essential part of the algorithm and indicates the chance of winning (Returns a double)
```
//Returns the win rate of the AI for that specific move
public double movePredictor(int x, int y) {
   //This creates another instance of a game so that the computer can make predictions based on simulations
   GameEngine predictTTT = new GameEngine();
   //If it's the computer's turn

       //initializing the board
       predictTTT.initBoard();
       //cloning the state of the main game on to the simulation board
       for (int i = 0; i < predictTTT.board.length; i++)
           for (int j = 0; j < predictTTT.board[i].length; j++)
               predictTTT.board[i][j] = board[i][j];
       //Cloning the count of the move
       predictTTT.move = move;
       //Move mod 2 gives either 1 or 0 which is either O or X
       //This method gets the suggested x,y coordinates and evaluates how strong the move is
       predictTTT.board[x][y] = move % 2;
       //Increment to next move in simulation
       predictTTT.move++;

       //If this is the last move then return true
       if (move == 9)
           return 1.0;

       float sumXWins = 0;
       int draws = 0;
       //The computer runs 20 simulations and checks the total number of wins(sum)
       for (int i = 0; i < 20; i++) {
           GameEngine tt = new GameEngine();
           for (int i1 = 0; i1 < predictTTT.board.length; i1++)
               for (int j = 0; j < predictTTT.board[i1].length; j++)
                   tt.board[i1][j] = predictTTT.board[i1][j];
           tt.move = predictTTT.move;
           //if simulating the game returns 0 that means that X (The computer) has won a simulation
           int winner = simulateGame(tt);
           if (winner == 0) {
               sumXWins++;//Gives a weight of 1 to wins
           }
           else if(winner==-1)
           {
               sumXWins+=0.5;//Gives a weight of 0.5 to draws
           }
           else
           {
               sumXWins--;//Gives a weight of -1 to losses
           }

       }

   return (double)(sumXWins/20);

}
```
The code below explains what the simulateGame() method does
```
   //returns -1 if draw or game over(Possible error since it returns -1 at the end of the game even if the computer has won)
   public int simulateGame(GameEngine ttt) {


       //counts the number of moves
       int count = 0;
       if (findEmpty(ttt)) {
           //Loop while there is no winner and number of moves should be less than 9
           while (ttt.winner() == -1 && count < 9) {
               //Simulate the rest of the moves
               ttt.moveGeneratorSimulate();
               if (!findEmpty(ttt))
                   return -1;

//       System.out.println("Simulation");
               count++;
           }
       }
       //Returns the winner of the game simulation
       return ttt.winner();
   }
```
moveGeneratorSimulate() basically tries out combinations of several random moves and gives the coordinates of each move
```
//Generates random move for both sides
public List<Integer> moveGeneratorSimulate() {
   //Generates random X and Y coordinates
   Random rx = new Random();
   Random ry = new Random();
   int x = 0, y = 0;
   boolean complete = false;
   int ctr = 0;
   if (findEmpty(this)) {
       while (complete == false) {


           x = rx.nextInt(3);
           y = ry.nextInt(3);


           if (board[x][y] != -1) {
               //System.out.println("Move Generate");

               continue;
           } else {

               board[x][y] = move % 2;
               complete = true;


           }
       }
   }

   move++;

   List<Integer> coord = new ArrayList<>();
   coord.add(x);
   coord.add(y);
   return coord;


}

```






