module hust.hedspi.coganhgame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens hust.hedspi.coganhgame to javafx.fxml;
    exports hust.hedspi.coganhgame;
    exports hust.hedspi.coganhgame.Controller;
    exports hust.hedspi.coganhgame.Model;
    exports hust.hedspi.coganhgame.Model.Game;
    exports hust.hedspi.coganhgame.Model.Player;
    exports hust.hedspi.coganhgame.Model.Tile;
    exports hust.hedspi.coganhgame.Model.Move;
    exports hust.hedspi.coganhgame.Exception;
    exports hust.hedspi.coganhgame.Model.Settings;
    opens hust.hedspi.coganhgame.Controller to javafx.fxml;
    exports hust.hedspi.coganhgame.Utilities;
    opens hust.hedspi.coganhgame.Utilities to javafx.fxml;
}