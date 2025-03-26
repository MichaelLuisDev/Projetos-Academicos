package entities;

public class NoAvl<T extends Comparable<T>> {
	private T elemento;
	private NoAvl<T> direita, esquerda;
	private Integer altura;

	public NoAvl() {
	}

	public NoAvl(T elemento, NoAvl<T> direita, NoAvl<T> esquerda) {
		this.elemento = elemento;
		this.direita = null;
		this.esquerda = null;
		this.altura = 0;
	}

//---------------------------------------------------------------------------
	public T getElemento() {
		return elemento;
	}

	public void setElemento(T elemento) {
		this.elemento = elemento;
	}

	public NoAvl<T> getDireito() {
		return direita;
	}

	public void setDireito(NoAvl<T> direita) {
		this.direita = direita;
	}

	public NoAvl<T> getEsquerdo() {
		return esquerda;
	}

	public void setEsquerdo(NoAvl<T> esquerda) {
		this.esquerda = esquerda;
	}

	public Integer getAltura() {
		return altura;
	}

	public void setAltura(Integer altura) {
		this.altura = altura;
	}

}
