package com.bomber.common;

/**
 * Vai encapsular os objectos que existiram no ObjectsPool
 */
public abstract class PoolObject {
	public Short mIndex;
	
	/**
	 * � utilizado pela ObjectPool quando o objecto � marcado como disponivel.
	 * Este m�todo deve ser sempre chamar o seu super antes/depois de efectuar
	 * mudan�as numa classe derivada
	 * 
	 * mIsBeingDestroyed = false;
	 */
	public abstract void reset();
}