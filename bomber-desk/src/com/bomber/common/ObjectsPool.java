package com.bomber.common;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Se a op��o de atribuir o hashcode estiver activa ent�o o mUUID ser� criado
 * pela classe ObjectPool aquando da cria��o do novo objecto.
 * 
 * D� muito jeito poder iterar uma colec��o por isso existir� apenas um iterador
 * 
 * Implementa Iterable<T>
 */
public class ObjectsPool<T> {
	private Stack<PoolObject> mFreeObjects;
	private ArrayList<T> mUsedObjects;
	private Stack<Short> mFreePositions;
	public Factory<T> mFactory;
	public ObjectsPoolIterator<T> mObjectsIterator;

	public ObjectsPool(short _initialQuantity, boolean _autoCreateHashs,
			Factory<T> _factory) {
		throw new UnsupportedOperationException();
	}

	/**
	 * coloca o objecto devolvido(obtido do topo da stack mFreeObjects) na
	 * posi��o do topo da mFreePositions.
	 */
	public T getFreeObject()
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Passa o objecto do array de ocupados para a stack de livres. Os objectos
	 * n�o s�o retirados do array de usados, a sua posi��o � apenas colocada a
	 * null e esse indice � adicionado � stack de posi��es livres.
	 */
	public void releaseObject(PoolObject obj)
	{
		throw new UnsupportedOperationException();
	}
}