package com.bomber.remote.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.bomber.remote.LocalServer;
import com.bomber.remote.MessageContainer;

public class TCPLocalServer extends LocalServer {


	ServerSocket mSocket = null;
	Socket mReceivedSocket = null;

	/**
	 * Cria um socket TCP no porto indicado.
	 * 
	 * @param _msgContainer
	 * @param _port
	 * @throws IOException
	 */
	public TCPLocalServer(MessageContainer _msgContainer, int _port, short _max) throws IOException {
		super(_msgContainer, _max);
		mSocket = new ServerSocket(_port);
	}


	public int getLocalPort()
	{
		return mSocket.getLocalPort();
	}
	
	@Override
	public void waitForConnection()
	{
		try
		{
			mReceivedSocket = mSocket.accept();
			cacheConnection(new TCPMessageSocketIO(mReceivedSocket));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onStopReceiving()
	{
		if (mSocket != null)
		{
			try
			{
				mSocket.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
