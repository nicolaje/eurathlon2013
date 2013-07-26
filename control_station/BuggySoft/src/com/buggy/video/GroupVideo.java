package com.buggy.video;

import java.io.File;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;

import static com.buggy.main.Main.MyModel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yoann
 */
public final class GroupVideo extends Group {

    public final PanelVideo panelVideo;
    public final Button videoShoot;

    public GroupVideo() {
        this.panelVideo = new PanelVideo();
        this.panelVideo.setLayoutX(20);
        this.getChildren().add(this.panelVideo);


        this.videoShoot = new Button("Snapshot");
        this.videoShoot.setLayoutX(95);
        this.videoShoot.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                // Screen !
                
                File file = new File("CanvasImage" + MyModel.nombreScreen + ".png");
                WritableImage img = new WritableImage(320, 240);
                panelVideo.snapshot(null, img);
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", file);
                    MyModel.nombreScreen++;
                
                    System.out.println("Snapshot done !");
                    
                } catch (IOException ex) {
                    Logger.getLogger(GroupVideo.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        this.getChildren().add(this.videoShoot);
    }
}
