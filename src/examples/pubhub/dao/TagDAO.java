package examples.pubhub.dao;

import java.util.ArrayList;
import java.util.List;
import examples.pubhub.model.Tag;
import examples.pubhub.model.Book;

public interface TagDAO {

    boolean addTag(Tag tag);

    boolean removeTag(String ISBN, String tagName);

    ArrayList getTagsByBook(Book book);

    List getBooksByTagName(String tagName);

}