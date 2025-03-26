package entities;

import model.Interface.BalanceTree;

public class ArvoreAvl<T extends Comparable<T>> implements BalanceTree<T> {
	private NoAvl<T> raiz;

	public ArvoreAvl() {
	}

	public ArvoreAvl(NoAvl<T> raiz) {
		this.raiz = raiz;
	}
//------------------------------------------------------------------------------------

	@Override
	public void inserirElemento(T elemento) {
		raiz = inserirRecursivo(raiz, elemento);

	}

	@Override
	public boolean removerElemento(T elemento) {
		if (raiz == null) {
			return false;
		}
		raiz = removerRecursivo(raiz, elemento, localizarElemento(elemento));
		return true;
	}

	@Override
	public boolean localizarElemento(T elemento) {
		return localizarRecursivo(raiz, elemento);
	}

	@Override
	public int getAltura() {
		return getAltura(raiz);
	}

	@Override
	public void imprimirEmOrder() {
		imprimirEmOrderRecursivo(raiz);
	}

	// Percorre recursivamento a arvore para inserir
	private NoAvl<T> inserirRecursivo(NoAvl<T> no, T elemento) {
		if (no == null) {
			return new NoAvl<>(elemento, null, null); // Instancia o novo no
		}
		// Percorre a arvore
		if (elemento.compareTo(no.getElemento()) < 0) {
			no.setEsquerdo(inserirRecursivo(no.getEsquerdo(), elemento));
		} else if (elemento.compareTo(no.getElemento()) > 0) {
			no.setDireito(inserirRecursivo(no.getDireito(), elemento));

		} else {
			return no;
		}
		// Percorre a arvore a esquerda, direita e retorna o maior valor de altura
		no.setAltura(Math.max(getAltura(no.getEsquerdo()), getAltura(no.getDireito())) + 1);

		int balance = getBalanceamento(no);

		// Faz o balanceamento da árvore
		if (balance > 1 && elemento.compareTo(no.getEsquerdo().getElemento()) < 0) {
			return rotacionarDireita(no);
		}
		if (balance < -1 && elemento.compareTo(no.getDireito().getElemento()) > 0) {
			return rotacionarEsquerda(no);
		}
		if (balance > 1 && elemento.compareTo(no.getEsquerdo().getElemento()) > 0) {
			no.setEsquerdo(rotacionarEsquerda(no.getEsquerdo()));
			return rotacionarDireita(no);
		}
		if (balance < -1 && elemento.compareTo(no.getDireito().getElemento()) < 0) {
			no.setDireito(rotacionarDireita(no.getDireito()));
			return rotacionarEsquerda(no);
		}

		return no;
	}

	private int getAltura(NoAvl<T> no) {
		return (no == null) ? 0 : no.getAltura();
	}

	private NoAvl<T> rotacionarDireita(NoAvl<T> y) {
		NoAvl<T> x = y.getEsquerdo();// Pega o filho esquerdo de y
		NoAvl<T> z = x.getDireito();// Pega o filho direito de x

		x.setDireito(y);// Faz y virar filho direito de x
		y.setEsquerdo(z);// Faz z virar filho esquerdo de y

		y.setAltura(Math.max(getAltura(y.getEsquerdo()), getAltura(y.getDireito())) + 1);
		x.setAltura(Math.max(getAltura(x.getEsquerdo()), getAltura(x.getDireito())) + 1);

		return x;
	}

	private NoAvl<T> rotacionarEsquerda(NoAvl<T> x) {
		NoAvl<T> y = x.getDireito();// Pega o filho direito de x
		NoAvl<T> z = y.getEsquerdo();// Pega o filho esquerdo de y

		y.setEsquerdo(x);// Faz x virar filho esquerdo de y
		x.setDireito(z);// Faz z virar filho direito de x

		x.setAltura(Math.max(getAltura(x.getEsquerdo()), getAltura(x.getDireito())) + 1);
		y.setAltura(Math.max(getAltura(y.getEsquerdo()), getAltura(y.getDireito())) + 1);

		return y;
	}

