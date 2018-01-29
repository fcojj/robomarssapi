package br.com.contaazul.francisco.robomarsapi.model;

import org.springframework.stereotype.Component;

@Component
public class Mars {
	
	private static final int WIDTH = 5;
	private static final int HEIGHT = 5;
	private static final int DIRECTION = 4;
	private static final int INITIAL_X = 0;
	private static final int INITIAL_Y = 0;
	private static final int INITIAL_D = 0; //NORTH DIRECTION 
	private Position[][][] landMars;
	
	private Position[][][] getLandMars() {
		if(landMars == null) {
			landMars = new Position[WIDTH][HEIGHT][DIRECTION];
			for(int y = 0; y < HEIGHT; y++) {
				for(int x = 0; x < WIDTH; x++) {
					for(int d = 0; d < DIRECTION ; d++) {
						landMars[y][x][d] = new Position(x,y,encodeDirection(d), false);
					}
				}
			}
			//initial position Robot
			landMars[INITIAL_Y][INITIAL_X][INITIAL_D].setBusy(true);
		}
		
		return landMars;
	}

	private char encodeDirection(int d) {
		if(d == 0 || d == -4) {
			return 'N';
		} else if(d == 1 || d == -3) {
			return 'E';
		}else if(d == 2 || d == -2) {
			return 'S';
		}else if(d == -1 || d == 3) {
			return 'W';
		}
		
		 throw new IllegalArgumentException("Invalid argument!");
	}
	
	private int decodeDirection(char d) {
		if(d == 'N') {
			return 0;
		} else if(d == 'E') {
			return 1;
		}else if(d == 'S') {
			return 2;
		}else if(d == 'W') {
			return 3;
		}
		
		 throw new IllegalArgumentException("Invalid argument!");
	}

	public Position getCurrentPositionOfRobot(String commands) {
		/*
		 * If you want to store the position of the robot after command, uncomment this line and not run the unit tests. 
		 * Since the test did not require the state of the robot to be stored, I did not do a unit test for this scenario, 
		 * however the solution can easily be adapted to reach both scenarios.
		 * */
		this.landMars = null;
		
		Position currentRobotPosition = findRobot();
		
		for(int i = 0; i < commands.length(); i++) {
			char command = commands.charAt(i);
			currentRobotPosition = executeCommand(command, currentRobotPosition);
			
			if(currentRobotPosition == null) {
				return currentRobotPosition;
			}
		}	
		
		return currentRobotPosition;
	}
	
	private Position executeCommand(char command, Position currentRobotPosition) {
		Position newCurrentPosition = null;
		
		switch(command) {
			case 'R': {
				if(decodeDirection(currentRobotPosition.getDirection()) < 3) {
					newCurrentPosition = new Position(currentRobotPosition.getX(), currentRobotPosition.getY(), (encodeDirection(decodeDirection(currentRobotPosition.getDirection()) + 1)), true);
				} else {
					newCurrentPosition = new Position(currentRobotPosition.getX(), currentRobotPosition.getY(), encodeDirection(0), true);
				}
				
				break;
			}
			
			case 'L': {
				if(decodeDirection(currentRobotPosition.getDirection()) > -3) {
					newCurrentPosition = new Position(currentRobotPosition.getX(), currentRobotPosition.getY(), (encodeDirection(decodeDirection(currentRobotPosition.getDirection()) - 1)), true);
				} else {
					newCurrentPosition = new Position(currentRobotPosition.getX(), currentRobotPosition.getY(), encodeDirection(0), true);
				}
				
				break;
			}
			
			case 'M': {
				switch(currentRobotPosition.getDirection()) {
					case 'N':{
						newCurrentPosition = new Position(currentRobotPosition.getX(), currentRobotPosition.getY()+1, currentRobotPosition.getDirection(), true);
						
						break;
					}
						
					case 'E':{
						newCurrentPosition = new Position(currentRobotPosition.getX()+1, currentRobotPosition.getY(), currentRobotPosition.getDirection(), true);
						
						break;
					}
						
					case 'S':{
						newCurrentPosition = new Position(currentRobotPosition.getX(), currentRobotPosition.getY()-1, currentRobotPosition.getDirection(), true);
						
						break;
					}
					
					case 'W':{
						newCurrentPosition = new Position(currentRobotPosition.getX()-1, currentRobotPosition.getY(), currentRobotPosition.getDirection(), true);
						
						break;
					}
				}
				
				break;
			}
			
			default:{
				return null;
			}
		}
		
		try {
			landMars[newCurrentPosition.getY()][newCurrentPosition.getX()][decodeDirection(newCurrentPosition.getDirection())] =  newCurrentPosition; 
			currentRobotPosition.setBusy(false);
		} catch(ArrayIndexOutOfBoundsException ex) {
			return null;
		}
		
		return newCurrentPosition;
	}

	private Position findRobot() {
		Position robotPosition = null;
		
		for(int y = 0; y < HEIGHT; y++) {
			for(int x = 0; x < WIDTH; x++) {
				for(int d = 0; d < DIRECTION ; d++) {
					if(getLandMars()[y][x][d].isBusy()) {
						robotPosition = getLandMars()[y][x][d];
					}
				}
			}
		}
		
		return robotPosition;
	}
}
