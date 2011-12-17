package com.bomber.remote;

import java.io.IOException;
import java.util.ArrayList;

import com.bomber.remote.tcp.TCPLocalServer;

public class RemoteConnections {
	private Connection mGameServer = null;

	private ArrayList<Connection> mPlayers;
	private LocalServer mLocalServer = null;

	private boolean mAcceptingConnections = false;
	private short mMaxConnectionsToAccept = 0;
	public MessageContainer mRecvMessages = null;

	public RemoteConnections() {
		mRecvMessages = new MessageContainer();
		mPlayers = new ArrayList<Connection>();
	}

	public void update()
	{
		if (mAcceptingConnections)
		{
			mLocalServer.getCachedConnections(mPlayers, (short) (mMaxConnectionsToAccept - mPlayers.size()));
			mAcceptingConnections = mPlayers.size() < mMaxConnectionsToAccept;

			if (!mAcceptingConnections)
			{
				mLocalServer.stopReceiving();
				mLocalServer = null;
			}
		}

		// Actualiza as liga��es j� existentes
		for (int i = 0; i < mPlayers.size(); i++)
			mPlayers.get(i).update();
	}

	/**
	 * Cria um server local que vai aceitar conex�es remotas.
	 * 
	 * @param _protocol
	 *            O protocolo a usar um dos {@link Protocols}.
	 * @param _port
	 *            O porto onde ficar � escuta. Null se n�o se aplicar.
	 * @param _maxConnections
	 *            O n�mero m�ximo de conex�es a aceitar.
	 * @throws IOException
	 */
	public void acceptConnections(short _protocol, short _port, short _maxConnections) throws IOException
	{
		mMaxConnectionsToAccept = _maxConnections;

		switch (_protocol)
		{
		case Protocols.TCP:
			mLocalServer = new TCPLocalServer(mRecvMessages, _port);
		}

		if (null != mLocalServer)
		{
			mAcceptingConnections = true;
			mLocalServer.start();
		}
	}

	/**
	 * Chama a outra fun��o broadcast com o parametro _include server a true.
	 */
	public void broadcast(Message _msg, boolean _includeServer)
	{
		Connection tmpConn;
		for (int i = 0; i < mPlayers.size(); i++)
		{
			tmpConn = mPlayers.get(i);

			if (!tmpConn.mIsConnected)
			{
				// Remove a conex�o da lista de conex�es activas
				mPlayers.remove(i);
				i--;
				continue;
			}

			tmpConn.sendMessage(_msg);
		}

		if (_includeServer && mGameServer != null)
			sendToServer(_msg);
	}

	public void sendToServer(Message _msg)
	{
		if (mGameServer != null && mGameServer.mIsConnected)
			mGameServer.sendMessage(_msg);
	}
}