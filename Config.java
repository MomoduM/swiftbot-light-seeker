

// PURPOSE: Storage for all program constants

public class Config {
	
	
    // --- CALIBRATION CONSTANTS ---
	

    public static final int DEFAULT_SPEED = 30;

    public static final double DIST_AT_DEF_SPEED = 15.0; // in cm

    // used to calculate distance from speed.
     // Formula: distance = speed * CALIBRATION_FACTOR
    
    public static final double CALIBRATION_FACTOR = 0.5;  
    
   
    // --- MOVEMENT ---
    
  
    // How long the robot moves (milliseconds)
    
    public static final int MOVEMENT_DURATION = 1000;

     // pause after each movement (milliseconds)
   
    public static final int POST_MOVEMENT_PAUSE = 100;

     // Degrees to turn left or right 
     
    public static final int TURN_DEGREES = 30;

     // duration of a turn
    
    public static final int TURN_DURATION = 300;
    

    // --- OBSTACLE DETECTION ---
    
   
     // Distance for obstacle detection (centimetres)
     
    public static final double OBSTACLE_DISTANCE = 25.0;

     // Max obstacles before program asks to terminate
     
    public static final int MAX_OBSTACLES = 5;
    
     // duration of obstacle warning (milliseconds)
    
    public static final long OBSTACLE_TIME_WINDOW = 300_000L;

    
    // --- IMAGE PROCESSING ---
  
 
    public static final String IMAGE_SIZE = "SQUARE_480x480";

     // Max number of camera retries before failure
     
    public static final int MAX_CAMERA_RETRIES = 3;

    // Wait time between camera retry attempts (milliseconds)
    
    public static final int CAMERA_RETRY = 1000;

 
    // ---UNDERLIGHT COL ---
    

    // Green - used during normal movement
    public static final int[] COLOUR_GREEN  = {0, 255, 0};

    // Red - used when obstacle detected 
    public static final int[] COLOUR_RED    = {255, 0, 0};

    // Off 
    public static final int[] COLOUR_OFF    = {0, 0, 0};
    
    // Brightest light source found 
    public static final int[] COLOUR_YELLOW = {255, 255, 0};


    // --- ADAPTIVE SPEED CONTROL --- (Additional) 
   

     // Mini speed when adaptive control is on
 
    public static final int ADAPTIVE_SPEED_MIN = 20;

     // Max speed when adaptive control is on

    public static final int ADAPTIVE_SPEED_MAX = 60;

     // If intensity difference is less than below threshold, use minimum speed
    
    public static final double INTENSITY_LOW_THRESHOLD = 10.0;

     // If intensity difference is above below threshold, use maximum speed
     
    public static final double INTENSITY_HIGH_THRESHOLD = 200.0;

    
    // --- LOGGING ---
  

    // Directory where log files are saved 
    public static final String LOG_DIRECTORY = "logs";

    // Directory where obstacle images are saved 
    public static final String OBSTACLE_IMAGE_DIRECTORY = "obstacles";

    // Timestamp format used within file names / log entries 
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd_HH-mm-ss";

    // Timestamp format used within log file 
    public static final String LOG_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

  
    // --- TERMINATION ---
 

     // String user must type in terminate after 5 obstacles
    
    public static final String TERMINATION_COMMAND = "TERMINATE";
  
    
}
