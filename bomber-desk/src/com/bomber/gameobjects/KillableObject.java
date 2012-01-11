package com.bomber.gameobjects;


public abstract class KillableObject extends WorldMovableObject {


	public boolean mIsDead = false;

	final public void kill(short _killerColor)
	{
		if(mIsDead)
			return;
		
		boolean ignoreKill = onKill(_killerColor);
		
		if( ignoreKill )
			return;
		

		mIsDead = true;
		stop();
		
		if (mMovableAnimations != null)
			setCurrentAnimation(mMovableAnimations.die, mMovableAnimations.numberOfFramesDying, true, false);
	}

	@Override
	protected void onStop()
	{
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

	/**
	 * 
	 * @return Deve devolver se � suposto ignorar a morte ou n�o.
	 */
	protected abstract boolean onKill(short _killerId);
}