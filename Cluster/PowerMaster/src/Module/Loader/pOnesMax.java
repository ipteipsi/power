/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Module.Loader;

import genetics.Individual;
import genetics.Solver;
import genetics.StopCriterion;
import org.json.JSONException;
import org.json.JSONObject;
import powermaster.GeneticEvents;
import powermaster.PowerMaster;

/**
 *
 * @author Bruno Oliveira nº 11127 IPT-ESTT
 */
public class pOnesMax extends Problem {

    public static final String ProblemName = "OneMax";
    /**
     * Tamanho da população
     */
    private int popSize;
    public static final String PARAM_POPULATION_SIZE = "pop";
    /**
     * Tamanho do alelo
     */
    private int alelloSize;
    public static final String PARAM_ALELLO_SIZE = "alello";
    /**
     * Número maximo de Iterações
     */
    private int Iterations;
    public static final String PARAM_ITERATIONS = "iterations";
    /**
     * Condição de paragem. Melhor fitness individuo
     */
    private int bestFitness;
    public static final String PARAM_BEST_FITNESS = "best";
    /**
     * Número de parametros que são necessário para que o problema OneMax seja corrido
     */
    private static int PARAM_REQUIRED = 4;//Número de parametros obrigatórios
    private int PARAM_ADDED = 0;//Número de parametros adicionados obrigatórios
    private static int PARAM_COUNT = 4;//Número de parametros existentes no problema
    /**
     * Variável que indica o estado do loader
     */
    private boolean loadStatus;
    private JSONObject data;

    /**
     * Construtor do problema OnesMax
     * @param data Informação do problema a ser carregado
     */
    public pOnesMax(JSONObject data) throws Exception {
        super(pOnesMax.ProblemName, PARAM_REQUIRED);
        this.data = data;
        this.loadStatus = Load();
    }

    private boolean Load() throws Exception {
        try {
            System.out.println("----Params Data-----");
            if (!this.containsParam(pOnesMax.PARAM_ITERATIONS)) {
                //carregar o parametro para um dicionario de parametros
                this.Iterations = this.data.getInt(pOnesMax.PARAM_ITERATIONS);
                this.addParam(pOnesMax.PARAM_ITERATIONS, this.Iterations);
                //registar parametros obrigatório
                this.PARAM_ADDED++;
                System.out.println(pOnesMax.PARAM_ITERATIONS + "+:" + this.Iterations);
            }
            //parametro 2
            if (!this.containsParam(pOnesMax.PARAM_POPULATION_SIZE)) {
                this.popSize = this.data.getInt(pOnesMax.PARAM_POPULATION_SIZE);
                this.addParam(pOnesMax.PARAM_POPULATION_SIZE, this.popSize);
                //registar parametros obrigatório
                this.PARAM_ADDED++;
                System.out.println(pOnesMax.PARAM_POPULATION_SIZE + "+:" + this.popSize);
            }
            //parametro 3
            if (!this.containsParam(pOnesMax.PARAM_ALELLO_SIZE)) {
                this.alelloSize = this.data.getInt(pOnesMax.PARAM_ALELLO_SIZE);
                this.addParam(pOnesMax.PARAM_ALELLO_SIZE, this.alelloSize);
                //registar parametros obrigatório
                this.PARAM_ADDED++;
                System.out.println(pOnesMax.PARAM_ALELLO_SIZE + "+:" + this.alelloSize);
            }
            //parametro 4
            if (!this.containsParam(pOnesMax.PARAM_BEST_FITNESS)) {
                this.bestFitness = this.data.getInt(pOnesMax.PARAM_BEST_FITNESS);
                this.addParam(pOnesMax.PARAM_BEST_FITNESS, this.bestFitness);
                //registar parametros obrigatório
                this.PARAM_ADDED++;
                System.out.println(pOnesMax.PARAM_BEST_FITNESS + "+:" + this.bestFitness);
            }
        } catch (Exception e) {
            this.loadStatus=false;
            e.printStackTrace();
            throw e;
        }


        //Verificar se todos os parametros foram carregados
        if (this.PARAM_ADDED == pOnesMax.PARAM_REQUIRED) {
            this.loadStatus=true;
            //Ler dados (restantes Linhas)
            //Neste caso não existe a necessidade de carregar parametros

            System.out.println("#### NO DATA ####");

            //Ler linha 2 para ver se começa com <DATASTART>
            //LER TODOS os valores por LINHA (com um while)
            //Colocar todos os valores lidos numa estrutura de dados (Unica para cada problema)
            //Este ciclo para quando chegar a tag <DATAEND>
            //O restante texto é rejeitado
            return true;
        }
        return false;
    }

    /**
     * Método que retorna o NOVO solver com os parametros carregados por o ficheiro.
     * @return 
     */
    @Override
    public Solver getNewSolver() {
        
            //        // Operadores
            //        ArrayList<Operator> __operators = new ArrayList<Operator>();
            //        //__operators.add(new SUS(70)); 
            //        __operators.add(new operators.selections.Tournament(70, 2));
            //        __operators.add(new operators.recombinations.Crossover());
            //        __operators.add(new operators.mutation.Flipbit(0.01));
            //        __operators.add(new operators.replacements.Tournament(100, 2));

        try {
             this.LoadOperators(data);
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
        
        int __sizePopulation = this.popSize;
        int __sizeAllelo = this.alelloSize;
        Individual __prototypeIndividual = new genetics.algorithms.OnesMax();
        int __iteractions = this.Iterations;
        double __bestFitness = (double) this.bestFitness;

        StopCriterion __stopCriterion = new StopCriterion(__iteractions, __bestFitness);

        System.out.println("\n\nClient: "+this.getClientID() + "| Problem: "+this.getProblemID()+" \n\n");
        Solver solver = new Solver(__sizePopulation, __sizeAllelo, __prototypeIndividual, __stopCriterion, this.getOperators(), new GeneticEvents(PowerMaster.INTERVAL_PART, this.getClientID(), this.getProblemID()));
        return solver;
        //return new Solver(__sizePopulation, __sizeAllelo, __prototypeIndividual,__stopCriterion,__operators, new GeneticEvents(PowerMaster.INTERVAL_PART,1,1));

    }

    public boolean getStatus() {
        return this.loadStatus;
    }

    public int getIterations() {
        return Iterations;
    }

    public int getAlelloSize() {
        return alelloSize;
    }

    public int getBestFitness() {
        return bestFitness;
    }

    public int getPopSize() {
        return popSize;
    }
}