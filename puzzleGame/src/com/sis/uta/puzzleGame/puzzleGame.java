package com.sis.uta.puzzleGame;

import com.badlogic.gdx.Game;
import com.sis.uta.puzzleGame.screens.Splash;

public class puzzleGame extends Game {

	private PuzzlePreferences preferences;
	
	public puzzleGame()
	{
		preferences=new PuzzlePreferences();
	}
	
	public PuzzlePreferences getPreferences()
	{
		return preferences;
	}
	
	@Override
	public void create() {		

		setScreen(new Splash(this));
		
	}

	@Override
	public void dispose() {
		
		super.dispose();
	}

	@Override
	public void render() {		
		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		
		super.resize(width, height);
	}

	@Override
	public void pause() {
		
		super.pause();
	}

	@Override
	public void resume() {
		
		super.resume();
	}
}
