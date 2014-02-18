package com.sis.uta.puzzleGame.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.sis.uta.puzzleGame.puzzleGame;
import com.sis.uta.puzzleGame.screens.FirstSection;
import com.sis.uta.puzzleGame.screens.GraphPuzzle;
import com.sis.uta.puzzleGame.screens.SectionSelect;

public class GraphPuzzleController implements GestureListener {

	private GraphPuzzle view;
	private puzzleGame game;
	
	
	public GraphPuzzleController(puzzleGame game, GraphPuzzle view)
	{	
		this.view = view;
		this.game = game;
	}


	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {	
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {

		if(x > 100)
		{
			if( x > Gdx.graphics.getWidth()-100 )
			{
				// user clicked back button
				if( view.getButtonsArray()[2].contains( x, y ))
				{
					game.setScreen(new FirstSection(game));
				}
			}
			else
			{
				// user clicked somewhere in the middle of the screen
				for(int i = 0; i < view.trianglesclasses().length; ++i)
				{
					
					if(view.trianglesclasses()[i].contains(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY()))
					{
						Gdx.app.log("Thirdpuzzle", "found one!");
						view.trianglesclasses()[i].setColour(view.getChosencolor());
						break;
					}
				}
			}
		}
		else
		{
			if( view.getButtonsArray()[1].contains( x, y ) )
			{
				view.changeChosenColor();
			}
			else if( view.getButtonsArray()[0].contains( x, y ) )
			{
				if( view.checkTriangles() )
				{
					game.setScreen(new FirstSection(game));
				}
			}
		}
		
		return true;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

}
