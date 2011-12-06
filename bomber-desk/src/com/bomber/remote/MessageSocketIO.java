package com.bomber.remote;

import java.nio.ByteBuffer;

public abstract class MessageSocketIO {
	/**
	 * O ByteBuffer que est� ligado ao mRecvBytes
	 */
	public ByteBuffer mRecvByteBuffer;
	/**
	 * O ByteBuffer que est� ligado ao mSendBytes
	 */
	public ByteBuffer mSendByteBuffer;
	/**
	 * Os bytes que v�o ser recebidos
	 */
	public byte[] mRecvBytes;
	/**
	 * Os bytes que v�o ser enviados
	 */
	public byte[] mSendBytes;
	/**
	 * � usada para as mensagens recebidas, evita ter que criar um objecto novo
	 * de cada vez que � recebida uma nova mensagem.
	 */
	public Message mMessage;

	public abstract void sendMessage(Message _message);

	public abstract Message recvMessage();
}