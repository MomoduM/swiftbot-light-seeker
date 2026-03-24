

import swiftbot.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ImageProcessor {
	private SwiftBotAPI swiftBot;
    private int CamErrCount;

    public ImageProcessor(SwiftBotAPI swiftBot) {
        this.swiftBot = swiftBot;
        this.CamErrCount = 0;
    }

    public double[] GetNScan() {
        BufferedImage img = captureImageWithRetry();
        if (img == null) return null;
        CamErrCount = 0;
        return calculateColumnIntensities(img);
    }

    public double GetThreshold() {
        System.out.println("Capturing baseline image...");
        BufferedImage img = captureImageWithRetry();
        if (img == null) return -1;
        double threshold = calculateWholeImageIntensity(img);
        System.out.println("Threshold: " + String.format("%.2f", threshold));
        return threshold;
    }

    public String GetObImage() {
        BufferedImage img = captureImageWithRetry();
        if (img == null) return null;

        File dir = new File(Config.OBSTACLE_IMAGE_DIRECTORY);
        if (!dir.exists()) dir.mkdirs();

        String timeMark = LocalDateTime.now()
                           .format(DateTimeFormatter.ofPattern(Config.TIMESTAMP_FORMAT));
        String fileLoc = Config.OBSTACLE_IMAGE_DIRECTORY + "/obstacle_" + timeMark + ".jpg";

        try {
            ImageIO.write(img, "jpg", new File(fileLoc));
            System.out.println("Obstacle image saved: " + fileLoc);
            return fileLoc;
        } catch (Exception e) {
            System.out.println("FILE WRITE ERROR: " + e.getMessage());
            return null;
        }
    }

    private BufferedImage captureImageWithRetry() {
        while (CamErrCount++ < Config.MAX_CAMERA_RETRIES) {
            try {
                return swiftBot.takeStill(ImageSize.SQUARE_480x480);
            } catch (Exception e) {
                CamErrCount++;
                System.out.println("Camera error - retrying... (" + CamErrCount + "/" + Config.MAX_CAMERA_RETRIES + ")");
                try {
                    Thread.sleep(Config.CAMERA_RETRY);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        System.out.println("Critical camera error - terminating.");
        return null;
    }

    private double[] calculateColumnIntensities(BufferedImage image) {
        int W = image.getWidth();
        int H = image.getHeight();
        int columnW = W / 3;

        double[] columnTotals = {0.0, 0.0, 0.0};
        int[] columnPixelCounts = {0, 0, 0};

        for (int x = 0; x < W; x++) {
            for (int y = 0; y < H; y++) {
                int columnIndex = Math.min(x / columnW, 2);

                int pixel = image.getRGB(x, y);
                int r = (pixel >> 16) & 0xFF;
                int g = (pixel >> 8)  & 0xFF;
                int b = pixel         & 0xFF;

                columnTotals[columnIndex] += (r + g + b) / 3.0;
                columnPixelCounts[columnIndex]++;
            }
        }

        double[] intensities = new double[3];
        for (int i = 0; i < 3; i++) {
            intensities[i] = columnPixelCounts[i] > 0 ? columnTotals[i] / columnPixelCounts[i] : 0.0;
        }
        return intensities;
    }

    private double calculateWholeImageIntensity(BufferedImage image) {
        double total = 0.0;
        int pixelAmount = 0;

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int pixel = image.getRGB(x, y);
                int r = (pixel >> 16) & 0xFF;
                int g = (pixel >> 8)  & 0xFF;
                int b = pixel         & 0xFF;
                total += (r + g + b) / 3.0;
                pixelAmount++;
            }
        }
        return pixelAmount > 0 ? total / pixelAmount : 0.0;
    }
}