	private boolean localizarRecursivo(NoAvl<T> no, T elemento) {
		if (no == null) {
			return false;
		}
		int comparacao = elemento.compareTo(no.getElemento());

		if (comparacao < 0) {
			return localizarRecursivo(no.getEsquerdo(), elemento);
		} else if (comparacao > 0) {
			return localizarRecursivo(no.getDireito(), elemento);
		}

		return elemento.equals(no.getElemento());
	}

	private void imprimirEmOrderRecursivo(NoAvl<T> no) {
		if (no != null) {
			imprimirEmOrderRecursivo(no.getEsquerdo());
			System.out.print(no.getElemento() + " ");
			imprimirEmOrderRecursivo(no.getDireito());
		}
	}

	public boolean estaBalanceada() {
		return verificarBalanceamento(raiz);
	}

	private boolean verificarBalanceamento(NoAvl<T> no) {
		if (no == null) {
			return true; // Árvore vazia ou folha sempre está balanceada
		}

		int fatorBalanceamento = getAltura(no.getEsquerdo()) - getAltura(no.getDireito());

		if (Math.abs(fatorBalanceamento) > 1) {
			return false; // Nó desbalanceado encontrado
		}

		// Verifica recursivamente as subárvores esquerda e direita
		return verificarBalanceamento(no.getEsquerdo()) && verificarBalanceamento(no.getDireito());
	}

	private NoAvl<T> removerRecursivo(NoAvl<T> no, T elemento, Boolean localizado) {
		if (no == null && localizado != true) {
			return null; // Nó não encontrado
		}

		if (elemento.compareTo(no.getElemento()) < 0) {
			no.setEsquerdo(removerRecursivo(no.getEsquerdo(), elemento, localizado));
		} else if (elemento.compareTo(no.getElemento()) > 0) {
			no.setDireito(removerRecursivo(no.getDireito(), elemento, localizado));
		} else {

			// Caso 1: Nó folha (sem filhos)
			if (no.getEsquerdo() == null && no.getDireito() == null) {
				return null;
			}

			// Caso 2: Nó com um único filho
			if (no.getEsquerdo() == null) {
				return no.getDireito();
			} else if (no.getDireito() == null) {
				return no.getEsquerdo();
			}

			// Caso 3: Nó com dois filhos - substituir pelo menor valor da subárvore direita
			T menorValor = encontrarMinimo(no.getDireito());
			no.setElemento(menorValor);
			no.setDireito(removerRecursivo(no.getDireito(), menorValor, localizado));
		}

		// Atualiza a altura do nó
		no.setAltura(1 + Math.max(getAltura(no.getEsquerdo()), getAltura(no.getDireito())));

		// Rebalanceia o nó, se necessário
		return rebalancear(no);
	}

	// Encontra no menor valor da sub arvore direita
	private T encontrarMinimo(NoAvl<T> no) {
		T minimo = no.getElemento();
		while (no.getEsquerdo() != null) {
			no = no.getEsquerdo();
			minimo = no.getElemento();
		}
		return minimo;
	}

	private NoAvl<T> rebalancear(NoAvl<T> no) {
		int balance = getBalanceamento(no);

		// Rotação à direita
		if (balance > 1 && getBalanceamento(no.getEsquerdo()) >= 0) {
			return rotacionarDireita(no);
		}

		// Rotação à esquerda
		if (balance < -1 && getBalanceamento(no.getDireito()) <= 0) {
			return rotacionarEsquerda(no);
		}

		// Rotação dupla (esquerda-direita)
		if (balance > 1 && getBalanceamento(no.getEsquerdo()) < 0) {
			no.setEsquerdo(rotacionarEsquerda(no.getEsquerdo()));
			return rotacionarDireita(no);
		}

		// Rotação dupla (direita-esquerda)
		if (balance < -1 && getBalanceamento(no.getDireito()) > 0) {
			no.setDireito(rotacionarDireita(no.getDireito()));
			return rotacionarEsquerda(no);
		}

		return no;
	}

	// Confere o fator de balanceamento de cada no
	private int getBalanceamento(NoAvl<T> no) {
		return (no == null) ? 0 : getAltura(no.getEsquerdo()) - getAltura(no.getDireito());
	}

}
