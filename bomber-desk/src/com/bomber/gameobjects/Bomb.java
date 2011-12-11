package com.bomber.gameobjects;

import com.bomber.common.Assets;
import com.bomber.common.ObjectsPool;
import com.bomber.common.Utils;
import com.bomber.world.GameMap;
import com.bomber.world.GameWorld;

/**
 * A bomba quando rebenta chama o metodo respectivo no GameWorld, que faz todas
 * as verifica��es e s� depois cria a explos�o.
 */
public class Bomb extends KillableObject {

	public short mBombPower;
	public Tile mContainer = null;

	private int mTicksSinceDrop = 0;
	private static final int mTicksToExplode = 300; // 100/sec = 3secs;

	public Bomb(GameWorld _world) {
		mWorld = _world;
		mUUID = Utils.getNextUUID();
		mIgnoreDestroyables = false;
	}

	@Override
	public void update()
	{
		super.update();

		if (mTicksSinceDrop++ >= mTicksToExplode || mIsDead)
		{
			if (mIsMoving)
			{
				mContainer = mWorld.mMap.getTile(mPosition);
				mContainer.mContainsBomb = true;
			}

			mWorld.spawnExplosion(this);
		}

		if (mIsMoving)
		{
			// Verifica as colis�es
			GameMap map = mWorld.mMap;
			int currentTileIdx = map.calcTileIndex(mPosition);
			int forbiddenTileIdx = map.calcTileIndex(currentTileIdx, mDirection, (short) 1);

			// Contra monstros
			if (checkCollisionsAgainstMovableObjects(mWorld.mMonsters, forbiddenTileIdx))
				return;

			// Contra outros players
			if (checkCollisionsAgainstMovableObjects(mWorld.mPlayers, forbiddenTileIdx))
				return;
		}
	}

	private <T extends MovableObject> boolean checkCollisionsAgainstMovableObjects(ObjectsPool<T> pool, int _forbiddenTileIdx)
	{
		for (MovableObject m : pool)
		{
			if (m.mIgnoreDestroyables)
				continue;

			int objTileIdx = mWorld.mMap.calcTileIndex(m.mPosition);
			if (objTileIdx == _forbiddenTileIdx)
			{
				Tile tmpTile;
				if (m.mDirection == mDirection && m.mIsMoving)
				{
					// Centra a bomba no tile em que o objecto com o qual
					// colidimos est� actualmente
					tmpTile = mWorld.mMap.getTile(m.mPosition);
					mPosition.set(tmpTile.mPosition.x + Tile.TILE_SIZE_HALF, tmpTile.mPosition.y + Tile.TILE_SIZE_HALF);
				} else
				{
					// Centra a bomba no tile em que est� actualmente
					tmpTile = mWorld.mMap.getTile(mPosition);
					mPosition.set(tmpTile.mPosition.x + Tile.TILE_SIZE_HALF, tmpTile.mPosition.y + Tile.TILE_SIZE_HALF);
				}

				stop();
				return true;
			}
		}

		return false;
	}

	@Override
	public void reset()
	{
		super.reset();
		setMovableAnimations(Assets.mBomb);
		mPlayAnimation = true;
		mContainer = null;
		mIsDead = false;
		mTicksSinceDrop = 0;
		mSpeed = 0;
	}

	@Override
	protected void onChangedDirection()
	{
		mContainer.mContainsBomb = false;
		mSpeed = 3.5f;
	}

	@Override
	protected void onStop()
	{
		mSpeed = 0.0f;
		mContainer = mWorld.mMap.getTile(mPosition);
		mContainer.mContainsBomb = true;

	}

	@Override
	protected void onKill()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean onMapCollision(short _collisionType)
	{
		stop();
		return true;
	}
}