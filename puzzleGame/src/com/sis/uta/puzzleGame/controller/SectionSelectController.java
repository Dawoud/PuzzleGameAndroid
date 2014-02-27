package com.sis.uta.puzzleGame.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.sis.uta.puzzleGame.puzzleGame;
import com.sis.uta.puzzleGame.screens.FirstSection;
import com.sis.uta.puzzleGame.screens.MainMenu;
import com.sis.uta.puzzleGame.screens.SectionSelect;

public class SectionSelectController implements GestureListener {

	private puzzleGame game;
	private SectionSelect view;
	
	public SectionSelectController(puzzleGame game, SectionSelect view) {
//		super(game);
		this.game = game;
		this.view = view;
	}
	
//	@Override
//	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//		Gdx.app.log("SectionSelect controller", "Touchdown noticed");
//		return super.touchDown(screenX, screenY, pointer, button);	
//	}
//	
//	@Override
//	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//		Gdx.app.log("SectionSelect controller", "touchup noticed");
//		return super.touchUp(screenX, screenY, pointer, button);
//	}


	@Override
	public boolean tap(float x, float y, int count, int button) {
		if(((puzzleGame)game).getMenurectangle().contains(x, y))
		{
			game.setScreen(new MainMenu(game));
		}
		else
		{
			switch(view.getCurrentBuilding())
			{
			case SectionSelect.LINNA:
				game.setScreen( new FirstSection(game));
				break;
			case SectionSelect.PAATALO:
				view.startDialog("Second section hasn't been implemented yet");
				break;
			case SectionSelect.PINNI:
				view.startDialog("Third section hasn't been implemented yet");
			}
		}
		return true;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	/* Change sectionselect background */
	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		Gdx.app.log("SectionSelect controller", "fling noticed");
		if(velocityX > 0)
		{
			view.nextBuilding();
		}
		else if(velocityX < 0)
		{
			view.previousBuilding();
		}
		return true;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		return false;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

}
