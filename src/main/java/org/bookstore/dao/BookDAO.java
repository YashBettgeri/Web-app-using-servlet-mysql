package org.bookstore.dao;

import org.bookstore.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
CRUD
c - create (insert)
r - read (select)
u - update (replace)
d - delete
 */
public class BookDAO {

    private final String jdbcURL;
    private final String jdbcUsername;
    private final String jdbcPassword;
    private Connection jdbcConnection;

    public BookDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }


    public void connect() throws SQLException{
        if(this.jdbcConnection == null || jdbcConnection.isClosed()){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
            }catch (ClassNotFoundException e){
                throw new SQLException();
            }
            this.jdbcConnection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
        }
    }

    public void disconnect() throws SQLException{
        if(this.jdbcConnection != null && !jdbcConnection.isClosed()){
            jdbcConnection.close();
            jdbcConnection = null;
        }
    }

    public boolean insertBook(Book book) throws SQLException{
        connect();
        String query = "INSERT into book(title, author, price) VALUES(?,?,?)";
        var ps = jdbcConnection.prepareStatement(query);
        ps.setString(1, book.getTitle());
        ps.setString(2, book.getAuthor());
        ps.setFloat(3, book.getPrice());

        boolean isRowInserted = ps.executeUpdate() > 0;
        ps.close();
        disconnect();

        return isRowInserted;

    }

    public List<Book> listAllBooks() throws SQLException{
        connect();
        String query = "SELECT * from book";
        var ps = jdbcConnection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<Book> books = new ArrayList<>();
        while(rs.next()){
            Book book = new Book();
            book.setId(rs.getInt("book_id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setPrice(rs.getFloat("price"));
            books.add(book);
        }
        ps.close();
        disconnect();
        return books;
    }

    public boolean deleteBook(Book book) throws SQLException{
        connect();

        String query = "DELETE from book where book_id = ?";
        var ps = jdbcConnection.prepareStatement(query);
        ps.setInt(1, book.getId());
        boolean isRowDeleted = ps.executeUpdate() > 0;

        ps.close();
        disconnect();
        return isRowDeleted;

    }

    public boolean updateBook(Book book) throws SQLException{
        connect();

        String query = "UPDATE book SET title = ?, author = ?, price = ?" +
                " WHERE book_id = ?";
        var ps = jdbcConnection.prepareStatement(query);
        ps.setString(1, book.getTitle());
        ps.setString(2, book.getAuthor());
        ps.setFloat(3, book.getPrice());
        ps.setInt(4, book.getId());
        boolean isRowUpdated = ps.executeUpdate() > 0;

        ps.close();
        disconnect();
        return isRowUpdated;
    }

    public Book getBook(int id) throws SQLException{
        Book book = null;
        connect();

        String query = "SELECT * from book where book_id = ?";
        var ps = jdbcConnection.prepareStatement(query);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            book = new Book();
            book.setId(id);
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setPrice(rs.getFloat("price"));
        }

        ps.close();
        disconnect();
        return book;


    }
}
