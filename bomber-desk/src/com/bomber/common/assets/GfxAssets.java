package com.bomber.common.assets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bomber.common.Directions;
import com.bomber.gameobjects.MovableObjectAnimation;
import com.bomber.gameobjects.bonus.Bonus;

/**
 * @author Filipe
 * 
 */
public class GfxAssets {
	private static final float PLAYER_WALK_FRAME_DURATION = 15f;
	private static final float PLAYER_DIE_FRAME_DURATION = 15f;
	private static final float PLAYER_SHIELD_FRAME_DURATION = 20f;
	private static final short PLAYER_DIE_FRAMES_COUNT = 8;
	private static final short PLAYER_WALK_FRAMES_COUNT = 4;

	public static final short PORTAL_FRAMES_COUNT = 2;
	public static final short PORTAL_FRAME_DURATION = 50;

	private static final float BONUS_FRAME_DURATION = 10f;
	private static final float BOMB_EXPLOSIONS_FRAME_DURATION = 10f;
	private static final float BOMB_FRAME_DURATION = 30f;

	private static final float TILE_EXPLOSION_FRAME_DURATION = 5;
	private static final short TILE_EXPLOSION_FRAME_COUNT = 6;

	private static final float N_MONSTER_WALK_FRAME_DURATION = 35f;
	private static final float N_MONSTER_DIE_WALK_FRAME_DURATION = 30f;
	private static final short N_MONSTER_DIE_FRAME_COUNT = 6;
	private static final short N_MONSTER_WALK_FRAME_COUNT = 3;

	private static final float G_MONSTER_WALK_FRAME_DURATION = 35f;
	private static final float G_MONSTER_DIE_FRAME_DURATION = 30f;
	private static final short G_MONSTER_DIE_FRAME_COUNT = 6;
	private static final short G_MONSTER_WALK_FRAME_COUNT = 3;
	
	private static final float WAITING_FRAME_DURATION = 8f;
	
	private static final String ATLAS_FILE = "atlas.txt";
	public static final String ATLAS_HD_FILE = "atlas_hd.txt";

	public static TextureAtlas mAtlas;
	public static TextureAtlas mAtlasHD;
	public static HashMap<String, MovableObjectAnimation> mMonsters;
	public static HashMap<String, MovableObjectAnimation> mPlayers;
	public static HashMap<String, TextureRegion> mPlayersHeads;
	public static HashMap<String, TextureRegion> mPlayersSad;
	public static HashMap<String, TextureRegion> mPlayersHappy;
	public static HashMap<String, Animation> mBonusAnimations;
	public static HashMap<String, TextureRegion> mBonusIcons;
	public static HashMap<String, Animation> mPlayerEffects;
	public static HashMap<String, TextureRegion> mNonDestroyableTiles;

	public static TextureRegion mControlPad;
	public static TextureRegion mButtonPause;
	public static TextureRegion mButtonBomb;
	public static TextureRegion mClockBar;
	public static TextureRegion mBonusBar;

	/**
	 * Posi��o 0 da anima��o � o tile antes de ser destruido. Da� para a frente
	 * � a destrui��o do tile.
	 */
	public static HashMap<String, Animation> mDestroyableTiles;
	public static HashMap<String, Animation> mExplosions;

	public static MovableObjectAnimation mBomb;

	public static HashMap<String, TextureRegion> mScreens = new HashMap<String, TextureRegion>();

	public static Animation mSoundButton;
	public static Animation mPortal;

	public static TextureRegion[] mTrophy = new TextureRegion[3];

	public static HashMap<String, TextureRegion> mFlags;

	public static HashMap<String, TextureRegion> mPauseButtons;

	public static BitmapFont mGenericFont;
	public static BitmapFont mNamesFont;
	public static BitmapFont mBigFont;

	public static Animation mWaitingAnimation;
	
	public static boolean mFinishedLoading = false;

