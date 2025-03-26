package entities;

import model.Enum.Cor;

public class NoRubroNegro<T extends Comparable<T>> {
	private T elemento;
	private NoRubroNegro<T> direito, esquerdo, pai;
	private Cor cor;

	public NoRubroNegro(T elemento) {
		this.elemento = elemento;
		this.cor = Cor.VERMELHO;
	}

	public T getElemento() {
		return elemento;
	}

	public void setElemento(T elemento) {
		this.elemento = elemento;
	}

	public NoRubroNegro<T> getDireito() {
		return direito;
	}

	public void setDireito(NoRubroNegro<T> direito) {
		this.direito = direito;
	}

	public NoRubroNegro<T> getEsquerdo() {
		return esquerdo;
	}

	public void setEsquerdo(NoRubroNegro<T> esquerdo) {
		this.esquerdo = esquerdo;
	}

	public NoRubroNegro<T> getPai() {
		return pai;
	}

	public void setPai(NoRubroNegro<T> pai) {
		this.pai = pai;
	}

	public Cor getCor() {
		return cor;
	}

	public void setCor(Cor cor) {
		this.cor = cor;
	}
}
