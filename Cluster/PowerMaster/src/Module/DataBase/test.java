/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Module.DataBase;

import java.sql.SQLException;

/**
 *
 * @author Joao
 */
public class test {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException {
        Database db = new Database("power", "_p55!gv{7MJ]}dIpPk7n1*0-,hq(PD", "code.dei.estt.ipt.pt", "powercomputing");
        //System.out.println(db.ExecuteCountQuery(122, 122, 122));
        //System.out.println(db.InserirIteracoes("122", 12,12 , 123, 123123, 312312, 123123, "dsads", 312312, 12, 12312));


        int interval = 0;
        int idClient = 37;
        int idProblem = 133666719;
        
       double ze = 0.12;
       String jose = "asdas";
       //db.InserirIteracoes(interval, interval, idClient, idProblem, ze, ze, idClient, jose, ze, idClient, ze);
       //db.InserirResult(interval, idClient, idProblem, ze, ze, ze, idClient, ze);
       //db.ExecuteMedia(interval, idClient, idProblem);
       System.out.println(db.ExecuteCountQuery(interval, idClient, idProblem));
        
       
        // db.ExecuteNonQuery("INSERT INTO tblResults VALUES (1,1,1,0.1,0.1,0.1,10,0.1)");
        // db.ExecuteCountQuery(1, 1, 1);
//        db.ExecuteNonQuery("INSERT INTO tblIterations VALUES (1,1,1,1,NOW(),0.1,0.1,1,'das',0.1,1,0.1)");
//        System.out.println(db.ExecuteCountQuery(1, 1, 1));

        //db.ExecuteNonQuery("INSERT INTO tblIterations VALUES (" + 1 + "," + 1 + "," + 1 + "," + 1 + ",NOW()," + 1 + "," + 0.1 + "," + 0.1 + ",'" + "dsa" + "'," + 0.1 + "," + 1 + "," + 0.1 + ");");

        //db.ExecuteNonQuery("SELECT AVG(average) AS mediaAverage, MAX(best) AS best, AVG(deviation) AS deviation, AVG(numBest) AS numBest, AVG(variance) AS variance FROM tblIterations WHERE itera='"+ 1 +"' AND idClient='"+1 + "' AND idProblem='"+ 1 + "';");     

    }
}
