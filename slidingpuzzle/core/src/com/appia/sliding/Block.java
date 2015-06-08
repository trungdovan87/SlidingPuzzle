package com.appia.sliding;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Block extends ClickListener{
	private Position pos;
	private Position ori;
	private Actor actor;
	private GameBoard gameBoard;
	
	public Block(GameBoard gameBoard, Actor actor, Position ori, Position pos) {
		this.gameBoard = gameBoard;
		this.actor = actor;
		this.ori = ori;
		this.pos = pos;
		actor.addListener(this);
	}
	
	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	public Position getOri() {
		return ori;
	}

	public void setOri(Position ori) {
		this.ori = ori;
	}

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		gameBoard.click(this.pos);
		return super.touchDown(event, x, y, pointer, button);
	}
}
