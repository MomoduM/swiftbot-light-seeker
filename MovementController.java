

import swiftbot.*;

public class MovementController {
	
	 private SwiftBotAPI swiftBot;

	    public MovementController(SwiftBotAPI swiftBot) {
	        this.swiftBot = swiftBot;
	    }

	    public void moveForward(int speed) {
	    	
	    	// set green
	    	// move robot forward for set amount of time
	    	// pause for set amount of time
	    	
	        swiftBot.fillUnderlights(Config.COLOUR_GREEN);
	        swiftBot.move(speed, speed, Config.MOVEMENT_DURATION);
	        try {
	            Thread.sleep(Config.POST_MOVEMENT_PAUSE);
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	    } 


	    public void turnAndMove(String direction, int speed) {
	        // if direction is "LEFT" - turn left by set amount of degrees 
	    	   // if direction is "RIGHT" - turn right by set amount of degrees 
	    	  //  move forward
	    	if (direction.equals("LEFT")) {
				swiftBot.move(-speed, speed, Config.TURN_DURATION);
				swiftBot.move(speed, speed, Config.MOVEMENT_DURATION);
			}
	    	else if (direction.equals("RIGHT") ) {
	    		swiftBot.move(speed, -speed, Config.TURN_DURATION);
				swiftBot.move(speed, speed, Config.MOVEMENT_DURATION);
			}
	    	//test and check
	    	else {
				moveForward(speed);
			}
	    }

	    public void stopRobot() {
	        // stop all wheel movement
	        // turn off underlights
	    	swiftBot.stopMove();
	    	swiftBot.fillUnderlights(Config.COLOUR_OFF);
	    }
}

