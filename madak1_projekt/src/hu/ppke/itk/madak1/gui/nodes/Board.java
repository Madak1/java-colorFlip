package hu.ppke.itk.madak1.gui.nodes;

import hu.ppke.itk.madak1.GameLogic;
import hu.ppke.itk.madak1.GuiMaster;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class Board extends Pane {

    private final double boardSize;
    private final int tileNum;
    private final Tile[][] tiles;
    private final double tileSize;
    private final double space;

    private final GameLogic logic;

    /**
     * Board constructor
     *  - Init some variables
     * @param size Board size
     * @param tileNum Tile number
     * @param logic The game logic
     */
    public Board(double size, int tileNum, GameLogic logic){
        this.boardSize = size;
        this.tileNum = tileNum;
        this.tiles = new Tile[tileNum][tileNum];
        this.space = this.boardSize * 0.01D;
        this.tileSize = (this.boardSize - (3.0D + tileNum) * this.space) / tileNum;
        this.logic = logic;
    }

    /**
     * Create the game board
     *  - Create the board
     *  - Create the tiles on the board
     */
    public void createBoard(){
        this.setMaxSize(this.boardSize, this.boardSize);

        Rectangle board = new Rectangle(this.boardSize, this.boardSize, Color.SLATEGRAY);
        board.setArcHeight(this.tileSize * 0.15D);
        board.setArcWidth(this.tileSize * 0.15D);
        board.setStroke(Color.BLACK);
        board.setStrokeWidth(this.tileSize * 0.05D);
        board.setStrokeType(StrokeType.OUTSIDE);
        board.setEffect(new DropShadow(20.0D, 0.0D, 0.0D, Color.BLACK.brighter()));
        this.getChildren().add(board);

        for (int posY = 0; posY < this.tileNum; posY++) {
            for (int posX = 0; posX < this.tileNum; posX++) {
                Tile tile = new Tile(this.tileSize, this.logic.getBoard()[posX][posY] == 0);
                tile.setTranslateX(posY * (tileSize + this.space) + 2.0D * this.space);
                tile.setTranslateY(posX * (tileSize + this.space) + 2.0D * this.space);
                final int tmpInputX = posX, tmpInputY = posY;
                tile.setOnMouseClicked(mouseEvent -> {
                    this.newInput(tmpInputX, tmpInputY);
                    this.updateTilesHintStrokes();
                });
                this.getChildren().add(tile);
                this.tiles[posX][posY] = tile;
            }
        }
    }

    /**
     * New input
     *  - Make change on the board
     *  - Check win
     * @param posX X position
     * @param posY Y position
     */
    private void newInput(int posX, int posY){
        this.logic.newInput(posX, posY);
        for (int x = 0; x < this.tileNum; x++) {
            for (int y = 0; y < this.tileNum; y++) {
                if(posX == x || posY == y) this.tiles[x][y].colorFlip();
            }
        }
        if(this.logic.winCheck()) GuiMaster.WIN = true;
    }

    /**
     * Update tiles color
     *  - Update all tiles color
     */
    public void updateTilesColors(){
        for(Tile[] tmpTileArray : this.tiles) for(Tile tile : tmpTileArray) tile.updateColor();
    }

    /**
     * Update tiles stroke
     *  - In a new thread, the program search for the solution
     */
    public void updateTilesHintStrokes(){
        new Thread(() -> {
            try {
                String solution = "";
                if(GuiMaster.SOLVER_ON && !GuiMaster.WIN)
                    solution = logic.solver().substring(1, logic.solver().length() - 1);
                for(Tile[] tmpTileArray : tiles) for (Tile tile : tmpTileArray) tile.setHelpStroke(false);
                if(GuiMaster.SOLVER_ON && !GuiMaster.WIN) {
                    String[] hints = solution.split(", ");
                    for(String hint : hints){
                        int posX = Integer.parseInt(hint.substring(0, hint.indexOf(' ')));
                        int posY = Integer.parseInt(hint.substring(hint.indexOf(' ') + 1));
                        tiles[posX - 1][posY - 1].setHelpStroke(true);
                    }
                }
            }catch (Exception ignored){}
        }).start();
    }

}
