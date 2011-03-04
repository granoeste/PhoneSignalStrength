package net.granoeste.creador.PhoneSignalStrength;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class PhoneSignalStrengthActivity extends Activity {
	final String TAG = "PhoneSignalStrengthActivity";

	TextView textViewLog;
	EditText editTextLog;
	SignalStrengthListener mListener;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		editTextLog = (EditText) findViewById(R.id.editTextLog);

	}

	@Override
	protected void onResume() {
		super.onResume();

		mListener = new SignalStrengthListener();
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//		tm.listen(mListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTH);
//		tm.listen(mListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTH|PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
		tm.listen(mListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

	}

	@Override
	protected void onPause() {
		super.onPause();

		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		tm.listen(mListener, PhoneStateListener.LISTEN_NONE);	// LISTEN_NONE : Stop listening for updates.
	}


	public class SignalStrengthListener extends PhoneStateListener {

//		@Override
//		public void onSignalStrengthChanged(int asu) {
//			String str = asu + "/"+String.valueOf(-113+2*asu)+"dBm";
//			editTextLog.append(str+"\n");
//			Log.v(TAG,"asu="+str);
//		}

		@Override
		public void onSignalStrengthsChanged(SignalStrength signalStrength) {
			super.onSignalStrengthsChanged(signalStrength);

			boolean	isGsm = signalStrength.isGsm();
			// Get the CDMA RSSI value in dBm
			int	iCdmaDbm = signalStrength.getCdmaDbm();
			// Get the CDMA Ec/Io value in dB*10
			int	iCdmaEcio = signalStrength.getCdmaEcio();
			// Get the EVDO RSSI value in dBm
			int	iEvdoDbm = signalStrength.getEvdoDbm();
			// Get the EVDO Ec/Io value in dB*10
			int	iEvdoEcio = signalStrength.getEvdoEcio();
			// Get the signal to noise ratio. Valid values are 0-8. 8 is the highest.
			int	iEvdoSnr = signalStrength.getEvdoSnr();
			// Get the GSM bit error rate (0-7, 99) as defined in TS 27.007 8.5
			int	iGsmBitErrorRate = signalStrength.getGsmBitErrorRate();
			// Get the GSM Signal Strength, valid values are (0-31, 99) as defined in TS 27.007 8.5
			int	iGsmSignalStrength = signalStrength.getGsmSignalStrength();

			String str = "GSM="+isGsm
						+",GSM Signal Strength="+iGsmSignalStrength
						+",GSM Bit Error Rate="+iGsmBitErrorRate
						+",CDMA RSSI="+iCdmaDbm+"dBm"
						+",CDMA Ec/Io="+iCdmaEcio+"dB*10"
						+",EVDO RSSI="+iEvdoDbm+"dBm"
						+",EVDO Ec/Io="+iEvdoEcio+"dB*10"
						+",EVDO SNR="+iEvdoSnr
						+"\n";

			Log.v(TAG,str);
			editTextLog.append(str);
		}
	}
}
