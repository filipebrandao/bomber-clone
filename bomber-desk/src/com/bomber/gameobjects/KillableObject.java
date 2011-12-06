package com.bomber.gameobjects;

public abstract class KillableObject extends MovableObject {
	private boolean mIsDead = false;

	/**
	 * mIsDead = true; mCurrentAnimation = mAnimations.die;
	 */
	public void kill()
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * � utilizado pela ObjectPool quando o objecto � marcado como disponivel.
	 * Este m�todo deve ser sempre chamar o seu super antes/depois de efectuar
	 * mudan�as numa classe derivada
	 * 
	 * mIsBeingDestroyed = false;
	 */
	public void reset()
	{
		throw new UnsupportedOperationException();
	}

	protected abstract void onKill();
}