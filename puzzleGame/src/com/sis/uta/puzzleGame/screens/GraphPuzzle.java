package com.sis.uta.puzzleGame.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.sis.uta.puzzleGame.puzzleGame;
import com.sis.uta.puzzleGame.controller.GraphPuzzleController;
import com.sis.uta.puzzleGame.model.PuzzleTriangle;

public class GraphPuzzle implements Screen {

	public GraphPuzzle(puzzleGame game) {
		super();
		this.game = game;
		game.Screen=game.GRAPHPUZZLE;
	}

	private puzzleGame game;
	
	private PuzzleTriangle[] trianglesclasses;
	
	private ShapeRenderer renderer;
	
	private OrthographicCamera camera;

	private float middleX;
	private float middleY;

	private float[] triangles;

	private int trianglescount;

	private Color[] colours;
	
	private Color chosencolor;

	private float yunit;

	private float xunit;

	private int trianglecounter;
	
	private SpriteBatch batch;
	private Sprite checkButton, backButton;
	private Texture checkTexture, backTexture;
	
	/* Rectangles for buttons, numbering starting from bottom left and moving up and right */
	private Rectangle[] buttonsArray;
	
	@Override
	public void render(float delta) {


		Gdx.gl.glClearColor(0,0,0,0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// render first filled triangles
		renderer.begin(ShapeType.Filled);
		for(int i = 0; i < trianglescount; ++i)
		{	
			
			PuzzleTriangle triangled = trianglesclasses()[i];
			renderer.setColor(triangled.getColour());
			float[] xn = triangled.getVertices();
			renderer.triangle(xn[0], xn[1], xn[2], xn[3], xn[4], xn[5]);
		}
		renderer.end();

		// render outlines to triangles
		renderer.begin(ShapeType.Line);
		
		for(int i = 0; i < trianglescount; ++i)
		{
			renderer.setColor(Color.BLACK);
		
			PuzzleTriangle triangled = trianglesclasses()[i];
			float[] xn = triangled.getVertices();
			renderer.triangle(xn[0], xn[1], xn[2], xn[3], xn[4], xn[5]);
		}
		renderer.flush();
		renderer.end();
		
		// render current color button
		renderer.begin(ShapeType.Filled);
		renderer.setColor(new Color(chosencolor));
		renderer.rect(0, 0, 100, 100);
		renderer.flush();
		renderer.end();
		
		// render other buttons
		batch.begin();
		checkButton.draw(batch);
		backButton.draw(batch);
		batch.flush();
		batch.end();
		
	}

	
	
	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		
		renderer = new ShapeRenderer();
		
		batch=new SpriteBatch();
	
		// Button rectangles for controller
		buttonsArray = new Rectangle[3];
		
		buttonsArray[0] = new Rectangle(0, 0, 100, 100);
		
		buttonsArray[1] = new Rectangle(0, Gdx.graphics.getHeight()-100, 100, 100);
		checkTexture=new Texture("data/checkbutton.png");
		checkButton=new Sprite(checkTexture);
		checkButton.setSize(100, 100);
		checkButton.setPosition(0, Gdx.graphics.getHeight()-100);
		
		buttonsArray[2] = new Rectangle(Gdx.graphics.getWidth()-100, 0, 100, 100);
		backTexture=new Texture("data/backbutton.png");
		backButton=new Sprite(backTexture);
		backButton.setSize(100, 100);
		backButton.setPosition(Gdx.graphics.getWidth()-100, Gdx.graphics.getHeight()-100);
		
		// camera configuration
		camera = new OrthographicCamera();
		
		camera.viewportHeight = Gdx.graphics.getHeight();
		camera.viewportWidth = Gdx.graphics.getWidth();
		
		middleX = Gdx.graphics.getWidth() / 2f;
		
		middleY = Gdx.graphics.getHeight() / 2f;
		
		camera.position.set( middleX, middleY ,0);
		
		camera.update();
		
		renderer.setProjectionMatrix(camera.combined);
		
		// create triangles 
		createTriangles();
		
		trianglesclasses = new PuzzleTriangle[triangles.length/6];
		boolean changeable = true;
		int n = 0;
		for(int i = 0; i < triangles.length;)
		{
			if(Math.random() < 0.7 && (n % 4) == 0)
			{
				changeable = false;
			}
			PuzzleTriangle triangle = new PuzzleTriangle(new float[] {triangles[i], triangles[i+1], triangles[i+2], triangles[i+3], triangles[i+4], triangles[i+5]},  changeable);
			trianglesclasses()[n] = triangle;
			i += 6;
			n++;
			changeable = true;
		}
		
		trianglesclasses[0].setAdjacentTriangles(null, trianglesclasses()[1], trianglesclasses()[6]);
		trianglesclasses[1].setAdjacentTriangles(trianglesclasses()[0], trianglesclasses()[2], null);
		trianglesclasses[2].setAdjacentTriangles(trianglesclasses()[1], trianglesclasses()[3], trianglesclasses()[8]);
		trianglesclasses[3].setAdjacentTriangles(trianglesclasses()[2], trianglesclasses()[4], null);
		trianglesclasses[4].setAdjacentTriangles(trianglesclasses()[3], null, trianglesclasses()[10]);
		trianglesclasses[5].setAdjacentTriangles(null, trianglesclasses()[6], trianglesclasses()[12]);
		trianglesclasses[6].setAdjacentTriangles(trianglesclasses()[5], trianglesclasses()[7], trianglesclasses()[0]);
		trianglesclasses[7].setAdjacentTriangles(trianglesclasses()[6], trianglesclasses()[8], trianglesclasses()[14]);
		trianglesclasses[8].setAdjacentTriangles(trianglesclasses()[7], trianglesclasses()[9], trianglesclasses()[2]);
		trianglesclasses[9].setAdjacentTriangles(trianglesclasses()[8], trianglesclasses()[10], trianglesclasses()[16]);
		trianglesclasses[10].setAdjacentTriangles(trianglesclasses()[9], trianglesclasses()[11], trianglesclasses()[4]);
		trianglesclasses[11].setAdjacentTriangles(trianglesclasses()[10], null, trianglesclasses()[18]);
		trianglesclasses[12].setAdjacentTriangles(null, trianglesclasses()[13], trianglesclasses()[5]);
		trianglesclasses[13].setAdjacentTriangles(trianglesclasses()[12], trianglesclasses()[14], trianglesclasses()[19]);
		trianglesclasses[14].setAdjacentTriangles(trianglesclasses()[13], trianglesclasses()[15], trianglesclasses()[7]);
		trianglesclasses[15].setAdjacentTriangles(trianglesclasses()[14], trianglesclasses()[16], trianglesclasses()[21]);
		trianglesclasses[16].setAdjacentTriangles(trianglesclasses()[15], trianglesclasses()[17], trianglesclasses()[9]);
		trianglesclasses[17].setAdjacentTriangles(trianglesclasses()[16], trianglesclasses()[18], trianglesclasses()[23]);
		trianglesclasses[18].setAdjacentTriangles(trianglesclasses()[17], null, trianglesclasses()[11]);
		trianglesclasses[19].setAdjacentTriangles(null, trianglesclasses()[20], trianglesclasses()[13]);
		trianglesclasses[20].setAdjacentTriangles(trianglesclasses()[19], trianglesclasses()[21], null);
		trianglesclasses[21].setAdjacentTriangles(trianglesclasses()[20], trianglesclasses()[22], trianglesclasses()[15]);
		trianglesclasses[22].setAdjacentTriangles(trianglesclasses()[21], trianglesclasses()[23], null);
		trianglesclasses[23].setAdjacentTriangles(trianglesclasses()[22], null, trianglesclasses()[17]);
		
		chosencolor = new Color(Color.GREEN);
		
		// set inputprocessor
		Gdx.input.setInputProcessor( new GestureDetector( new GraphPuzzleController( game, this )));
		
	}


