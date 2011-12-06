package com.bomber.gameobjects;

public abstract class KillableObject extends MovableObject {
	protected boolean mIsDead = false;
	
	@Override
	public void update()
	{
		super.update();
		// TODO Auto-generated method stub
		
	}



	/**
	 *  
	 */
	public void kill()
	{
		mIsDead = true;
		
		onKill();
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