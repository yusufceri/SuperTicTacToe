package com.smartnif.game.tictacai;

import com.smartnif.game.tictacai.GameMain.Seed;

public abstract class AIPlayer {
   protected int ROWS = GameMain.ROWS;  // number of rows
   protected int COLS = GameMain.COLS;  // number of columns
 
   protected Cell[][] cells; // the board's ROWS-by-COLS array of Cells
   protected Seed mySeed;    // computer's seed
   protected Seed oppSeed;   // opponent's seed

   abstract int[] move(int depth);

   /** Constructor with reference to game board */
   public AIPlayer(Board board) {
      cells = board.cells;
   }
 
   /** Set/change the seed used by computer and opponent */
   public void setSeed(Seed seed) {
      this.mySeed = seed;
      oppSeed = (mySeed == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
   }
}
