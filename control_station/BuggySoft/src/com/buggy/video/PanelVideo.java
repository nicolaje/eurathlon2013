package com.buggy.video;

import java.nio.ByteBuffer;


import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritablePixelFormat;
import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;
import uk.co.caprica.vlcj.player.direct.BufferFormat;
import uk.co.caprica.vlcj.player.direct.BufferFormatCallback;
import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;
import uk.co.caprica.vlcj.player.direct.format.RV32BufferFormat;


import com.sun.jna.Memory;
import javafx.scene.image.PixelFormat;

/**
 *
 * @author Yoann
 */
public class PanelVideo extends Canvas {

    private final PixelWriter pixelWriter;
    private final WritablePixelFormat<ByteBuffer> pixelFormat;
    public final DirectMediaPlayerComponent mediaPlayerComponent;
    public int sizeX = 320;
    public int sizeY = 240;

    public PanelVideo() {

        this.setWidth(sizeX);
        this.setHeight(sizeY);
        pixelWriter = this.getGraphicsContext2D().getPixelWriter();
        pixelFormat = PixelFormat.getByteBgraInstance();

        mediaPlayerComponent = new TestMediaPlayerComponent();
    }

    /**
     * Implementation of a direct rendering media player component that renders
     * the video to a JavaFX canvas.
     */
    private class TestMediaPlayerComponent extends DirectMediaPlayerComponent {

        @Override
        public void display(DirectMediaPlayer mediaPlayer, Memory[] nativeBuffers, BufferFormat bufferFormat) {
            Memory nativeBuffer = nativeBuffers[0];
            ByteBuffer byteBuffer = nativeBuffer.getByteBuffer(0, nativeBuffer.size());
            pixelWriter.setPixels(0, 0, bufferFormat.getWidth(), bufferFormat.getHeight(), pixelFormat, byteBuffer, bufferFormat.getPitches()[0]);
        }

        public TestMediaPlayerComponent() {
            super(new TestBufferFormatCallback());
        }
    }

    class TestBufferFormatCallback implements BufferFormatCallback {

        @Override
        public BufferFormat getBufferFormat(int sourceWidth, int sourceHeight) {
            return new RV32BufferFormat(sourceWidth, sourceHeight);
        }
    }
}
