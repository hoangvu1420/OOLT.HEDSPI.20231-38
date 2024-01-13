package hust.hedspi.coganhgame.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class PresentationController {

    @FXML
    public ImageView imvGif;
    @FXML
    private ImageView boardImageId;

    private int currentSlide = 0;

    @FXML
    private void initialize() {
        loadSlide(currentSlide);
    }

    @FXML
    private void onBackClick() {
        if (currentSlide > 0) {
            currentSlide--;
            loadSlide(currentSlide);
        }
    }

    @FXML
    private void onNextClick() {
        if (currentSlide < 10) {
            currentSlide++;
            loadSlide(currentSlide);
        }
    }

    private void loadSlide(int slideIndex) {
        switch (slideIndex) {
            case 0 -> {
                boardImageId.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/hust/hedspi/coganhgame/View/images/1_intro.png"))));
                imvGif.setImage(null);
            }
            case 1 -> {
                boardImageId.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/hust/hedspi/coganhgame/View/images/2_plain_board.png"))));
                imvGif.setImage(null);
            }
            case 2 -> {
                boardImageId.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/hust/hedspi/coganhgame/View/images/3_start_board.png"))));
                imvGif.setImage(null);
            }
            case 3 -> {
                boardImageId.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/hust/hedspi/coganhgame/View/images/4_how.png"))));
                imvGif.setImage(null);
            }
            case 4 -> {
                boardImageId.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/hust/hedspi/coganhgame/View/images/5_ways.png"))));
                imvGif.setImage(null);
            }
            case 5 -> {
                boardImageId.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/hust/hedspi/coganhgame/View/images/6_ganh.png"))));
                imvGif.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/hust/hedspi/coganhgame/View/images/ganh_gif.gif"))));
            }
            case 6 -> {
                boardImageId.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/hust/hedspi/coganhgame/View/images/7_vay.png"))));
                imvGif.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/hust/hedspi/coganhgame/View/images/vay_gif.gif"))));
            }
            case 7 -> {
                boardImageId.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/hust/hedspi/coganhgame/View/images/8_open.png"))));
                imvGif.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/hust/hedspi/coganhgame/View/images/open_gif.gif"))));
            }
            case 8 -> {
                boardImageId.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/hust/hedspi/coganhgame/View/images/9_end.png"))));
                imvGif.setImage(null);
            }
        }
    }
}
