package com.sis.uta.puzzleGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PuzzlePreferences {
	
	private static final String PREF_MUSIC="music";
	private static final String PREF_SOUND="sound";
	private static final String PREF_LANGUAGE="language";
	private static final String PREFS_NAME="puzzle";
	
	public PuzzlePreferences()
	{
	}
	
	protected Preferences getPrefs()
	{
		return Gdx.app.getPreferences(PREFS_NAME);
	}
	
	public float getMusic()
	{
		return getPrefs().getFloat(PREF_MUSIC, 0.5f);
	}
	
	public void setMusic(float music)
	{
		getPrefs().putFloat(PREF_MUSIC, music);
		getPrefs().flush();
	}
	
	public float getSound()
	{
		return getPrefs().getFloat(PREF_SOUND, 0.5f);
	}
	
	public void setSound(float sound)
	{
		getPrefs().putFloat(PREF_SOUND, sound);
		getPrefs().flush();
	}
	
	public String getLanguage()
	{
		return getPrefs().getString(PREF_LANGUAGE);
	}
	
	public void setLanguage(String language)
	{
		getPrefs().putString(PREF_LANGUAGE, language);
		getPrefs().flush();
	}

}
