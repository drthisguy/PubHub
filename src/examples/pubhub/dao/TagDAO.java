package examples.pubhub.dao;

import java.util.ArrayList;
import java.util.List;
import examples.pubhub.model.Tag;
import examples.pubhub.model.Book;

public interface TagDAO {

    boolean addTag(Tag tag);

    boolean removeTag(String ISBN, String tagName);
    
    boolean removePreviousTags(String ISBN);

    ArrayList<String> getTagsByBook(Book book);

    List<Book> getBooksByTagName(String tagName);

}