	public static void loadAssets()
	{

		mFinishedLoading = false;
		
		if(null == mPlayers)
			mPlayers = new HashMap<String, MovableObjectAnimation>();
		
		if(null == mPlayerEffects)
			mPlayerEffects = new HashMap<String, Animation>();
			
		if(null == mPlayersHeads)	
			mPlayersHeads = new HashMap<String, TextureRegion>();
			
		if(null == mBonusAnimations)
			mBonusAnimations = new HashMap<String, Animation>();			
			
		if(null == mBonusIcons)	
			mBonusIcons = new HashMap<String, TextureRegion>();
			
		if(null == mExplosions)	
			mExplosions = new HashMap<String, Animation>();
			
		if(null == mMonsters)
			mMonsters = new HashMap<String, MovableObjectAnimation>();
			
		if(null == mNonDestroyableTiles)	
			mNonDestroyableTiles = new HashMap<String, TextureRegion>();
			
		if(null == mDestroyableTiles)
			mDestroyableTiles = new HashMap<String, Animation>();
			
		if(null == mPlayersSad)	
			mPlayersSad = new HashMap<String, TextureRegion>();
			
		if(null == mPlayersHappy)	
			mPlayersHappy = new HashMap<String, TextureRegion>();


		if(null == mFlags)
			mFlags = new HashMap<String, TextureRegion>();

		loadAtlas();
		loadUI();

		loadPortal();
		loadPlayerAnimations();
		loadPlayersHeads();
		loadPlayersFaceExpressions();
		loadPlayerEffects();
		loadExplosions();
		loadBonus();
		loadBomb();
		loadFlags();


		
		mFinishedLoading = true;
	}

	private static void loadAtlas()
	{
//		if(null == mAtlas)
			mAtlas = new TextureAtlas(Gdx.files.internal(ATLAS_FILE));
			mAtlasHD = new TextureAtlas(Gdx.files.internal(ATLAS_HD_FILE));
			
	}

	private static void loadFlags()
	{
		// Com o var�o
		if(null == mFlags.get("flag_pole_team1"))
			mFlags.put("flag_pole_team1", mAtlasHD.findRegion("flag_pole_team", 1));
		
		if(null == mFlags.get("flag_pole_team2"))
			mFlags.put("flag_pole_team2", mAtlasHD.findRegion("flag_pole_team", 2));

		// Transport�veis
		if(null == mFlags.get("flag_transport_team1"))
			mFlags.put("flag_transport_team1", mAtlasHD.findRegion("flag_transport_team", 1));
		
		if(null == mFlags.get("flag_transport_team2"))
			mFlags.put("flag_transport_team2", mAtlasHD.findRegion("flag_transport_team", 2));
		
		if(null == mFlags.get("flag_transport_left_team1"))
			mFlags.put("flag_transport_left_team1", mAtlasHD.findRegion("flag_transport_left_team", 1));
		
		if(null == mFlags.get("flag_transport_left_team2"))
			mFlags.put("flag_transport_left_team2", mAtlasHD.findRegion("flag_transport_left_team", 2));

	}

	private static void loadPortal()
	{
		if(null == mPortal)
			mPortal = loadAnimation(mAtlas,"portal_", PORTAL_FRAME_DURATION);
	}

	private static void loadPlayersFaceExpressions()
	{
		String[] players = { "b_white", "b_blue", "b_green", "b_red" };

		// Tristes
		List<AtlasRegion> regions = mAtlasHD.findRegions("b_sad_");
		for (int i = 0; i < regions.size(); i++)
			mPlayersSad.put(players[i], regions.get(i));

		// Contentes
		regions = mAtlasHD.findRegions("b_happy_");
		for (int i = 0; i < regions.size(); i++)
			mPlayersHappy.put(players[i], regions.get(i));
	}

