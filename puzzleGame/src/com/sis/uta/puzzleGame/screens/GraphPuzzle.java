package com.sis.uta.puzzleGame.screens;

import java.util.Random;

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
import com.badlogic.gdx.math.Polygon;

public class GraphPuzzle implements Screen {

	public GraphPuzzle(Game game) {
		super();
		this.game = game;
	}

	private Game game;
	
	private Triangle[] trianglesclasses;
	
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
	
	@Override
	public void render(float delta) {


		Gdx.gl.glClearColor(0,0,0,0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// render first filled triangles
		renderer.begin(ShapeType.Filled);
		for(int i = 0; i < trianglescount; ++i)
		{	
			
			Triangle triangled = trianglesclasses[i];
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
		
			Triangle triangled = trianglesclasses[i];
			float[] xn = triangled.getVertices();
			renderer.triangle(xn[0], xn[1], xn[2], xn[3], xn[4], xn[5]);
		}
		renderer.flush();
		renderer.end();
		
		// currently just get back when screen is touched
		if(Gdx.input.justTouched())
		{

			Gdx.app.log("Thirdpuzzle", "checking puzzles, total of "+ trianglesclasses.length);
			for(int i = 0; i < trianglesclasses.length; ++i)
			{
				
				if(trianglesclasses[i].contains(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY()))
				{
					Gdx.app.log("Thirdpuzzle", "found one!");
					trianglesclasses[i].setColour(chosencolor);
					break;
				}
			}
			
		}
		
		renderer.begin(ShapeType.Filled);
//		renderer.setColor(new Color(Color.CYAN));
//		renderer.rect(0, Gdx.graphics.getHeight()-100, 100, 100);
		renderer.setColor(new Color(chosencolor));
		renderer.rect(0, 0, 100, 100);
//		renderer.setColor(new Color(Color.PINK));
//		renderer.rect(Gdx.graphics.getWidth()-100, Gdx.graphics.getHeight()-100, 100, 100);
		renderer.flush();
		renderer.end();
		
		
		
		batch.begin();
		checkButton.draw(batch);
		backButton.draw(batch);
		batch.flush();
		batch.end();
		
		
		if(Gdx.input.justTouched())
		{
			if(Gdx.input.getX() < 100)
			{
				if(Gdx.input.getY() < 100)
				{	
			
					boolean allok = false;
					Gdx.app.log("Thirdpuzzle", "Checking triangle colours");
					for(int i = 0; i < trianglesclasses.length; ++i)
					{
						if(!trianglesclasses[i].checkTriangles())
						{
							Gdx.app.log("Thirdpuzzle", "Two triangles adjacent each other have the same color :(");
							allok = false;
							break;
						}
						else
						{
							allok = true;
						}
						
					}
					if(allok)
					{
						
						game.setScreen(new FirstSection(game));
					}
				}
				else if(Gdx.input.getY() > (Gdx.graphics.getHeight() - 100))
				{
					if(chosencolor.equals(Color.GREEN))
						chosencolor = new Color(Color.BLUE);
					else if (chosencolor.equals(Color.BLUE))
						chosencolor = new Color(Color.MAGENTA);
					else if (chosencolor.equals(Color.MAGENTA))
						chosencolor = new Color(Color.GREEN);
				}
			}
			else if(Gdx.input.getX() > Gdx.graphics.getWidth()-100 && Gdx.input.getY() < 100)
			{
				game.setScreen(new FirstSection(game));
			}
		}
		
	}

	
	
	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		
		renderer = new ShapeRenderer();
		
		batch=new SpriteBatch();
		
		checkTexture=new Texture("data/checkbutton.png");
		checkButton=new Sprite(checkTexture);
		checkButton.setSize(100, 100);
		checkButton.setPosition(0, Gdx.graphics.getHeight()-100);
		
		backTexture=new Texture("data/backbutton.png");
		backButton=new Sprite(backTexture);
		backButton.setSize(100, 100);
		backButton.setPosition(Gdx.graphics.getWidth()-100, Gdx.graphics.getHeight()-100);
		
		camera = new OrthographicCamera();
		
		camera.viewportHeight = Gdx.graphics.getHeight();
		camera.viewportWidth = Gdx.graphics.getWidth();
		
		middleX = Gdx.graphics.getWidth() / 2f;
		
		middleY = Gdx.graphics.getHeight() / 2f;
		
		camera.position.set( middleX, middleY ,0);
		
		camera.update();
		
		renderer.setProjectionMatrix(camera.combined);
	
		Gdx.input.setInputProcessor(null);
		
		createTriangles();
		
		trianglesclasses = new Triangle[triangles.length/6];
		boolean changeable = true;
		int n = 0;
		for(int i = 0; i < triangles.length;)
		{
			System.out.println(i % 3);
			if(Math.random() < 0.7 && (n % 4) == 0)
			{
				changeable = false;
			}
			Triangle triangle = new Triangle(new float[] {triangles[i], triangles[i+1], triangles[i+2], triangles[i+3], triangles[i+4], triangles[i+5]},  changeable);
			trianglesclasses[n] = triangle;
			i += 6;
			n++;
			changeable = true;
		}
		
		trianglesclasses[0].setAdjacentTriangles(null, trianglesclasses[1], trianglesclasses[6]);
		trianglesclasses[1].setAdjacentTriangles(trianglesclasses[0], trianglesclasses[2], null);
		trianglesclasses[2].setAdjacentTriangles(trianglesclasses[1], trianglesclasses[3], trianglesclasses[8]);
		trianglesclasses[3].setAdjacentTriangles(trianglesclasses[2], trianglesclasses[4], null);
		trianglesclasses[4].setAdjacentTriangles(trianglesclasses[3], null, trianglesclasses[10]);
		trianglesclasses[5].setAdjacentTriangles(null, trianglesclasses[6], trianglesclasses[12]);
		trianglesclasses[6].setAdjacentTriangles(trianglesclasses[5], trianglesclasses[7], trianglesclasses[0]);
		trianglesclasses[7].setAdjacentTriangles(trianglesclasses[6], trianglesclasses[8], trianglesclasses[14]);
		trianglesclasses[8].setAdjacentTriangles(trianglesclasses[7], trianglesclasses[9], trianglesclasses[2]);
		trianglesclasses[9].setAdjacentTriangles(trianglesclasses[8], trianglesclasses[10], trianglesclasses[16]);
		trianglesclasses[10].setAdjacentTriangles(trianglesclasses[9], trianglesclasses[11], trianglesclasses[4]);
		trianglesclasses[11].setAdjacentTriangles(trianglesclasses[10], null, trianglesclasses[18]);
		trianglesclasses[12].setAdjacentTriangles(null, trianglesclasses[13], trianglesclasses[5]);
		trianglesclasses[13].setAdjacentTriangles(trianglesclasses[12], trianglesclasses[14], trianglesclasses[19]);
		trianglesclasses[14].setAdjacentTriangles(trianglesclasses[13], trianglesclasses[15], trianglesclasses[7]);
		trianglesclasses[15].setAdjacentTriangles(trianglesclasses[14], trianglesclasses[16], trianglesclasses[21]);
		trianglesclasses[16].setAdjacentTriangles(trianglesclasses[15], trianglesclasses[17], trianglesclasses[9]);
		trianglesclasses[17].setAdjacentTriangles(trianglesclasses[16], trianglesclasses[18], trianglesclasses[23]);
		trianglesclasses[18].setAdjacentTriangles(trianglesclasses[17], null, trianglesclasses[11]);
		trianglesclasses[19].setAdjacentTriangles(null, trianglesclasses[20], trianglesclasses[13]);
		trianglesclasses[20].setAdjacentTriangles(trianglesclasses[19], trianglesclasses[21], null);
		trianglesclasses[21].setAdjacentTriangles(trianglesclasses[20], trianglesclasses[22], trianglesclasses[15]);
		trianglesclasses[22].setAdjacentTriangles(trianglesclasses[21], trianglesclasses[23], null);
		trianglesclasses[23].setAdjacentTriangles(trianglesclasses[22], null, trianglesclasses[17]);
		
//		while(createStaticTriangles()) {};
		
//		Create random colours to triangles, just because
		Random rand = new Random();
		
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		
		Color randomColor = new Color(r, g, b, 1 );
		
		colours = new Color[trianglescount];
		
		colours[0] = randomColor;
		
		for(int i = 1; i < trianglescount; ++i)
		{
			r = rand.nextFloat();
			g = rand.nextFloat();
			b = rand.nextFloat();
		
			randomColor = new Color(r, g, b, 1 );
			
			colours[i] = randomColor;
		}
		
		chosencolor = new Color(Color.GREEN);
		
	}


