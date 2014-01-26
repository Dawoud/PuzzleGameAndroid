package com.sis.uta.puzzleGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class puzzleAudio {

	private puzzleAudio() {}
	
	public static Music song = Gdx.audio.newMusic(Gdx.files.internal("data/determination.mp3"));
	
	public static void playMusic() {
		song.setLooping(true);
		song.setVolume(0.5f);
		song.play();
	}	
	
	public static void stopMusic() {
		song.stop();
		
	}
	
	public static void dispose() {
		song.dispose();
		
	}
	
	public static void pause() {
		song.pause();
		
	}

}


//package com.sis.uta.puzzleGame;
//
//import com.badlogic.gdx.ApplicationListener;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.audio.Music;
//
//public class puzzleAudio implements ApplicationListener {
//
//	Music music;
//	
//	@Override
//	public void create() {
//		music = Gdx.audio.newMusic(Gdx.files.internal("data/bah.mp3"));
//		music.setLooping(true);
//		music.setVolume(0.5f);
//		music.play();
//		
//	}
//
//	@Override
//	public void resize(int width, int height) {
//		
//	}
//
//	@Override
//	public void render() {
//		
//	}
//
//	@Override
//	public void pause() {
//		
//	}
//
//	@Override
//	public void resume() {
//		
//	}
//
//	@Override
//	public void dispose() {
//		music.dispose();
//		
//	}
//
//}
