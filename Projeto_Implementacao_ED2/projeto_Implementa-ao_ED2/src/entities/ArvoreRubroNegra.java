package entities;

import model.Enum.Cor;
import model.Interface.BalanceTree;

public class ArvoreRubroNegra<T extends Comparable<T>> implements BalanceTree<T> {
	private NoRubroNegro<T> raiz;

	public ArvoreRubroNegra() {
		this.raiz = null;
	}

	@Override
	public void inserirElemento(T elemento) {
		raiz = inserirRecursivo(raiz, elemento);
		raiz.setCor(Cor.PRETO); // Garante que a raiz seja sempre preta
	}

	@Override
	public boolean removerElemento(T elemento) {
		if (!localizarElemento(elemento)) {
			return false;
		}
		raiz = removerRecursivo(raiz, elemento);
		if (raiz != null) {
			raiz.setCor(Cor.PRETO);
		}
		return true;
	}

	@Override
	public boolean localizarElemento(T elemento) {
		return localizarRecursivo(raiz, elemento);
	}

	@Override
	public int getAltura() {
		return contarSubArvores(raiz);
	}

	@Override
	public void imprimirEmOrder() {
		imprimirEmOrderRecursivo(raiz);
	}
	public int contarNosVermelhos() {
	    return contarNosVermelhosRecursivo(raiz);
	}
	public int contarNosPretos() {
	    return contarNosPretosRecursivo(raiz);
	}

	private NoRubroNegro<T> inserirRecursivo(NoRubroNegro<T> no, T elemento) {
		if (no == null) {
			return new NoRubroNegro<>(elemento);
		}

		if (elemento.compareTo(no.getElemento()) < 0) {
			no.setEsquerdo(inserirRecursivo(no.getEsquerdo(), elemento));
			no.getEsquerdo().setPai(no);
		} else if (elemento.compareTo(no.getElemento()) > 0) {
			no.setDireito(inserirRecursivo(no.getDireito(), elemento));
			no.getDireito().setPai(no);
		} else {
			return no;
		}

		return balancear(no);
	}

	private NoRubroNegro<T> removerRecursivo(NoRubroNegro<T> no, T elemento) {
		if (no == null) {
			return null;
		}

		if (elemento.compareTo(no.getElemento()) < 0) {
			no.setEsquerdo(removerRecursivo(no.getEsquerdo(), elemento));
		} else if (elemento.compareTo(no.getElemento()) > 0) {
			no.setDireito(removerRecursivo(no.getDireito(), elemento));
		} else {
			if (no.getEsquerdo() == null) {
				return no.getDireito();
			} else if (no.getDireito() == null) {
				return no.getEsquerdo();
			}

			NoRubroNegro<T> sucessor = encontrarMinimo(no.getDireito());
			no.setElemento(sucessor.getElemento());
			no.setDireito(removerRecursivo(no.getDireito(), sucessor.getElemento()));
		}

		return balancear(no);
	}

	private NoRubroNegro<T> balancear(NoRubroNegro<T> no) {
		if (ehVermelho(no.getDireito()) && !ehVermelho(no.getEsquerdo())) {
			no = rotacionarEsquerda(no);
		}
		if (ehVermelho(no.getEsquerdo()) && ehVermelho(no.getEsquerdo().getEsquerdo())) {
			no = rotacionarDireita(no);
		}
		if (ehVermelho(no.getEsquerdo()) && ehVermelho(no.getDireito())) {
			inverterCores(no);
		}

		return no;
	}

	private NoRubroNegro<T> rotacionarEsquerda(NoRubroNegro<T> no) {
		NoRubroNegro<T> novaRaiz = no.getDireito();
		no.setDireito(novaRaiz.getEsquerdo());
		novaRaiz.setEsquerdo(no);
		novaRaiz.setCor(no.getCor());
		no.setCor(Cor.VERMELHO);
		return novaRaiz;
	}

	private NoRubroNegro<T> rotacionarDireita(NoRubroNegro<T> no) {
		NoRubroNegro<T> novaRaiz = no.getEsquerdo();
		no.setEsquerdo(novaRaiz.getDireito());
		novaRaiz.setDireito(no);
		novaRaiz.setCor(no.getCor());
		no.setCor(Cor.VERMELHO);
		return novaRaiz;
	}

	private void inverterCores(NoRubroNegro<T> no) {
		no.setCor(Cor.VERMELHO);
		no.getEsquerdo().setCor(Cor.PRETO);
		no.getDireito().setCor(Cor.PRETO);
	}

	private boolean ehVermelho(NoRubroNegro<T> no) {
		return no != null && no.getCor() == Cor.VERMELHO;
	}

	private boolean localizarRecursivo(NoRubroNegro<T> no, T elemento) {
		if (no == null) {
			return false;
		}

		int comparacao = elemento.compareTo(no.getElemento());

		if (comparacao < 0) {
			return localizarRecursivo(no.getEsquerdo(), elemento);
		} else if (comparacao > 0) {
			return localizarRecursivo(no.getDireito(), elemento);
		}

		return true;
	}

	private void imprimirEmOrderRecursivo(NoRubroNegro<T> no) {
		if (no != null) {
			imprimirEmOrderRecursivo(no.getEsquerdo());
			System.out.print(no.getElemento() + " ");
			imprimirEmOrderRecursivo(no.getDireito());
		}
	}

	  private int contarSubArvores(NoRubroNegro<T> no) {
	        if (no == null) {
	            return 0;
	        }
	        return 1 + contarSubArvores(no.getEsquerdo()) + contarSubArvores(no.getDireito());
	  }

	private NoRubroNegro<T> encontrarMinimo(NoRubroNegro<T> no) {
		while (no.getEsquerdo() != null) {
			no = no.getEsquerdo();
		}
		return no;
	}

	private int contarNosPretosRecursivo(NoRubroNegro<T> no) {
	    if (no == null) {
	        return 0;
	    }
	    int count = (no.getCor() == Cor.PRETO) ? 1 : 0;
	    return count + contarNosPretosRecursivo(no.getEsquerdo()) + contarNosPretosRecursivo(no.getDireito());
	}


	private int contarNosVermelhosRecursivo(NoRubroNegro<T> no) {
	    if (no == null) {
	        return 0;
	    }
	    int count = (no.getCor() == Cor.VERMELHO) ? 1 : 0;
	    return count + contarNosVermelhosRecursivo(no.getEsquerdo()) + contarNosVermelhosRecursivo(no.getDireito());
	}

}