	private static void loadPlayerAnimations()
	{
		MovableObjectAnimation temp;
		
		if(null == mPlayers.get("b_white"))
		{
			temp = loadPlayerMovableObjectAnimation("b_white");
			mPlayers.put("b_white", temp);
		}
		
		if(null == mPlayers.get("b_red"))
		{
			temp = loadPlayerMovableObjectAnimation("b_red");
			mPlayers.put("b_red", temp);
		}
		
		if(null == mPlayers.get("b_blue"))
		{
			temp = loadPlayerMovableObjectAnimation("b_blue");
			mPlayers.put("b_blue", temp);
		}
		
		if(null == mPlayers.get("b_green"))
		{
			temp = loadPlayerMovableObjectAnimation("b_green");
			mPlayers.put("b_green", temp);
		}
	}

	private static void loadPlayerEffects()
	{
		if(null == mPlayerEffects.get("shield"))
		{
			Animation shield = loadAnimation(mAtlas, "shield_", PLAYER_SHIELD_FRAME_DURATION);
			mPlayerEffects.put("shield", shield);
		}
		// TODO : ler water splash
	}

	/**
	 * Cria MovableObjectAnimation com Animations de Players
	 */
	private static MovableObjectAnimation loadPlayerMovableObjectAnimation(String _id)
	{

		MovableObjectAnimation movableAnimation = new MovableObjectAnimation();

		// load die animation
		movableAnimation.die = loadAnimation(mAtlas, _id + "_die_", PLAYER_DIE_FRAME_DURATION);

		// load walkup animation
		movableAnimation.walk[Directions.UP] = loadAnimation(mAtlas,_id + "_walk_up_", PLAYER_WALK_FRAME_DURATION);

		// load walkdown animation
		movableAnimation.walk[Directions.DOWN] = loadAnimation(mAtlas,_id + "_walk_down_", PLAYER_WALK_FRAME_DURATION);

		// load walkleft animation
		movableAnimation.walk[Directions.LEFT] = loadAnimation(mAtlas,_id + "_walk_left_", PLAYER_WALK_FRAME_DURATION);

		// load walkright animation
		movableAnimation.walk[Directions.RIGHT] = loadAnimation(mAtlas, _id + "_walk_right_", PLAYER_WALK_FRAME_DURATION);

		movableAnimation.numberOfFramesDying = PLAYER_DIE_FRAMES_COUNT;
		movableAnimation.numberOfFramesPerWalk = PLAYER_WALK_FRAMES_COUNT;

		return movableAnimation;
	}

	/**
	 * Carrega anima��o identificada por _id Ex : loadAnimation("b_white_die_");
	 */
	private static Animation loadAnimation(TextureAtlas _atlas, String _id, float _frameDuration)
	{
		List<AtlasRegion> regions = _atlas.findRegions(_id);

		return new Animation(_frameDuration, regions);

	}

	public static void loadNormalMonster(String _id)
	{
		MovableObjectAnimation temp = loadNormalMonsterMovableObjectAnimation(_id);
		mMonsters.put(_id, temp);
	}

	/**
	 * Cria MovableObjectAnimation com Animations de Monsters
	 */
	private static MovableObjectAnimation loadNormalMonsterMovableObjectAnimation(String _id)
	{
		MovableObjectAnimation movableAnimation = new MovableObjectAnimation();

		// load die animation
		movableAnimation.die = loadAnimation(mAtlas, _id + "_die_", N_MONSTER_DIE_WALK_FRAME_DURATION);

		// load walkup animation
		movableAnimation.walk[Directions.UP] = loadBackloopingAnimation(mAtlas,_id + "_walk_up_", N_MONSTER_WALK_FRAME_COUNT, N_MONSTER_WALK_FRAME_DURATION);

		// load walkdown animation
		movableAnimation.walk[Directions.DOWN] = loadBackloopingAnimation(mAtlas,_id + "_walk_down_", N_MONSTER_WALK_FRAME_COUNT, N_MONSTER_WALK_FRAME_DURATION);

		// load walkleft animation
		movableAnimation.walk[Directions.LEFT] = loadBackloopingAnimation(mAtlas,_id + "_walk_left_", N_MONSTER_WALK_FRAME_COUNT, N_MONSTER_WALK_FRAME_DURATION);

		// load walkright animation
		movableAnimation.walk[Directions.RIGHT] = loadBackloopingAnimation(mAtlas,_id + "_walk_right_", N_MONSTER_WALK_FRAME_COUNT, N_MONSTER_WALK_FRAME_DURATION);

		movableAnimation.numberOfFramesDying = N_MONSTER_DIE_FRAME_COUNT;
		movableAnimation.numberOfFramesPerWalk = N_MONSTER_WALK_FRAME_COUNT;

		return movableAnimation;
	}

