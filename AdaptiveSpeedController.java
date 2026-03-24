

import java.util.Scanner;


public class AdaptiveSpeedController {
	private boolean enabled;
    private int minSpeed;
    private int maxSpeed;

    public AdaptiveSpeedController(Scanner scanner) {
    
    	 // if Y - prompt for min speed (validate 20-40)
        //      - prompt for max speed (validate 41-100)
        //      - set enabled = true
        // if N - set enabled = false, use default speed
    	
    System.out.println("Enable Adaptive Speed Conrol? (Y/N)");
    	String Prompt = scanner.nextLine();
    
    if (Prompt.equals("Y")) {
		 enabled = true;
	
    
    do {
		System.out.println("Enter Min Speed (20-40)");
    	minSpeed = scanner.nextInt();
    	if (minSpeed < 20 || minSpeed > 40) {
			System.out.println("Invalid Speed. Mustg be 20-40");
		}
	} while (minSpeed < 20 || minSpeed > 40);
    
    do {
		System.out.println("Enter Max Speed (41 - 100)");
		maxSpeed = scanner.nextInt();
		
		if (maxSpeed < 41 || maxSpeed > 100) {
			System.out.println("Invalid Speed. Must be (41 - 100)");
		}
	} while (maxSpeed < 41 || maxSpeed > 100);
    
    } else if (Prompt.equals("N")) {
		enabled = false;
	minSpeed = Config.DEFAULT_SPEED;
	maxSpeed = Config.DEFAULT_SPEED;
	System.out.println("Adaptive Speed Control disabled. Fixed Speed: " + Config.DEFAULT_SPEED);
	}
       
    }

    public int calculateSpeed(double maxIntensity, double threshold) {
        // if not on return default speed
        // calculate intensity_diff 
        // if diff < Config.INTENSITY_LOW_THRESHOLD return minSpeed
        // if diff > Config.INTENSITY_HIGH_THRESHOLD return maxSpeed

    	
    	if (!enabled) {
			return Config.DEFAULT_SPEED;
		}
    	double intensityDiff = maxIntensity -  threshold;
    	
    	if (intensityDiff < Config.INTENSITY_LOW_THRESHOLD) {
    		return minSpeed;
    	}
    	else if (intensityDiff > Config.INTENSITY_HIGH_THRESHOLD) {
			return maxSpeed;
		}
    	else {
			double noramlisedDiff = Math.min(intensityDiff / 255.0, 1.0);
			int speed = (int) Math.round(minSpeed + (noramlisedDiff * (maxSpeed - minSpeed)));
			return speed;
		}
    }

    public boolean isEnabled() {
        // return enabled
    	return enabled;
    }
}
