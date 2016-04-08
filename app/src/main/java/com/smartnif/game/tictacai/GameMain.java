package com.smartnif.game.tictacai;

public class GameMain {
	public static int ROWS=3, COLS=3;
	
	public enum Seed {
		EMPTY,
		NOUGHT,
		CROSS
	}
}

class Cell{
	public GameMain.Seed content;
	public int value;
}

class Board{
	public Cell cells[][] = new Cell[GameMain.ROWS][GameMain.COLS];
	
	public Board(int gboard[][]){

		setBoard(gboard);
	}
	
	public void setGameBoard(int gboard[][]){

		setBoard(gboard);
	}
	
	private void setBoard(int gboard[][]){
		
		for(int i=0;i<GameMain.ROWS;i++){
			for(int j=0;j<GameMain.COLS;j++){
				cells[i][j] = new Cell();
				
				if(gboard[i][j] == 1)
					if(cells[i][j].content == null)
						cells[i][j].content = GameMain.Seed.CROSS;
				if(gboard[i][j] == 0)
					cells[i][j].content = GameMain.Seed.EMPTY;
				if(gboard[i][j] == -1)
					cells[i][j].content = GameMain.Seed.NOUGHT;
			}
		}
	}
}