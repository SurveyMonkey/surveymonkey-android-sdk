package com.surveymonkey.surveymonkeyandroidsdkexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.surveymonkey.surveymonkeyandroidsdk.SMFeedbackFragment;
import com.surveymonkey.surveymonkeyandroidsdk.SMFeedbackFragmentListener;
import com.surveymonkey.surveymonkeyandroidsdk.SurveyMonkey;
import com.surveymonkey.surveymonkeyandroidsdk.utils.SMError;

import org.json.JSONObject;


public class SimpleFragmentActivity extends FragmentActivity implements SMFeedbackFragmentListener {


    public static final String COLLECTOR_HASH = "collectorHash";
    private String mCollectorHash;
    private Toast mThanksToast;
    private Toast mErrorToast;

    public static void startActivity(Activity context, String collectorHash) {
        Intent intent = new Intent(context, SimpleFragmentActivity.class);
        if (collectorHash != null) {
            intent.putExtra(COLLECTOR_HASH, collectorHash);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mCollectorHash = intent.getStringExtra(COLLECTOR_HASH);
        if (savedInstanceState == null) {
            //This is how you can add the SMFeedbackFragment to your activity
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, SurveyMonkey.newSMFeedbackFragmentInstance(mCollectorHash), SMFeedbackFragment.TAG).commit();
        }

        //Create Toasts to Display to User
        mErrorToast = Toast.makeText(getApplicationContext(), getString(R.string.error_prompt), Toast.LENGTH_LONG);
        mThanksToast = Toast.makeText(this, getString(R.string.thanks_prompt), Toast.LENGTH_LONG);
    }


    public void onFetchRespondentSuccess(JSONObject respondent) {
        mThanksToast.show();
        finish();
    }

    public void onFetchRespondentFailure(SMError e) {
        mErrorToast.show();
        Log.d("SM-ERROR", e.getDescription());
        finish();
    }
}
