package com.sis.uta.puzzleGame.screens;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class PicturePuzzle implements Screen {
	
	public class SpotObject
	{
		private String name;
		private int coordX;
		private int coordY;
		private int rad;
		private boolean found;
		
		public SpotObject()
		{
			name="";
			coordX=0;
			coordY=0;
			rad=0;
			found=false;
		}
		
		public SpotObject(String n, int x, int y, int r)
		{
			name=n;
			coordX=x;
			coordY=y;
			rad=r;
			found=false;
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
		
		public void setRad(int r)
		{
			rad=r;
		}
		
		public void setFound(boolean f)
		{
			found=f;
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
		
		public int getRad()
		{
			return rad;
		}
		
		public boolean getFound()
		{
			return found;
		}
		
	}

	public PicturePuzzle(Game game)
	{
		super();
		this.game=game;

	}
	
	
	
	private Game game;
	
	private SpriteBatch batch;
	
	private ShapeRenderer renderer;
	
	private OrthographicCamera camera;

	private float middleX;
	private float middleY;
	
	private Sprite img;
	private Texture imgTexture;
	
	private Sprite backButton;
	private Texture backTexture;
	
	private ArrayList<SpotObject> objectList;
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0,0,0,0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		img.draw(batch);
		backButton.draw(batch);
		batch.flush();
		batch.end();
		
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.RED);
		for (SpotObject o: objectList)
		{
			if (o.getFound()==true)
			{
				renderer.circle(o.getX(), o.getY(), o.getRad());
			}
		}
		renderer.flush();
		renderer.end();
		
		if (Gdx.input.justTouched())
		{
			
			if (Gdx.input.getX()>Gdx.graphics.getWidth()-60 && Gdx.input.getY()>Gdx.graphics.getHeight()-30)
			{
				game.setScreen(new FirstSection(game));
			}
			
			for(SpotObject o: objectList)
			{
				if (Gdx.input.getX()>o.getX()-o.getRad() && Gdx.input.getX()<o.getX()+o.getRad())
				{
					if (Gdx.input.getY()>o.getY()-o.getRad() && Gdx.input.getY()<o.getY()+o.getRad())
					{
						Gdx.app.log("FourthPuzzle", o.getName()+" found!");
						o.setFound(true);
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
		
		renderer = new ShapeRenderer();
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
//		camera.viewportHeight = Gdx.graphics.getHeight();
//		camera.viewportWidth = Gdx.graphics.getWidth();
//		
//		middleX = Gdx.graphics.getWidth() / 2f;
//		
//		middleY = Gdx.graphics.getHeight() / 2f;
//		
//		camera.position.set( middleX, middleY ,0);
//		
//		camera.update();
		
		renderer.setProjectionMatrix(camera.combined);
		
		imgTexture=new Texture("puzzle/test4p.png");
		img=new Sprite(imgTexture);
		img.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		backTexture=new Texture("data/backbutton_small.png");
		backButton=new Sprite(backTexture);
		backButton.setSize(60, 30);
		backButton.setPosition(Gdx.graphics.getWidth()-60, 0);
		
		Gdx.input.setInputProcessor(null);
		
		objectList=new ArrayList<PicturePuzzle.SpotObject>();
		
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
				objectList.add(new SpotObject(o.get("name"), (int)(Math.round((Integer.parseInt(o.get("x"))/500.0)*Gdx.graphics.getWidth())), (int)(Math.round((Integer.parseInt(o.get("y"))/500.0)*Gdx.graphics.getHeight())), Integer.parseInt(o.get("r"))));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}