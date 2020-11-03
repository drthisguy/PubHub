package examples.pubhub.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.dao.TagDAO;
import examples.pubhub.model.Book;
import examples.pubhub.model.Tag;
import examples.pubhub.utilities.DAOUtilities;

/**
 * Servlet implementation class UpdateBookServlet
 */
@WebServlet("/UpdateBook")
public class UpdateBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess= false;
		String isbn13 = request.getParameter("isbn13");
		
		BookDAO bookDB = DAOUtilities.getBookDAO();
		TagDAO tagsDB = DAOUtilities.getTagDAO(); 
		Book book = bookDB.getBookByISBN(isbn13);
		
		if(book != null) {
			// The only fields we want to be updatable are title, author and price. A new ISBN has to be applied for
			// And a new edition of a book needs to be re-published.
			book.setTitle(request.getParameter("title"));
			book.setAuthor(request.getParameter("author"));
			book.setPrice(Double.parseDouble(request.getParameter("price")));
			request.setAttribute("book", book);
			
			isSuccess = bookDB.updateBook(book);
		} else {
			//ASSERT: couldn't find book with isbn. Update failed.
			isSuccess = false;
		}
		
		if(isSuccess) {
			String message = book.getTitle()+" updated successfully";
			String messageClass = "alert-success";
			String[] tags = request.getParameter("tags").split(",");
			ArrayList<String> previousTags = tagsDB.getTagsByBook(book);
			
			boolean areRemoved = tagsDB.removePreviousTags(isbn13);
			boolean hasNoPreviousTags = (previousTags.size() == 0);
			
			//Hold that each one of these conditions must be true when (and only when) the other is false.
			if (areRemoved ^ hasNoPreviousTags) {
				for (String str : tags) {
					
					Tag tag = new Tag(isbn13, str);
					boolean tagAdded = tagsDB.addTag(tag);
					
					if (!tagAdded) {
						message = book.getTitle()+" updated. However, one or more tags may have failed to update";
						messageClass = "alert-warning";
					}
				}
			} else {
				message = "Failed to update any tags for "+ book.getTitle();
				messageClass = "alert-warning";
			}
					
			request.getSession().setAttribute("message", message);
			request.getSession().setAttribute("messageClass", messageClass);
			response.sendRedirect("ViewBookDetails?isbn13=" + isbn13);
		} else {
			request.getSession().setAttribute("message", "There was a problem updating this book");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("bookDetails.jsp").forward(request, response);
		}
	}

}