	public static void loadGenericMonster(String _id)
	{
		MovableObjectAnimation temp = loadGenericMonsterMovableObjectAnimations(_id);
		mMonsters.put(_id, temp);
	}

	/**
	 * Cria MovableObjectAnimation de monstros gen�ricos
	 * 
	 */
	private static MovableObjectAnimation loadGenericMonsterMovableObjectAnimations(String _id)
	{

		MovableObjectAnimation movableAnimation = new MovableObjectAnimation();
		movableAnimation.die = loadAnimation(mAtlas, _id + "_die_", G_MONSTER_DIE_FRAME_DURATION);
		movableAnimation.walk[Directions.UP] = loadBackloopingAnimation(mAtlas, _id + "_walk_", G_MONSTER_WALK_FRAME_COUNT, G_MONSTER_WALK_FRAME_DURATION);
		movableAnimation.walk[Directions.DOWN] = movableAnimation.walk[Directions.UP];
		movableAnimation.walk[Directions.LEFT] = movableAnimation.walk[Directions.UP];
		movableAnimation.walk[Directions.RIGHT] = movableAnimation.walk[Directions.UP];

		movableAnimation.numberOfFramesDying = G_MONSTER_DIE_FRAME_COUNT;
		movableAnimation.numberOfFramesPerWalk = G_MONSTER_WALK_FRAME_COUNT;

		return movableAnimation;
	}

	private static void loadPlayersHeads()
	{
		TextureRegion r;
		
		if(null == mPlayersHeads.get("b_white"))
		{
			r = mAtlasHD.findRegion("face_white");
			mPlayersHeads.put("b_white", r);
		}
		
		if(null == mPlayersHeads.get("b_green"))
		{
			r = mAtlasHD.findRegion("face_green");
			mPlayersHeads.put("b_green", r);
		}

		if(null == mPlayersHeads.get("b_red"))
		{
			r = mAtlasHD.findRegion("face_red");
			mPlayersHeads.put("b_red", r);
		}

		if(null == mPlayersHeads.get("b_blue"))
		{
			r = mAtlasHD.findRegion("face_blue");
			mPlayersHeads.put("b_blue", r);
		}
	}

