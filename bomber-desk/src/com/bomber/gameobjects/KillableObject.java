package com.bomber.gameobjects;

import com.bomber.common.Directions;

public abstract class KillableObject extends MovableObject {


	public boolean mIsDead = false;

	final public void kill()
	{

		
		if(mIsDead)
			return;
		
		boolean ignoreKill = onKill();
		
		if( ignoreKill )
			return;
		
		mIsDead = true;
		mDirection = Directions.NONE;
		
		if (mMovableAnimations != null)
			setCurrentAnimation(mMovableAnimations.die, mMovableAnimations.numberOfFramesDying, true, false);
		
	}

	@Override
	protected void onStop()
	{
		// TODO Auto-generated method stub
		
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

	protected abstract boolean onKill();
}