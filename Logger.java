
 
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileWriter;

public class Logger {
	
	private String logFilePath;
    private List<String> MovementHistory;
    private List<String> ObImagePaths;
    private double TotalDist;
    private int TotalOb;
    private double Threshold;
    private double BrightestLight;
    private long StartTime;

    public Logger(double threshold) {
        // create logs directory if it doesn't exist
        // generate log filename using time mark
        // set all variables
        // record startTime 
    	
    	MovementHistory = new ArrayList<>();
    	ObImagePaths = new ArrayList<>();
    	
    	this.Threshold = threshold;
    	this.TotalDist = 0;
    	this.TotalOb = 0;
    	this.BrightestLight = 0;
    	this.StartTime = System.currentTimeMillis();
    	
    	File dir = new File(Config.LOG_DIRECTORY);
    	if (!dir.exists()) dir.mkdirs();
    	
    	String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(Config.TIMESTAMP_FORMAT));
    	this.logFilePath = Config.LOG_DIRECTORY + "/log_" + timeStamp + ".txt";
    }

    public void logMovement(String direction, double distance) {
        // add entry to movementHistory list
        // add dist to totalDist
    	
    	MovementHistory.add(direction + " " + String.format("%.2f", distance) + "cm");
    	TotalDist += distance;
    }

    public void logObstacle(String imagePath) {
        // add image location to obImgPaths
      
    	
    	ObImagePaths.add(imagePath);
    	TotalOb++;
    }

    public void updateBrightestLight(double intensity) {
        // if intensity > brightestLight, update it
    	if (intensity > BrightestLight) {
    		BrightestLight = intensity;
		}
    }

    public void writeLogFile(long endTime) {
    	try {
            PrintWriter writer = new PrintWriter(new FileWriter(logFilePath));
            
            // write header
            writer.println("========================================");
            writer.println("        SEARCH FOR LIGHT - LOG FILE     ");
            writer.println("========================================");
            writer.println("Start Time: " + LocalDateTime.now()
                           .format(DateTimeFormatter.ofPattern(Config.LOG_TIMESTAMP_FORMAT)));
            writer.println("----------------------------------------");
            // write threshold
            writer.println("Threshold: " + String.format("%.2f", Threshold));
            // write brightest light
            writer.println("Brightest Light: " + String.format("%.2f", BrightestLight));
            // write movement history (loop through the list)
            writer.println("--- Movement History ---");
            for (String entry : MovementHistory) writer.println(entry);
            // write obstacle count
            writer.println("totalObstacles: " + TotalOb);
            // write obstacle image paths (loop through the list)
            writer.println("--- Obstacle Images ---");
            for (String entry2 : ObImagePaths) writer.println(entry2);
            
            writer.println("End Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern(Config.LOG_TIMESTAMP_FORMAT)));
            
            writer.close();
        } catch (Exception e) {
            System.out.println("FILE WRITE ERROR: " + e.getMessage());
        }
    }

    public String getLogFilePath() {
        return logFilePath;
    }
}
