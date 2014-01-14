package com.sis.uta.puzzleGame.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.sis.uta.puzzleGame.model.Player;

public class PlayerController implements InputProcessor {

	public final int UP = 1, DOWN = 2, LEFT = 3,  RIGHT = 4;
	
	private Player player;
	
	public PlayerController(Player player) {
		super();
		this.player = player;
	}
	
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode)
		{
		case Keys.W:
			player.move(UP);
			break;
		case Keys.S:
			player.move(DOWN);
			break;
		case Keys.A:
			player.move(LEFT);
			break;
		case Keys.D:
			player.move(RIGHT);
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode)
		{
		case Keys.W:
		case Keys.S:
			player.setVelocityY(0f);
			break;
		case Keys.A:
		case Keys.D:
			player.setVelocityX(0f);
			break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
