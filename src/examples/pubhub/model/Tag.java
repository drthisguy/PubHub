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
        this.tagName = capitalizeEachWord(tagName.trim());
    }
    
    private String capitalizeEachWord(String str){  
        String words[]=str.split("\\s");  
        String capitalizeWord="";  
        for(String w:words) {
            String first=w.substring(0,1);  
            String afterfirst=w.substring(1);  
            capitalizeWord+=first.toUpperCase()+afterfirst+" ";  
        }  
        return capitalizeWord.trim();  
    }  
}
