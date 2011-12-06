package com.bomber.remote;

import java.util.concurrent.ArrayBlockingQueue;

import com.bomber.common.ObjectsPool;

public class MessageContainer {
	private ObjectsPool<Message> mMessagesPool;
	/**
	 * Uma fila de mensagens recebidas. A cria��o/obten��o destas mensagens �
	 * gerida pela pool.
	 */
	private ArrayBlockingQueue<Message> mMessages;

	/**
	 * A mensagem do parametro deve ser copiada para uma nova j� existente na
	 * pool pois esta ser� reutlizada.
	 * 
	 * Se for uma mensagem do servidor do tipo SYNC, elimina todas as mensagens
	 * do mesmo tipo que j� existam.
	 */
	public synchronized void add(Message _message)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Devolve uma referencia para a proxima mensagem na fila.
	 */
	public Message getNext()
	{
		throw new UnsupportedOperationException();
	}
}