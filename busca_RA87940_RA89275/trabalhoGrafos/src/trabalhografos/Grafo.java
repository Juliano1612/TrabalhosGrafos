/**
 * @author Juliano Cézar Chagas Tavares & Kevin Levrone Rodrigues Machado Silva
 */
package trabalhografos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Grafo {

    public int matrizAdj[][];
    public int numeroVertices;
    ArrayList<Vertice> vertices = new ArrayList<>(); //Array de vértices do grafo

    public Grafo(int numeroVertices) {//construtor do grafo
        this.numeroVertices = numeroVertices;
        this.matrizAdj = new int[numeroVertices][numeroVertices];
        inicializaMatriz(); //funcao que inicializa a matriz de adjacências 
        for (int i = 0; i < numeroVertices; i++) {
            vertices.add(new Vertice(i));
        }
    }

    public void inicializaMatriz() { //inicializa a matriz de adjacências do tamanho do grafo com o valor 0
        for (int i = 0; i < numeroVertices; i++) {
            for (int j = 0; j < numeroVertices; j++) {
                matrizAdj[i][j] = 0;
            }
        }
    }

    public void printMatriz(int matriz[][]) { //imprime a matriz de adjacências
        for (int i = 0; i < numeroVertices; i++) {
            for (int j = 0; j < numeroVertices; j++) {
                System.out.print(matriz[i][j] + "\t");
            }
            System.out.println();
        }
    }

    int tempo;// controle do tempo de descoberta dos vértices

// BUSCA EM PROFUNDIDADE (INICIO)
    int nivel;//controle do nível de cada vértice visitado

    public void dfs(int idVertice) {
        for (Vertice v : vertices) {//inicializa todos os vértices com a cor branca e o pai null
            v.cor = Cor.BRANCO;
            v.pai = null;
        }
        tempo = 0; //inicializa o controle do tempo de descoberta com 0
        //essa primeira parte serve para iniciar a visita pelo vértice escolhido
        if (vertices.get(idVertice).cor == Cor.BRANCO) { //se o vértice tem a cor branca, então visita o vértice
            nivel = 0;
            vertices.get(idVertice).nivel = nivel;
            dfs_visit(vertices.get(idVertice)); //chama função dfs que visita o vértice
        }
        //segunda parte que continua a visita nos demais vértices não alcançados
        for (Vertice v : vertices) { //
            if (v.cor == Cor.BRANCO) {
                nivel = 0;
                v.nivel = nivel;
                dfs_visit(v);//chama dfs_visit
            }
        }
    }

    private void dfs_visit(Vertice v) {//função que visita o vértice e descobre seus adjacentes
        tempo = tempo + 1; //incrementa o valor do tempo
        v.cor = Cor.CINZA; //inicializa com a cor cinza
        v.descoberta = tempo; //tempo de descoberta do vértice = variavel de controle do tempo
        int i = v.id;
        for (int j = 0; j < numeroVertices; j++) {//busca os vértices adjacentes do vertice visitado
            if (this.matrizAdj[i][j] != 0) {//se há aresta entre os vértices
                Vertice u = vertices.get(j);
                if (u.cor == Cor.BRANCO) {// se o vertice ainda não foi visitado
                    nivel = nivel + 1;//incrementa o nível
                    u.nivel = nivel;//atualiza o nível do vértice
                    u.pai = v;//coloca o vertice atual como pai do próximo vértice
                    dfs_visit(u); //visita o vértice adjacente
                }
            }
        }
        v.cor = Cor.PRETO;//depois de visitar todos os adjacêntes, atualiza a cor de v para preto
        tempo = tempo + 1; //incrementa o tempo
        v.finalizado = tempo; //atribui o tempo de finalização
    }
// BUSCA EM PROFUNDIDADE (FIM)    

//BUSCA EM LARGURA (INICIO)
    List<Vertice> filaVertices = new LinkedList<Vertice>();

    public void bfs(int idVertice) {
        nivel = (int) Double.NEGATIVE_INFINITY;
        for (Vertice v : vertices) { //inicializa todos os vertices
            v.descoberta = (int) Double.POSITIVE_INFINITY;
            v.pai = null;
            v.cor = Cor.BRANCO;
            v.nivel = nivel;
        }
        vertices.get(idVertice).descoberta = 0; //inicializa o vertice escolhido pra iniciar
        vertices.get(idVertice).pai = null;
        vertices.get(idVertice).cor = Cor.CINZA;
        vertices.get(idVertice).nivel = 0;

        filaVertices.add(vertices.get(idVertice)); //insere o vertice escolhido pra iniciar

        int i;
        while (filaVertices.isEmpty() == false) {//enquanto a fila nao estiver vazia
            Vertice u = filaVertices.remove(0); //remove o primeiro vértice para iniciar a busca
            i = u.id;
            for (int j = 0; j < this.numeroVertices; j++) { //busca as ligações do vértice
                if (this.matrizAdj[i][j] != 0) {//se houver ligação entre os vértices
                    Vertice v = vertices.get(j);
                    if (v.cor == Cor.BRANCO) {//se o vertice adjacente ainda não foi visitado
                        v.descoberta = u.descoberta + 1; //inicializa o vértice e insere na fila
                        v.pai = u;
                        v.nivel = v.pai.nivel + 1;
                        v.cor = Cor.CINZA;//após a descoberta do vértice, colore com cinza
                        filaVertices.add(v);
                    }
                }
            }
            u.cor = Cor.PRETO; //após visitar todos os adjacentes, colore o vértice com preto
        }
    }
// BUSCA EM LARGURA (FIM)

// COMPONENTES FORTEMENTE CONECTADOS (INICIO)
    public void strongly_connected_components(int idVertice) {
        dfs(idVertice); //realiza uma busca em profundidade no vértice a partir do vertice inicial dado 
        int matrizAdjT[][] = new int[numeroVertices][numeroVertices]; //cria uma matriz para ser transposta da matriz de adj
        monta_transposta(matrizAdjT);//monta a matriz transposta
        dfs_GT();//chama busca em profundidade do grafo transposto
        for (Vertice v : verticesGT) { //imprime os vértices com seus respectivos componentes
            System.out.println("Vertice " + v.id + "\n\tComponente: " + v.idComponente);
        }
        verticesGT.clear();//limpa o vetor de vértices
    }

    private void monta_transposta(int matrizAdjT[][]) {//função que monta a matriz transposta da matriz dada
        for (int i = 0; i < numeroVertices; i++) {
            for (int j = 0; j < numeroVertices; j++) {
                matrizAdjT[j][i] = matrizAdj[i][j];
            }
        }
    }

    ArrayList<Vertice> verticesGT = new ArrayList<>(); //vetor de vértices do grafo transposto
    int componente; //variável de controle de componente

    private void dfs_GT() {
        for (int i = 0; i < numeroVertices; i++) { //copia vertices para lista de transpostos
            verticesGT.add(vertices.get(i));
        }
        for (Vertice v : verticesGT) {//inicializa os vertices transpostos com branco
            v.cor = Cor.BRANCO;
            v.pai = null;
        }
        tempo = 0;//inicializa a variável de tempo com 0
        Collections.sort(verticesGT, new Comparator() { //ordena verticesGT por ordem de finalização
            @Override
            public int compare(Object o1, Object o2) {
                Vertice v1 = (Vertice) o1;
                Vertice v2 = (Vertice) o2;
                return v1.finalizado < v2.finalizado ? -1 : (v1.finalizado > v2.finalizado ? +1 : 0);
            }
        });
        componente = 0;//inicializa o controle de componente com zero
        for (Vertice v : verticesGT) {//busca os vértices adjacentes
            if (v.cor == Cor.BRANCO) {//se a cor do adjacente for branco, inicializa ele e visita
                componente = componente + 1;
                v.idComponente = componente;
                dfs_visit_GT(v);
            }
        }
    }

    private void dfs_visit_GT(Vertice v) {//dfs visit para grafo transposto
        tempo = tempo + 1;//incrementa controle de tempo
        v.cor = Cor.CINZA; //inicializa o vértice com cinza
        v.descoberta = tempo; //inicializa o tempo de descoberta do vértice
        int i = v.id;
        for (int j = 0; j < numeroVertices; j++) {//para a lista de adjacências do vértice
            if (this.matrizAdj[i][j] != 0) {//se existe ligação entre os vértices
                Vertice u = vertices.get(j);
                if (u.cor == Cor.BRANCO) {//se a cor do adjacente for branco
                    u.idComponente = componente;//visita o vértice
                    u.pai = v;
                    dfs_visit_GT(u);
                }
            }
        }
        v.cor = Cor.PRETO;//colore o vértice com preto
        tempo = tempo + 1;//incrementa variável de controle do tempo
        v.finalizado = tempo;//atribui valor ao tempo de finalização
    }
// COMPONENTES FORTEMENTE CONECTADOS (FIM)

// CAMINHOS MINIMOS ENTRE DOIS VERTICES (INICIO)
    private void relax(Vertice u, Vertice v) {//função de relaxamento de aresta
        if (v.descoberta > u.descoberta + matrizAdj[u.id][v.id]) {
            v.descoberta = u.descoberta + matrizAdj[u.id][v.id];
            v.pai = u;
        }
    }

    private void initialize_single_source(int idVertice) {//função de incialização
        for (Vertice v : vertices) {
            v.descoberta = 9999999;
            v.pai = null;
        }
        vertices.get(idVertice).descoberta = 0;
    }

    private boolean Bellman_ford(int idVertice) {//algoritmo de bellman-ford
        initialize_single_source(idVertice);//inicializa cada vértice
        for (int i = 0; i < numeroVertices - 1; i++) { //executa |G.V|-1 vezes o relaxamento das arestas 
            for (int j = 0; j < numeroVertices; j++) {
                for (int k = 0; k < numeroVertices; k++) {
                    if (matrizAdj[j][k] != 0) {
                        relax(vertices.get(j), vertices.get(k));
                    }
                }
            }
        }
        for (int i = 0; i < numeroVertices - 1; i++) { //Para cada aresta, verifica se existem ciclos negativos
            for (int j = 0; j < numeroVertices; j++) {
                if (matrizAdj[j][i] != 0) {
                    if (vertices.get(i).descoberta > vertices.get(j).descoberta + matrizAdj[j][i]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void caminhoMinimo(int idVertice1, int idVertice2) {//algoritmo do caminho mínimo
        boolean temNegativo = Bellman_ford(idVertice1);//variável que guarda o valor que bellman-ford retorna, se existe ciclo negativo ou não

        if (vertices.get(idVertice2).descoberta >= 9999999) {//imprime os dados obtidos
            System.out.println("Não existe caminho mínimo entre os dois vértices!");
        } else if (temNegativo) {
            System.out.println("Tamanho do caminho: " + vertices.get(idVertice2).descoberta);
            System.out.printf("\tCaminho: " + idVertice2);
            Vertice verticeAux = vertices.get(idVertice2);
            while (verticeAux.pai != null) {
                System.out.printf(" <- %d", verticeAux.pai.id);
                verticeAux = verticeAux.pai;
            }
            System.out.println("");
        } else {
            System.out.println("O caminho possui ciclos negativos!");
        }
    }

    public void printRaizNiveis() {//função que printa a árvore e os níveis
        for (Vertice v : vertices) {
            if (v.pai == null) {
                System.out.println("Vertice Raiz " + v.id + "\n\tpai  : " + v.pai);
            } else {
                System.out.println("Vertice " + v.id + "\n\tpai: " + v.pai.id);
            }
            System.out.println("\tNivel: " + v.nivel + "\n");
        }
    }

}
