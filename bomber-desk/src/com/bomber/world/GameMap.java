package com.bomber.world;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.bomber.common.Directions;
import com.bomber.common.ObjectFactory;
import com.bomber.common.ObjectsPool;
import com.bomber.gameobjects.Tile;

public class GameMap {
	public ArrayList<Tile> mTiles = new ArrayList<Tile>();
	public ObjectsPool<Tile> mDestroyableTiles;
	public short mWidth;
	public short mHeight;

	public GameMap() {
		mDestroyableTiles = new ObjectsPool<Tile>((short) 20, new ObjectFactory.CreateTile());
	}

	public void update()
	{
		// TODO: Verifica os tiles que est�o destroyed se a anima��o j�
		// terminou, e se sim remove-o da pool
		
		for(Tile tmpTile : mDestroyableTiles)
		{
			if(!tmpTile.mIsDestroyed)
				continue;
			
			// Verifica se anima��o de explos�o terminou
			if(tmpTile.mLooped)
				mDestroyableTiles.releaseObject(tmpTile);
		}
	}

	/**
	 * 
	 * @param _startPos
	 *            A posi��o (x,y) onde iniciar a procura.
	 * @param _direction
	 *            A direc��o do tipo @link(Directions) onde a procura ser�
	 *            efectuada.
	 * @param _tileTypes
	 *            Os tipos @link(Tile) de tiles a procurar.
	 * @return Devolve a dist�ncia em n�mero de tiles at� ao primeiro
	 * @link(Tile) de um dos tipos passados como argumento.
	 */
	public int getDistanceToNext(Vector2 _startPos, short _direction, short _tileTypes)
	{
		return getDistanceToNext(-1, _startPos, _direction, _tileTypes);
	}

	/**
	 * 
	 * @param _maxSize
	 *            N�mero m�ximo de @link(Tile)'s a percorrer na direc��o dada.
	 *            -1 significa sem limite.
	 * @param _startPos
	 *            A posi��o (x,y) onde iniciar a procura
	 * @param _direction
	 *            A direc��o do tipo @link(Directions) onde a procura ser�
	 *            efectuada
	 * @param _tileTypes
	 *            Os tipos @link(Tile) de tiles a procurar.
	 * @return Devolve a dist�ncia em n�mero de tiles at� ao primeiro
	 * @link(Tile) de um dos tipos passados como argumento. Se nenhum
	 * @link(Tile) dos tipos pretendidos forem encontrados � devolvido -1.
	 */
	public int getDistanceToNext(int _maxSize, Vector2 _startPos, short _direction, short... _tileTypes)
	{
		boolean found = false;
		int lastIdx = -1;
		int idx = calcTileIndex(_startPos);
		for (int i = 0; i < _maxSize; i++)
		{
			// Anda um tile na direc��o pretendida
			idx = calcTileIndex(idx, _direction, (short) 1);

			// Se estamos na mesma posi��o da itera��o anterior � porque
			// atingimos uma border
			if (idx == lastIdx)
			{
				idx = -1;
				break;
			}

			// Verifica se o tile actual � do tipo de um dos tipos pretendidos
			Tile tmpTile = mTiles.get(idx);
			for (int c = 0; c < _tileTypes.length; c++)
			{
				if (tmpTile.mType == _tileTypes[c])
				{
					found = true;
					break;
				}
			}

			if (found)
				break;
		}

		return idx;
	}

	/**
	 * N�o est� implementada porque o tile mais pr�ximo vai ser sempre aquele
	 * que corresponde � posi��o passada como par�metro
	 * 
	 * @param _position
	 *            A posi��o (x,y) onde iniciar a procura.
	 * @param _direction
	 *            A direc��o do tipo @link(Directions) onde a procura ser�
	 *            efectuada.
	 * @return Devolve o @link(Tile) mais pr�ximo baseado na posi��o e direcc��o
	 *         passados como parametros. Em caso de igualdade de dist�ncia �
	 *         devolvido o tile oposto � direc��o.
	 */
	public Vector2 getNearestTilePosition(Vector2 _position, short _direction)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Tranforma uma posi��o 2D num index do array de tiles. Isto � feito
	 * baseado no tamanho do tile e na largura do mapa.
	 * 
	 * @param _position
	 *            A posi��o (x,y) a transformar.
	 * @return Posi��o no array local de tiles do @link(Tile) mais pr�ximo.
	 */
	public int calcTileIndex(Vector2 _position)
	{
		int x = (int) (_position.x / Tile.TILE_SIZE);
		int y = (int) (_position.y / Tile.TILE_SIZE);

		return y * mWidth + x;
	}

	/**
	 * Soma uma _distance ao _startIndex baseado na _direction e devolve o index
	 * respectivo.
	 * 
	 * @param _startIndex
	 *            Indice do @link(Tile) no array local onde iniciar.
	 * @param _direction
	 *            A direc��o do tipo @link(Directions) onde a _distance ser�
	 *            somada.
	 * @param _distance
	 *            A dist�ncia a somar ao _starIndex.
	 * @return Posi��o no array local de tiles do @link(Tile) calculado.
	 */
	public int calcTileIndex(int _startIndex, short _direction, short _distance)
	{
		int finalY;
		int currentY;

		int res = _startIndex;

		switch (_direction)
		{
		case Directions.DOWN:
			res += _distance * mWidth;

			if (res > mTiles.size() - 1)
				res -= _distance * mWidth;

			break;

		case Directions.UP:
			res -= _distance * mWidth;

			if (res < 0)
				res += _distance * mWidth;
			break;

		case Directions.LEFT:
			// Obtem a linha inicial
			currentY = _startIndex / mWidth;

			res -= _distance;

			// Verifica a linha ap�s o movimento
			finalY = res / mWidth;
			if (currentY != finalY)
			{
				// clamp
				res = currentY * mWidth;
			}

			break;

		case Directions.RIGHT:
			// Obtem a linha inicial
			currentY = _startIndex / mWidth;

			res -= _distance;

			// Verifica a linha ap�s o movimento
			finalY = res / mWidth;
			if (currentY != finalY)
			{
				// clamp
				res = currentY * mWidth + (mWidth - 1);
			}

		}

		// Verifica se n�o � devolvido um valor inv�lido
		if (res < 0)
			res = 0;

		if (res > mTiles.size() - 1)
			res = mTiles.size() - 1;

		return res;
	}

	/**
	 * Obt�m o @link(Tile) mais pr�ximo da posi��o providenciada.
	 * 
	 * @param _position
	 *            A posi��o (x,y) onde iniciar a procura.
	 * @return Devolve o tile mais pr�ximo da _position.
	 */
	public Tile getTile(Vector2 _position)
	{
		int idx = calcTileIndex(_position);
		return mTiles.get(idx);
	}

	/**
	 * Obt�m o @link(Tile) mais pr�ximo da posi��o providenciada, tendo em conta
	 * uma direc��o e uma dist�ncia.
	 * 
	 * @param _position
	 *            A posi��o (x,y) onde iniciar a procura.
	 * @param _direction
	 *            A direc��o do tipo @link(Directions) onde a procura ser�
	 *            efectuada.
	 * @param _distance
	 *            A dist�ncia em n�mero de @link(Tile)'s a somar ao � posi��o
	 *            inicial.
	 * @return Devolve o @link(Tile) calculado.
	 */
	public Tile getTile(Vector2 _position, short _direction, short _distance)
	{
		int idx = calcTileIndex(_position);
		idx = calcTileIndex(idx, _direction, _distance);

		return mTiles.get(idx);
	}
}