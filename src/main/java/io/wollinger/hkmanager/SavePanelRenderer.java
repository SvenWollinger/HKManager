package io.wollinger.hkmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class SavePanelRenderer extends JPanel {
    private Save save;

    public SavePanelRenderer(Save save) {
        this.save = save;
    }

    @Override
    public void paint(Graphics g) {
        int mode = save.getMode();

        BufferedImage hud = ImageManager.hud;
        Point point = ImageManager.hud_point;
        float scaling = ImageManager.hud_scale;
        switch(mode) {
            case 1:
                hud = ImageManager.hud_steel;
                point = ImageManager.hud_steel_point;
                scaling = ImageManager.hud_steel_scale;
                break;
            case 2:
                hud = ImageManager.hud_god;
                point = ImageManager.hud_god_point;
                scaling = ImageManager.hud_god_scale;
                break;
        }

        Dimension hudDim = getScaledDimension(hud, new Dimension((int)(getWidth() / scaling), (int)(getHeight() / scaling)));
        g.drawImage(hud, 0, 0, hudDim.width, hudDim.height, this);
        int offsetX = (int) ((hudDim.width * (point.x / 100F)));
        int offsetY = (int) ((hudDim.height * (point.y / 100F)));

        int maskSize = (getWidth() - offsetX) / 9;
        BufferedImage mask = mode == 1 ? ImageManager.mask_steel : ImageManager.mask;
        for(int i = 0; i < save.getMasks(); i++) {
            //We multiply by 1000 so that the dimension grows if possible
            Dimension dim = getScaledDimension(mask, new Dimension(maskSize, maskSize));
            g.drawImage(mask, offsetX + maskSize * i, offsetY, dim.width, dim.height, this);
        }

        BufferedImage nail = ImageManager.nails[save.getNailUpgrades() - 1];
        Dimension nailDim = getScaledDimension(nail, new Dimension(maskSize * 6, maskSize * 5));
        g.drawImage(nail, offsetX, maskSize + offsetY, nailDim.width, nailDim.height, this);

        g.drawRect(0, 0, getWidth()-1, getHeight()-1);
    }

    public static Dimension getScaledDimension(BufferedImage img, Dimension boundary) {
        int original_width = img.getWidth() * 9999;
        int original_height = img.getHeight() * 9999;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }
}
