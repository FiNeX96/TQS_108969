
package tqs.lab5;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class LibrarySteps {

    static final Logger log = getLogger(lookup().lookupClass());

    private Library library;
    private ArrayList <Book> result_books;

    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
    public LocalDateTime isodate8601(String year, String month, String day) {
        return LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day),0, 0);
    }

    @Given("the following books in the Library")
    public void setup( DataTable table) {
        log.debug("Setting up the library");
        library = new Library();
        table.asMaps().forEach(row -> {
            String title = row.get("title");
            String author = row.get("author");
            String[] date = row.get("date").split(" ");
            LocalDateTime local_date = LocalDateTime.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]),0, 0);
            library.addBook(new Book(title, author, local_date));
        });
    }

    @When("the user searches for the book {string} by title")
    public void searchByTitle(String title) {
        log.debug("Searching for book with title: " + title);
        result_books = library.getBooksByTitle(title);
    }

    @When("the user searches books of the author {string}")
    public void searchByAuthor(String author) {
        log.debug("Searching for books of the author: " + author);
        result_books = library.getBooksByAuthor(author);
    }

    @When("the user searches books published in {int}")
    public void searchByYear(int year) {
        log.debug("Searching for books published in year: " + year);
        result_books = library.getBooksByDate(year);
    }

    @When("the user removes the book {string} from the library")
    public void removeBook(String title) {
        log.debug("Removing book with title: " + title + " from the library");
        library.removeBook(title);
    }

    @Then("the user should see {int} books in the search results")
    public void checkNumberOfBooks(int number) {
        log.debug("Checking if the number of books in the search results is: " + number);
        assertEquals(result_books.size(), number);
    }

    @Then("the user should not see the book {string} in the search results")
    public void checkBookNotInResults(String title) {
        log.debug("Checking if book with title: " + title + " is not in the search results");
        assertEquals(result_books.size(), 0);
    }

    @Then("the user should see the book {string} in the search results")
    public void checkBookByTitle(String title) {
        log.debug("Checking if book with title: " + title + " is in the search results");
        assertEquals(result_books.get(0).getTitle(), title);
    }
    

}