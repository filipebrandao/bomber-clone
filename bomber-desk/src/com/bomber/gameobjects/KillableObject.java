package com.bomber.gameobjects;

import com.bomber.common.Directions;

public abstract class KillableObject extends MovableObject {
	protected boolean mIsDead = false;

	public void kill()
	{
		mIsDead = true;
		mDirection = Directions.NONE;
		setCurrentAnimation(mAnimations.die, mAnimations.numberOfFramesDying, true);
		onKill();
	}

	/**
	 * � utilizado pela ObjectPool quando o objecto � marcado como disponivel.
	 * Este m�todo deve ser sempre chamar o seu super antes/depois de efectuar
	 * mudan�as numa classe derivada
	 */
	public void reset()
	{
		mIsDead = false;
	}

	protected abstract void onKill();
}