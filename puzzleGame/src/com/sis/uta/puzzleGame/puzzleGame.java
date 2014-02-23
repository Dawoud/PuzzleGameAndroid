package com.sis.uta.puzzleGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.sis.uta.puzzleGame.screens.FirstSection;
import com.sis.uta.puzzleGame.screens.GraphPuzzle;
import com.sis.uta.puzzleGame.screens.MainMenu;
import com.sis.uta.puzzleGame.screens.SectionSelect;
import com.sis.uta.puzzleGame.screens.Splash;

/**
 * @author Jere
 *
 */
public class puzzleGame extends Game implements InputProcessor {

	private PuzzlePreferences preferences;
	
	private Sprite menubutton;
	
	private String[] puzzles;
	private int scores;
	private String scoresString;
	private int puzzlesSolved;

	private Rectangle menurectangle;

	private final int puzzleCount = 4;
	
	private BitmapFont scoresFont;
	
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

		puzzles = new String[puzzleCount];
		
		for(int i = 0; i < puzzleCount ; ++i)
		{
			puzzles[i] = "0";
		}
		
		scores = 0;
		puzzlesSolved = 0;
		
	}
	
	public PuzzlePreferences getPreferences()
	{
		return preferences;
	}
	
	@Override
	public void create() {
		
		// Font for scores
		scoresFont = new BitmapFont();
		
		// read game save file
		if(Gdx.files.local("SISAdventure_savefile.txt").exists())
		{
			try
			{
				FileHandle handle = Gdx.files.local("SISAdventure_savefile.txt");
				
				String file = handle.readString();
				String[] fileSplit = file.split(" ");
				
				scores = Integer.parseInt(fileSplit[1]);
				
				puzzles = fileSplit[0].split("/");
				
				for(int i = 0; i < puzzles.length; ++i)
				{
					if(puzzles[i].equals("1"))
						++puzzlesSolved;
				}
			}
			catch(IllegalArgumentException e)
			{
				e.printStackTrace();
				Gdx.app.log("Main", "Invalid save file found");
			}
		}
		
		scoresString = "Puzzles: " + puzzlesSolved + "/" + puzzles.length + " Score: "+ scores;
		
		Texture.setEnforcePotImages(false);
		
		//create menubutton to be shown in maps and section select screen.
		menubutton = new Sprite(new Texture("maps/configure-3.png"));
		menubutton.setSize(50, 50);
		menurectangle = new Rectangle(Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 50, 50 , 50);
		menubutton.setBounds(menurectangle.getX(), menurectangle.getY(), menurectangle.getWidth(), menurectangle.getHeight());
		menurectangle.setY(0);
		
		// enable back key
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		
		// start the game
		setScreen(new Splash(this));
		
	}
	
	
	@Override
	public void dispose() {
		this.saveGame();
		menubutton.getTexture().dispose();
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

	/** Updates solved puzzle array and score. Called whenever puzzle is solved.
	 * @param index, solved puzzle index.
	 */
	public void puzzleCompleted(int index)
	{
		if(index <= puzzles.length && index > 0)
		{
			// puzzle solved
			puzzles[index-1] = "1";
			
			puzzlesSolved++;
			scores++;
			Gdx.app.log("Main", "Puzzle with "+ index + " index completed!");
			
			scoresString = "Puzzles: " + puzzlesSolved + "/" + puzzles.length + " Score: "+ scores;
		}
		else
		{
			Gdx.app.log("Main", "Puzzle with given index was not found");
		}
		
	}
		
	/** Draws current score on top left corner of the screen.
	 * @param batch for drawing the fonts
	 */
	public void drawPoints(SpriteBatch batch)
	{
		scoresFont.setColor(new Color(Color.BLACK));
		scoresFont.draw(batch, scoresString, 15, Gdx.graphics.getHeight()-15); 
	}

	
	
	/**
	 * Saves the game. Writes "SISAdventure_savefile.txt" into app's local memory.
	 */
	public void saveGame()
	{

		String puzzleStatusSave = puzzles[0];
	
		for(int i = 1; i < puzzles.length; ++i)
		{
			puzzleStatusSave += "/";
			puzzleStatusSave += puzzles[i];
		}
		
		FileHandle handle =  Gdx.files.local(("SISAdventure_savefile.txt"));
		puzzleStatusSave += " ";
		
		handle.writeString(puzzleStatusSave, false);
		
		handle.writeString(Integer.toString(scores), true);
		
		Gdx.app.log("Main", "Game saved");
	}


}
