package io.wollinger.hkmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SavePanelRenderer extends JPanel {
    private final Save save;

    public SavePanelRenderer(Save save) {
        this.save = save;
    }

    @Override
    public void paint(Graphics _g) {
        int mode = save.getMode();

        Graphics2D g = (Graphics2D) _g;

        //Antialising
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHints(hints);

        //Draw background
        g.drawImage(ImageManager.bg, 0, 0, getWidth(), getHeight(), this);

        //Select "hud" (The soul thing on the left), hud point is where we start drawing masks and stuff
        BufferedImage hud = ImageManager.hud;
        Point point = ImageManager.hud_point;
        switch(mode) {
            case 1: hud = ImageManager.hud_steel; break;
            case 2: hud = ImageManager.hud_god; break;
        }

        //Get hud dimensions for point and draw hud
        Dimension hudDim = getScaledDimension(hud, new Dimension(getWidth(), getHeight()));
        g.drawImage(hud, 0, 0, hudDim.width, hudDim.height, this);
        int offsetX = (int) ((hudDim.width * (point.x / 100F)));
        int offsetY = (int) ((hudDim.height * (point.y / 100F)));

        //Draw masks
        int maskSize = (getWidth() - offsetX) / 9;
        BufferedImage mask = mode == 1 ? ImageManager.mask_steel : ImageManager.mask;
        for(int i = 0; i < save.getMasks(); i++) {
            //We multiply by 1000 so that the dimension grows if possible
            Dimension dim = getScaledDimension(mask, new Dimension(maskSize, maskSize));
            g.drawImage(mask, offsetX + maskSize * i, offsetY, dim.width, dim.height, this);
        }

        //Draw nail
        int nailIndex = save.getNailUpgrades() - 1;
        if(nailIndex < 0) nailIndex = 0;
        BufferedImage nail = ImageManager.nails[nailIndex];
        Dimension nailDim = getScaledDimension(nail, new Dimension(maskSize * 6, maskSize * 5));
        g.drawImage(nail, offsetX, maskSize + offsetY, nailDim.width, nailDim.height, this);

        //Draw soul vessels
        for(int i = 0; i < save.getSoulVessels(); i++) {
            Dimension dim = getScaledDimension(ImageManager.vessel, new Dimension(maskSize, maskSize));
            int size = (int) (dim.width / 1.5F);
            g.drawImage(ImageManager.vessel, size / 4 + maskSize * 6 + offsetX + maskSize * i, size / 4 + offsetY + maskSize + (maskSize/4), size, size, this);
        }

        //Get font and set size
        float fSize = (float)(getHeight()/10);
        g.setFont(HKManager.getHKFont().deriveFont(fSize));
        g.setColor(Color.WHITE);

        //Draw percentage
        String percentageString = save.getCompletionPercentage() + "%";
        int percentageWidth = _g.getFontMetrics(g.getFont()).stringWidth(percentageString);
        g.drawString(percentageString, getWidth() / 2F - percentageWidth / 2F, hudDim.height + fSize);

        //Draw hours
        float hours = save.getPlayTime() / 60 / 60;
        int minutes = (int) ((save.getPlayTime()/60) % 60F);
        g.drawString((int)hours + "h " + minutes + "m", getWidth()/10F, hudDim.height + fSize);

        //Draw geo
        String geoString = save.getGeo() + "";
        int geoStringWidth = _g.getFontMetrics(g.getFont()).stringWidth(geoString);
        g.drawImage(ImageManager.geo, getWidth() - maskSize, hudDim.height, maskSize, maskSize, this);
        g.drawString(geoString, getWidth() - maskSize - geoStringWidth, hudDim.height + fSize);

        //Draw version
        int versionWidth = _g.getFontMetrics(g.getFont()).stringWidth(save.getVersion());
        g.drawString(save.getVersion(), getWidth() / 2F - versionWidth / 2F, fSize);
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
