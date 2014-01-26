package com.sis.uta.puzzleGame.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sis.uta.puzzleGame.puzzleAudio;
import com.sis.uta.puzzleGame.tween.SpriteAccessor;

public class Splash implements Screen {
	
	public Splash(Game game) {
		this.game = game;
	}

	private Game game;
	
	private SpriteBatch batch;
	private Sprite splash;
	
	private TweenManager tweenManager;
	
	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		
		batch.begin();
		splash.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		
		puzzleAudio.playMusic();

		batch=new SpriteBatch();
		tweenManager=new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		
		Texture splashTexture=new Texture("data/splash.png");
		splash=new Sprite(splashTexture);
		splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(splash, SpriteAccessor.ALPHA, 2).target(1).repeatYoyo(1, 2).setCallback(new TweenCallback() {
			
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				// TODO Auto-generated method stub
				SectionSelect startup = new SectionSelect(game);
				
				((Game)Gdx.app.getApplicationListener()).setScreen(startup);
				
				startup.startDialog("Welcome to SIS Adventures!\n Start by selecting a building with tapping it,\n you can also change the bulding" +
						" by swiping the screen.");
			}
		}).start(tweenManager);
//		Tween.to(splash, SpriteAccessor.ALPHA, 2).target(0).delay(2).start(tweenManager);
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {

		batch.dispose();
		splash.getTexture().dispose();
	}

}
