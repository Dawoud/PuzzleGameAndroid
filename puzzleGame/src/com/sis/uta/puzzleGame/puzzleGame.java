package com.sis.uta.puzzleGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.sis.uta.puzzleGame.screens.SectionSelect;
import com.sis.uta.puzzleGame.screens.Splash;

public class puzzleGame extends Game {

	private PuzzlePreferences preferences;
	
	private Sprite menubutton;

	private Rectangle menurectangle;
	
	
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
		menurectangle = new Rectangle(Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 100, 100 , 100);
		menubutton.setBounds(menurectangle.getX(), menurectangle.getY(), menurectangle.getWidth(), menurectangle.getHeight());
		menurectangle.setY(0);
		
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

	public Rectangle getMenurectangle() {
		return menurectangle;
	}


}
