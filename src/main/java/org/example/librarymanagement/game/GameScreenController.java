package org.example.librarymanagement.game;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.UIHelper;
import org.example.librarymanagement.entity.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScreenController {

    @FXML
    private TextField guessTextField;
    @FXML
    private Button submitButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button skipButton;
    @FXML
    private TextArea descriptionText;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label wrongLabel;
    @FXML
    private Book correctAnswer;
    private List<Book> remainingBooks;
    private int score;
    private int incorrectGuessCount;
    private int incorrectWrongCount;

    public void initialize() {
        List<Book> bookList = loadBooks();
        remainingBooks = new ArrayList<>(bookList);

        score = 0;
        incorrectGuessCount = 0;
        incorrectWrongCount = 0;
        updateScoreLabel();
        wrongLabel.setText("Số lần bỏ qua: " + incorrectWrongCount);
        loadGame();

        guessTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (submitButton.isDisable()) {
                    event.consume();
                } else {
                    handleSubmitButton();
                }
            }
        });

    }

    private List<Book> loadBooks() {
        return Book.getAllBooks();
    }

    private void loadGame() {
        if (incorrectWrongCount >= 3) {
            UIHelper.showAlert(Alert.AlertType.INFORMATION, "Bạn đã trả lời sai 3 lần, bạn đã thua cuộc!\nĐiểm của bạn: " + score);
            disableGame();
            return;
        }
        if (remainingBooks.isEmpty()) {
            UIHelper.showAlert(Alert.AlertType.INFORMATION, "Không còn sách nào để chơi. Bạn đã hoàn thành trò chơi!\nĐiểm của bạn: " + score);
            disableGame();
            return;
        }

        Random random = new Random();
        int index = random.nextInt(remainingBooks.size());
        correctAnswer = remainingBooks.get(index);
        remainingBooks.remove(index);

        String hint = String.format("Tác giả: %s\nMô tả: %s\nNăm: %s", correctAnswer.getAuthor(), correctAnswer.getDescription(), correctAnswer.getYear());
        descriptionText.setText(hint);

        guessTextField.clear();
        submitButton.setDisable(false);
        nextButton.setDisable(true);
        skipButton.setDisable(false);
    }

    @FXML
    private void handleSubmitButton() {
        String userGuess = guessTextField.getText().trim();

        if (userGuess.equalsIgnoreCase(correctAnswer.getTitle())) {
            score += 5;
            updateScoreLabel();
            submitButton.setDisable(true);
            incorrectGuessCount = 0;
            UIHelper.showAlert(Alert.AlertType.INFORMATION, "Chúc mừng, bạn đã đoán đúng!");
            nextButton.setDisable(false);
        } else {
            incorrectGuessCount++;
            if (incorrectGuessCount < 3) {
                UIHelper.showAlert(Alert.AlertType.ERROR, "Đáp án sai. Bạn còn " + (3 - incorrectGuessCount) + " lần thử nữa.");
                nextButton.setDisable(true);
            } else {
                incorrectWrongCount++;
                wrongLabel.setText("Số lần bỏ qua: " + incorrectWrongCount);
                UIHelper.showAlert(Alert.AlertType.ERROR, "Đáp án sai. Đáp án đúng là: " + correctAnswer.getTitle());
                submitButton.setDisable(true);
                incorrectGuessCount = 0;
                nextButton.setDisable(false);
            }
        }
    }


    @FXML
    private void handleNextButton() {
        loadGame();
    }

    @FXML
    private void handleSkipButton() {
        incorrectWrongCount++;
        wrongLabel.setText("Số lần bỏ qua: " + incorrectWrongCount);
        UIHelper.showAlert(Alert.AlertType.INFORMATION, "Bạn đã bỏ qua câu hỏi này!");
        loadGame();
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Điểm: " + score);
    }

    private void disableGame() {
        submitButton.setDisable(true);
        nextButton.setDisable(true);
        guessTextField.setDisable(true);
        skipButton.setDisable(true);
    }

    @FXML
    void actionBack(MouseEvent event) {
        UIHelper.switchWindow(event, "user", "back to user");
    }
}
