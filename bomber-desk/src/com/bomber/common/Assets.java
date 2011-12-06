package com.bomber.common;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bomber.gameobjects.MovableObjectAnimation;

public class Assets {
	private Texture mAtlas;
	private HashMap<String, MovableObjectAnimation> mMonsters;
	private HashMap<String, MovableObjectAnimation> mPlayers;
	private HashMap<String, TextureRegion> mPlayersHeads;
	private HashMap<String, Animation> mBonus;
	private HashMap<String, TextureRegion> mNonDestroyableTiles;
	/**
	 * Posi��o 0 da anima��o � o tile antes de ser destruido. Da� para a frente
	 * � a destrui��o do tile.
	 */
	private HashMap<String, Animation> mDestroyableTiles;
	private Animation mBomb;
	private TextureRegion mMainScreen;
	private Animation mSoundButton;
	private HashMap<String, TextureRegion> mPauseButtons;
	private TextureRegion mControllerBar;
	/**
	 * Usado no pause.
	 */
	private TextureRegion mDarkGlass;
	/**
	 * Usado no pause.
	 */
	private TextureRegion mPauseScreen;
	private BitmapFont mFont;
}