

import swiftbot.Button;
import swiftbot.SwiftBotAPI;
import java.util.Scanner;

public class SearchForLight {
	public static void main(String[] args) {
       
		SwiftBotAPI swiftBot = SwiftBotAPI.INSTANCE;
		
       
		
		ImageProcessor imageProcessor = new ImageProcessor(swiftBot);
		MovementController movementController = new MovementController(swiftBot);
		ObstacleDetector obstacleDetector = new ObstacleDetector(swiftBot, imageProcessor);
		Logger logger = new Logger(0);
		
        // wait for button A
		try {
		final boolean[] started = {false};

		swiftBot.enableButton(Button.A, () -> {
		    started[0] = true;
		});

		// wait until button A is pressed
		System.out.println("Press button A to start...");
		while (!started[0]) {
		    Thread.sleep(100);
		}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	
        // display welcome message
		
		System.out.println("=================");
        System.out.println("     WELCOME     ");
        System.out.println("=================");
        // adaptive speed setup
        
        Scanner scanner = new Scanner(System.in);
        AdaptiveSpeedController adaptiveSpeed = new AdaptiveSpeedController(scanner);
        
        // capture threshold
        double threshold = imageProcessor.GetThreshold();

        if (threshold == -1) {
            System.out.println("Critical camera error - shutting down.");
            System.exit(0);
        }

        logger = new Logger(threshold);
        
         // check button X or shouldTerminate
        boolean running = true;
        
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
        
        
        final boolean[] stopped = {false};
        swiftBot.enableButton(Button.X, () -> {
            stopped[0] = true;
        });
        while (running) {
            // capture and analyse img
        	System.out.println("Capturing image...");
            double[] intensities = imageProcessor.GetNScan();
System.out.println("Image captured.");
            if (intensities == null) {
                System.out.println("Camera failed - stopping.");
                running = false;
                continue;
            }

            // decide direction = highest intensity
         
            String direction;
            if (intensities[1] >= intensities[0] && intensities[1] >= intensities[2]) {
                direction = "centre";
            } else if (intensities[0] >= intensities[2]) {
                direction = "left";
            } else {
                direction = "right";
            }

            // check for obstacle
            boolean obstacleDetected = obstacleDetector.isObstacleDetected();

            // movement
            if (obstacleDetected) {
                System.out.println("Obstacle detected - stopping.");
                movementController.stopRobot();
               String ImagePath = obstacleDetector.handleObstacle();
               logger.logObstacle(ImagePath);
               
               
               String DifDir;
               if (direction.equals("centre")) {
                   DifDir = intensities[0] >= intensities[2] ? "left" : "right";
               } else if (direction.equals("left")) {
                   DifDir = intensities[1] >= intensities[2] ? "centre" : "right";
               } else {
                   DifDir = intensities[1] >= intensities[0] ? "centre" : "left";
               }
               System.out.println("Redirecting to: " + DifDir);
               double maxIntensity = Math.max(intensities[0], Math.max(intensities[1], intensities[2]));
               movementController.turnAndMove(DifDir, adaptiveSpeed.calculateSpeed(maxIntensity, threshold));
               logger.logMovement(DifDir, 0.02);

               
               
               
               
               
            } else {
            	double maxIntensity = Math.max(intensities[0], Math.max(intensities[1], intensities[2]));
            	movementController.turnAndMove(direction, adaptiveSpeed.calculateSpeed(maxIntensity, threshold));
            	logger.logMovement(direction, 0.02); 
                logger.updateBrightestLight(maxIntensity); 
                
                if (maxIntensity > threshold * 1.5) {
                    System.out.println("=== BRIGHT LIGHT SOURCE DETECTED ===");
                    System.out.println("Intensity: " + String.format("%.2f", maxIntensity));
                    swiftBot.fillUnderlights(Config.COLOUR_YELLOW);
                }
            }

            if (stopped[0]) running = false;

            if (obstacleDetector.shouldTerminate()) {
           
                System.out.println("--- WARNING: 5 obstacles detected in under 5 minutes ---");
                System.out.println("--- Type TERMINATE to end the program ---");
                System.out.println("--- Any other input will reset and continue ---");
           
                Scanner terminateScanner = new Scanner(System.in);
                String input = terminateScanner.nextLine();
                if (input.equals(Config.TERMINATION_COMMAND)) {
                    running = false;
                } else {
                    // reset counter and continue
                
                    System.out.println("Continuing light search...");
                }
            }
            
        }
        // 8. logging and shutdown
        logger.writeLogFile(System.currentTimeMillis());
        swiftBot.disableButton(Button.A);
        swiftBot.disableButton(Button.X);
        swiftBot.disableUnderlights();
        System.out.println("Shutting down.");
    }
}

