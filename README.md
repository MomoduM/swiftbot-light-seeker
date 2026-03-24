SwiftBot — Autonomous Light-Seeking Robot

A Java-based autonomous robot programmed to seek out the strongest light source in its environment, built as part of my Computer Science degree at Brunel University.
Demo
LinkedIn video demo — [insert LinkedIn post link here]
Overview
SwiftBot uses LDR (Light Dependent Resistor) sensors to continuously scan its environment, comparing left and right light intensity readings to steer itself toward the brightest source. The robot operates autonomously once started, with no human input.
Features

🟢 Light Seeking — Compares LDR sensor readings left vs right to navigate toward the strongest light source
🟡 Light Tracking — Locks onto a detected light source and follows it in real time
🔴 Obstacle Detection — Detects obstacles, stops immediately and redirects to the centre
⚡ Adaptive Speed Control — Automatically adjusts speed based on light intensity proximity
💡 LED Status Indicators — Green when searching, yellow when tracking, red when an obstacle is detected

Tech Stack

Language: Java
Hardware: SwiftBot autonomous robot
Platform: Raspberry Pi
Sensors: LDR light sensors, camera for obstacle detection

File Structure
FileDescriptionSearchForLight.javaMain entry point, core phototaxis logicAdaptiveSpeedController.javaManages speed based on light intensityMovementController.javaControls wheel movement and steeringObstacleDetector.javaHandles obstacle detection and avoidanceImageProcessor.javaProcesses camera image dataConfig.javaConfiguration constants, and settingsLogger.javaLogging utility for terminal output
How It Works

The robot starts and scans the environment using LDR sensors
Compares left and right intensity readings
Steers toward the stronger reading
Adaptive speed reduces as light intensity increases
If an obstacle is detected, the robot stops, flashes red and re-centres
