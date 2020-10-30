package examples.pubhub.model;

public class Tag {
    private String isbn13;
    private String tagName;


    public Tag(String isbn13, String tagName) {
        this.setIsbn13(isbn13);
        this.setTagName(tagName);
    }

    public Tag() {
        this.setIsbn13(null);
        this.setTagName(null);
    }
    
    @Override
    public String toString() {
		return tagName;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName.toLowerCase().trim();
    }
}
