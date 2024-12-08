package org.example.librarymanagement.BookControllerTest;
import javafx.beans.binding.When;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.BookController.BookAddController;
import org.example.librarymanagement.Class.Book;
import org.example.librarymanagement.UIHelper;
import org.junit.Test;
import org.mockito.*;
import org.example.librarymanagement.UIHelper.*;

import static org.mockito.Mockito.*;


public class BookAddControllerTest {
    @InjectMocks
    private BookAddController bookAddController;

    @Mock
    private TextField txtBookTitle;

    @Mock
    private TextField txtAuthor;

    @Mock
    private TextField txtISBN;

    @Mock
    private TextField txtYear;

    @Mock
    private TextField txtPageNumber;

    @Mock
    private TextField txtDescription;

    @Mock
    private TextField txtQuantity;

    @Mock
    private Button btnAdd;

    @Mock
    private Button btnBack;

    @Mock
    private UIHelper uiHelper;

    @Mock
    private Book book;

    private void setUpBookInput(String title, String author, String isbn, String year, String pages, String description, String quantity) {
        when(txtBookTitle.getText()).thenReturn(title);
        when(txtAuthor.getText()).thenReturn(author);
        when(txtISBN.getText()).thenReturn(isbn);
        when(txtYear.getText()).thenReturn(year);
        when(txtPageNumber.getText()).thenReturn(pages);
        when(txtDescription.getText()).thenReturn(description);
        when(txtQuantity.getText()).thenReturn(quantity);
    }

    void testBookAddAction_Sucess() {
        setUpBookInput("eng", "java", "1000", "1000", "1000","grammar", "1");
        doNothing().when(book).create();
        bookAddController.actionBookAdd(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0,
                MouseButton.SECONDARY, 1, false, false, false, false, false, false, false, false, false, false,
                null));
        verify(uiHelper).
    }

    void testBookAddAction_EmptyInput() {

    }
}
