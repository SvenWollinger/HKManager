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
        boolean ss = save.isSteelsoul();

        int maskSize = getWidth() / 9;
        BufferedImage mask = ss ? ImageManager.mask_steel : ImageManager.mask;
        for(int i = 0; i < save.getMasks(); i++) {
            //We multiply by 1000 so that the dimension grows if possible
            Dimension dim = getScaledDimension(new Dimension(mask.getWidth()*1000, mask.getHeight()*1000), new Dimension(maskSize, maskSize));
            g.drawImage(mask, maskSize * i, 0, dim.width, dim.height, this);
        }

        BufferedImage nail = ImageManager.nails[save.getNailUpgrades() - 1];
        Dimension nailDim = getScaledDimension(new Dimension(nail.getWidth()*1000, nail.getHeight()*1000), new Dimension(maskSize, maskSize * 2));
        g.drawImage(nail, 0, maskSize, nailDim.width, nailDim.height, this);
    }

    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {
        int original_width = imgSize.width;
        int original_height = imgSize.height;
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
