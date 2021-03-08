package requestservlets;

import actors.DatabaseManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import shared.InstitutionAddress;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

@WebServlet("/institutionslist")
public class InstitutionListRequest extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();


    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        System.out.println("InstitutionslistRequest: doGet");
        DatabaseManager manager = new DatabaseManager();
        LinkedList<String> instList = manager.getInstitutionList();
        PrintWriter out = new PrintWriter(response.getOutputStream());
        try{
            ObjectMapper mapper = new ObjectMapper();
            String jsonList = mapper.writeValueAsString(instList);
            out.println(jsonList);
        }catch (Exception e){
            e.printStackTrace();
        }
        out.close();
        System.out.println("doGet:END");

    }
}