	@Override
	public void hide() {
		dispose();
		

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
		//renderer.dispose();

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
	
	private class Triangle extends Polygon {

		private Triangle[] adjacentTriangles;
		private Color colour;
		private boolean changeable;
		private float[] vertices;
		
		public Triangle(float[] vertices, boolean changeable)
		{
			super(vertices);
			colour = new Color(Color.WHITE);
			this.vertices = vertices;
			this.changeable = changeable;
			
			if(!changeable)
			{
				Color[] colors = new Color[] {new Color(Color.BLUE), new Color(Color.MAGENTA)};
				
				if(Math.random() < 0.5)
				{
					this.colour = colors[1];
				}
				else
				{
					this.colour = colors[0];
				}
			
			}
		}

		
		/** Method to check if adjacent triangles are same color as this one.
		 * @return true if adjacent triangles were other color than this triangle
		 */
		public boolean checkTriangles()
		{
			if(this.colour.equals(Color.WHITE))
			{
				return false;
			}
			for(int i = 0; i < adjacentTriangles.length; ++i)
			{
				if(adjacentTriangles[i] != null)
				{
					if(adjacentTriangles[i].getColour().equals(this.colour))
					{
						return false;
					}
				}
			}
			
			return true;
		}

		
		
		public Color getColour() {
			return colour;
		}


		public void setColour(Color colour) {
			if(changeable)
				this.colour = colour;
		}

		
		public float[] getVertices() {
			return vertices;
		}
		


		public void setAdjacentTriangles(Triangle a, Triangle b, Triangle c) {
			this.adjacentTriangles = new Triangle[3];
			adjacentTriangles[0] = a;
			adjacentTriangles[1] = b;
			adjacentTriangles[2] = c;
		}

	}

}
