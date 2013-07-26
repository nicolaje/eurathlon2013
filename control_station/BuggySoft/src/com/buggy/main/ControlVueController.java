package com.buggy.main;

import com.buggy.carte.CarteCanvas;
import com.buggy.video.GroupVideo;
import com.buggy.socket.Connection;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Yoann
 */
public class ControlVueController implements Initializable {

    private ObservableList<String> listEtat = FXCollections.observableList(new ArrayList<String>());
    private ObservableList<String> listFocus = FXCollections.observableList(new ArrayList<String>());
    private static final int STOP = 0;
    private static final int START = 1;
    private static final int AUTO = 2;
    private ArrayList<Connection> listSocket = new ArrayList<>();
    private int vitesse = 90;
    public boolean zPressed;
    public boolean sPressed;
    public boolean dPressed;
    public boolean qPressed;
    @FXML
    private ChoiceBox<String> choiceBoxFocus;
    @FXML
    private static CheckBox checkBoxConnection1;
    @FXML
    private static CheckBox checkBoxConnection2;
    @FXML
    private static CheckBox checkBoxConnection3;
    @FXML
    private static CheckBox checkBoxConnection4;
    @FXML
    private static CheckBox checkBoxConnection5;
    @FXML
    private ChoiceBox<String> choiceBoxEtat1;
    @FXML
    private ChoiceBox<String> choiceBoxEtat2;
    @FXML
    private ChoiceBox<String> choiceBoxEtat3;
    @FXML
    private ChoiceBox<String> choiceBoxEtat4;
    @FXML
    private ChoiceBox<String> choiceBoxEtat5;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Pane paneCameraFocus;
    @FXML
    private Button buttonCamera;
    @FXML
    private Label labelVitesse;
    @FXML
    private CheckBox checkBoxPosition1;
    @FXML
    private CheckBox checkBoxPosition2;
    @FXML
    private CheckBox checkBoxPosition3;
    @FXML
    private CheckBox checkBoxPosition4;
    @FXML
    private CheckBox checkBoxPosition5;
    @FXML
    private CheckBox checkBoxUltrason;
    @FXML
    private MenuBar menu;
    @FXML
    private ScrollPane canvasCarte;
    @FXML
    private ScrollPane canvasCarteLocale;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("**************************************");
        System.out.println("Initialize ControlVueController");
        initList();
        initChoiceBox();
        initCheckBoxConnection();
        initCommande();
        initExport();
        initCarte();
        initCamera();
        System.out.println("Initialize ControlVueController done");
        System.out.println("**************************************");
    }

    private void initList() {
        listEtat.addAll("STOP", "START", "AUTO");
        for (int i = 0; i < Main.MyModel.nombreRobot + 0; i++) {
            listFocus.add(numBuggyToString(i));
            listSocket.add(new Connection(i));
        }
    }

    private void initExport() {
        Menu menuExport = new Menu("Export Menu");
        this.menu.getMenus().add(menuExport);
        MenuItem export = new MenuItem("Export");
        export.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                System.out.println("???????? EXPORT ????????????");
            }
        });
        menuExport.getItems().add(export);
    }

    private void initCarte() {
        this.canvasCarteLocale.setContent(new CarteCanvas());
    }

    private void initCamera() {
        this.buttonCamera.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                System.out.println("Video connection at : " + "http://192.168.1.10" + (getFocus() + 1) + ":8080/");
                ((GroupVideo) paneCameraFocus.getChildren().get(0)).panelVideo.mediaPlayerComponent.getMediaPlayer().playMedia("http://192.168.1.10" + (getFocus() + 1) + ":8080/", ":network-caching=50");
            }
        });
        this.paneCameraFocus.getChildren().add(new GroupVideo());
    }

    public String numBuggyToString(int i) {
        if (i < 0 | i > 5) {
            return null;
        } else {
            return "Buggy " + (i + 1);
        }
    }

    public int getFocus() {
        return listFocus.indexOf(choiceBoxFocus.getValue());

    }

    public void setFocus(int i) {
        choiceBoxFocus.setValue(listFocus.get(i));
    }

    private void initCommande() {
        this.anchorPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                KeyCode keyCode = t.getCode();
                int i = getFocus();
                if (keyCode == KeyCode.Z) {
                    if (!zPressed) {
                        zPressed = true;
                        Main.MyModel.robotList.get(i).setOrder(formatOrder());
                        t.consume();
                    }
                } else if (keyCode == KeyCode.S) {
                    if (!sPressed) {
                        sPressed = true;
                        Main.MyModel.robotList.get(i).setOrder(formatOrder());
                        t.consume();
                    }
                } else if (t.getCode() == KeyCode.Q) {
                    if (!qPressed) {
                        qPressed = true;
                        Main.MyModel.robotList.get(i).setOrder(formatOrder());
                        t.consume();
                    }
                } else if (t.getCode() == KeyCode.D) {
                    if (!dPressed) {
                        dPressed = true;

                        Main.MyModel.robotList.get(i).setOrder(formatOrder());
                        t.consume();
                    }
                }
            }
        });

        this.anchorPane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {

                KeyCode keyCode = t.getCode();
                int i = getFocus();

                if (keyCode == KeyCode.Z) {
                    zPressed = false;
                    Main.MyModel.robotList.get(i).setOrder(formatOrder());
                    t.consume();
                } else if (keyCode == KeyCode.S) {
                    sPressed = false;
                    Main.MyModel.robotList.get(i).setOrder(formatOrder());
                    t.consume();
                } else if (keyCode == KeyCode.Q) {
                    qPressed = false;
                    Main.MyModel.robotList.get(i).setOrder(formatOrder());
                    t.consume();
                } else if (keyCode == KeyCode.D) {
                    dPressed = false;
                    Main.MyModel.robotList.get(i).setOrder(formatOrder());
                    t.consume();
                }
            }
        });

        this.anchorPane.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                final int i = getFocus();

                switch (t.getCharacter()) {
                    case "r":
                    case "R":
                        if (vitesse <= 80) {
                            vitesse += 10;
                            labelVitesse.setText("Vitesse " + vitesse + "/90");
                            Main.MyModel.robotList.get(i).setOrder(formatOrder());
                            t.consume();
                        }
                        break;
                    case "f":
                    case "F":
                        if (vitesse >= 10) {
                            vitesse -= 10;
                            labelVitesse.setText("Vitesse " + vitesse + "/90");
                            Main.MyModel.robotList.get(i).setOrder(formatOrder());
                            t.consume();
                        }
                        break;


                    case "e":
                    case "E":
                        Main.MyModel.robotList.get(i).setOrder("CAM L");
                        t.consume();
                        break;

                    case "a":
                    case "A":
                        Main.MyModel.robotList.get(i).setOrder("CAM R");
                        t.consume();

                    case "&":
                    case "1":
                        setFocus(0);
                        t.consume();
                        break;
                    case "é":
                    case "2":
                        setFocus(1);
                        t.consume();
                        break;
                    case "\"":
                    case "3":
                        setFocus(2);
                        t.consume();
                        break;
                    case "'":
                    case "4":
                        setFocus(3);
                        t.consume();
                        break;
                    case "(":
                    case "5":
                        setFocus(4);
                        t.consume();
                        break;
                }
            }
        });
    }

    public String formatOrder() {
        String order;
        if (zPressed) {
            if (dPressed) {
                order = "MOVE " + (vitesse + 90) + " R";
                return order;
            }
            if (qPressed) {
                order = "MOVE " + (vitesse + 90) + " L";
                return order;
            }
            order = "MOVE " + (vitesse + 90);
            return order;

        }

        // z not Pressed
        if (sPressed) {
            if (dPressed) {
                order = "MOVE " + (-vitesse + 90) + " R";
                return order;
            }
            if (qPressed) {
                order = "MOVE " + (-vitesse + 90) + " L";
                return order;
            }

            order = "MOVE " + (-vitesse + 90);
            return order;

        }

        // z and s not Pressed
        if (dPressed) {

            order = "MOVE " + (90) + " R";
            return order;
        }
        if (qPressed) {
            order = "MOVE " + (90) + " L";
            return order;
        }

        order = "MOVE " + 90;
        return order;
    }

    private void initChoiceBox() {
        for (int i = 0; i < Main.MyModel.robotList.size(); i++) {
            getChoiceBoxEtat(i).setItems(listEtat);
            getChoiceBoxEtat(i).setValue(listEtat.get(START));
        }
        choiceBoxFocus.setItems(listFocus);
        choiceBoxFocus.setValue(listFocus.get(0));

        choiceBoxFocus.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                for (int i = 0; i < Main.MyModel.robotList.size(); i++) {
                    if (i != (Character.getNumericValue(t1.charAt(t1.length() - 1)) - 1)) {
                        Main.MyModel.robotList.get(i).setOrder(listEtat.get(STOP));
                        getChoiceBoxEtat(i).setValue(listEtat.get(STOP));
                        System.out.println(numBuggyToString(i) + " " + listEtat.get(STOP));
                    } else {
                        Main.MyModel.robotList.get(i).setOrder(listEtat.get(START));
                        getChoiceBoxEtat(i).setValue(listEtat.get(START));
                        System.out.println(numBuggyToString(i) + " " + listEtat.get(START));
                        System.out.println("Video connection at : " + "http://192.168.1.10" + (i + 1) + ":8080/");
                        ((GroupVideo) paneCameraFocus.getChildren().get(0)).panelVideo.mediaPlayerComponent.getMediaPlayer().playMedia("http://192.168.1.10" + (i + 1) + ":8080/", ":network-caching=50");

                    }
                }
            }
        });
    }

    public ChoiceBox getChoiceBoxEtat(int i) {
        switch (i) {
            case 0:
                return choiceBoxEtat1;
            case 1:
                return choiceBoxEtat2;
            case 2:
                return choiceBoxEtat3;
            case 3:
                return choiceBoxEtat4;
            case 4:
                return choiceBoxEtat5;
            default:
                return null;
        }
    }

    public static CheckBox getCheckBoxConnection(int i) {
        switch (i) {
            case 0:
                return checkBoxConnection1;
            case 1:
                return checkBoxConnection2;
            case 2:
                return checkBoxConnection3;
            case 3:
                return checkBoxConnection4;
            case 4:
                return checkBoxConnection5;
            default:
                return null;
        }
    }

    private void initCheckBoxConnection() {
        for (int i = 0; i < Main.MyModel.nombreRobot; i++) {
            final int numBuggy = i;
            getCheckBoxConnection(numBuggy).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (((CheckBox) event.getSource()).isSelected()) {
                        new Thread(listSocket.get(numBuggy)).start();
                    } else {
                        System.out.println("DECONNEXION BUGGY " + (numBuggy + 1));
                        listSocket.get(numBuggy).disconnect();
                    }
                }
            });
        }



    }
}
