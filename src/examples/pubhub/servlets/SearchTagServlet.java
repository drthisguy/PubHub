package examples.pubhub.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.dao.TagDAO;
import examples.pubhub.model.Book;
import examples.pubhub.utilities.DAOUtilities;

/*
 * This servlet will take you to the Tag, Search page "/TagSearch".
 */
@WebServlet("/TagSearch")
public class SearchTagServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Grab the list of Books and tags from the Databases
		BookDAO bookDAO = DAOUtilities.getBookDAO();
		List<Book> bookList = bookDAO.getAllBooks();
		
		TagDAO tagDAO = DAOUtilities.getTagDAO();
		
		//Add book tags to the field of each book instance to be forwarded.
		for (Book book : bookList) {
			ArrayList<String> tagList = tagDAO.getTagsByBook(book);
			String tags = "";
			
			if (tagList.size() > 0) {
				
				StringBuffer sb = new StringBuffer();
				
				for (String str : tagList) {
			         sb.append(str);
			         sb.append(", ");
			      }
				//print tag names and remove last appendage;
				tags += sb.toString().substring(0, sb.length() - 2); 
			}
			book.merge(tags);
		}

		// Populate the list into a variable that will be stored in the session
		request.getSession().setAttribute("books", bookList);
		
		request.getRequestDispatcher("tagSearch.jsp").forward(request, response);
	}
}