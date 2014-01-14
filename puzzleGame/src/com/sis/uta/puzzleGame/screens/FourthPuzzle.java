package com.sis.uta.puzzleGame.screens;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class FourthPuzzle implements Screen {
	
	public class SpotObject
	{
		private String name;
		private int coordX;
		private int coordY;
		
		public SpotObject()
		{
			name="";
			coordX=0;
			coordY=0;
		}
		
		public SpotObject(String n, int x, int y)
		{
			name=n;
			coordX=x;
			coordY=y;
		}
		
		public void setName(String n)
		{
			name=n;
		}
		
		public void setX(int x)
		{
			coordX=x;
		}
		
		public void setY(int y)
		{
			coordY=y;
		}
		
		public String getName()
		{
			return name;
		}
		
		public int getX()
		{
			return coordX;
		}
		
		public int getY()
		{
			return coordY;
		}
	}

	public FourthPuzzle(Game game)
	{
		super();
		this.game=game;

	}
	
	
	
	private Game game;
	
	private SpriteBatch batch;
	
	private Sprite img;
	private Texture imgTexture;
	
	private ArrayList<SpotObject> objectList;
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		batch.begin();
		img.draw(batch);
		batch.end();
		
		if (Gdx.input.justTouched())
		{
			for(SpotObject o: objectList)
			{
				if (Gdx.input.getX()>o.getX()-10 && Gdx.input.getX()<o.getX()+10)
				{
					if (Gdx.input.getY()>o.getY()-10 && Gdx.input.getY()<o.getY()+10)
					{
						Gdx.app.log("FourthPuzzle", o.getName()+" found!");
					}
				}
			}
		}
		
//		if (Gdx.input.justTouched())
//		{
//			Gdx.app.log("FourthPuzzle", "touched: "+Integer.toString(Gdx.input.getX())+", "+Integer.toString(Gdx.input.getY()));
//		}
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		batch=new SpriteBatch();
		
		imgTexture=new Texture("puzzle/test4p.png");
		img=new Sprite(imgTexture);
		img.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Gdx.input.setInputProcessor(null);
		
		objectList=new ArrayList<FourthPuzzle.SpotObject>();
		
		getObjectsFromXml("puzzle/test4pdesc.xml");
		
//		objectList.add(new SpotObject("o1", (int) (Math.round((115.0/500.0)*Gdx.graphics.getWidth())), (int)(Math.round((105.0/500.0)*Gdx.graphics.getHeight()))));
//		objectList.add(new SpotObject("o2", (int) (Math.round((350.0/500.0)*Gdx.graphics.getWidth())), (int)(Math.round((270.0/500.0)*Gdx.graphics.getHeight()))));
//		objectList.add(new SpotObject("o3", (int) (Math.round((100.0/500.0)*Gdx.graphics.getWidth())), (int)(Math.round((460.0/500.0)*Gdx.graphics.getHeight()))));
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public void getObjectsFromXml(String filepath)
	{
		XmlReader xml=new XmlReader();
		
		try {
			Element root=xml.parse(Gdx.files.internal(filepath));
			Array<Element> objects=root.getChildrenByName("object");
			
			for (Element o: objects)
			{
				objectList.add(new SpotObject(o.get("name"), (int)(Math.round((Integer.parseInt(o.get("x"))/500.0)*Gdx.graphics.getWidth())), (int)(Math.round((Integer.parseInt(o.get("y"))/500.0)*Gdx.graphics.getHeight()))));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
