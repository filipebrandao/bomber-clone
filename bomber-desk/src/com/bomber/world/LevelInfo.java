package com.bomber.world;

public class LevelInfo {
	public String mCurrentLevelName;
	public String mNextLevelName;

	public short mMinutes;
	public short mSeconds;

	/**
	 * @param _levelInfo
	 *            Posi��o 0: Nome do nivelseguinte
	 *            Posi��o 1: Minutos 
	 *            Posi��o 2: Segundos
	 */
	public void set(String[] _levelInfo)
	{
		mNextLevelName = _levelInfo[0];
		mMinutes = Short.valueOf(_levelInfo[1]);
		mSeconds = Short.valueOf(_levelInfo[2]);
	}
}
