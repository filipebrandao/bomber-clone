package com.bomber.world;

public class LevelInfo {
	public String mCurrentLevelName;
	public String mNextLevelName;

	public short mMinutes;
	public short mSeconds;
	public short mNumberBonus;
	public short mBonusSeed;

	/**
	 * @param _levelInfo
	 *            Posi��o 0: Nome do nivelseguinte
	 *            Posi��o 1: Minutos 
	 *            Posi��o 2: Segundos
	 *            Posi��o 3: N� de b�nus a spawnar
	 *            Posi��o 4: Seed a usar no spawn dos b�nus
	 */
	public void set(String[] _levelInfo)
	{
		mNextLevelName = _levelInfo[0];
		mMinutes = Short.valueOf(_levelInfo[1]);
		mSeconds = Short.valueOf(_levelInfo[2]);
		mNumberBonus = Short.valueOf(_levelInfo[3]);
		mBonusSeed = Short.valueOf(_levelInfo[4]);
	}
}
