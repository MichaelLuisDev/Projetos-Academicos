package model.Interface;

public interface BalanceTree<T extends Comparable<T>>{
	
	void inserirElemento(T elemento);
	boolean removerElemento(T elemento);
	boolean localizarElemento(T elemento);
	int getAltura();
	void imprimirEmOrder();
	
}
