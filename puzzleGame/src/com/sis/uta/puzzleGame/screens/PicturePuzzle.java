package com.sis.uta.puzzleGame.screens;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.sis.uta.puzzleGame.puzzleGame;
import com.sis.uta.puzzleGame.controller.GraphPuzzleController;
import com.sis.uta.puzzleGame.controller.SectionSelectController;

public class PicturePuzzle implements Screen {
	
	private BitmapFont white_normal=new BitmapFont(Gdx.files.internal("font/white_normal.fnt"), false);
	private LabelStyle normalStyle=new LabelStyle(white_normal, Color.WHITE);
	
	public class PicPuzzle{
		private String picture;
		private String description;
		
		public PicPuzzle()
		{
			picture="";
			description="";
		}
		
		public PicPuzzle(String pic, String desc)
		{
			picture=pic;
			description=desc;
		}
		
		public void setPicture(String pic)
		{
			picture=pic;
		}
		
		public void setDescription(String desc)
		{
			description=desc;
		}
		
		public String getPicture()
		{
			return picture;
		}
		
		public String getDescription() {
			return description;
		}
	}
	
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
	
	
	private puzzleGame game;
	
	private SpriteBatch batch;
	
	private ShapeRenderer renderer;
	
	private OrthographicCamera camera;
	
	private Table table;
	private Stage stage;
	private Skin skin;
	
	private InputMultiplexer multiplexer;
	private Sprite img;
	private Texture imgTexture;
	
	private Sprite backButton;
	private Texture backTexture;
	
	private Dialog dialog;
	
	private ArrayList<PicPuzzle> puzzleList;
	
	private ArrayList<SpotObject> objectList;
	
	private PicPuzzle actPuzzle;
	
	public PicturePuzzle(puzzleGame game)
	{
		super();
		this.game=game;
		game.Screen=game.PICTUREPUZZLE;
		puzzleList=new ArrayList<PicPuzzle>();
		puzzleList.add(new PicPuzzle("puzzle/comproom.png", "puzzle/comproomdesc.xml"));
		puzzleList.add(new PicPuzzle("puzzle/office2.png", "puzzle/office2desc.xml"));
	}
	
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
		
		getLabels();
		stage.act(delta);
		stage.draw();
		
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
			
			boolean allfound=true;
			for (SpotObject o: objectList)
			{
				allfound=allfound && o.getFound();
			}
			if (allfound)
			{
				float delay=2;
				Timer.schedule(new Task()
				{

					@Override
					public void run() {
						game.puzzleCompleted(4);
						game.setScreen(new FirstSection(game));
					}}, delay);
			}
		}
		
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
		
		renderer.setProjectionMatrix(camera.combined);
		
		stage=new Stage();
		
		skin=new Skin(Gdx.files.internal("data/uiskin.json"));
		
		multiplexer=new InputMultiplexer();
		multiplexer.addProcessor(game);
		
		showDialog();
		
		table=new Table(skin);
		table.setBounds(0, Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/6, Gdx.graphics.getWidth(), Gdx.graphics.getWidth()/6);
		
		stage.addActor(table);
		
		Random rnd=new Random();
		int rndix=rnd.nextInt(puzzleList.size());
		
		actPuzzle=puzzleList.get(rndix);
		
		imgTexture=new Texture(actPuzzle.getPicture());
		img=new Sprite(imgTexture);
		img.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		backTexture=new Texture("data/backbutton_small.png");
		backButton=new Sprite(backTexture);
		backButton.setSize(60, 30);
		backButton.setPosition(Gdx.graphics.getWidth()-60, 0);
		
		objectList=new ArrayList<PicturePuzzle.SpotObject>();
		
		getObjectsFromXml(actPuzzle.getDescription());

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
	
	public void getLabels()
	{
		table.clear();
		
		for(SpotObject o : objectList)
		{
			if (!o.getFound())
			{
				table.add(o.getName()).minWidth(90).minHeight(20);
			}
		}
	}
	
	public void showDialog()
	{
		multiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(multiplexer);
		
		dialog=new Dialog("Info", skin, "dialog")
		{
			protected void result (Object object)
			{
				multiplexer.removeProcessor(stage);
				
				dialog.setVisible(false);
			}
		};
		dialog.text("Help the professor find his objects.");
		dialog.button("OK", true);
		dialog.show(stage);
	}
	
	public void startDialog(String string) {
		Gdx.input.setInputProcessor(stage);
		Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		Dialog dialog = new Dialog("Information", skin)
		{

			protected void result (Object object)
			{
			}
		}
		.text(string).button("  OK  ").show(stage);
		
	}


}
