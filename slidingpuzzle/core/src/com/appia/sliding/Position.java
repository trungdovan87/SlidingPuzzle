package com.appia.sliding;

public class Position  {
	private int row;
	private int col;
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}	
	
	public Position() {
		
	}
	
	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public void swap(Position pos){
		int tmp = this.row;
		this.row = pos.row;
		pos.row = tmp;
		
		tmp = this.col;
		this.col = pos.col;
		pos.col = tmp;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Position) {
			Position pos = (Position) obj;
			return (this.getCol() == pos.getCol() && this.getRow() == pos.getRow()); 
		} else {
			return false;
		}				
	}
}
