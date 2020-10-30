package examples.pubhub.dao;

import examples.pubhub.model.Book;
import examples.pubhub.model.Tag;
import examples.pubhub.utilities.DAOUtilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagDAOImpl implements TagDAO {
    Connection connection = null;	// Our connection to the database
    PreparedStatement stmt = null;	// We use prepared statements to help protect against SQL injection

    @Override
    public boolean addTag(Tag tag) {

        try {
            connection = DAOUtilities.getConnection();
            String sql = "INSERT INTO book_tags VALUES (?, ?)";
            stmt = connection.prepareStatement(sql);

            stmt.setString(1, tag.getIsbn13());
            stmt.setString(2, tag.getTagName());

             return stmt.executeUpdate() != 0;

        } catch (SQLException e) {
             e.printStackTrace();
             return false;
        } finally {
            closeResources();
        }
    }

    @Override
    public boolean removeTag(String ISBN13, String tagName) {
        try {
            connection = DAOUtilities.getConnection();
            String sql = "DELETE book_tag WHERE isbn_13=? AND tag_name=?";
            stmt = connection.prepareStatement(sql);

            stmt.setString(1, ISBN13);
            stmt.setString(2, tagName);

            return stmt.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    @Override
    public ArrayList<String> getTagsByBook(Book book) {
        ArrayList<String> tags = new ArrayList<String>();

        try {
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM book_tags WHERE isbn_13=?";
            stmt = connection.prepareStatement(sql);

            stmt.setString(1, book.getIsbn13());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Tag tag = new Tag(rs.getString("isbn_13"), rs.getString("tag_name"));

                tags.add(tag.toString());
            }
            System.out.println(tags);

        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return tags;
    }
    @Override
    public List getBooksByTagName(String tagName) {
        List<Book> books = new ArrayList();

        try {
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM book_tags WHERE isbn_13=?";
            stmt = connection.prepareStatement(sql);

            stmt.setString(1, tagName);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Book book = new Book();

                book.setIsbn13(rs.getString("isbn_13"));
                book.setAuthor(rs.getString("author"));
                book.setTitle(rs.getString("title"));
                book.setPublishDate(rs.getDate("publish_date").toLocalDate());
                book.setPrice(rs.getDouble("price"));
                book.setContent(rs.getBytes("content"));

                books.add(book);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return books;
    }


    private void closeResources() {
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException e) {
            System.out.println("Could not close statement!");
            e.printStackTrace();
        }

        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            System.out.println("Could not close connection!");
            e.printStackTrace();
        }
    }
}
