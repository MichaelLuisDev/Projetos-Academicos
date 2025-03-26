package entities;

public class ChaveHash<T extends Comparable<T>> {
	private T elemento;

	public ChaveHash() {
	}

	public ChaveHash(T elemento) {
		this.elemento = elemento;
	}
//====================================================================================

	public T getElemento() {
		return elemento;
	}

	public void setElemento(T elemento) {
		this.elemento = elemento;
	}

}
