package com.fpt.robot.example.apis.tracking;

import com.fpt.robot.RobotException;
import com.fpt.robot.RobotHardware;
import com.fpt.robot.example.apis.R;
import com.fpt.robot.example.apis.RobotApiDemoActivity;
import com.fpt.robot.example.apis.R.layout;
import com.fpt.robot.example.apis.R.menu;
import com.fpt.robot.motion.RobotMotionStiffnessController;
import com.fpt.robot.motion.RobotPosture;
import com.fpt.robot.tracking.RobotCupTracker;
import com.fpt.robot.vision.RobotFlashcardRecognition;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CupTracking  extends RobotApiDemoActivity implements OnItemClickListener {
	private static final String TAG = "CupTracking";
	
	private String[] mCupTrackingTestActionList = {
    		"Start",
    		"Stop",
    		"Check Target Reachable",
    		"Go To Target",
    		"Grab Cup",
    		"Return Cup",
    		"Wake Up",
    		"Rest"
    };
    
    private ListView lvCupTrackingActionList = null;    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_cup_tracking);
		
		lvCupTrackingActionList = (ListView) findViewById(R.id.lvCupTrackingActionList);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
        		android.R.layout.simple_list_item_1, 
        		android.R.id.text1, mCupTrackingTestActionList);
        adapter.setNotifyOnChange(true);
        lvCupTrackingActionList.setAdapter(adapter);
        lvCupTrackingActionList.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cup_tracking, menu);
		return true;
	}

	@Override
	protected int getLayoutID() {
		// TODO Auto-generated method stub
		return R.layout.activity_cup_tracking;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Log.d(TAG, "onItemClick(position=" + position + ")");
		if (position > mCupTrackingTestActionList.length) {
			Log.e(TAG, "out of range!");
			return;
		}
		String action_name = mCupTrackingTestActionList[position];
		Log.d(TAG, "action: " + action_name);
		callAction(action_name);
	}

    private void callAction(String action_name) {
    	if (action_name.equals("Start")) {
            startCupTracker();
        } else if (action_name.equals("Stop")) {
            stopCupTracker();
        } else if (action_name.equals("Check Target Reachable")) {
            checkTargetReachable();
        } else if (action_name.equals("Go To Target")) {
        	goToTarget();
        } else if (action_name.equals("Grab Cup")) {
        	grabCup();
        } else if (action_name.equals("Return Cup")) {
            returnCup();
        } else if (action_name.equals("Wake Up")) {
            wakeUp();
        } else if (action_name.equals("Rest")) {
            rest();
        }
	}

	private void rest() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				showProgress("Processing...");
				try {
					RobotPosture.goToPosture(getRobot(), RobotHardware.Posture.CROUCH, 0.5f);
					RobotMotionStiffnessController.rest(getRobot());
				} catch (RobotException e) {
					cancelProgress();
					e.printStackTrace();
					makeToast(e.getMessage());
					return;
				}
				cancelProgress();
			}
		}).start();
	}

	private void wakeUp() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				showProgress("Processing...");
				try {
					RobotMotionStiffnessController.wakeUp(getRobot());
				} catch (RobotException e) {
					cancelProgress();
					e.printStackTrace();
					makeToast(e.getMessage());
					return;
				}
				cancelProgress();
			}
		}).start();
	}

	private void returnCup() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				showProgress("Processing...");
				try {
					RobotCupTracker.returnCup(getRobot());
				} catch (RobotException e) {
					cancelProgress();
					e.printStackTrace();
					makeToast(e.getMessage());
					return;
				}
				cancelProgress();
			}
		}).start();
	}

	private void grabCup() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				showProgress("Processing...");
				try {
					RobotCupTracker.grabCup(getRobot());
				} catch (RobotException e) {
					cancelProgress();
					e.printStackTrace();
					makeToast(e.getMessage());
					return;
				}
				cancelProgress();
			}
		}).start();
	}

	private void goToTarget() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				showProgress("Processing...");
				try {
					RobotCupTracker.goToTarget(getRobot(), RobotCupTracker.MAX_TARGET_DISTANCE, 
							RobotCupTracker.STOP_MOVE_THRESHOLD);
				} catch (RobotException e) {
					cancelProgress();
					e.printStackTrace();
					makeToast(e.getMessage());
					return;
				}
				cancelProgress();
			}
		}).start();
	}

	private void checkTargetReachable() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				showProgress("Processing...");
				boolean reachable = false;
				try {
					reachable = RobotCupTracker.checkTargetReachable(getRobot());
				} catch (RobotException e) {
					cancelProgress();
					e.printStackTrace();
					makeToast(e.getMessage());
					return;
				}
				cancelProgress();
				if (reachable) {
					makeToast("Cup is in reachable area.");
				} else {
					makeToast("Cup is not reachable.");
				}
			}
		}).start();
	}

	private void stopCupTracker() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				showProgress("Processing...");
				try {
					RobotCupTracker.stopTracker(getRobot());
				} catch (RobotException e) {
					cancelProgress();
					e.printStackTrace();
					makeToast(e.getMessage());
					return;
				}
				cancelProgress();
			}
		}).start();
	}

	private void startCupTracker() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				showProgress("Processing...");
				try {
					RobotCupTracker.startTracker(getRobot());
				} catch (RobotException e) {
					cancelProgress();
					e.printStackTrace();
					makeToast(e.getMessage());
					return;
				}
				cancelProgress();
			}
		}).start();
	}

}
