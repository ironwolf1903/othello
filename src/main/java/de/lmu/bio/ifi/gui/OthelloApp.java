package de.lmu.bio.ifi.gui;

import de.lmu.bio.ifi.GameStatus;
import de.lmu.bio.ifi.Othello;
import de.lmu.bio.ifi.players.BMOArtificalPlayerforTest;
import de.lmu.bio.ifi.players.BMOMinimaxPlayer;
import de.lmu.bio.ifi.players.RandomArtificalPlayer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import szte.mi.Move;
import szte.mi.Player;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class OthelloApp extends Application {
    Othello othello;
    GridPane grid;
    Image squareImage;
    Image blackDisk;
    Image redDisk;

    int imageSize = 50;
    Player player2;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        grid = new GridPane();
        grid.setBackground(new Background(new BackgroundFill(Color.web("#35654d"), CornerRadii.EMPTY, Insets.EMPTY)));
        this.othello = new Othello();
        player2 = new BMOMinimaxPlayer();
        player2.init(1,16,new Random());

        int imageDimension = imageSize;
        squareImage = new Image(getClass().getResource("/img/icons8-square-50.png").toExternalForm(),imageDimension,imageDimension,false,false);
        blackDisk = new Image(getClass().getResource("/img/black_circle.png").toExternalForm(),imageDimension,imageDimension,false,false);
        redDisk = new Image(getClass().getResource("/img/red_circle.png").toExternalForm(),imageDimension,imageDimension,false,false);
        generateFieldView();
        Scene scene = new Scene(grid, imageDimension * 8, imageDimension * 8);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public Othello getOthello() {
        return othello;
    }

    private void generateFieldView(){

        grid.getChildren().clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ImageView imageView = null;

                Button button = new Button();
                button.setMinSize(50,50);
                button.setMaxSize(50,50);
                button.setStyle("-fxbackground-color: transparent; -fxbackground-radius: 2px;");
                button.setStyle(
                        "-fx-background-color: transparent; " +
                                "-fx-border-color: black; " +
                                "-fx-border-width: 2px; " +     // Adjust border width if needed
                                "-fx-border-radius: 1px; " +    // No rounded corners
                                "-fx-padding: 0; " +            // Remove padding
                                "-fx-focus-color: transparent; " + // Remove focus color
                                "-fx-faint-focus-color: transparent;" // Remove any faint focus glow
                );
                button.setOpacity(1.0);
                button.setDisable(true);
                if(othello.getBoard().getBoard()[i][j] == 1){
                    imageView = new ImageView(blackDisk);
                    imageView.setOpacity(1.0);

                }
                if (othello.getBoard().getBoard()[i][j] == 2) {
                    imageView = new ImageView(redDisk);
                    imageView.setOpacity(1.0);

                }
                AtomicBoolean isPossibleMove = new AtomicBoolean(false);
                int finalJ = j;
                int finalI = i;
                othello.getPossibleMoves(othello.isPlayerOneTurn()).forEach((move)->{
                    if(move.x == finalJ && move.y == finalI){
                        isPossibleMove.set(true);
                    }
                });
                if(isPossibleMove.get() && othello.getBoard().getBoard()[i][j] == 0){
                    button.setDisable(false);
                    imageView = new ImageView(blackDisk);
                    imageView.setOpacity(0.5);
                    button.setOpacity(0.5);
                    button.setOnAction((x) -> {
                        othello.makeMove(othello.isPlayerOneTurn(),finalJ,finalI);
                        othello.setPlayerOneTurn(false);
                        System.out.println(othello.toString());
                        refreshGrid(grid);
                        generateFieldView();
                        checkAndTerminateGame();
                        //
                        if(othello.isPlayerOneTurn() == false){
                           Move opponentMove = player2.nextMove(new Move(finalJ,finalI),60000,6000);
                            System.out.println("Opponent Move " + opponentMove.x + opponentMove.y);
                           if(opponentMove != null) {
                              boolean valid = othello.makeMove(othello.isPlayerOneTurn(), opponentMove.x, opponentMove.y);
                              othello.setPlayerOneTurn(true);
                              if(!valid){
                                  System.out.println("Invalid Move:" + opponentMove.x + ", " + opponentMove.y);
                              }
                           } else {
                               System.out.println("Null Move");
                               othello.setPlayerOneTurn(true);
                           }
                        }
                        refreshGrid(grid);
                        generateFieldView();
                        checkAndTerminateGame();
                        while(othello.getPossibleMoves(othello.isPlayerOneTurn()).isEmpty()){
                            othello.setPlayerOneTurn(false);
                            Move opponentMove = player2.nextMove(null,60000,6000);
                            othello.makeMove(false,opponentMove.x, opponentMove.y);
                            othello.setPlayerOneTurn(true);
                            refreshGrid(grid);
                            generateFieldView();
                            if(othello.gameStatus() != GameStatus.RUNNING){
                                break;
                            }
                        }
                        //
                        refreshGrid(grid);
                        generateFieldView();
                        checkAndTerminateGame();
                    });
                }
                button.setGraphic(imageView);
                grid.add(button, j, i);

            }
        }
    }

    private void checkAndTerminateGame(){
        if(othello.gameStatus() != GameStatus.RUNNING){
            String message = "";
            switch(othello.getCurrentStatus()){
                case PLAYER_1_WON: {message = "Player 1 has won"; break;}
                case PLAYER_2_WON:{message = "Player 2 has won"; break;}
                case DRAW: {message="There has been a draw"; break;}
                default: {message ="ERROR"; break;}
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(message);
            alert.setContentText(String.format("Player 1's Score: %d    Player 2's Score: %d",othello.getPlayerOneScore(),othello.getPlayerTwoScore()));
            alert.showAndWait();
            othello = new Othello();
            generateFieldView();
            player2 = new BMOMinimaxPlayer();
            player2.init(1,6000,new Random());
        }
    }

    private void generateView(){
        grid.getChildren().clear();
        for (int i = 0; i < 8 ; i++) {
            for (int j = 0; j < 8; j++) {
                ImageView imageView= new ImageView(squareImage);

                if(othello.getBoard().getBoard()[i][j] == 1){
                    imageView = new ImageView(blackDisk);

                }
                if (othello.getBoard().getBoard()[i][j] == 2) {
                    imageView = new ImageView(redDisk);

                }
                AtomicBoolean isPossibleMove = new AtomicBoolean(false);
                int finalJ = j;
                int finalI = i;
                othello.getPossibleMoves(othello.isPlayerOneTurn()).forEach((move)->{
                    if(move.x == finalJ && move.y == finalI){
                        isPossibleMove.set(true);
                    }
                } );
                if(isPossibleMove.get() && othello.getBoard().getBoard()[i][j] == 0){
                    imageView = othello.isPlayerOneTurn() ? new ImageView(blackDisk) : new ImageView(redDisk);
                    imageView.setOpacity(0.1);
                    imageView.addEventHandler(MouseEvent.MOUSE_CLICKED,(x) -> {
                        othello.makeMove(othello.isPlayerOneTurn(),finalJ,finalI);
                        generateView();
                        System.out.println(othello.toString());
                        refreshGrid(grid);
                    });
                }
                grid.add(imageView, j, i);

            }
        }
    }

    private void refreshGrid(GridPane gridToRefresh){
        gridToRefresh.layout();

    }
}
