package com.bomber.world;

public class Clock {
	/**
	 * Valor atribuido aquando do start em milisegundos (obtido do sistema)
	 */
	private long mStartTime;
	/**
	 * Valor em milisegundos ao qual vai ser feito o countdown
	 */
	private int mCountdownValue;
	private String mLastTimeString;
	private int mLastTimeMilis;

	public void reset(int _countdownValue)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Actualiza o mStartTime.
	 */
	public void start()
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Actualiza o mCountdown subtraindo-lhe o tempo que j� decorreu.
	 */
	public void pause()
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Devolve o tempo restante.
	 * 
	 * Usar stringbuffer para construir a string...
	 * 
	 * Como vai ser chamada v�rias vezes por segundo, e a granularidade � de 1
	 * segundo, para evitar a cria��o constante de strings com o mesmo valor
	 * verificamos se j� passou mais um segundo. (usar o mLastTimeMilis)
	 */
	public String toString()
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Devolve o tempo restante.
	 * 
	 * Usar stringbuffer para construir a string...
	 * 
	 * Como vai ser chamada v�rias vezes por segundo, e a granularidade � de 1
	 * segundo, para evitar a cria��o constante de strings com o mesmo valor
	 * verificamos se j� passou mais um segundo. (usar o mLastTimeMilis)
	 */
	public boolean reachedZero()
	{
		throw new UnsupportedOperationException();
	}
}