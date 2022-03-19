package io.wollinger.hkmanager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageManager {
    private static BufferedImage geo;
    private static BufferedImage mask;
    private static BufferedImage mask_steel;
    private static BufferedImage vessel;
    private static BufferedImage vessel_steel;

    private static BufferedImage hud;
    private static BufferedImage hud_god;
    private static BufferedImage hud_steel;

    private static BufferedImage[] nails = new BufferedImage[4];

    public static void load() {
        geo = load("geo");
        mask = load("mask");
        mask_steel = load("mask_steel");
        vessel = load("vessel");
        vessel_steel = load("vessel_steel");

        hud = load("hud");
        hud_god = load("hud_god");
        hud_steel = load("hud_steel");

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

    public BufferedImage getVessel() {
        return vessel;
    }

    public BufferedImage getNail(int index) {
        return nails[index];
    }
}
