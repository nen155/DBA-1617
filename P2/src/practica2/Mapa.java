package practica2;

import javafx.util.Pair;

/**
 * Esta clase contiene los atributos y métodos para el mapa de conocimiento del mundo
 * 
 * @author Emilio Chica Jiménez
 * @author Miguel Angel Torres López
 * @author Juan José Jiménez García
 * @author Antonio Javier Benitez Guijarro
 */
public class Mapa {
    
    private static final int TAMANIO_MAPA = 504;
    private static final int TAMANIO_RADAR = 5;
    private static final int TAMANIO_SCANNER = 5;
    private int[][] matriz_mapa;
    private int[][] matriz_radar;
    private double[][] matriz_scanner;
    boolean objetivo_inalcanzable;
    private Pair<Integer, Integer> posicion_objetivo; //Si la posicion es null significa que no hemos encontrado el objetivo aún.
    private int antiguedad;
    
    /**
     * Constructor de la clase Mapa
     * 
     * @author Juan José Jiménez García
     */
    public Mapa() {
        
        this.matriz_mapa = new int[TAMANIO_MAPA][TAMANIO_MAPA];
        this.matriz_radar = new int[TAMANIO_RADAR][TAMANIO_RADAR];
        this.matriz_scanner = new double[TAMANIO_SCANNER][TAMANIO_SCANNER];
        this.posicion_objetivo = null;
        this.objetivo_inalcanzable = false;
        
        antiguedad=-1;
        this.inicializarMapa();
    }
    
    /**
     * Método para obtener la posición del objetivo
     * 
     * @return La posición del objetivo
     * @author Juan José Jiménez García
     */
    public Pair<Integer,Integer> getPosicionObjetivo() {
        return this.posicion_objetivo;
    }
    
    /**
     * Método para actualizar la matriz mapa de conocimiento del mundo
     * 
     * @param posicion La posición en la que se encuentra el agente
     * @author Miguel Ángel Torres López
     * @author Antonio Javier Benítez Guijarro
     */
    public void actualizarMapa(Pair<Integer, Integer> posicion) {
        int posicion_inicial_x=posicion.getKey();
        int posicion_inicial_y=posicion.getValue();
        
        for(int i=0;i<TAMANIO_RADAR;i++)
        {
            for(int j=0;j<TAMANIO_RADAR;j++)
            {
                    //Nos interesa que primero actualice el mapa y despues compruebe si hay un 2
                    if(matriz_mapa[posicion_inicial_y+i][posicion_inicial_x+j]==3)
                    {
                        matriz_mapa[posicion_inicial_y+i][posicion_inicial_x+j]=matriz_radar[i][j];
                    }
					
                    //Compruebo si hay un 2
                    if(matriz_mapa[posicion_inicial_y+i][posicion_inicial_x+j] == 2){
                        this.posicion_objetivo= new Pair(posicion_inicial_x+2,posicion_inicial_y+2);
                    }
            }
        }
        
        if(matriz_mapa[posicion.getValue()+2][posicion.getKey()+2]!=2)
        {
            matriz_mapa[posicion.getValue()+2][posicion.getKey()+2]=antiguedad;
        }
    }
    
    /**
     * Método para comprobar si se está pisando el objetivo
     * 
     * @param posicion
     * @return Un booleano que indica si se está pisando el objetivo en esa casilla
     * @author Antonio Javier Benítez Guijarro
     */
    public boolean pisandoObjetivo(Pair<Integer, Integer> posicion){
        
        return (matriz_mapa[posicion.getValue()+2][posicion.getKey()+2]==2);
    }
    
    /**
     * Método para inicializar la matriz mapa de conocimiento del mundo
     * 
     * @author Juan José Jiménez García
     */
    public void inicializarMapa() {
        
        for (int i = 0; i < TAMANIO_MAPA; i++) {
	    for (int j = 0; j < TAMANIO_MAPA; j++) {
		matriz_mapa[i][j] = 3;
	    }
	}
    }
    
    /**
     * Método para obtener la matriz del mapa de conocimiento del mundo
     * 
     * @return La matriz del mapa
     * @author Juan José Jiménez García
     */
    public int[][] devolverMapa() {
        
        return this.matriz_mapa;
    }
    
