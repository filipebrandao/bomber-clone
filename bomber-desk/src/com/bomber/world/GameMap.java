package com.bomber.world;

import com.badlogic.gdx.math.Vector2;
import com.bomber.common.ObjectFactory;
import com.bomber.common.ObjectsPool;
import com.bomber.gameobjects.Tile;

public class GameMap {
	public ObjectsPool<Tile> mTiles;
	public short mWidth;
	public short mHeight;

	public GameMap() {
		mTiles = new ObjectsPool<Tile>((short) 50, true, new ObjectFactory.CreateTile());
	}
	
	public Tile getTile(Vector2 _position)
	{
		throw new UnsupportedOperationException();
	}

	public Tile getTile(Vector2 _position, short _direction, short _distance)
	{
		throw new UnsupportedOperationException();
	}

	public void update()
	{
		// TODO: Verifica os tiles que est�o destroyed se a anima��o j� terminou, e se 
		// sim remove-o do da pool
	}
	
	/**
	 * Um wrapper para o metodo com o mesmo nome mas que pede tambem o parametro
	 * _maxSize. � devolvida uma chamada ao segundo m�todo em que o primeiro
	 * parametro ser� -1 (significa sem limite).
	 */
	public int getDistanceToNext(Vector2 _startPos, short _direction,
			short _tileTypes)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Devolve a distancia em tiles desde a posi��o e na direc��o passadas como
	 * argumento at� um dos tipos de tile pretendidos. � devolvida a dist�ncia
	 * at� ao tile mais pr�ximo que corresponda a um dos tipos pretendidos
	 * (passados como varargs). A dist�ncia � limitada pelo parametro _maxSize,
	 * se _maxSize==-1 ent�o n�o existe um limite.
	 */
	public int getDistanceToNext(short _maxSize, Vector2 _startPos,
			short _direction, short _tileTypes)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Obtem o tile mais proximo baseado na posi��o e direcc��o passados como
	 * parametros. Em caso de igualdade de dist�ncia � devolvido o tile oposto �
	 * direc��o.
	 */
	public Vector2 getNearestTilePosition(Vector2 _position, short _direction)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Tranforma uma posi��o 2D num index do array de tiles. Isto � feito
	 * baseado no tamanho do tile e na altura/largura do mapa.
	 */
	public int calcTileIndex(Vector2 _position)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Soma uma _distance ao _startIndex baseado na _direction e devolve o index
	 * respectivo.
	 */
	public int calcTileIndex(int _startIndex, short _direction, short _distance)
	{
		throw new UnsupportedOperationException();
	}
	
	public void explodeTile(Tile _tile)
	{
		
	}
}