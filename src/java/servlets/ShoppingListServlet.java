package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class ShoppingListServlet extends HttpServlet {
    ArrayList<String> items = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            HttpSession session = request.getSession();
            String username = (String)session.getAttribute("username");
            session.setAttribute("username",username);
            String action = request.getParameter("action");
            
            if(action!= null && action.equals("logout")){
                items.clear();
                session.invalidate();
                getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request,response);
            }
            
            else if (username != null){
                session.setAttribute("username",username);
                getServletContext().getRequestDispatcher("/WEB-INF/shoppingList.jsp").forward(request,response);
            } else {
                getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request,response);
            }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String action = request.getParameter("action");
        String item = request.getParameter("item");

       String[] userItems = request.getParameterValues("userItems");
        
     
            if(action.equals("register")){
                session.setAttribute("username", username);
            }else if(action.equals("add") && !item.equals("")){
                items.add(item);
                session.setAttribute("itemList", items);
            } else if (action.equals("delete") && items != null){
                for (int i = 0; i < userItems.length; i++){
                    for (int j = 0; j < items.size(); j++){
                        if (userItems[i].equals(items.get(j))){
                            items.remove(j);
                            session.setAttribute("itemList", items);
                        }
                        getServletContext().getRequestDispatcher("/WEB-INF/shoppingList.jsp").forward(request, response);
                    }
                }
            }
            
            getServletContext().getRequestDispatcher("/WEB-INF/shoppingList.jsp").forward(request,response);
    }        
        }