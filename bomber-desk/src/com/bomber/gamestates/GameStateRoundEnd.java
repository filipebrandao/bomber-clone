package com.bomber.gamestates;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.bomber.Game;
import com.bomber.Team;
import com.bomber.common.BlinkObject;
import com.bomber.common.assets.Assets;
import com.bomber.gameobjects.Player;
import com.bomber.remote.RemoteConnections;

public abstract class GameStateRoundEnd extends GameStateLoadingPVP {

	private final short TROPHYS_POS_X = 250;
	private final short TROPHYS_POS_Y = 260;
	private final short TROPHYS_DIST_X = 120;
	private final short TROPHYS_DIST_Y = 190;

	private final short TROPHYS_BLINK_INTERVAL = 25;
	private final short TROPHYS_BLINK_DURATION = 400;

	List<Vector2> mWonTrophys = new ArrayList<Vector2>();
	List<BlinkObject> mBlinkTrophys = new ArrayList<BlinkObject>();

	protected Team mTeam1 = Game.mTeams[0];
	protected Team mTeam2 = Game.mTeams[1];

	// 0 : Empate
	// 1 : Equipa 1 ganhou
	// 2 : Equipa 2 ganhou
	protected short mResult = -1;

	public GameStateRoundEnd(Game _game) {
		super(_game);

		mTeam1 = Game.mTeams[0];
		mTeam2 = Game.mTeams[1];

		updateResults();

		if (mGame.mRoundsPlayed++ == mGame.mRoundsToPlay)
		{
			Game.mGameIsOver = true;
			onAllRoundsPlayed();
		} else if (RemoteConnections.mIsGameServer)
		{
			mGame.updateRandomSeed((int) System.currentTimeMillis());
			Game.mRemoteConnections.mGameServer.resetCountdown((short) 3);
		}

	}

	protected abstract void onAllRoundsPlayed();
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if (mBlinkTrophys.get(0).mExceededDuration && Game.mGameIsOver)
			if (Gdx.input.justTouched())
				Game.goBackToActivities();

		for (int i = 0; i < mBlinkTrophys.size(); i++)
			mBlinkTrophys.get(i).update();
	}

	private void updateResults()
	{
		short curX = TROPHYS_POS_X;
		short curY = TROPHYS_POS_Y;

		short team1OldRoundWon = mTeam1.mRoundsWon;
		short team2OldRoundWon = mTeam2.mRoundsWon;

		// Trof�us j� ganhos a desenhar
		for (short i = 0; i < team1OldRoundWon; i++)
		{
			mWonTrophys.add(new Vector2(curX, curY));
			curX += TROPHYS_DIST_X;
		}

		curX = TROPHYS_POS_X;
		curY = TROPHYS_POS_Y - TROPHYS_DIST_Y;
		for (short i = 0; i < team2OldRoundWon; i++)
		{
			mWonTrophys.add(new Vector2(curX, curY));
			curX += TROPHYS_DIST_X;
		}

		onUpdateResults();

		if (mTeam1.mRoundsWon > team1OldRoundWon)
			mBlinkTrophys.add(new BlinkObject(TROPHYS_BLINK_INTERVAL, TROPHYS_BLINK_DURATION, Assets.mTrophy[2], TROPHYS_POS_X + team1OldRoundWon * TROPHYS_DIST_X, TROPHYS_POS_Y));
		if (mTeam2.mRoundsWon > team2OldRoundWon)
			mBlinkTrophys.add(new BlinkObject(TROPHYS_BLINK_INTERVAL, TROPHYS_BLINK_DURATION, Assets.mTrophy[2], TROPHYS_POS_X + team2OldRoundWon * TROPHYS_DIST_X, TROPHYS_POS_Y - TROPHYS_DIST_Y));

		// Resultados finais
		if (mTeam1.mRoundsWon > mTeam2.mRoundsWon)
			mResult = 1;
		else if (mTeam2.mRoundsWon > mTeam1.mRoundsWon)
			mResult = 2;
		else
			mResult = 3;
	}

	public void onPresent(float _interpolation)
	{
		mBatcher.setProjectionMatrix(mUICamera.combined);
		BitmapFont font = Assets.mFont;

		if (!Game.mGameIsOver || !mBlinkTrophys.get(0).mExceededDuration)
			drawRoundResults();

		else
			drawFinalResults();

		if (mCountdownSeconds != -1)
			font.draw(mBatcher, "Round " + mGame.mRoundsPlayed + " incia em " + mCountdownSeconds.toString(), 275, 20);
	}

	private void drawRoundResults()
	{
		// Desenha as cabe�as
		drawTeams();

		for (int i = 0; i < mWonTrophys.size(); i++)
		{
			Vector2 tmpPos = mWonTrophys.get(i);
			mBatcher.draw(Assets.mTrophy[2], tmpPos.x, tmpPos.y);
		}

		for (int i = 0; i < mBlinkTrophys.size(); i++)
			mBlinkTrophys.get(i).draw(mBatcher);
	}

	private void drawTeams()
	{
		Player player;
		if (Game.mNumberPlayers > 2)
		{
			player = mTeam1.mPlayers.get(0);
			mBatcher.draw(player.mExtraTextures.get("head"), 50, 350);

			player = mTeam1.mPlayers.get(1);
			mBatcher.draw(player.mExtraTextures.get("head"), 50, 290);

			player = mTeam2.mPlayers.get(0);
			mBatcher.draw(player.mExtraTextures.get("head"), 50, 150);

			player = mTeam2.mPlayers.get(1);
			mBatcher.draw(player.mExtraTextures.get("head"), 50, 90);

		} else
		{
			player = mTeam1.mPlayers.get(0);
			mBatcher.draw(player.mExtraTextures.get("head"), 50, 265, 0f, 0f, 56f, 56f, 2f, 2f, 0f);

			player = mTeam2.mPlayers.get(0);
			mBatcher.draw(player.mExtraTextures.get("head"), 50, 80, 0f, 0f, 56f, 56f, 2f, 2f, 0f);
		}

	}

	private void drawFinalResults()
	{
		// Trof�u grande centrado no ecr�
		mBatcher.draw(Assets.mTrophy[1], 363, 160);

		short startX = 250;
		short startY = 100;
		// Players da equipa 1
		TextureRegion texture;
		for (int i = 0; i < mTeam1.mPlayers.size(); i++)
		{
			texture = mResult == 0 || mResult == 1 ? mTeam1.mPlayers.get(i).mExtraTextures.get("happy") : mTeam1.mPlayers.get(i).mExtraTextures.get("sad");
			mBatcher.draw(texture, startX, startY);
			startX -= 80;
		}

		startX = 500;
		// Players da equipa 2
		for (int i = 0; i < mTeam2.mPlayers.size(); i++)
		{
			texture = mResult == 0 || mResult == 2 ? mTeam2.mPlayers.get(i).mExtraTextures.get("happy") : mTeam2.mPlayers.get(i).mExtraTextures.get("sad");
			mBatcher.draw(texture, startX, startY);
			startX += 80;
		}

		if (mResult == 0 || (mResult == 1 && mTeam1.mPlayers.contains(mGameWorld.getLocalPlayer())) || (mResult == 2 && mTeam2.mPlayers.contains(mGameWorld.getLocalPlayer())))
			Assets.mFont.draw(mBatcher, "GANHASTE!!", 320, 60);
		else
			Assets.mFont.draw(mBatcher, "PERDESTE..", 320, 60);
	}

	@Override
	protected void onFinish()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void onUpdateFinishing()
	{
		// TODO Auto-generated method stub

	}

	protected abstract void onUpdateResults();
}