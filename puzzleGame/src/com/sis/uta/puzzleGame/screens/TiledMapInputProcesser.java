package com.sis.uta.puzzleGame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class TiledMapInputProcesser implements InputProcessor {

	private Game game;
	
	public TiledMapInputProcesser(Game game)
	{
		super();
		this.game = game;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
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
				
				Gdx.app.log("SectionSelect", "Second section not implemented yet");
				break;
			case 3:
				Gdx.app.log("SectionSelect", "Third section not implemented yet");
				break;
		}
		return true;
	}

	private int checkTile(int screenX, int screenY) {
		// TODO Auto-generated method stub
		
		Gdx.app.log("TiledInputProcesser", "Checking X: " + screenX + " Y: " + screenY);
		
		MapScreen a = (MapScreen)game.getScreen();
		
		MapProperties prop = a.getMap().getProperties();
		
		int width = prop.get("width", Integer.class) - 1; 
		int height = prop.get("height", Integer.class) - 1;
		
		Gdx.app.log("TiledInputProcesser", "Got mapwidth: " + width + " and height: " + height);
		
		int resowidth = Gdx.graphics.getWidth();
		int resoheight = Gdx.graphics.getHeight();
		
		int tilewidth = resowidth/width;
		int tileheight = resoheight/height;
		
		Gdx.app.log("TiledInputProcessor", "Tileheight: " + tileheight + " and tilewidth: " + tilewidth);
		
		int answer = -1;
		
		
		TiledMapTileLayer clickableLayers = (TiledMapTileLayer) a.getMap().getLayers().get("inputlocations");
		
		int x = (int) Math.floor(screenX/tilewidth);
		int y = (int) Math.floor((resoheight - screenY)/tileheight);
		
		Gdx.app.log("TiledInputProcessor", "Coordinate X: " + x + " Y: " + y);
		
		for(int i = 0; i < 4; ++i)
		{
			Gdx.app.log("TiledInputProcessor", "checking object " + i);
			if(clickableLayers.getCell(x,y ) != null)
			{
				if(clickableLayers.getCell( x, y ).getTile().getProperties().containsKey("puzzle" + i) )
				{
					answer = i;
					break;
				}
			}
		}
		
		
		Gdx.app.log("TiledInputProcessor", "Got ID prop: " + answer);

		return answer;
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
