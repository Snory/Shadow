package util;

public class Time {

	private static double delta;
	public final static float SECOND = 1000.0f;
	
	public static long getTime() {
		return System.currentTimeMillis();
	}

	public static double getDelta() {
		return delta;
	}

	public static void setDelta(double delta) {
		Time.delta = delta;
	}
	
	
}
