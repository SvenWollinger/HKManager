package io.wollinger.hkmanager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageManager {
    private static BufferedImage geo;
    private static BufferedImage mask;
    private static BufferedImage[] nails = new BufferedImage[4];

    public static void load() {
        geo = load("geo");
        mask = load("mask");
        for(int i = 0; i < nails.length; i++)
            nails[i] = load("nail" + (i + 1));
    }

    private static BufferedImage load(String name) {
        try {
            return ImageIO.read(ImageManager.class.getResourceAsStream("/io/wollinger/hkmanageR/img/" + name + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BufferedImage getGeo() {
        return geo;
    }

    public BufferedImage getMask() {
        return mask;
    }

    public BufferedImage getNail(int index) {
        return nails[index];
    }
}
