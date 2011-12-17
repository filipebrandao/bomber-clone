package com.bomber.remote;

import java.util.LinkedList;

import com.bomber.common.ObjectFactory;
import com.bomber.common.ObjectsPool;

public class MessageContainer {
	private ObjectsPool<Message> mMessagesPool;
	private LinkedList<Message> mMessages;

	public MessageContainer() {
		mMessages = new LinkedList<Message>();
		mMessagesPool = new ObjectsPool<Message>((short) 10, new ObjectFactory.CreateMessage());
	}

	/**
	 * Adiciona uma nova mensagem � fila de mensagens � espera de serem
	 * tratadas. A mensagem � copiada para uma pertencente � pool de mensagens
	 * locais e por isso pode ser reutilizada.
	 * 
	 * @param _message
	 *            A mensagem a adicionar
	 */
	public synchronized void add(Message _message)
	{
		Message tmpMessage;

		switch (_message.eventType)
		{
		case EventType.SYNC:

			// Um SYNC do servidor, podemos eliminar todas as mensagens do mesmo
			// tipo referentes ao mesmo objecto
			for (int i = 0; i < mMessages.size(); i++)
			{
				tmpMessage = mMessages.get(i);

				if (tmpMessage.UUID != _message.UUID)
					continue;

				if (tmpMessage.messageType == _message.messageType)
				{
					mMessages.remove(i);
					i--;
				}
			}

			// Os SYNC's t�m prioridade sobre todos os outros tipos excepto os
			// DISCONNECTS por isso adiciona ao inicio da fila
			tmpMessage = mMessagesPool.getFreeObject();
			_message.cloneTo(tmpMessage);
			mMessages.addFirst(tmpMessage);
			break;

		case EventType.DISCONNECT:
			tmpMessage = mMessagesPool.getFreeObject();
			_message.cloneTo(tmpMessage);
			mMessages.addFirst(tmpMessage);
			break;

		default:
			// Adiciona a nova mensagem ao final da fila
			tmpMessage = mMessagesPool.getFreeObject();
			_message.cloneTo(tmpMessage);
			mMessages.add(tmpMessage);
		}
	}

	/**
	 * Deve ser chamado assim que uma mensagem obtida atrav�s do m�todo
	 * {@link #getNext()} tiver sido tratada para que o objecto seja devolvido �
	 * pool.
	 * 
	 * @param _msg
	 *            A mensagem a libertar
	 */
	public void setParsed(Message _msg)
	{
		mMessagesPool.releaseObject(_msg);
	}

	/**
	 * Devolve uma referencia para a proxima mensagem na fila. Ap�s o tratamento
	 * da mensagem, esta deve ser libertada usando o m�todo.
	 * {@link #setParsed(Message)}
	 */
	public Message getNext()
	{
		return mMessages.remove();
	}
	
	public boolean hasNext()
	{
		return !mMessages.isEmpty();
	}
}