package requestservlets;
import actors.DatabaseManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import shared.InstitutionAddress;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


@WebServlet("/addressrequest")
public class AddressRequest extends  HttpServlet{

    @Override
    public void init() throws ServletException {
        super.init();


    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        System.out.println("doGet:Start");
        DatabaseManager manager = new DatabaseManager();

        String mID = request.getParameter("mid");
        PrintWriter out = new PrintWriter(response.getOutputStream());

        try{
            InstitutionAddress address = manager.getAddress(mID);
            ObjectMapper mapper = new ObjectMapper();
            String jsonaddress = mapper.writeValueAsString(address);
            out.println(jsonaddress);
        }catch (Exception e){
            e.printStackTrace();
        }
        out.close();
        System.out.println("doGet:END");

    }
}
