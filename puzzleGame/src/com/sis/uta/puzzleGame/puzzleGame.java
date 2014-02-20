package com.sis.uta.puzzleGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.sis.uta.puzzleGame.screens.FirstSection;
import com.sis.uta.puzzleGame.screens.GraphPuzzle;
import com.sis.uta.puzzleGame.screens.MainMenu;
import com.sis.uta.puzzleGame.screens.SectionSelect;
import com.sis.uta.puzzleGame.screens.Splash;

public class puzzleGame extends Game implements InputProcessor {

	private PuzzlePreferences preferences;
	
	private Sprite menubutton;

	private Rectangle menurectangle;
	
	public static int Screen;
	public static final int SPLASH=0;
	public static final int SECTIONSELECT=1;
	public static final int FIRSTSECTION=2;
	public static final int MAINMENU=3;
	public static final int OPTIONS=4;
	public static final int INFO=5;
	public static final int GRIDPUZZLE=6;
	public static final int TEXTPUZZLE=7;
	public static final int GRAPHPUZZLE=8;
	public static final int PICTUREPUZZLE=9;
	
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
		
		Texture.setEnforcePotImages(false);
		
		menubutton = new Sprite(new Texture("maps/configure-3.png"));
		menubutton.setSize(50, 50);
		menurectangle = new Rectangle(Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 50, 50 , 50);
		menubutton.setBounds(menurectangle.getX(), menurectangle.getY(), menurectangle.getWidth(), menurectangle.getHeight());
		menurectangle.setY(0);
		
		
		
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		
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

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		if(keycode == Keys.BACK)
		{
            switch (Screen)
            {
            	case SPLASH: 
            		Gdx.app.exit();
            		break;
            	case SECTIONSELECT:
            		Gdx.app.exit();
            		break;
            	case FIRSTSECTION:
            		setScreen(new SectionSelect(this));
            		break;
            	case MAINMENU:
            		setScreen(new FirstSection(this));
            		break;
            	case OPTIONS:
            		setScreen(new MainMenu(this));
            		break;
            	case INFO:
            		setScreen(new MainMenu(this));
            		break;
            	case GRIDPUZZLE:
            		setScreen(new FirstSection(this));
            		break;
            	case TEXTPUZZLE:
            		setScreen(new FirstSection(this));
            		break;
            	case GRAPHPUZZLE:
            		setScreen(new FirstSection(this));
            		break;
            	case PICTUREPUZZLE:
            		setScreen(new FirstSection(this));
            	default:
            		break;
            }
            
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
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