    /**
     * Método para encontrar el objetivo en una matriz radar
     * 
     * @return Un booleano indicando si ha encontrado el objetivo o no
     * @author Juan José Jiménez García
     */
   /* public boolean buscarObjetivo() {
        
        boolean encontrado = false;
        
        for (int i = 0; i < TAMANIO_MAPA; i++) {
            for (int j = 0; j < TAMANIO_MAPA; j++) {
            
                if (matriz_mapa[i][j] == 2) {
                    encontrado = true;
                }
            }
        }
        
        return encontrado;
    }*/
    
	
    /**
     * Método para calcular la ubicación del objetivo, siendo llamada una única vez
     * 
     * @param posicion_coche La posición en la que actualmente se encuentra el coche
     * @author Juan José Jiménez García
     * @author Miguel Ángel Torres López
     * @author Antonio Javier Benítez Guijarro
     */
    /*BORRAR
	public void calcularPosicionObjetivo(Pair<Integer, Integer> posicion_coche) {
        
        double minimo_1 = Double.MAX_VALUE;
        double minimo_2 = Double.MAX_VALUE;
        Pair<Integer, Integer> pos_min_1 = null;
        Pair<Integer, Integer> pos_min_2 = null;
        
        // Calcular mínimo 1
        if(this.matriz_scanner[0][0] < minimo_1) {
            minimo_1 = this.matriz_scanner[0][0];
            pos_min_1 = new Pair(0, 0);
        }
        if(this.matriz_scanner[0][4] < minimo_1) {
            minimo_1 = this.matriz_scanner[0][4];
            pos_min_1 = new Pair(0, 4);
        }
        if(this.matriz_scanner[4][0] < minimo_1) {
            minimo_1 = this.matriz_scanner[4][0];
            pos_min_1 = new Pair(4, 0);
        }
        if(this.matriz_scanner[4][4] < minimo_1) {
            minimo_1 = this.matriz_scanner[4][4];
            pos_min_1 = new Pair(4, 4);
        }
        
        // Calcular mínimo 2
        // Debemos asegurarnos de que no asignamos la casilla que ya está asignada en el mínimo 1
        if((this.matriz_scanner[0][0] < minimo_2) && (pos_min_1.getKey() != 0) && (pos_min_1.getValue() != 0)) {
            minimo_2 = this.matriz_scanner[0][0];
            pos_min_2 = new Pair(0, 0);
        }
        if((this.matriz_scanner[0][4] < minimo_2) && (pos_min_1.getKey() != 0) && (pos_min_1.getValue() != 4)) {
            minimo_2 = this.matriz_scanner[0][4];
            pos_min_2 = new Pair(0, 4);
        }
        if((this.matriz_scanner[4][0] < minimo_2) && (pos_min_1.getKey() != 4) && (pos_min_1.getValue() != 0)) {
            minimo_2 = this.matriz_scanner[4][0];
            pos_min_2 = new Pair(4, 0);
        }
        if((this.matriz_scanner[4][4] < minimo_2) && (pos_min_1.getKey() != 4) && (pos_min_1.getValue() != 4)) {
            minimo_2 = this.matriz_scanner[4][4];
            pos_min_2 = new Pair(4, 4);
        }
        
        // Obtener la posición 1 en el mapa de memoria del agente
        Pair pos_final_1 = null;
        
        if (pos_min_1.getKey() == 0 && pos_min_1.getValue() == 0) {
            pos_final_1 = new Pair(posicion_coche.getKey()-2, posicion_coche.getValue()-2);
        }
        if (pos_min_1.getKey() == 0 && pos_min_1.getValue() == 4) {
            pos_final_1 = new Pair(posicion_coche.getKey()+2, posicion_coche.getValue()-2);
        }
        if (pos_min_1.getKey() == 4 && pos_min_1.getValue() == 0) {
            pos_final_1 = new Pair(posicion_coche.getKey()-2, posicion_coche.getValue()+2);
        }
        if (pos_min_1.getKey() == 4 && pos_min_1.getValue() == 4) {
            pos_final_1 = new Pair(posicion_coche.getKey()+2, posicion_coche.getValue()+2);
        }
        
        // Obtener la posición 2 en el mapa de memoria del agente
        Pair pos_final_2 = null;
        
        if (pos_min_2.getKey() == 0 && pos_min_2.getValue() == 0) {
            pos_final_2 = new Pair(posicion_coche.getKey()-2, posicion_coche.getValue()-2);
        }
        if (pos_min_2.getKey() == 0 && pos_min_2.getValue() == 4) {
            pos_final_2 = new Pair(posicion_coche.getKey()+2, posicion_coche.getValue()-2);
        }
        if (pos_min_2.getKey() == 4 && pos_min_2.getValue() == 0) {
            pos_final_2 = new Pair(posicion_coche.getKey()-2, posicion_coche.getValue()+2);
        }
        if (pos_min_2.getKey() == 4 && pos_min_2.getValue() == 4) {
            pos_final_2 = new Pair(posicion_coche.getKey()+2, posicion_coche.getValue()+2);
        }
        
        // Obtenemos las variables x2 e y2
        int x2 = 1;
        int y2 = 1;
        
        // Actualizamos la posición del objetivo con los datos obtenidos
        // Las coordenadas obtenidas son la posición real del objetivo en el mapa del servidor
        this.posicion_objetivo = new Pair(x2-2, y2-2);
    } BORRAR*/
    
    /**
     * Método para ajustar la matriz de radar
     * 
     * @param radar_percibido La matriz de 5x5 casillas en la que buscar el objetivo
     * @author Miguel Angel Torres Lopez
     * @author Antonio Javier Benitez Guijarro
     */
    public void setRadar(int[][] radar_percibido) {
        for(int i=0;i<TAMANIO_RADAR;i++)
        {
            for(int j=0;j<TAMANIO_RADAR;j++)
            {
                matriz_radar[i][j]=radar_percibido[i][j];
            }
        }
    }

    public void setMatrizScanner(double[][] matriz_scanner) {
        this.matriz_scanner = matriz_scanner;
    }
    
    public double[][] getMatriz_scanner() {
        return matriz_scanner;
    }
    
    public int[][] getMatrizRadar() {
        return matriz_radar;
    }

    public int getAntiguedad() {
        return antiguedad;
    }
    
    public void decrementarAntiguedad(){
        this.antiguedad--;
    }
}
