/**
 * @author Juliano Cézar Chagas Tavares & Kevin Levrone Rodrigues Machado Silva
 */
package trabalhografos;

public class Vertice {
    public Vertice pai;
    public Cor cor;
    public int descoberta;
    public int finalizado;
    public int id;
    public int nivel;
    public int idComponente;

    public Vertice(int id) { //Construtor
        this.id = id;
        this.nivel = -1;
        this.pai = null;
        this.cor = Cor.BRANCO;
        this.descoberta = 0;
        this.finalizado = 0;
        this.idComponente = -1;
    }
    
    
}
