package com.bomber.remote;

import java.util.LinkedList;
import java.util.List;

import com.bomber.Game;

public abstract class LocalServer extends Thread {

	// Onde as novas conex�es v�o ser guardadas at� a thread principal as vir
	// buscar. Este m�todo � preferivel porque assim o m�todo sincronizado est�
	// nesta classe, que deixar� de ser usado quando se para de receber novas
	// liga��es. A alternativa seria criar m�todos sincronizados para adicionar
	// e listar as liga��es existentes no RemoteConnections.mConnections. O
	// m�todo para listar seria usado ao longo de toda a execu��o que por ser do
	// tipo synchronized teria um maior impacto na performance.
	private LinkedList<Connection> mConnectionsCache;
	private boolean mKeepReceiving = true;
	private MessageContainer mMessageContainer;

	private short mMax = 0;
	
	public boolean mAllConnected = false;
	
	public LocalServer(MessageContainer _msgContainer, short _max) {
		mConnectionsCache = new LinkedList<Connection>();
		mMessageContainer = _msgContainer;
		
		mMax = _max;
	}

	/**
	 * M�todo a ser chamado quando n�o se pretender que sejam recebidas novas
	 * liga��es.
	 */
	public void stopReceiving()
	{
		mKeepReceiving = false;
		onStopReceiving();
	}

	@Override
	public void run()
	{
		short nAdded = 0;
		while (mKeepReceiving && nAdded++ < mMax)
		{
			waitForConnection();
		}
		
		mAllConnected = true;
		mKeepReceiving = false;
		
		Game.LOGGER.log("Server desligado...");
	}

	/**
	 * Insere as novas liga��es recebidas e guardadas em cache no Map passado
	 * como parametro
	 * 
	 * @param _container
	 *            O atributo {@link RemoteConnections.mPlayers}
	 */
	public synchronized void getCachedConnections(List<Connection> _container)
	{
		Connection tmpConnection;

		while (!mConnectionsCache.isEmpty())
		{
			tmpConnection = mConnectionsCache.remove();
			_container.add(tmpConnection.mLocalID, tmpConnection);
		}
	}

	/**
	 * Chamado pelas classes derivadas para adicionar uma nova liga��o recebida
	 * � cache de liga��es � espera de serem obtidas pela thread principal.
	 * 
	 * @param _newSocket
	 *            A nova conex�o a ser adicionada � cache.
	 */
	protected synchronized void cacheConnection(MessageSocketIO _newSocket)
	{
		Connection tmpConn = new Connection(_newSocket, mMessageContainer);
		tmpConn.setDaemon(true);
		tmpConn.start();
		mConnectionsCache.add(tmpConn);
	}

	/**
	 * O m�todo a implementar nas classes derivadas. Este m�todo deve aceitar
	 * uma nova liga��o, adicion�-la � cache e sair. Ser� invocado novamente
	 * para aceitar nova conex�o.
	 */
	public abstract void waitForConnection();
	
	public abstract void onStopReceiving();
}
