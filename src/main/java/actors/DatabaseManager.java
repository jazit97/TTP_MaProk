package actors;



import shared.InstitutionAddress;

import java.sql.*;
import java.util.LinkedList;

public class DatabaseManager {
    private String url = "jdbc:postgresql://143.93.17.127/maprokTTPDB";
    private String username = "janek";
    private String password = "pcBj=,#c2SpDXb4)";
    private String schema = "ttp";


    public InstitutionAddress getAddress(String mID){
        String statement = "SELECT * FROM inst_list WHERE name = \'" + mID + "\'";
        ResultSet rs = query(statement, null);
        InstitutionAddress address;
        try {
            rs.beforeFirst();
            rs.next();
            address = new InstitutionAddress(rs.getString(4),rs.getInt(5));

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return address;
    }
    public LinkedList<String> getInstitutionList(){
        String statement = "SELECT * FROM inst_list";
        ResultSet rs = query(statement,null);
        LinkedList<String> inst = new LinkedList<>();
        try{
            while(rs.next()){
                inst.add(rs.getString(3));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return inst;
    }

    private Connection connect(String schema) {
        Connection con = null;
        try {
            System.out.println("Attempting to connect to: " + url);
            con = DriverManager.getConnection(url, username, password);
            con.setReadOnly(true);
            if (schema != null) {
                con.setSchema(schema);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return con;
    }

    private ResultSet query(String statement, String specificSchema){
        if(specificSchema == null){
            specificSchema = schema;
        }
        Connection con = connect(specificSchema);;
        ResultSet rs = null;
        try {
            System.out.println("Query: " + statement);
            Statement query = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            query.execute(statement);
            rs = query.getResultSet();
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return rs;
    }

}
