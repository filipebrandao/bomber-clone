package com.bomber.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.bomber.common.Directions;
import com.bomber.world.GameWorld;

public abstract class MovableObject extends Drawable {
	public float mSpeed = 1f;
	public short mDirection;
	public GameWorld mWorld;
	public boolean mIsMoving = false;
	public boolean mJustCollided = false;
	public MovableObjectAnimation mMovableAnimations;

	private final Vector2 mCollision = new Vector2();

	public void setMovableAnimations(MovableObjectAnimation _anim)
	{
		mMovableAnimations = _anim;
		setCurrentAnimation(mMovableAnimations.walkDown, mMovableAnimations.numberOfFramesPerWalk, false);
	}
	public void moveLeft()
	{
		mIsMoving = true;
		if (mDirection != Directions.LEFT)
		{
			mDirection = Directions.LEFT;
			setCurrentAnimation(mMovableAnimations.walkLeft, mMovableAnimations.numberOfFramesPerWalk, true);
		}

		onChangedDirection();
	}

	public final void moveRight()
	{
		mIsMoving = true;
		if (mDirection != Directions.RIGHT)
		{
			mDirection = Directions.RIGHT;
			setCurrentAnimation(mMovableAnimations.walkRight, mMovableAnimations.numberOfFramesPerWalk, true);
		}

		onChangedDirection();
	}

	public void moveUp()
	{
		mIsMoving = true;
		if (mDirection != Directions.UP)
		{
			mDirection = Directions.UP;
			setCurrentAnimation(mMovableAnimations.walkUp, mMovableAnimations.numberOfFramesPerWalk, true);
		}

		onChangedDirection();
	}

	public void moveDown()
	{
		mIsMoving = true;
		if (mDirection != Directions.DOWN)
		{
			mDirection = Directions.DOWN;
			setCurrentAnimation(mMovableAnimations.walkDown, mMovableAnimations.numberOfFramesPerWalk, true);
		}

		onChangedDirection();
	}

	protected void move(float _amount)
	{
		// Actualiza a posi��o
		if (mDirection == Directions.LEFT)
			mPosition.x -= _amount;
		else if (mDirection == Directions.RIGHT)
			mPosition.x += _amount;
		else if (mDirection == Directions.UP)
			mPosition.y += _amount;
		else if (mDirection == Directions.DOWN)
			mPosition.y -= _amount;

	}

	protected void checkTileCollisions(boolean _ignoreDestroyables)
	{
		mJustCollided = mWorld.mMap.checkIfTileCollidingWithObject(this, mCollision, _ignoreDestroyables);

		if(!mJustCollided)
			return;
		
		mPosition.x += mCollision.x;
		mPosition.y += mCollision.y;
	}

	protected void checkBombCollisions()
	{
		mJustCollided = mWorld.checkIfBombCollidingWithObject(this, mCollision);
		
		if(!mJustCollided)
			return;
		
		mPosition.x += mCollision.x;
		mPosition.y += mCollision.y;	
	}

	public void stop()
	{
		mIsMoving = false;
		stopCurrentAnimation();
		onStop();
	}

	protected abstract void onChangedDirection();

	protected abstract void onStop();
}