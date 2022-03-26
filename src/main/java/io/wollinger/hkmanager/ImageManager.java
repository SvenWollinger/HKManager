package io.wollinger.hkmanager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageManager {
    public static BufferedImage bg;
    public static BufferedImage geo;
    public static BufferedImage mask;
    public static BufferedImage mask_steel;
    public static BufferedImage vessel;
    public static BufferedImage vessel_steel;

    public static BufferedImage hud;
    public static final Point hud_point = new Point(31,50);
    public static BufferedImage hud_steel;
    public static BufferedImage hud_god;

    public static final BufferedImage[] nails = new BufferedImage[4];

    public static BufferedImage gear;
    public static BufferedImage folder;
    public static BufferedImage trash;

    public static BufferedImage grub;
    public static BufferedImage grub_square;

    public static void load() {
        bg = load("bg");
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

        gear = load("gear");
        trash = load("trash");
        folder = load("folder");
        grub = load("grub");
        grub_square = load("grub_square");
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
