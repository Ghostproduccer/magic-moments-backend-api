package com.magicmoments.backendapi.service.srv;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class UploadService {
    public BufferedImage overlayImages(Image fgImage, Image bgImage) {

        int bgWidth = bgImage.getWidth(null);
        int bgHeight = bgImage.getHeight(null);
        int fgWidth = fgImage.getWidth(null);
        int fgHeight = fgImage.getHeight(null);

        final BufferedImage finalImage = new BufferedImage(bgWidth, bgHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = finalImage.createGraphics();

        g.drawImage(bgImage, 0, 0, null);

        int x = (bgWidth - fgWidth) / 2;
        int y = (bgHeight - fgHeight) / 2;

        g.drawImage(fgImage, x, y, null);

        g.dispose();

        return finalImage;
    }

}
