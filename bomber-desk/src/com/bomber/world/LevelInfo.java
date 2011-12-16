package com.bomber.world;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class LevelInfo {
	public String mCurrentLevelName;
	public String mNextLevelName;

	public short mMinutes;
	public short mSeconds;
	public short mNumberBonus;
	public short mBonusSeed;
	public int mHighScore;
	/**
	 * @param _levelInfo
	 *            Posi��o 0: Nome do nivelseguinte
	 *            Posi��o 1: Minutos 
	 *            Posi��o 2: Segundos
	 *            Posi��o 3: N� de b�nus a spawnar
	 *            Posi��o 4: Seed a usar no spawn dos b�nus
	 *            Posi��o 5: Pontua��o M�xima actual
	 */
	public void set(String[] _levelInfo)
	{
		mNextLevelName = _levelInfo[0];
		mMinutes = Short.valueOf(_levelInfo[1]);
		mSeconds = Short.valueOf(_levelInfo[2]);
		mNumberBonus = Short.valueOf(_levelInfo[3]);
		mBonusSeed = Short.valueOf(_levelInfo[4]);
		mHighScore = Short.valueOf(_levelInfo[5]);
	}
	
	public void writeToFile()
	{
		//FileHandle fh = Gdx.files.internal("levels/" + mCurrentLevelName + "/info.txt");
		FileHandle fh = Gdx.files.external("./assets/levels/" + mCurrentLevelName + "/info.txt");
		
		BufferedWriter bw = new BufferedWriter(fh.writer(false));
		
		try {
			bw.write("#nome do pr�ximo n�vel");
			bw.write(mNextLevelName);
			bw.write("#minutos");
			bw.write(mMinutes);
			bw.write("#segundos");
			bw.write(mSeconds);
			bw.write("#n� de b�nus");
			bw.write(mNumberBonus);
			bw.write("#seed para os b�nus");
			bw.write(mBonusSeed);
			bw.write("#highscore");			
			bw.write(mHighScore);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}
