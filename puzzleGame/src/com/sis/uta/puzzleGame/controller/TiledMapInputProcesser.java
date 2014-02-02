package com.sis.uta.puzzleGame.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.sis.uta.puzzleGame.puzzleGame;
import com.sis.uta.puzzleGame.screens.FirstSection;
import com.sis.uta.puzzleGame.screens.GraphPuzzle;
import com.sis.uta.puzzleGame.screens.GridPuzzle;
import com.sis.uta.puzzleGame.screens.MainMenu;
import com.sis.uta.puzzleGame.screens.MapScreen;
import com.sis.uta.puzzleGame.screens.SecondPuzzle;
import com.sis.uta.puzzleGame.screens.SectionSelect;

public class TiledMapInputProcesser implements InputProcessor {

	private Game game;
	
	public TiledMapInputProcesser(Game game)
	{
		super();
		this.game = game;
		
//		Gdx.input.setCatchBackKey(true);
	}
	
	@Override
	public boolean keyDown(int keycode) {
//		if(keycode == Keys.BACK)
//		{
//			
//		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(((Game)Gdx.app.getApplicationListener()).getScreen() instanceof SectionSelect)
		{	
			switch(checkTile(screenX, screenY))
			{
				/* empty space, continue */
				case -1:
					break;
					
				/* Main menu area clicked, return */
				case 0:
					((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
					break;
				
				/* Puzzle area clicked, start first puzzle */
				case 1:
					((Game)Gdx.app.getApplicationListener()).setScreen(new FirstSection(game));
					break;
				
				case 2:
					((MapScreen)((Game)Gdx.app.getApplicationListener()).getScreen()).startDialog("Second section not implemented yet");
					Gdx.app.log("SectionSelect", "Second section not implemented yet");
					break;
				case 3:
					((MapScreen)((Game)Gdx.app.getApplicationListener()).getScreen()).startDialog("Third section not implemented yet");
					Gdx.app.log("SectionSelect", "Third section not implemented yet");
					break;
			}
		}
		else if(((Game)Gdx.app.getApplicationListener()).getScreen() instanceof FirstSection)
		{
			switch(checkTile(screenX, screenY))
			{
				case -1:
					break;
				case 0:
					((Game)Gdx.app.getApplicationListener()).setScreen(new SectionSelect(game));
					break;
				case 1:
					((Game)Gdx.app.getApplicationListener()).setScreen(new GridPuzzle(game));
					break;
				case 2:
					((Game)Gdx.app.getApplicationListener()).setScreen(new SecondPuzzle(game));
					break;
				case 3:
					((Game)Gdx.app.getApplicationListener()).setScreen(new GraphPuzzle(game));
					break;
			}	
		}
		
		/* Menu button at topright corner */
		if(((puzzleGame)game).getMenurectangle().contains(screenX, screenY))
		{
			game.setScreen(new MainMenu(game));
		}
		
		return true;
	}

	private int checkTile(int screenX, int screenY) {
		
		MapScreen a = (MapScreen)game.getScreen();
		
		MapProperties prop = a.getMap().getProperties();
		
		int width = prop.get("width", Integer.class) - 1; 
		int height = prop.get("height", Integer.class) - 1;
		
		int resowidth = Gdx.graphics.getWidth();
		int resoheight = Gdx.graphics.getHeight();
		
		int tilewidth = resowidth/width;
		int tileheight = resoheight/height;
		
		int answer = -1;
		
		TiledMapTileLayer clickableLayers = (TiledMapTileLayer) a.getMap().getLayers().get("inputlocations");
		
		int x = (int) Math.floor(screenX/tilewidth);
		int y = (int) Math.floor((resoheight - screenY)/tileheight);
		
		for(int i = 0; i < 4; ++i)
		{
			if(clickableLayers != null)
			{
				if(clickableLayers.getCell(x,y ) != null)
				{
					if(clickableLayers.getCell( x, y ).getTile().getProperties().containsKey("puzzle" + i) )
					{
						answer = i;
						break;
					}
				}
			}
		}

		return answer;
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

}
