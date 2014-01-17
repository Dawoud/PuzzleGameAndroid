package com.sis.uta.puzzleGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.sis.uta.puzzleGame.screens.SectionSelect;
import com.sis.uta.puzzleGame.screens.Splash;

public class puzzleGame extends Game {

	private PuzzlePreferences preferences;
	
	private Sprite menubutton;
	
	
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
		menubutton = new Sprite(new Texture("maps/character.png"));
		menubutton.setBounds(Gdx.graphics.getWidth() - 30, Gdx.graphics.getHeight() - 30, 30 , 30);
		
		setScreen(new Splash(this));
		
	}

	@Override
	public void dispose() {
		
		super.dispose();
		menubutton.getTexture().dispose();
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
	
	public Sprite getMenubutton() {
		return menubutton;
	}


}
