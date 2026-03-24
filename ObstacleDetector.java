


import swiftbot.*;

public class ObstacleDetector {
	private SwiftBotAPI swiftBot;
    private ImageProcessor imageProcessor;
    private int ObCount;
    private long FirstObTime;

    public ObstacleDetector(SwiftBotAPI swiftBot, ImageProcessor imageProcessor) {
       
    	this.swiftBot = swiftBot;
    	this.ObCount = 0;
    	this.FirstObTime = 0;
    	this.imageProcessor = imageProcessor;
    }

    public boolean isObstacleDetected() {
        // read distance sensor
        // return true if distance <= OBSTACLE DIST
try {
	
  	double dist = swiftBot.useUltrasound();
    	if (dist <= Config.OBSTACLE_DISTANCE) {
		 return true;
		}
    	else {
			return false;
		}
    	
    	} catch (Exception e) {
	System.out.println("Ultrasound error: " + e.getMessage());
	return false;
}
  
    	
    }

    public String handleObstacle() {
        // blink red 3 times
    	try {
			
		
   for (int i = 0; i < 3; i++) {
	
	   swiftBot.fillUnderlights(Config.COLOUR_RED);
    	Thread.sleep(200);
    	swiftBot.fillUnderlights(Config.COLOUR_OFF);
    	Thread.sleep(200);
    	swiftBot.move(-Config.DEFAULT_SPEED, Config.DEFAULT_SPEED, Config.TURN_DURATION); //turn swiftbot clockwise if obstacle detected
    	                                                                                  // Test and check
    	}
  } catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
        // display distance and time mark
    	
    	double dist = 0;
        try {
            Thread.sleep(500);
            dist = swiftBot.useUltrasound();
        } catch (Exception e) {
            System.out.println("Ultrasound error: " + e.getMessage());
        }
        System.out.println("OBSTACLE DETECTED - Distance:" + String.format("%.2f", dist) + "cm");
     

        // capture obstacle 
    String ImageMark = imageProcessor.GetObImage();
 
    ObCount++;
        // return image file path
    if (ObCount == 1) {
    	FirstObTime = System.currentTimeMillis();
	}
    	return ImageMark;
    
    }

    public boolean shouldTerminate() {
        // return true if obstacleCount >= Max number of obstacles
    	if (ObCount < Config.MAX_OBSTACLES) {
			return false;
		} 
    	
    	long TimePassed = System.currentTimeMillis() - FirstObTime;
    	
    	if ( ObCount >= Config.MAX_OBSTACLES && TimePassed < Config.OBSTACLE_TIME_WINDOW) {  //obstacleCount >= Config.MAX_OBSTACLES && - add back if needed
    		System.out.println("MAX HIT - 5 Obstacles detected");
			return true;
		}
    	else {
    		//reset 
    		ObCount = 0;
    		FirstObTime = 0;
			return false;
		}
    	
    }
}