	private static void loadBonus()
	{
		if(null == mBonusAnimations.get("bonus_bomb"))
				mBonusAnimations.put("bonus_bomb", loadBackloopingAnimation(mAtlasHD, "bonus_bomb_", Bonus.NUMBER_OF_ANIMATION_FRAMES, BONUS_FRAME_DURATION));
		
		if(null == mBonusAnimations.get("bonus_hand"))
			mBonusAnimations.put("bonus_hand", loadBackloopingAnimation(mAtlasHD,"bonus_hand_", Bonus.NUMBER_OF_ANIMATION_FRAMES, BONUS_FRAME_DURATION));
		
		if(null == mBonusAnimations.get("bonus_life"))
			mBonusAnimations.put("bonus_life", loadBackloopingAnimation(mAtlasHD,"bonus_life_", Bonus.NUMBER_OF_ANIMATION_FRAMES, BONUS_FRAME_DURATION));
		
		if(null == mBonusAnimations.get("bonus_potion"))
			mBonusAnimations.put("bonus_potion", loadBackloopingAnimation(mAtlasHD,"bonus_potion_", Bonus.NUMBER_OF_ANIMATION_FRAMES, BONUS_FRAME_DURATION));

		if(null == mBonusAnimations.get("bonus_shield"))
			mBonusAnimations.put("bonus_shield", loadBackloopingAnimation(mAtlasHD,"bonus_shield_", Bonus.NUMBER_OF_ANIMATION_FRAMES, BONUS_FRAME_DURATION));
			
		if(null == mBonusAnimations.get("bonus_speed"))
			mBonusAnimations.put("bonus_speed", loadBackloopingAnimation(mAtlasHD,"bonus_speed_", Bonus.NUMBER_OF_ANIMATION_FRAMES, BONUS_FRAME_DURATION));
		
		if(null == mBonusAnimations.get("bonus_star"))
			mBonusAnimations.put("bonus_star", loadBackloopingAnimation(mAtlasHD,"bonus_star_", Bonus.NUMBER_OF_ANIMATION_FRAMES, BONUS_FRAME_DURATION));

		if(null == mBonusIcons.get("shield"))
			mBonusIcons.put("shield", mAtlasHD.findRegion("bonus_shield"));
		
		if(null == mBonusIcons.get("hand"))
			mBonusIcons.put("hand", mAtlasHD.findRegion("bonus_throw"));
		
		if(null == mBonusIcons.get("star"))
			mBonusIcons.put("star", mAtlasHD.findRegion("bonus_invencibility"));

	}

	private static Animation loadBackloopingAnimation(TextureAtlas _atlas,String _id, short _howManyFrames, float _frameDuration)
	{
		ArrayList<TextureRegion> regions = new ArrayList<TextureRegion>();

		for (int i = 0; i < _howManyFrames; i++)
			regions.add(_atlas.findRegion(_id, i));

		for (int i = _howManyFrames - 2; i > 0; i--)
			regions.add(_atlas.findRegion(_id, i));

		return new Animation(_frameDuration, regions);
	}

	public static void loadDestroyableTile(String _id)
	{

		if(null != mDestroyableTiles.get(_id))
			return;
		
		ArrayList<TextureRegion> regions = new ArrayList<TextureRegion>();

		// parte "tiles_2" em "tiles" e "2"
		String[] splittedId = _id.split("_");
		short tileNumber = Short.parseShort(splittedId[1]);

		TextureRegion r = mAtlas.findRegion("tiles_", tileNumber);
		regions.add(r);

		String destroyName = _id + "_destroy_";
		for (int i = 0; i < TILE_EXPLOSION_FRAME_COUNT; i++)
		{
			r = mAtlas.findRegion(destroyName, i);
			regions.add(r);
		}

		mDestroyableTiles.put(_id, new Animation(TILE_EXPLOSION_FRAME_DURATION, regions));
	}

	public static void loadNonDestroyableTile(String _id)
	{
		if(null != mNonDestroyableTiles.get(_id))
			return;
		
		String[] splitted = _id.split("_");
		int tileIndex = Integer.parseInt(splitted[1]);
		TextureRegion r = mAtlas.findRegion("tiles_", tileIndex);
		mNonDestroyableTiles.put(_id, r);
	}

