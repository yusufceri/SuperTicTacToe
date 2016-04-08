package com.tictac.game.smartnif.tictactoegame;

import com.smartnif.game.tictacai.*;
import com.smartnif.game.tictacai.GameMain.Seed;

import java.util.ArrayList;

/**
 * Created by Ceri on 1/3/2016.
 */
public interface GameInterface {
        public void nextMoveforComputer(ArrayList<Integer> move);
        public void gameResult(boolean result, Seed player);

}
