package examples.pubhub.model;

public class Tag {
    private String isbn13;
    private String tagName;


    public Tag(String isbn13, String tagName) {
        this.isbn13 = isbn13;
        this.tagName = capitalizeEachWord(tagName.trim());
    }

    @Override
    public String toString() {
		return tagName;
    }

    private String capitalizeEachWord(String str){
    	String words[]=str.split("\\s"); 
        String capitalizedWord="";
        for(String w:words){
            String first=w.substring(0,1);
            String afterFirst=w.substring(1);
            capitalizedWord+= first.toUpperCase() + afterFirst + " ";
        }
        return capitalizedWord.trim();
    }

    public String getIsbn13() {
        return isbn13;
    }

    public String getTagName() {
        return tagName;
    }

}