	private static void loadExplosions()
	{
		if(null == mExplosions.get("xplode_center"))
			mExplosions.put("xplode_center", loadAnimation(mAtlas, "xplode_center_", BOMB_EXPLOSIONS_FRAME_DURATION));
	
		if(null == mExplosions.get("xplode_mid_hor"))
			mExplosions.put("xplode_mid_hor", loadAnimation(mAtlas, "xplode_hor_", BOMB_EXPLOSIONS_FRAME_DURATION));
		
		if(null == mExplosions.get("xplode_mid_ver"))
			mExplosions.put("xplode_mid_ver", loadAnimation(mAtlas,"xplode_vert_", BOMB_EXPLOSIONS_FRAME_DURATION));
		
		if(null == mExplosions.get("xplode_tip_down"))
			mExplosions.put("xplode_tip_down", loadAnimation(mAtlas,"xplode_tip_down_", BOMB_EXPLOSIONS_FRAME_DURATION));
		
		if(null == mExplosions.get("xplode_tip_left"))
			mExplosions.put("xplode_tip_left", loadAnimation(mAtlas,"xplode_tip_left_", BOMB_EXPLOSIONS_FRAME_DURATION));
		
		if(null == mExplosions.get("xplode_tip_right"))
			mExplosions.put("xplode_tip_right", loadAnimation(mAtlas,"xplode_tip_right_", BOMB_EXPLOSIONS_FRAME_DURATION));
		
		if(null == mExplosions.get("xplode_tip_up"))
			mExplosions.put("xplode_tip_up", loadAnimation(mAtlas, "xplode_tip_up_", BOMB_EXPLOSIONS_FRAME_DURATION));
	}
	

	private static void loadBomb()
	{

		MovableObjectAnimation movableAnimation = new MovableObjectAnimation();
		// a anima��o die � mesmo necess�ria porque bombas matam bombas
		movableAnimation.die = loadAnimation(mAtlas, "bomb_orig_", BOMB_FRAME_DURATION);
		movableAnimation.walk[Directions.UP] = loadAnimation(mAtlas, "bomb_orig_", BOMB_FRAME_DURATION);
		movableAnimation.walk[Directions.DOWN] = loadAnimation(mAtlas, "bomb_orig_", BOMB_FRAME_DURATION);
		movableAnimation.walk[Directions.LEFT] = loadAnimation(mAtlas, "bomb_orig_", BOMB_FRAME_DURATION);
		movableAnimation.walk[Directions.RIGHT] = loadAnimation(mAtlas, "bomb_orig_", BOMB_FRAME_DURATION);
		movableAnimation.numberOfFramesPerWalk = 4;
		mBomb = movableAnimation;
	}

	public static void loadBigFont()
	{
		mFinishedLoading = false;
		mBigFont = new BitmapFont(Gdx.files.internal("font_28.fnt"), false);
	}
	
	public static void loadUI()
	{

		if(null == mControlPad)
			mControlPad = mAtlasHD.findRegion("d-pad");
		
		if(null == mButtonPause)
			mButtonPause = mAtlasHD.findRegion("btn_pause");
		
		if(null == mButtonBomb)
			mButtonBomb = mAtlasHD.findRegion("btn_bomb");
		
		if(null == mClockBar)
			mClockBar = mAtlasHD.findRegion("clock_bar");
		
		if(null == mBonusBar)
			mBonusBar = mAtlasHD.findRegion("bonus_bar");
		
		if(null == mScreens.get("pause"))
			mScreens.put("pause", mAtlasHD.findRegion("pause_screen"));
		
		if(null == mScreens.get("levelcompleted"))
			mScreens.put("levelcompleted", mAtlasHD.findRegion("level_completed"));
		
		if(null == mScreens.get("gameover"))
			mScreens.put("gameover", mAtlasHD.findRegion("gameover"));
		
		if(null == mSoundButton)
			mSoundButton = new Animation(1, mAtlasHD.findRegions("sound_"));

		if(null == mTrophy[0])
			mTrophy[0] = mAtlasHD.findRegion("trophy");
		
		if(null == mTrophy[1])
			mTrophy[1] = mAtlasHD.findRegion("trophyBig");
		
		if(null == mTrophy[2])
			mTrophy[2] = mAtlasHD.findRegion("trophySmall");

		
//		if(null == mGenericFont)
			mGenericFont = new BitmapFont(Gdx.files.internal("teste_22.fnt"), false);
		
//		if(null == mNamesFont)
			mNamesFont = new BitmapFont(Gdx.files.internal("name_font.fnt"), false);
		
//		if(null == mBigFont)
//			mBigFont = new BitmapFont(Gdx.files.internal("font_28.fnt"), false);


		if(null == mWaitingAnimation)
			mWaitingAnimation = new Animation(WAITING_FRAME_DURATION, mAtlasHD.findRegions("waiting_animation_"));

	}