	@Override
	public void hide() {
		dispose();
		

	}

	@Override
	public void pause() {
		game.saveGame();

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}
	
	
	/**
	 * Creates triangles to graph puzzle map. 
	 */
	private void createTriangles()
	{
		Gdx.app.log("ThirdPuzzle", "creating triangles");
		trianglescount = 24;
		
		trianglecounter = 0;
		
		// units which declares how big triangles are going to be
		yunit = Gdx.graphics.getHeight()/12;
		xunit = Gdx.graphics.getWidth()/16;
		
		triangles = new float[trianglescount* 3 * 2];
		
		// first of the first row, placement of whole structure is based on these values
		triangles[0] = (middleX - xunit*2);
		triangles[1] = (middleY + yunit*4);
		
		triangles[2] = ((middleX - xunit*2) - xunit);
		triangles[3] = ((middleY + yunit*4) - (yunit*2));
		
		triangles[4] = ((middleX - xunit*2) + xunit);
		triangles[5] = ((middleY + yunit*4) - (yunit*2));
		
		int i = 6;
		
		
//		rest of the first row 
		for(trianglecounter = 1; trianglecounter < 5; ++trianglecounter)
		{
			if(trianglecounter % 2 == 0)
			{
				i = createEvenTriangle(i);				
			}
			else
			{
				i = createOddTriangle(i);
			}
		}
		
		// first of the second row
		
		triangles[i] = triangles[2];
		i++;
		triangles[i] = triangles[3];
		i++;
		
		triangles[i] = triangles[2] - xunit;
		i++;
		triangles[i] = triangles[3] - yunit*2;
		i++;
		
		triangles[i] = triangles[2] + xunit;
		i++;
		triangles[i] = triangles[3] - yunit*2;
		i++;
		
		// rest of the second row
		for(trianglecounter = 1; trianglecounter < 7; ++trianglecounter)
		{
			if(trianglecounter % 2 == 0)
			{
				i = createEvenTriangle(i);				
			}
			else
			{
				i = createOddTriangle(i);
			}
		}
		
		// first triangle of the third row
		triangles[i] = triangles[2] - xunit;
		i++;
		triangles[i] = triangles[3] - yunit*2;
		i++;
		
		triangles[i] = triangles[2] +xunit;
		i++;
		triangles[i] = triangles[3] - yunit*2;
		i++;
		
		triangles[i] = triangles[2];
		i++;
		triangles[i] = triangles[3] - yunit*4;
		i++;
		
		// rest of the third row
		for(trianglecounter = 0; trianglecounter < 6; ++trianglecounter)
		{
			if(trianglecounter % 2 == 0)
			{
				i = createEvenTriangle(i);				
			}
			else
			{
				i = createOddTriangle(i);
			}
		}
		
		// Create fourth rows first triangle
		triangles[i] = triangles[2];
		i++;
		triangles[i] = triangles[3] - yunit*4;
		i++;
		
		triangles[i] = triangles[2] +xunit*2;
		i++;
		triangles[i] = triangles[3] - yunit*4;
		i++;
		
		triangles[i] = triangles[2] + xunit;
		i++;
		triangles[i] = triangles[3] - yunit*6;
		i++;
	
		// Rest of the fourth row
		for(trianglecounter = 0; trianglecounter < 4; ++trianglecounter)
		{
			if(trianglecounter % 2 == 0)
			{
				i = createEvenTriangle(i);				
			}
			else
			{
				i = createOddTriangle(i);
			}
		}
	}
	
