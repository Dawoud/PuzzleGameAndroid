package com.sis.uta.puzzleGame.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.sis.uta.puzzleGame.puzzleGame;
import com.sis.uta.puzzleGame.model.Player;
import com.sis.uta.puzzleGame.screens.MainMenu;

public class PlayerController implements InputProcessor {

	public final int UP = 1, DOWN = 2, LEFT = 3,  RIGHT = 4;
	
	private Player player;
	
	private int controlmode;

	private puzzleGame game;

	private Rectangle[] buttons1;
	private Rectangle[] buttons2;
	
	
	public PlayerController(Player player, puzzleGame game) {

		this.game = game;
		this.player = player;
		controlmode = 1;
		
		float stepX = Gdx.graphics.getWidth()/8;
		float stepY = Gdx.graphics.getHeight()/6; 
		
		float middleX = Gdx.graphics.getWidth()/2;
		float middleY = Gdx.graphics.getHeight()/2;
		
		Rectangle rectangle1 = new Rectangle(0, middleY-stepY*2, stepX, stepY*2); // up
		Rectangle rectangle2 = new Rectangle(Gdx.graphics.getWidth()-stepX, middleY, stepX, stepY*2); // down
		Rectangle rectangle3 = new Rectangle(0, middleY-stepY, stepX, stepY*2); // left
		Rectangle rectangle4 = new Rectangle(Gdx.graphics.getWidth()-stepX, middleY-stepY*2, stepX, stepY*2); // right
		
		
		buttons1 = new Rectangle[] {rectangle1, rectangle2, rectangle3, rectangle4};
		
		
		rectangle1 = new Rectangle(middleX-stepX, 0, stepX*2, stepY); // up
		rectangle2 = new Rectangle(middleX-stepX, Gdx.graphics.getHeight()-stepY, stepX*2,stepY); // down
		rectangle3 = new Rectangle(0, middleY-stepY, stepX,stepY*2); // left
		rectangle4 = new Rectangle(Gdx.graphics.getWidth()-stepX, middleY-stepY, stepX, stepY*2); // right
		
		
		buttons2 = new Rectangle[] {rectangle1, rectangle2, rectangle3, rectangle4};
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
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		// up and down buttons are at top and bottom of the screen
		if(controlmode == 1)
		{
			for(int i = 0; i < buttons1.length; ++i)
			{
				if(buttons1[i].contains(screenX, screenY))
				{
					player.move(i+1);
				}
			}
		}
		// up and down buttons are more reachable, near left and right buttons
		else if(controlmode == 2)
		{
			for(int i = 0; i < buttons2.length; ++i)
			{
				if(buttons2[i].contains(screenX, screenY))
				{
					player.move(i+1);
				}
			}
		}
		
		if(game.getMenurectangle().contains(screenX, screenY))
		{
			game.setScreen(new MainMenu(game));
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		player.setVelocityX(0f);
		player.setVelocityY(0f);
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	public int getControlmode() {
		return controlmode;
	}


	public void setControlmode(int controlmode) {
		if(controlmode < 3 && controlmode > 0)
			this.controlmode = controlmode;
	}
}
