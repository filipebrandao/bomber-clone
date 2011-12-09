package com.bomber.world;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.bomber.common.Directions;
import com.bomber.common.ObjectFactory;
import com.bomber.common.ObjectsPool;
import com.bomber.gameobjects.MovableObject;
import com.bomber.gameobjects.Tile;

/**
 * Os Tiles v�o ser lidos do nivel por camadas, imut�veis(mImutableTiles)e
 * destroyables(mDestroyableTiles).
 * 
 * Os tiles que s�o apresentados s�o os que est�o na lista mTilesMap e que �
 * inicializado aquando do nivel pela ordem mImutableTiles > mDestroyableTiles.
 * 
 * Quando um Tile � destru�do � actualizado a sua posi��o no atributo mTilesMap
 * com o Tile walkable respectivo.
 * 
 * @author sPeC!
 * 
 */
public class GameMap {
	private ObjectsPool<Tile> mImutableTiles;
	private ObjectsPool<Tile> mDestroyableTiles;
	private ObjectsPool<Tile> mTilesBeingDestroyed;

	public ArrayList<Tile> mTilesMap = new ArrayList<Tile>();

	public short mWidth, mHeight;

	public GameMap() {
		mImutableTiles = new ObjectsPool<Tile>((short) 20, new ObjectFactory.CreateTile(Tile.WALKABLE));
		mDestroyableTiles = new ObjectsPool<Tile>((short) 20, new ObjectFactory.CreateTile(Tile.DESTROYABLE));
		mTilesBeingDestroyed = new ObjectsPool<Tile>((short) 0, null);

	}

	/**
	 * Adiciona um novo tile Destroyable ao mapa.
	 * 
	 * @param _line
	 *            Posi��o vertical no mapa.
	 * @param _col
	 *            Posi��o horizontal no mapa.
	 * @param _type
	 *            Tipo de tile a adicionar.
	 * @param _anim
	 *            Inclui o frame do tile normal e a sua destrui��o.
	 */
	public void addDestroyableTile(short _line, short _col, short _type, Animation _anim)
	{
		Tile tmpTile = mDestroyableTiles.getFreeObject();

		tmpTile.mType = _type;
		tmpTile.setCurrentAnimation(_anim, (short) 8, false);
		tmpTile.mPositionInArray = (mHeight-(_line+1))*mWidth  + _col;
	
		tmpTile.mPosition.set(_col * Tile.TILE_SIZE, _line *Tile.TILE_SIZE);
	}

	/**
	 * Adiciona um novo tile NonDestroyable ao mapa.
	 * 
	 * @param _line
	 *            Posi��o vertical no mapa.
	 * @param _col
	 *            Posi��o horizontal no mapa.
	 * @param _type
	 *            Tipo de tile a adicionar.
	 * @param _texture
	 *            TextureRegion da tile.
	 */
	public void addNonDestroyableTile(short _line,short _col, short _type, TextureRegion _texture)
	{
		Tile tmpTile = mImutableTiles.getFreeObject();
		tmpTile.mType = _type;
		tmpTile.mCurrentFrame = _texture;
		tmpTile.mLoopAnimation = false;
		tmpTile.mPositionInArray =(mHeight-(_line+1)) *mWidth+ _col;
		
		tmpTile.mPosition.set(_col * Tile.TILE_SIZE , _line *Tile.TILE_SIZE );
		
		if(tmpTile.mPositionInArray > mWidth*mHeight  )
			throw new InvalidParameterException();
	}
	
	/**
	 * A ser chamado de cada vez que � inicializado um novo n�vel.
	 * 
	 * @param _width
	 *            A largura em tiles do novo mapa.
	 */
	public void reset(short _width, short _height)
	{
		mWidth = _width;
		mHeight = _height;

		mImutableTiles.clear();
		mDestroyableTiles.clear();
	}

	/**
	 * Sempre que forem adicionados tiles este m�todo deve ser usado para que o
	 * array que vai ser apresentado seja actualizado.
	 */
	public void updateTilesForPresentation()
	{
		mTilesMap.clear();
		
		for (int i = 0; i < mWidth*mHeight; i++)
			mTilesMap.add(null);
		
		for (Tile tl : mImutableTiles)
			mTilesMap.set(tl.mPositionInArray, tl);

		for (Tile tl : mDestroyableTiles)
			mTilesMap.set(tl.mPositionInArray, tl);
	}

	public void explodeTile(Tile _tile)
	{
		if (_tile.mType != Tile.DESTROYABLE)
			return;

		_tile.explode();

		// Actualiza as pools
		mDestroyableTiles.releaseObject(_tile);
		mTilesBeingDestroyed.addObject(_tile);

		// O mapa vai apresentar a partir de agora o tile walkable que estava
		// por baixo deste
		for (Tile tl : mImutableTiles)
		{
			if (tl.mPositionInArray == _tile.mPositionInArray)
			{
				mTilesMap.set(tl.mPositionInArray, tl);
				break;
			}
		}
	}

