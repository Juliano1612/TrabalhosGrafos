/**
 * @authores Juliano Cézar Chagas Tavares & Kevin Levrone Rodrigues Machado Silva
 *              RA89740                             RA89275
 */
package trabalhografos2;

import java.util.ArrayList;

public class Grafo {

    public int matrizAdj[][];
    public int numeroVertices;
    ArrayList<Vertice> vertices = new ArrayList<>();

    public Grafo(int numeroVertices) { //classe grafo que possui os dados que são usados no trabalho
        this.numeroVertices = numeroVertices; //numero de vertices do grafo
        this.matrizAdj = new int[numeroVertices][numeroVertices]; //matriz de adj do grafo
        inicializaMatriz(); //inicializa a matriz do grafo
        for (int i = 0; i < numeroVertices; i++) {//cria os vertices do grafo
            vertices.add(new Vertice(i));
        }
    }

    public void inicializaMatriz() {//funcao que inicializa a matriz de adjacencias
        for (int i = 0; i < numeroVertices; i++) {
            for (int j = 0; j < numeroVertices; j++) {
                matrizAdj[i][j] = 0;
            }
        }
    }

    public void printMatriz(int matriz[][]) {//funcao que imprime a matriz de adjacencias
        for (int i = 0; i < numeroVertices; i++) {
            for (int j = 0; j < numeroVertices; j++) {
                System.out.print(matriz[i][j] + "\t");
            }
            System.out.println();
        }
    }

    //Heuristica do Vizinho mais Próximo
    public void tsp_nn(int idVertice) {
        ArrayList<Integer> verticesVisitados = new ArrayList();//vetor que armazena os vertices que foram visitados
        verticesVisitados.add(idVertice);//add vertice aos vertices visitados
        int v = idVertice, vAux = -1, pesoCaminho = 0;
        for (int controle = numeroVertices; controle > 1; controle--) {//de todos os vertices
            int menorCaminho = Integer.MAX_VALUE;
            for (int i = 0; i < numeroVertices; i++) { //para cada vertice
                if (matrizAdj[v][i] < menorCaminho && i != v && !verticesVisitados.contains(i)) {//se forem adjacentes e nao visitados, nem de retorno
                    menorCaminho = matrizAdj[v][i]; //adiciona ao menor caminho
                    vAux = i;
                }
            }
            pesoCaminho += matrizAdj[v][vAux];//soma o peso do menor caminho
            verticesVisitados.add(vAux);//adiciona o menor caminho aos vértices visitados
            v = vAux;
        }
        verticesVisitados.add(idVertice);//adiciona a aresta que retorna ao vertice inicial
        pesoCaminho += matrizAdj[v][idVertice];//soma o peso da aresta final
        System.out.printf("\nCiclo encontrado:");//imprime o ciclo encontrado
        for (Integer i : verticesVisitados) {
            System.out.printf(" " + i);
        }
        System.out.println("\nPeso ciclo : " + pesoCaminho);//imprime o peso do ciclo
        verticesVisitados.clear();
    }
    //FIM HEURISTICA DO VIZINHO MAIS PRÓXIMO

