package com.appia.sliding;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.math.Interpolation.*;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameBoard extends Group {
	private static final String TAG = GameBoard.class.getSimpleName();

	private int row;
	private int col;
	private TextureRegion img;
	private TextureRegion border;
	private MyStage stage;
	

	private boolean canTouch = false;
	private boolean inGame = false;

	public boolean isInGame() {
		return inGame;
	}


	private Block[][] board;
	private Image[][] imgBoard;

	private static int[] dr = { -1, 0, 1, 0 };
	private static int[] dc = { 0, 1, 0, -1 };
	
	private static int[] random(int length) {
		int[] result = new int[length];
		boolean[] dau = new boolean[length];
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int r = random.nextInt(length - i);
			int j;
			for (j = 0; j < length; j++){
				if (!dau[j]) {
					r--;					
					if (r < 0) {
						dau[j] = true;
						break;
					}
				}			
			}
			result[i] = j;
		}

		return result;
	}
		

	public GameBoard(int row, int column, TextureRegion img, TextureRegion border) {		
		this.row = row;
		this.col = column;
		this.img = img;
		this.border = border;
		// setDebug(true);
	}
	
	public void setStage(MyStage stage){
		this.stage = stage;
	}
	
	public void setCanTouch(boolean canTouch) {
		this.canTouch = canTouch;
	}
	
	public void setImage(TextureRegion img) {
		this.img = img;
	}
	
	public void setRowColumn(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public void setTextureRegion(TextureRegion img) {
		this.img = img;
		float regionX = img.getRegionWidth() / col;
		float regionY = img.getRegionHeight() / row;

		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++) {
				int tx = Math.round(img.getRegionX() + regionX * j);
				int ty = Math.round(img.getRegionY() + regionY * i);
				TextureRegion region = new TextureRegion(img.getTexture(), tx, ty, Math.round(regionX), Math.round(regionY));
				imgBoard[i][j].setDrawable(new TextureRegionDrawable(region));
			}
	}
	
	public void init() {
		//this.setDebug(true);
		this.clear();		
		canTouch = false;
		inGame = false;
		board = new Block[row][col];
		imgBoard = new Image[row][col];
		
		float r = getHeight() / row;
		float c = getWidth() / col;

		float regionX = img.getRegionWidth() / col;
		float regionY = img.getRegionHeight() / row;

		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++) {
				int tx = Math.round(img.getRegionX() + regionX * j);
				int ty = Math.round(img.getRegionY() + regionY * i);
				TextureRegion region = new TextureRegion(img.getTexture(), tx, ty, Math.round(regionX), Math.round(regionY));
				Image image = new Image(region);
				image.setX(j * c);
				image.setY((row - i - 1) * r);
				image.setWidth(c);
				image.setHeight(r);		
				
				imgBoard[i][j] = image;

				//image.setDebug(true);
				if (i == row - 1 && j == col - 1) {					
					Actor actor = new Actor();
					//actor.setDebug(true);
					actor.setX(image.getX());
					actor.setY(image.getY());
					actor.setWidth(image.getWidth());
					actor.setHeight(image.getHeight());
					board[i][j] = new Block(this, actor, new Position(i, j), new Position(i, j));
				} else {
					Image imgBorder = new Image(border);
					imgBorder.setWidth(image.getWidth());
					imgBorder.setHeight(image.getHeight());
					Group group = new Group();
					group.setWidth(image.getWidth());
					group.setHeight(image.getHeight());
					group.setX(image.getX());
					group.setY(image.getY());
					
					image.setX(0);
					image.setY(0);
					group.addActor(image);
					group.addActor(imgBorder);
					
					board[i][j] = new Block(this, group, new Position(i, j), new Position(i, j));
				}

				this.addActor(board[i][j].getActor());
			}
	}

	public void newGame() {			
		canTouch = true;
		inGame = true;
		int[] random = random(row * col);	
//		for (int i = 0; i < random.length; i++)
//			random[i] = i;
//		random[random.length - 2] = random.length - 1;
//		random[random.length - 1] = random.length - 2;
		
		for (int i = 0; i < random.length; i++) {
			Position p1 = new Position(i / col, i % col);
			Position p2 = new Position(random[i] / col, random[i] % col);
			swapNow(p1, p2);
		}
	}

	public void click(Position pos) {			
		if (!inGame) {
			stage.newGame();
			return;
		}
		if (!canTouch)
			return;
		for (int i = 0; i < 4; i++) {
			int r = pos.getRow() + dr[i];
			if (0 <= r && r < row) {
				int c = pos.getCol() + dc[i];
				if (0 <= c && c < col) {
					if (board[r][c].getOri().equals(new Position(row - 1, col - 1))) {
						move(pos, new Position(r, c));
					}
				}
			}
		}
	}

	private void move(final Position pos, final Position to) {
		canTouch = false;		
		GameSound.instance().playWhoosh();
		final float ox = getBlock(pos).getActor().getX();
		final float oy = getBlock(pos).getActor().getY();
		getBlock(pos).getActor().addAction(sequence(moveTo(getBlock(to).getActor().getX(), getBlock(to).getActor().getY(), 0.25f, linear), run(new Runnable() {

			@Override
			public void run() {
				canTouch = true;
				getBlock(to).getActor().setX(ox);
				getBlock(to).getActor().setY(oy);

				swapBlock(pos, to);

				getBlock(pos).getPos().swap(getBlock(to).getPos());
				if (checkDoneGame()){
					Gdx.app.log(TAG, "END GAME");
					stage.doneGame();
				};
			}
		})));
	}
	
	
	private boolean checkDoneGame(){
		for (int i = 0; i < row; i++)
			for (int j = 0; j < row; j++) {
				if (!board[i][j].getPos().equals(board[i][j].getOri()))
					return false;
			}
		return true;
	}
	
	private void swapNow(Position pos, Position to){
		final float ox = getBlock(pos).getActor().getX();
		final float oy = getBlock(pos).getActor().getY();
		
		getBlock(pos).getActor().setX(getBlock(to).getActor().getX());
		getBlock(pos).getActor().setY(getBlock(to).getActor().getY());
		
		getBlock(to).getActor().setX(ox);
		getBlock(to).getActor().setY(oy);

		swapBlock(pos, to);

		getBlock(pos).getPos().swap(getBlock(to).getPos());

	}

	private void swapBlock(Position a, Position b) {
		Block tmp = board[a.getRow()][a.getCol()];
		board[a.getRow()][a.getCol()] = board[b.getRow()][b.getCol()];
		board[b.getRow()][b.getCol()] = tmp;
	}

	private Block getBlock(Position pos) {
		return board[pos.getRow()][pos.getCol()];
	}


	public void stop() {
		this.setCanTouch(false);
		inGame = false;		
	}
}
