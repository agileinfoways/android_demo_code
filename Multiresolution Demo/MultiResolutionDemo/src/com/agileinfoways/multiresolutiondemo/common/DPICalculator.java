package com.agileinfoways.multiresolutiondemo.common;

import android.util.Log;

/*
 Program output
 LDPI: 165.0 X 60.0
 MDPI: 220.0 X 80.0
 HDPI: 330.0 X 120.0
 XHDPI: 440.0 X 160.0
 XXHDPI: 660.0 X 240.0
 XXXHDPI: 880.0 X 320.0
 */

public class DPICalculator {

	private final String TAG = getClass().getSimpleName().toString();
	private final float LDPI = 120;
	private final float MDPI = 160;
	private final float HDPI = 240;
	private final float XHDPI = 320;
	private final float XXHDPI = 480;
	private final float XXXHDPI = 640;

	private float forDeviceDensity;
	private float width;
	private float height;

	public DPICalculator() {
	}

	public DPICalculator(float forDeviceDensity, float width, float height) {
		this.forDeviceDensity = forDeviceDensity;
		this.width = width;
		this.height = height;
	}

	public static void main(String... args) {
		DPICalculator dpiCalculator = new DPICalculator(240, 330, 120);
		dpiCalculator.calculateDPI();
	}

	private float getPx(float dp, float value) {
		float px = dp * (value / forDeviceDensity);
		return px;
	}

	private void calculateDPI() {

		float ldpiW = getPx(LDPI, width);
		float ldpiH = getPx(LDPI, height);
		float mdpiW = getPx(MDPI, width);
		float mdpiH = getPx(MDPI, height);
		float hdpiW = getPx(HDPI, width);
		float hdpiH = getPx(HDPI, height);
		float xdpiW = getPx(XHDPI, width);
		float xdpiH = getPx(XHDPI, height);
		float xxdpiW = getPx(XXHDPI, width);
		float xxdpiH = getPx(XXHDPI, height);
		float xxxdpiW = getPx(XXXHDPI, width);
		float xxxdpiH = getPx(XXXHDPI, height);

		Log.e(TAG, "LDPI: " + ldpiW + " X " + ldpiH);
		Log.e(TAG, "MDPI: " + mdpiW + " X " + mdpiH);
		Log.e(TAG, "HDPI: " + hdpiW + " X " + hdpiH);
		Log.e(TAG, "XHDPI: " + xdpiW + " X " + xdpiH);
		Log.e(TAG, "XXHDPI: " + xxdpiW + " X " + xxdpiH);
		Log.e(TAG, "XXXHDPI: " + xxxdpiW + " X " + xxxdpiH);
	}
}