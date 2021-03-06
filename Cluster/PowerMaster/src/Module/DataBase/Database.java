package Module.DataBase;

import Module.AbstractAplication;
import NodeJS.Statistics.AsyncStats;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rodrigo Marinheiro
 */
public class Database extends AbstractAplication {

    /**
     * Variável que contem a a ligação com o servido MySQL
     */
    public Connection Connection;
    /**
     * Variável que contem um comando a ser executado no servidor MySQL
     */
    private Statement Command;
    /**
     * Variável que contem o Username do servidor MySQL
     */
    private String username;
    /**
     * Variável que contem a Password do servidor de MySQL
     */
    private String password;
    /**
     * Variável o endereço de IP do servidor
     */
    private String ipAddress;
    private String Database;

    //public static String ModuleName = "Database Module";
    /**
     * Construtor do objecto Database
     * Este objecto permite criar uma ligação com um servidor de MySQL
     * @param user Nome de utilizador do servidor de MySQL
     * @param password Password do utilizador do servidor de MySQL
     * @param address  Endereço publico do servidor de MySQL
     */
    public Database(String user, String password, String address, String database) {
        //Invocar o constutor da classe abstracta
        super("Database Module");

        //Atribuição de balores
        this.username = user;
        this.password = password;
        this.ipAddress = address;
        this.Database = database;

        Connect();
    }

    @Override
    public boolean getAplicationStatus() {
        return super.getAplicationStatus();
    }

    /**
     * Método interno ao objecto para a inicialização da conecção ao MySQL 
     * e inicialização de variáveis
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    private void Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection = DriverManager.getConnection("jdbc:mysql://" + this.ipAddress + ":3306/" + this.Database, this.username, this.password);
            Command = Connection.createStatement();
            //Se chegar aqui o módulo foi inicializado correctamente
            this.AplicationStatus = true;
        } catch (Exception e) {
            //Se chegar a interrupção a iniciaçização do módulo foi comprometida
            this.AplicationStatus = false;
            System.out.println("Database connection failed. Database.Connect");
            e.printStackTrace();
        }
    }

    public int ExecuteCountQuery(int period, int idClient, int idProblem) {
        int count = 0;
        try {
            ResultSet rs = this.Command.executeQuery("call ExecuteCountQuery(" + period + "," + idClient + "," + idProblem + ");");
            rs.first();
            count = rs.getInt("num");
            rs.close();
        } catch (Exception e) {

            try {
                if (Connection.isClosed()) {
                    System.out.println("Connection Lost Database.CountQuery");
                    Connect();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return ExecuteCountQuery(period, idClient, idProblem);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }

            e.printStackTrace();
        }
        return count;
    }

        
    
    public boolean ExecuteMedia(int period, int idClient, int idProblem, String globalNumBest, String Final,int control) {
        boolean erro = false;
        try {
            erro = this.ExecuteNonQuery("call ExecuteMedia(" + period + "," + idClient + "," + idProblem + ",'"+globalNumBest+"','"+Final+"',"+control+");");
        } catch (Exception e) {

            try {
                if (Connection.isClosed()) {
                    System.out.println("Connection Lost Database.CountQuery");
                    Connect();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return ExecuteMedia(period, idClient, idProblem, globalNumBest,Final,control);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }

            e.printStackTrace();
        }
        return erro;
    }

    /**
     * Método para executar uma query sem retorno no servidor MySQL
     * @param cmd Comando a ser executado no MySQL
     * @return True -> Comando executado com sucesso; False -> Ocorreu um erro na execução do comando
     * @throws SQLException 
     */
    public boolean ExecuteNonQuery(String cmd) {
        boolean erro = false;
        try {
            erro = Command.execute(cmd);
        } catch (SQLException e) {

            try {
                if (Connection.isClosed()) {
                    System.out.println("Connection Lost Database.ExecuteNonQuery");
                    Connect();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return ExecuteNonQuery(cmd);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }

            e.printStackTrace();
        }
        return erro;
    }

    public boolean InserirIteracoes(int threadId, int itera, int idClient, int idProblem, double best, double average, String attributes, double deviation, int type, double variance) {
        boolean erro = false;
        try {
            //System.out.println("call InserirIteracoes(" + threadId + "," + itera + "," + idClient + "," + idProblem +","+ best + "," + average + "," + attributes.toString() + "'," + deviation + "," + type + "," + variance + ");");
            //this.ExecuteNonQuery("INSERT INTO tblIterations VALUES (" + threadId + "," + itera + "," + idClient + "," + idProblem + ",NOW()," + best + "," + average + "," + numBest + ",'" + attributes.toString() + "'," + deviation + "," + type + "," + variance + ");");
            erro = this.ExecuteNonQuery("call InserirIteracoes(" + threadId + "," + itera + "," + idClient + "," + idProblem +","+ best + "," + average + ",'" + attributes.toString() + "'," + deviation + "," + type + "," + variance + ");");
        } catch (Exception e) {

            try {
                if (Connection.isClosed()) {
                    System.out.println("Connection Lost Database.InserirResult");
                    Connect();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return InserirIteracoes(threadId, itera, idClient, idProblem, best, average, attributes, deviation, type, variance);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            e.printStackTrace();
        }
        return erro;
    }
}