	/**
	 * 
	 * @param _obj
	 *            O objecto a verificar se est� a colidir com um tile do tipo
	 *            collidable.
	 * @return O valor de overlap;
	 */
	public boolean checkIfTileCollidingWithObject(MovableObject _obj, Vector2 _results, boolean _ignoreDestroyables)
	{
		final Rectangle bbTile = new Rectangle(0, 0, Tile.TILE_SIZE, Tile.TILE_SIZE);

		_results.x = 0;
		_results.y = 0;
		//
		// Verifica apenas os 4 tiles � volta
		int testIdx;
		int startIdx = calcTileIndex(_obj.mPosition);

		Tile tmpTile;
		Rectangle bbObj = _obj.getBoundingBox();

		switch (_obj.mDirection)
		{
		case Directions.UP:
			testIdx = calcTileIndex(startIdx, Directions.UP, (short) 1);
			if (testIdx != startIdx)
			{
				tmpTile = mTilesMap.get(testIdx);

				if ((tmpTile.mType == Tile.DESTROYABLE && !_ignoreDestroyables)||tmpTile.mType == Tile.COLLIDABLE)
				{
					bbTile.x = tmpTile.mPosition.x;
					bbTile.y = tmpTile.mPosition.y;
					if (bbObj.overlaps(bbTile))
						_results.y =  bbTile.y -(bbObj.y + Tile.TILE_SIZE);
						
				}
			}
			break;
		case Directions.DOWN:
			testIdx = calcTileIndex(startIdx, Directions.DOWN, (short) 1);
			if (testIdx != startIdx)
			{
				tmpTile = mTilesMap.get(testIdx);

				if ((tmpTile.mType == Tile.DESTROYABLE && !_ignoreDestroyables)||tmpTile.mType == Tile.COLLIDABLE)
				{
					bbTile.x = tmpTile.mPosition.x;
					bbTile.y = tmpTile.mPosition.y;
					if (bbObj.overlaps(bbTile))
						_results.y = (bbTile.y + Tile.TILE_SIZE) - bbObj.y;
				}
			}
			break;

		case Directions.LEFT:
			testIdx = calcTileIndex(startIdx, Directions.LEFT, (short) 1);
			if (testIdx != startIdx)
			{
				tmpTile = mTilesMap.get(testIdx);

				if ((tmpTile.mType == Tile.DESTROYABLE && !_ignoreDestroyables)||tmpTile.mType == Tile.COLLIDABLE)
				{
					bbTile.x = tmpTile.mPosition.x;
					bbTile.y = tmpTile.mPosition.y;
					if (bbObj.overlaps(bbTile))
						_results.x = (bbTile.x + Tile.TILE_SIZE) - bbObj.x;
				}
			}
			break;

		case Directions.RIGHT:
			testIdx = calcTileIndex(startIdx, Directions.RIGHT, (short) 1);
			if (testIdx != startIdx)
			{
				tmpTile = mTilesMap.get(testIdx);

				if ((tmpTile.mType == Tile.DESTROYABLE && !_ignoreDestroyables)||tmpTile.mType == Tile.COLLIDABLE)
				{
					bbTile.x = tmpTile.mPosition.x;
					bbTile.y = tmpTile.mPosition.y;
					if (bbObj.overlaps(bbTile))
						_results.x = bbTile.x - (bbObj.x + Tile.TILE_SIZE);
				}
			}
			break;
		}

		return !((_results.x == 0) && (_results.y == 0));
	}

	public void update()
	{
		// Verifica se algum dos tiles que foram destruidos j� terminaram a
		// anima��o
		for (Tile tmpTile : mTilesBeingDestroyed)
		{
			tmpTile.update();

			if (tmpTile.mLooped)
				mTilesBeingDestroyed.releaseObject(tmpTile);
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
			Tile tmpTile = mTilesMap.get(idx);
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
		int col = (int) (_position.x / Tile.TILE_SIZE);
		int line = (int) (_position.y / Tile.TILE_SIZE);

		int res = (mHeight-(line+1)) *mWidth+ col;
		
		if( res > mTilesMap.size())
			throw new InvalidParameterException();
		
		return res;
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

			if (res > mTilesMap.size() - 1)
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
			// Obtem a coluna inicial
			currentY = _startIndex / mWidth;

			res += _distance;

			// Verifica a linha ap�s o movimento
			finalY = res / mWidth;
			if (currentY != finalY)
			{
				// clamp
				res = currentY * mWidth + (mWidth - 1);
			}

		}

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
		return mTilesMap.get(idx);
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

		return mTilesMap.get(idx);
	}
	
	
	/**
	 * Obt�m o @link(Tile) mais pr�ximo da posi��o providenciada, tendo em conta
	 * uma direc��o e uma dist�ncia.
	 * 
	 * @param _starIdx
	 *            A posi��o no array onde iniciar a procura.
	 * @param _direction
	 *            A direc��o do tipo @link(Directions) onde a procura ser�
	 *            efectuada.
	 * @param _distance
	 *            A dist�ncia em n�mero de @link(Tile)'s a somar ao � posi��o
	 *            inicial.
	 * @return Devolve o @link(Tile) calculado.
	 */
	public Tile getTile(int _starIdx, short _direction, short _distance)
	{
		int idx = calcTileIndex(_starIdx, _direction, _distance);

		return mTilesMap.get(idx);
	}
}