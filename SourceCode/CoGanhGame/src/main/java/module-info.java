module hust.hedspi.coganhgame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens hust.hedspi.coganhgame to javafx.fxml;
    exports hust.hedspi.coganhgame;
    exports hust.hedspi.coganhgame.Controller;
    opens hust.hedspi.coganhgame.Controller to javafx.fxml;
}