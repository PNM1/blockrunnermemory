package com.blockrunnermemory.states;

public class Cfg {

	public final int difficult;
	public final int time;
	public final int num;
	public final int numInRow;
	public final int numRows;

	public Cfg(int difficult) {
		this.difficult = difficult;
		switch (difficult) {
		case 1:
			time = 60;
			num = 6;
			numInRow = 3;
			numRows = 2;
			break;
		case 2:
			time = 90;
			num = 12;
			numInRow = 4;
			numRows = 3;
			break;
		case 3:
			time = 120;
			num = 18;
			numInRow = 6;
			numRows = 3;
			break;
		case 4:
			time = 180;
			num = 28;
			numInRow = 7;
			numRows = 4;
			break;
		case 5:
			time = 240;
			num = 32;
			numInRow = 8;
			numRows = 4;
			break;
		case 6:
			time = 300;
			num = 50;
			numInRow = 10;
			numRows = 5;
			break;	
		default:
			throw new IllegalArgumentException("Выберите один из пердставленных");
		}
	}

}
