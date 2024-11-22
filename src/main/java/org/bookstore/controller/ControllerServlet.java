package org.bookstore.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bookstore.dao.BookDAO;
import org.bookstore.model.Book;

import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.List;

public class ControllerServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    private BookDAO bookDao;

    public void init(){
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
        bookDao = new BookDAO(jdbcURL, jdbcUsername, jdbcPassword);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getServletPath();
        try{
            switch (action) {
                case "/new":
                    showNewForm(req, resp);
                    break;
                case "/insert":
                    insertBook(req, resp);
                    break;
                case "/delete":
                    deleteBook(req, resp);
                    break;
                case "/edit":
                    showEditForm(req, resp);
                    break;
                case "/update":
                    updateBook(req, resp);
                    break;
                default:
                    listBooks(req, resp);
                    break;
            }
        }catch(Exception e){
                throw new RuntimeException(e);
        }

    }

    public void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        try {
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            float price = Float.parseFloat(request.getParameter("price"));
            Book book = new Book(0,title, author, price);
            bookDao.insertBook(book);
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath+"/list");
        } catch (NumberFormatException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Book book = bookDao.getBook(id);
            RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
            request.setAttribute("book", book);
            dispatcher.forward(request, response);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void deleteBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Book book = bookDao.getBook(id);
            bookDao.deleteBook(book);

            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath+"/list");
        } catch (NumberFormatException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            float price = Float.parseFloat(request.getParameter("price"));
            Book book = new Book(id, title, author, price);
            bookDao.updateBook(book);

            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath+"/list");
        } catch (NumberFormatException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void listBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        try {
            List<Book> booksList = bookDao.listAllBooks();
            request.setAttribute("listBook", booksList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("BookList.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException | ServletException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        doGet(req, resp);
    }

}