    //ÁRVORE GERADORA MÍNIMA
    private ArrayList<No> mst_prim(int idVertice) {//funcao que busca arvore minima
        ArrayList<No> arvoreMinima = new ArrayList(); //vetor que guarda a árvore mínima encontrada
        ArrayList<No> arvoreVisitada = new ArrayList();//vetor que guarda a arvore que ja foi encontrada
        ArrayList<Integer> destinos = new ArrayList();//vetor que guarda os destinos tomados

        int v = idVertice;
        for (int controle = 0; controle < numeroVertices - 1; controle++) {//de todos os nós
            for (int i = 0; i < numeroVertices; i++) {//de todos os nós com ligação
                if (i != v) {//se não é selfloop
                    No noPossivel = new No(matrizAdj[v][i]);//cria um nó possivel
                    noPossivel.origem = v;
                    noPossivel.destino = i;
                    if (!destinos.isEmpty()) {//se destinos nao está vazio
                        if (!destinos.contains(noPossivel.destino)) {//se nao contem o destino
                            arvoreVisitada.add(noPossivel);// adiciona o destino
                        }
                    } else {//senao
                        arvoreVisitada.add(noPossivel);//adiciona o destino
                    }
                }
            }
            No menorLigacao = new No(Integer.MAX_VALUE);
            for (No n : arvoreVisitada) {
                if (n.pesoAresta < menorLigacao.pesoAresta) {//se o peso do nó for menor que o peso do menor encontrado
                    menorLigacao = n;//troca o peso e a ligacao
                }
            }
            destinos.add(menorLigacao.origem);//adiciona a origem da menor ligacao
            int indice = arvoreVisitada.indexOf(menorLigacao);//busca o indice da menor ligacao
            arvoreMinima.add(arvoreVisitada.get(indice));//adiciona o vertice na árvore mínima
            arvoreVisitada.remove(menorLigacao);//remove o vertice que já foi adicionado

            v = menorLigacao.destino;//começa a buscar a partir do destino do anterior
        }

        return arvoreMinima;
    }

    private void pre_ordem(ArrayList<No> arvoreMinima) {//percorre a representacao de arvore retornada em pre ordem, trata as repeticoes e imprime o ciclo encontrado
        ArrayList<Integer> controleVisita = new ArrayList(); //controla a ordem da visita
        No noArvore = arvoreMinima.remove(0);//remove o primeiro nó de arvore mínima pra iniciar a busca
        controleVisita.add(noArvore.origem);//adiciona em ordem pro controle - origem
        controleVisita.add(noArvore.destino);//adiciona em ordem pro controle -  destino
        int controle = noArvore.destino, peso = noArvore.pesoAresta;
        boolean stop = false;//flag que diz se foi um break ou nao
        while (!arvoreMinima.isEmpty()) {//enquanto a arvore nao estiver vazia
            stop = false;//deixa a flag como falsa
            for (No n : arvoreMinima) {//para cada nó da arvore mínima
                if (n.origem == controle) {//se a origem é igual ao controle
                    if (controleVisita.indexOf(n.destino) == -1) { //busca se aquele nó já está nos visitados
                        controleVisita.add(n.destino);//se nao estiver adiciona
                    }
                    controle = n.destino;//copia o valor de controle para destino
                    peso+=n.pesoAresta;//adiciona o valor do peso da aresta pro peso total
                    arvoreMinima.remove(n);//remove o nó da arvore mínima
                    stop = true;//diz que houve um break
                    break;
                }
            }
            if (controleVisita.indexOf(controle) > 0 && !stop) {
                controle = controleVisita.get(controleVisita.indexOf(controle) - 1);//se passar por uma varrida sem pegar nenhum nó, controle vira o nó visitado anteriormente ao atual
            }

        }
        controleVisita.add(noArvore.origem);//adiciona a aresta que fecha o ciclo com o primeiro
        peso += matrizAdj[noArvore.origem][controleVisita.get(controleVisita.size()-2)];//adiciona o peso dessa aresta
        System.out.println("Ciclo encontrado: ");//começa a printar o ciclo
        for(Integer i : controleVisita){
            System.out.printf(" "+i);
        }
        System.out.println("Peso :" + peso);//print o peso do ciclo
    }

    public void cicloHam(int idVertice) {//funcao que busca o ciclo hamiltoniano baseado em arvore geradora mínima
        pre_ordem(mst_prim(idVertice));
    }
    
    //FIM CICLO HAMILTONIANO POR ÁRVORE MINIMO

}

class No { //tipo de dado auxiliar que ajuda na simulacao de uma arvore

    public int origem;
    public int destino;
    public int pesoAresta;

    public No(int pesoAresta) {
        this.pesoAresta = pesoAresta;
    }

}
