package examples.pubhub.dao;

import examples.pubhub.model.Book;
import examples.pubhub.model.Tag;

import java.util.ArrayList;

public interface TagDAO {

    boolean addTag(Tag tag);

    boolean removePreviousTags(String ISBN);

    ArrayList<String> getTagsByBook(Book book);

}