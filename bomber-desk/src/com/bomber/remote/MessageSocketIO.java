package com.bomber.remote;

import java.nio.ByteBuffer;

public abstract class MessageSocketIO {
	
	// O n�mero de bytes que ser� enviado por mensagem
	public static final short MESSAGE_SIZE = 64;
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
	public byte[] mRecvBytes = new byte[MESSAGE_SIZE];
	/**
	 * Os bytes que v�o ser enviados
	 */
	public byte[] mSendBytes = new byte[MESSAGE_SIZE];
	/**
	 * � usada para as mensagens recebidas, evita ter que criar um objecto novo
	 * de cada vez que � recebida uma nova mensagem.
	 */
	public Message mMessage;

	public abstract void sendMessage(Message _message);

	public abstract Message recvMessage();
}