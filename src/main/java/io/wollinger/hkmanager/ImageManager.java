package io.wollinger.hkmanager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageManager {
    public static BufferedImage geo;
    public static BufferedImage mask;
    public static BufferedImage mask_steel;
    public static BufferedImage vessel;
    public static BufferedImage vessel_steel;

    public static BufferedImage hud;
    public static Point hud_point = new Point(52,43);
    public static float hud_scale = 2;
    public static BufferedImage hud_steel;
    public static Point hud_steel_point = new Point(31, 50);
    public static float hud_steel_scale = 1;
    public static BufferedImage hud_god;
    public static Point hud_god_point = new Point(40, 55);
    public static float hud_god_scale = 1.5F;

    public static BufferedImage[] nails = new BufferedImage[4];

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
            return ImageIO.read(ImageManager.class.getResourceAsStream("/io/wollinger/hkmanager/img/" + name + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