	public static TextureRegion getSoundButtonTexture()
	{
		if (SoundAssets.mIsSoundActive)
			return mSoundButton.getKeyFrame(1, false);
		else
			return mSoundButton.getKeyFrame(0, false);
	}

	public static void reset()
	{
		mMonsters.clear();
		mNonDestroyableTiles.clear();
		mDestroyableTiles.clear();
	}

	public static class Pixmaps {
		private static Pixmap mPixmapDarkGlass = null;
		private static Pixmap mPixmapNamePlate = null;
		private static Pixmap mPixmapGreen = null;
		private static Pixmap mPixmapRed = null;
		
		private static Texture mDarkGlass = null;
		private static Texture mGreen = null;
		private static Texture mRed = null;

		private static Texture mNamePlate = null;
		public static void dispose()
		{
			if (mPixmapDarkGlass == null)
				return;

			mPixmapDarkGlass.dispose();
			mPixmapGreen.dispose();
			mPixmapRed.dispose();
			mPixmapNamePlate.dispose();
			mDarkGlass.dispose();
			mGreen.dispose();
			mRed.dispose();

			mNamePlate.dispose();

			mPixmapDarkGlass = null;
			mPixmapGreen = null;
			mPixmapRed = null;
			mPixmapNamePlate = null;
			mDarkGlass = null;
			mGreen = null;
			mRed = null;

			mNamePlate = null;
		}

		public static Texture getDarkGlass()
		{
			if (mDarkGlass != null)
				return mDarkGlass;

			create();

			return mDarkGlass;
		}
		
		public static Texture getGreen()
		{
			if (mGreen != null)
				return mGreen;

			create();

			return mGreen;
		}
		
		public static Texture getRed()
		{
			if (mRed != null)
				return mRed;

			create();

			return mRed;
		}
		

		public static Texture getNamePlate()
		{
			if (mNamePlate != null)
				return mNamePlate;

			create();

			return mNamePlate;
		}
		
		private static void create()
		{
			//Cria background verde
			mPixmapGreen = new Pixmap(1024, 512, Pixmap.Format.RGBA4444);
			mPixmapGreen.setColor(0.14f, 0.45f, 0, 0.8f);
			mPixmapGreen.fill();
			mGreen = new Texture(mPixmapGreen);
			mGreen.draw(mPixmapGreen, 
					0, 0);
			mGreen.bind();
			
			//Cria background vermelho
			mPixmapRed = new Pixmap(1024, 512, Pixmap.Format.RGBA4444);
			mPixmapRed.setColor(0.75f, 0, 0, 0.8f);
			mPixmapRed.fill();
			mRed = new Texture(mPixmapRed);
			mRed.draw(mPixmapRed, 0, 0);
			mRed.bind();

			// Cria o vidro escuro
			mPixmapDarkGlass = new Pixmap(1024, 512, Pixmap.Format.RGBA4444);
			mPixmapDarkGlass.setColor(0, 0, 0, 0.8f);
			mPixmapDarkGlass.fill();

			mDarkGlass = new Texture(mPixmapDarkGlass);
			mDarkGlass.draw(mPixmapDarkGlass, 0, 0);
			mDarkGlass.bind();
			
			// Cria os nameplates
			mPixmapNamePlate= new Pixmap(256, 16, Pixmap.Format.RGBA4444);
			mPixmapNamePlate.setColor(0, 0, 0, 0.8f);
			mPixmapNamePlate.fill();

			mNamePlate = new Texture(mPixmapNamePlate);
			mNamePlate.draw(mPixmapNamePlate, 0, 0);
			mNamePlate.bind();
		}
	}
}