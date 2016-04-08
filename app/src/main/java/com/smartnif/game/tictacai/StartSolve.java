package com.smartnif.game.tictacai;

import android.util.Log;

import com.tictac.game.smartnif.tictactoegame.GameInterface;
import com.smartnif.game.tictacai.GameMain.Seed;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class StartSolve {
	static int gameBoard[][] = {{0,  0, 0 },
		     			 {0,  0, 0 },
		     			 {0,  0, 0 }};

	int debugCounter;
	static GameInterface gInterface;

	private static final String THIS_FILE = "StartSolve";

	private Seed player;
	private Seed oppPlayer;
	static AIPlayerMiniMax AIplayer;
	static Board board;
	public int depth;
	private static boolean ISDEBUG = false;

	public enum GameLEVEL{ EASY,
		MEDIUM,
		HARD
	}
	
	public StartSolve(GameInterface gInterface, Seed player, GameLEVEL gameLevel) {
		board = new Board(gameBoard);
		AIplayer = new AIPlayerMiniMax(board);
		this.depth = setGameLevel(gameLevel);

		setUserType(player);

		AIplayer.setSeed(oppPlayer);
		this.gInterface = gInterface;
		debugCounter = 0;

		prepareBoard();
	}

	private int setGameLevel(GameLEVEL gameLevel){
		int depth = 2;

		switch (gameLevel){
			case EASY:
				depth = 1;
				break;
			case MEDIUM:
				depth = 3;
				break;
			case HARD:
				depth = 4;
				break;
			default:
				System.out.println("Game Level can't found !!!!!!!  gamelevel="+gameLevel);
		}
		//System.out.println("!! Game Level depth = " + depth);
		return depth;
	}

	private void setUserType(Seed userPlay){
		player = userPlay;
		if(userPlay == Seed.CROSS)
			oppPlayer = Seed.NOUGHT;
		else if(userPlay == Seed.NOUGHT)
			oppPlayer = Seed.CROSS;
		else{
			System.out.println("Kullanici tipi belirlenemedi !!!!!!! ");
		}
	}

	private int getMoveValue(Seed player){
		if(Seed.CROSS == player){
			return 1;
		}else if(Seed.NOUGHT == player){
			return -1;
		}

		return 0;
	}

	public void nextMoveForUser(int x, int y) {
		//if(debugCounter > 10) {
		//	Log.d(THIS_FILE, "Hareket Sayisi 10 ve ustu !!!!!!!! ");
		//	return ;
		//}

		if(ISDEBUG){
			printBoard();
			System.out.println("USER= ======x=" + x + "  y=" + y + " ===== ");
		}

		//User's move
		gameBoard[x][y] = getMoveValue(this.player);

		//debugCounter++;

		if(isGameOver(this.player)){
			if(this.player == Seed.CROSS)
				if(ISDEBUG)
					System.out.println("====== USER - CROSS KAZANDI  !!!! ===== ");
			gInterface.gameResult(true, this.player);
			return;
		}

		//if(isGameOver(Seed.NOUGHT)){
		//	System.out.println("====== NOUGHT KAZANDI !!!! ===== ");
		//}

		if(++debugCounter >= 9){
			if(ISDEBUG)
				System.out.println("===== BERABER =======");
			gInterface.gameResult(false, this.player);
			return;
		}

		//User has won?
		//if(AIplayer.hasWon(Seed.NOUGHT)){
		//	gInterface.gameResult(true, Seed.NOUGHT);
		//}


		//Computer move
		if(!nextMoveforComputer()){
			if(ISDEBUG) System.out.println("======== Game COVER ================ ");
			gInterface.gameResult(true, oppPlayer);
			return;
		}

		if(isGameOver(oppPlayer)){
			if(this.player == Seed.NOUGHT)
				if(ISDEBUG) System.out.println("====== COMPUTER - NOUGHT KAZANDI !!!!!!!!!!! ===== ");
			gInterface.gameResult(true, oppPlayer);
			return;
		}

		if(++debugCounter >= 9){
			if(ISDEBUG) System.out.println("===== BERABER =======");
			gInterface.gameResult(false, oppPlayer);
			return;
		}

		//User has won?
		//if(AIplayer.hasWon(Seed.CROSS)){
		//	System.out.println("====== Computer Win ==== ");
		//	gInterface.gameResult(true, Seed.CROSS);
		//}

	}

	public void startMoveComp(){
		//Computer move
		Random r = new Random();
		int move = r.nextInt(8);

		int x = move/3;
		int y = move%3;

		gameBoard[x][y] = getMoveValue(oppPlayer);
		++debugCounter;

		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(x);
		l.add(y);

		gInterface.nextMoveforComputer(l);

//		if(!nextMoveforComputer()){
//			System.out.println("======== Game COVER ================ ");
//			gInterface.gameResult(true, oppPlayer);
//			return;
//		}
//		if(++debugCounter >= 9){
//			System.out.println("===== BERABER =======");
//			gInterface.gameResult(false, oppPlayer);
//			return;
//		}
	}

	public static boolean isGameOver(Seed player){
		board.setGameBoard(gameBoard);
		AIplayer.setBoard(board);

		if(AIplayer.hasWon(player)){
			if(ISDEBUG) System.out.println("!!!!!!! GAME OVER !!!!! ");
			return true;
		}

		return false;

	}

	public static boolean isExit(){
		if(ISDEBUG) System.out.println("Is Exit: ");
		Scanner in = new Scanner(System.in);
		int exit = in.nextInt();
		
		if(exit==0)
			return false;
		else
			return true;
	}
	
	static void prepareBoard(){
		
		for (int i = 0; i < GameMain.ROWS; i++) {
			for (int j = 0; j < GameMain.COLS; j++) {
				gameBoard[i][j] = 0;
			}
		}	
	}
	
	static void printBoard(){
		for (int i = 0; i < GameMain.ROWS; i++) {
			System.out.println();
			for (int j = 0; j < GameMain.COLS; j++) {
				System.out.print(gameBoard[i][j]+"	");
			}
		}
		System.out.println();
	}

	
	boolean nextMoveforComputer(){
		int move[] = new int[2];
		
		board.setGameBoard(gameBoard);
		AIplayer.setBoard(board);
		
		move = AIplayer.move(depth); //move[0] -> score    move[1] -> x   move[2] -> y

		if(move[1]==-1 && move[2]==-1){
			if(ISDEBUG) System.out.println("Game OVER ========================== ");
			ArrayList<Integer> l = new ArrayList<Integer>();
			l.add(move[1]);
			l.add(move[2]);

			gInterface.nextMoveforComputer(l);
			return false;  //Game end, computer won
		}else {
			System.out.println("====>>>>>> Game Continue == x="+move[1]+" y="+move[2]);
			gameBoard[move[1]][move[2]] = getMoveValue(oppPlayer);

			ArrayList<Integer> l = new ArrayList<Integer>();
			l.add(move[1]);
			l.add(move[2]);

			gInterface.nextMoveforComputer(l);

			return true;
		}

	}

}


