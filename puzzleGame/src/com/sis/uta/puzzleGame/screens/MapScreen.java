package com.sis.uta.puzzleGame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.tiled.TiledMap;

public abstract class MapScreen implements Screen {

	protected TiledMap map;
	
	public TiledMap getMap()
	{
		if(map != null)
		{
			return map;
		}
		else
		{
			Gdx.app.log("MapScreen", "Map not found!");
			return null;
		}
		
	}

}