	private int createEvenTriangle(int i)
	{
		triangles[i] = triangles[i-4];
		i++;
		triangles[i] = triangles[i-4];
		i++;
		 
		triangles[i] = triangles[i-2] - xunit;
		i++;
		triangles[i] = triangles[i-2] - yunit*2;
		i++;
		
		triangles[i] = triangles[i-2] + xunit*2;
		i++;	
		triangles[i] = triangles[i-2];
		i++; 
		
		return i;
	}

	private int createOddTriangle(int i)
	{
		triangles[i] = triangles[i-6];
		i++;
		triangles[i] = triangles[i-6]; 
		i++;
		
		triangles[i] = triangles[i-2] + xunit*2;
		i++;
		triangles[i] = triangles[i-2];
		i++;
		
		triangles[i] = triangles[i-2] - xunit;
		i++;	
		triangles[i] = triangles[i-2] - yunit*2;
		i++;
		
		return i;
	}
	
	public PuzzleTriangle[] trianglesclasses() {
		return trianglesclasses;
	}

	public boolean checkTriangles()
	{
		for(int i = 0; i < trianglesclasses().length; ++i)
		{
			if(!trianglesclasses()[i].checkTriangles())
			{
				Gdx.app.log("Thirdpuzzle", "Two triangles adjacent each other have the same color :(");
				return false;
			}
		}
		return true;
	}



	public Color getChosencolor() {
		
		return chosencolor;
	}
	
	public void changeChosenColor()
	{
		if(chosencolor.equals(Color.GREEN))
			chosencolor = new Color(Color.BLUE);
		else if (chosencolor.equals(Color.BLUE))
			chosencolor = new Color(Color.MAGENTA);
		else if (chosencolor.equals(Color.MAGENTA))
			chosencolor = new Color(Color.GREEN);
	}



	public Rectangle[] getButtonsArray() {
		return buttonsArray;
	}

}
