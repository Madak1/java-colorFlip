package hu.ppke.itk.madak1;

import hu.ppke.itk.madak1.gui.nodes.*;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class GuiMaster extends GameMaster {

    public static double MIN_XX = 850;
    public static double MIN_YY = 700;

    public static Color PRIMARY_COLOR = Color.GOLD;
    public static Color SECONDARY_COLOR = Color.STEELBLUE;

    public static boolean WIN = false;
    public static boolean SOLVER_ON = false;

    private final Stage stage;
    private final StackPane root;

    private final TimerLabel timerLabel;
    private OptionButton[] buttons;
    private Board board;

    private int tileNumber;
    private double tmpBoardMultiply;

    /**
     * Constructor
     *  - Init some variables
     * @param stage The Stage
     */
    public GuiMaster(Stage stage){
        this.stage = stage;
        this.root = new StackPane(new Pane());
        this.timerLabel = new TimerLabel(MIN_YY * 0.1D);
        this.tmpBoardMultiply = 1;
    }

    /**
     * Option buttons setter
     *  - It set the buttons
     *  - Set the actions for the buttons
     */
    @Override
    protected void showOptions() {
        this.buttons = new OptionButton[super.options.length];

        double width = MIN_XX / super.options.length;
        double height = width * 0.3D;
        double textSize = width * 0.08D;
        double hintSize = width * 0.07D;

        for (int i = 0; i < super.options.length; i++) this.buttons[i] =
                new OptionButton(width, height, super.options[i], textSize, super.description[i], hintSize);
        this.optionsActionSetup();
    }

    /**
     * Button action setter
     *  - It set the actions for the buttons
     */
    private void optionsActionSetup(){
        if (super.phase == Phase.MENU){
            this.buttons[0].setOnMouseClicked(mouseEvent -> {
                super.phaseChanger(Phase.START);
                this.showOptions();
                this.root.getChildren()
                        .set(0, new MenuPane(super.title, this.buttons, MIN_XX * 0.1D, MIN_YY * 0.05D));
            });
            this.buttons[1].setOnMouseClicked(mouseEvent -> {
                super.phaseChanger(Phase.EXIT);
                this.quitGame();
            });
        }
        else if (super.phase == Phase.START){
            this.buttons[0].setOnMouseClicked(mouseEvent -> {
                super.phaseChanger(Phase.NEW_GAME);
                this.createNewGame();
            });
            this.buttons[1].setOnMouseClicked(mouseEvent -> {
                try {
                    super.phaseChanger(Phase.LOAD_GAME);
                    this.loadPreviousGame();
                } catch (IOException e) {
                    super.phaseChanger(Phase.NEW_GAME);
                    this.createNewGame();
                }
            });
        }
        else if (super.phase == Phase.NEW_GAME){
            this.buttons[0].setOnMouseClicked(event -> {
                super.phaseChanger(Phase.GAME);
                this.tileNumber = new Random().nextInt(19) + 2;
                super.logic = new GameLogic(tileNumber);
                this.timerLabel.startTimer(0L);
                this.playGame();
            });
            for (int i = 1; i < 20; i++) {
                final int tmpIndex = i;
                this.buttons[i].setOnMouseClicked(event -> {
                    super.phaseChanger(Phase.GAME);
                    this.tileNumber = tmpIndex + 1;
                    super.logic = new GameLogic(tileNumber);
                    this.timerLabel.startTimer(0L);
                    this.playGame();
                });
            }
        }
        else if (super.phase == Phase.GAME){
            this.buttons[1].setOnMouseClicked(mouseEvent -> {
                SOLVER_ON = !SOLVER_ON;
                this.board.updateTilesHintStrokes();
            });
            this.buttons[2].setOnMouseClicked(mouseEvent -> {
                this.buttons[2].setDisable(true);
                FillTransition fillTransition =
                        new FillTransition(Duration.millis(300), (Shape) this.board.getChildren().get(0));
                fillTransition.setAutoReverse(true);
                fillTransition.setCycleCount(2);
                try {
                    super.logic.saveGame(this.timerLabel.getElapsedTime());
                    fillTransition.setToValue(Color.WHITESMOKE);
                } catch (IOException e) {
                    fillTransition.setToValue(Color.RED);
                }finally {
                    fillTransition.play();
                    fillTransition.setOnFinished(actionEvent -> this.buttons[2].setDisable(false));
                }
            });
            this.buttons[3].setOnMouseClicked(mouseEvent -> {
                super.phaseChanger(Phase.MENU);
                this.showOptions();
                this.root.getChildren()
                        .set(0, new MenuPane(super.title, this.buttons, MIN_XX * 0.1D, MIN_YY * 0.05D));
            });
            this.buttons[4].setOnMouseClicked(mouseEvent -> {
                super.phaseChanger(Phase.EXIT);
                this.quitGame();
            });
        }
    }

    /**
     * Setup for GUI mode
     *  - Set the background
     *  - Set name, icon and min sizes
     *  - Set the window to the center of the screen
     *  - Set the phase and set the buttons
     *  - Set a handler for window resizing
     */
    @Override
    public void gameMain() {
        this.root.setBackground(
                new Background(new BackgroundFill(Color.SLATEGRAY.darker(), CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene = new Scene(this.root);

        this.stage.setTitle("ColorFlip");
        this.stage.getIcons().add(new Image("icon.png"));
        this.stage.setMinWidth(MIN_XX);
        this.stage.setMinHeight(MIN_YY);

        double screenResX = Screen.getPrimary().getBounds().getWidth();
        double screenResY = Screen.getPrimary().getBounds().getHeight();
        this.stage.setX((screenResX - MIN_XX)/2.0D);
        this.stage.setY((screenResY - MIN_YY)/3.0D);

        super.phaseChanger(Phase.MENU);
        this.showOptions();
        this.root.getChildren()
                .set(0, new MenuPane(super.title, this.buttons, MIN_XX * 0.1D, MIN_YY * 0.05D));

        this.stage.setScene(scene);
        this.stage.show();

        scene.heightProperty().addListener((observableValue, oldY, newY) -> {
            this.tmpBoardMultiply += (newY.doubleValue() - oldY.doubleValue())/500D;
            if(super.phase == Phase.GAME){
                this.board.setScaleX(this.board.getScaleX() + (newY.doubleValue() - oldY.doubleValue())/500D);
                this.board.setScaleY(this.board.getScaleY() + (newY.doubleValue() - oldY.doubleValue())/500D);
            }
        });
    }

    /**
     * Setup new GUI game
     *  - Show the buttons which set the size of the board
     */
    @Override
    protected void createNewGame() {
        this.buttons = new OptionButton[20];
        double size = MIN_XX / 7;
        double textSize = size * 0.3D;
        double hintSize = size * 0.3D;
        this.buttons[0] = new OptionButton(size, size, "RND", textSize , "Set", hintSize);
        for (int i = 1; i < this.buttons.length; i++)
            this.buttons[i] = new OptionButton(size, size, String.valueOf(i+1), textSize, "Set", hintSize);
        this.optionsActionSetup();
        this.root.getChildren().set(0, new SettingsPane(this.buttons));
    }

    /**
     * Setup previous GUI game
     *  - Try load previous game
     *     --> If the program can't load previous game then start a new one
     * @throws IOException This exception will throw if something go wrong while loading prev game
     */
    @Override
    protected void loadPreviousGame() throws IOException {
        super.phaseChanger(Phase.GAME);
        super.logic = new GameLogic(new FileInputStream("savedGame.txt"));
        BufferedReader save = new BufferedReader(new InputStreamReader(new FileInputStream("savedGame.txt")));
        this.timerLabel.startTimer(Integer.parseInt(save.readLine()));
        this.tileNumber = super.logic.getBoardSize();
        this.playGame();
    }

    /**
     * Setup game phase
     *  - Set the buttons
     *  - Create board
     *  - Create primary and secondary color changer
     */
    @Override
    protected void playGame() {
        WIN = false;
        SOLVER_ON = false;
        this.showOptions();

        this.board = new Board(MIN_YY < MIN_XX ? MIN_YY * 0.4D : MIN_XX * 0.4D, this.tileNumber, super.logic);
        this.board.createBoard();
        this.board.setScaleX(this.tmpBoardMultiply);
        this.board.setScaleY(this.tmpBoardMultiply);
        this.board.setOnMouseClicked(event -> { if(WIN) winGame(); });

        ColorPicker primaryColorPicker = new ColorPicker(MIN_XX * 0.04, new Color[]{
                Color.GOLD,
                Color.ORANGE,
                Color.FIREBRICK,
                Color.DIMGRAY,
                Color.PINK
        }, PRIMARY_COLOR);
        primaryColorPicker.setOnMouseClicked(mouseEvent -> {
            PRIMARY_COLOR = primaryColorPicker.getActColor();
            this.board.updateTilesColors();
            for(OptionButton button : this.buttons) button.updateColors();
        });

        ColorPicker secondaryColorPicker = new ColorPicker(MIN_XX * 0.04, new Color[]{
                Color.STEELBLUE,
                Color.GREEN,
                Color.PURPLE,
                Color.LIGHTGRAY,
                Color.DARKKHAKI
        }, SECONDARY_COLOR);
        secondaryColorPicker.setOnMouseClicked(mouseEvent -> {
            SECONDARY_COLOR = secondaryColorPicker.getActColor();
            this.board.updateTilesColors();
            for(OptionButton button : this.buttons) button.updateColors();
        });

        GamePane gamePane =
                new GamePane(this.timerLabel, this.board, primaryColorPicker, secondaryColorPicker, this.buttons);
        gamePane.setup();
        this.root.getChildren().set(0, gamePane);
    }

    /**
     * Setup win screen
     *  - Show a Win label and the number of the steps
     *  - Remove useless buttons
     */
    private void winGame(){
        Label label = new Label("YOU WIN!\nSteps: " + super.logic.getStepCounter());
        label.setFont(Font.font("Arial", FontWeight.BOLD, MIN_YY * 0.1D));
        label.setTextFill(Color.WHITESMOKE);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setEffect(new DropShadow(15.0D, 3.0D, 3.0D, Color.BLACK));

        GamePane gamePane = (GamePane) this.root.getChildren().get(0);
        StackPane gamePaneCenter = (StackPane) gamePane.getCenter();
        gamePaneCenter.getChildren().add(label);

        StackPane gamePaneBottom = (StackPane) gamePane.getBottom();
        HBox buttonPane = (HBox) gamePaneBottom.getChildren().get(0);
        buttonPane.getChildren().remove(0);
        buttonPane.getChildren().remove(0);

        this.board.setOpacity(0.3);
        this.board.setDisable(true);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5D), label);
        fadeTransition.setFromValue(0D);
        fadeTransition.setToValue(1D);
        fadeTransition.play();

        this.timerLabel.stopTimer();
    }

    /**
     * Exit the program
     *  - It closes the program
     */
    @Override
    protected void quitGame() {
        System.exit(0);
    }
}
