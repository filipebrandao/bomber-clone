package com.bomber.remote;

import com.bomber.Game;

public class Connection extends Thread {

	private static final short RTT_CHECK_INTERVAL = Game.TICKS_PER_SECOND * 2;
	private static final short TIMEOUT_VALUE = Game.TICKS_PER_SECOND * 5;

	/**
	 * ID que identifica este cliente perante todos os outros � atribuido pelo
	 * servidor.
	 */
	public short mLocalID = 0;
	public short mRemoteID = -1;

	// Lat�ncia em ticks
	public short mRTT = 0;
	private long mLastRTTCheckTick = 0;
	private boolean mSentPing = false;

	private MessageSocketIO mSocket;
	private MessageContainer mMessagesContainer;

	// Para os pings/pongs e desconex�es.
	private Message mMessageForInternalUse;

	public boolean mIsConnected = true;

	public Connection(MessageSocketIO _socket, MessageContainer _msgContainer) {
		mSocket = _socket;
		mMessagesContainer = _msgContainer;

		mMessageForInternalUse = new Message();
		mMessageForInternalUse.senderID = mLocalID;
		mMessageForInternalUse.messageType = MessageType.CONNECTION;
	}

	public void setLocalId(short _id)
	{
		mMessageForInternalUse.senderID = _id;
		mLocalID = _id;
	}
	
	@Override
	public String toString()
	{
		return "Remote id: " + mRemoteID + " - " + mSocket.toString();
	}

	public void sendMessage(Message _msg)
	{
		// Se estamos disconectados nem vale a pena tentar
		if (!mIsConnected)
			return;

		// Se a mensagem for enviada com sucesso sai
		if (mSocket.sendMessage(_msg))
			return;

		// Houve um erro ao enviar a mensagem
		disconnect("Erro enviar mensagem!");
	}

	public synchronized void disconnect(String _reason)
	{
		if (!mIsConnected)
			return;

		// Avisa o World atrav�s de uma mensagem
		// Para verificar se foi o server basta comparar o valShort com o
		// RemoteId do atributo mGameServer
		mMessageForInternalUse.eventType = EventType.DISCONNECT;
		mMessageForInternalUse.valShort = mRemoteID;
		mMessageForInternalUse.setStringValue(_reason);
		mMessagesContainer.add(mMessageForInternalUse);

		mIsConnected = false;
		mSocket.close();

		Game.LOGGER.log("Conexion " + mRemoteID + " has disconnected...");
	}

	public void update()
	{
		if (!mIsConnected)
			return;

		if (mSentPing)
		{
			if ((Game.mCurrentTick - mLastRTTCheckTick) > TIMEOUT_VALUE)
			{
				Game.LOGGER.log("Conexion " + mLocalID + " timed out...");
				disconnect("Timeout!");
			}
		} else
		{
			// Verifica a lat�ncia
			if (Game.mCurrentTick > (mLastRTTCheckTick + RTT_CHECK_INTERVAL))
			{
				mLastRTTCheckTick = Game.mCurrentTick;
				mMessageForInternalUse.eventType = EventType.PING;
				sendMessage(mMessageForInternalUse);
				mSentPing = true;
			}
		}
	}

	@Override
	public void run()
	{
		Message rcvedMsg;
		while (true)
		{
			rcvedMsg = mSocket.recvMessage();
			if (rcvedMsg == null || mSocket.mIsClosed)
				break;

			mRemoteID = rcvedMsg.senderID;
			addMessageToContainer(rcvedMsg);
		}

		disconnect("Erro receber mensagem!");
	}

	private void addMessageToContainer(Message _msg)
	{
		switch (_msg.eventType)
		{
		case EventType.PING:
			// Responde imediatamente e n�o adiciona a mensagem ao contentor
			mMessageForInternalUse.eventType = EventType.PONG;
			sendMessage(mMessageForInternalUse);
			break;

		case EventType.PONG:
			// Actualiza o RTT e n�o adiciona a mensagem ao contentor
			mRTT = (short) (Game.mCurrentTick - mLastRTTCheckTick);
			Game.LOGGER.log("RTT liga��o(" + mLocalID + "<->" + mRemoteID + "): " + mRTT);
			mSentPing = false;
			break;

		default:
			mMessagesContainer.add(_msg);
		}
	}
}