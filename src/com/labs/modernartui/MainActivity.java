package com.labs.modernartui;

//import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static DialogFragment mDialog;
	private static final String URL = "http://www.moma.org";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// initialize colors
		
		final int MAXCOLOR = 255; // max value for color r,g,b values		
		// upper left
		final int[] upperLeftStart = {102, 51, 153};
		final int[] upperLeftEnd = {255, 228, 181};	
		// bottom left
		final int[] bottomLeftStart = {255, 105, 180};
		final int[] bottomLeftEnd = {240, 230, 140};		
		// upper right
		final int[] upperRightStart = {178, 34, 34};
		final int[] upperRightEnd = {255, 215, 0};		
		// middle right
		final int[] middleRightStart = {255, 255, 255};		
		// bottom right
		final int[] bottomRightStart = {25, 25, 112};
		final int[] bottomRightEnd = {144, 238, 144};
		
		
		// Assign ImageView to the ImageView variables
		
		final ImageView upperLeft = (ImageView) findViewById(R.id.upperLeft);
		upperLeft.setBackgroundColor(Color.rgb(upperLeftStart[0], upperLeftStart[1], upperLeftStart[2]));
		
		final ImageView bottomLeft = (ImageView) findViewById(R.id.bottomLeft);
		bottomLeft.setBackgroundColor(Color.rgb(bottomLeftStart[0], bottomLeftStart[1], bottomLeftStart[2]));
		
		final ImageView upperRight = (ImageView) findViewById(R.id.upperRight);
		upperRight.setBackgroundColor(Color.rgb(upperRightStart[0], upperRightStart[1], upperRightStart[2]));
		
		final ImageView middleRight = (ImageView) findViewById(R.id.middleRight);
		middleRight.setBackgroundColor(Color.rgb(middleRightStart[0], middleRightStart[1], middleRightStart[2]));
		
		final ImageView bottomRight = (ImageView) findViewById(R.id.bottomRight);
		bottomRight.setBackgroundColor(Color.rgb(bottomRightStart[0], bottomRightStart[1], bottomRightStart[2]));
		
		
		// setup seekbar
		
		SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
		seekBar.setMax(MAXCOLOR); // set max value
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// interpolate color values between start color and end color according to progress
				upperLeft.setBackgroundColor(
						Color.argb(MAXCOLOR, upperLeftStart[0]+(upperLeftEnd[0]-upperLeftStart[0])*progress/MAXCOLOR, 
								upperLeftStart[1]+(upperLeftEnd[1]-upperLeftStart[1])*progress/MAXCOLOR, 
								upperLeftStart[2]+(upperLeftEnd[2]-upperLeftStart[2])*progress/MAXCOLOR));
				bottomLeft.setBackgroundColor(
						Color.argb(MAXCOLOR, bottomLeftStart[0]+(bottomLeftEnd[0]-bottomLeftStart[0])*progress/MAXCOLOR, 
								bottomLeftStart[1]+(bottomLeftEnd[1]-bottomLeftStart[1])*progress/MAXCOLOR, 
								bottomLeftStart[2]+(bottomLeftEnd[2]-bottomLeftStart[2])*progress/MAXCOLOR));
				upperRight.setBackgroundColor(
						Color.argb(MAXCOLOR, upperRightStart[0]+(upperRightEnd[0]-upperRightStart[0])*progress/MAXCOLOR, 
								upperRightStart[1]+(upperRightEnd[1]-upperRightStart[1])*progress/MAXCOLOR, 
								upperRightStart[2]+(upperRightEnd[2]-upperRightStart[2])*progress/MAXCOLOR));
				bottomRight.setBackgroundColor(
						Color.argb(MAXCOLOR, bottomRightStart[0]+(bottomRightEnd[0]-bottomRightStart[0])*progress/MAXCOLOR, 
								bottomRightStart[1]+(bottomRightEnd[1]-bottomRightStart[1])*progress/MAXCOLOR, 
								bottomRightStart[2]+(bottomRightEnd[2]-bottomRightStart[2])*progress/MAXCOLOR));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// No specific actions need
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// No specific actions need
				
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.more_information) {
			// Create a new AlertDialogFragment
			mDialog = AlertDialogFragment.newInstance();

			// Show AlertDialogFragment
			mDialog.show(getFragmentManager(), "Alert");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	// Class that creates the AlertDialog
	public static class AlertDialogFragment extends DialogFragment {
		// return instance
		public static AlertDialogFragment newInstance() {
			return new AlertDialogFragment();
		}

		// Build AlertDialog using AlertDialog.Builder
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage(R.string.dialog_message)									
					// Set up Yes Button
					.setPositiveButton(R.string.visit_moma,
							new DialogInterface.OnClickListener() {
								public void onClick(
										final DialogInterface dialog, int id) {
									Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
									startActivity(intent);
								}
							})					
					// Set up No Button
					.setNegativeButton(R.string.not_now,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									mDialog.dismiss();
								}
							});
			AlertDialog dialog = builder.show();
			TextView messageView = (TextView) dialog.findViewById(android.R.id.message);
			messageView.setGravity(Gravity.CENTER); // center align message
			return dialog;
		}
	}

}
