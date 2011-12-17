package com.bomber.remote;

import java.nio.ByteBuffer;

public abstract class MessageSocketIO {

	// O n�mero de bytes que ser� enviado por mensagem
	public static final short MESSAGE_SIZE = 64;
	/**
	 * O ByteBuffer que est� ligado ao mRecvBytes
	 */
	private ByteBuffer mRecvByteBuffer;
	/**
	 * O ByteBuffer que est� ligado ao mSendBytes
	 */
	private ByteBuffer mSendByteBuffer;
	/**
	 * Os bytes que v�o ser recebidos
	 */
	protected byte[] mRecvBytes = new byte[MESSAGE_SIZE];
	/**
	 * Os bytes que v�o ser enviados
	 */
	protected byte[] mSendBytes = new byte[MESSAGE_SIZE];
	/**
	 * � usada para as mensagens recebidas, evita ter que criar um objecto novo
	 * de cada vez que � recebida uma nova mensagem.
	 */
	private Message mReceivedMessage;
	private MessageContainer mMessageContainer;

	public Message mMessageToSend;

	public MessageSocketIO(MessageContainer _container) {
		mRecvByteBuffer = ByteBuffer.wrap(mRecvBytes);
		mSendByteBuffer = ByteBuffer.wrap(mSendBytes);
		mMessageContainer = _container;

		mReceivedMessage = new Message();
		mMessageToSend = new Message();
	}

	public boolean sendMessage()
	{
		mMessageToSend.fillBuffer(mSendByteBuffer);
		return onSendMessage();
	}

	/**
	 * Deve ser chamada de cada vez que o array {@link mRecvBytes} �
	 * actualizado.
	 */
	protected void onNewMessageReceived()
	{
		mReceivedMessage.parse(mRecvByteBuffer);
		mMessageContainer.add(mReceivedMessage);
	}

	/**
	 * Deve implementar o envio do array {@link #mSendBytes}.
	 */
	public abstract boolean onSendMessage();

	/**
	 * Deve implementar o recebimento de mensagens. O que � recebido � um array
	 * de bytes, esse array deve ser o {@link mRecvBytes}. De seguida deve ser
	 * actualizada a {@link mReceivedMessage} passando-lhe o
	 * {@link mRecvByteBuffer}. De seguida a {@link mReceivedMessage} deve ser
	 * adicionada ao {@link mMessageContainer} que cont�m as mensagens � espera
	 * de serem tratadas .
	 */
	public abstract boolean recvMessage();
}