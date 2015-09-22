## SurveyMonkey Feedback SDK for Android

The SurveyMonkey Feedback SDK for Android allows app developers to integrate SurveyMonkey surveys and respondent data into their apps. Checkout our [documentation site](http://surveymonkey.github.io/surveymonkey-android-sdk/) for more information.

We strive to fix bugs and add new features as quickly as possible. Please watch our Github repo to stay up to date.

To download the SDK, either clone the SDK
```bash
git clone https://github.com/SurveyMonkey/surveymonkey-android-sdk.git
```
Or download the [latest release](https://github.com/SurveyMonkey/surveymonkey-android-sdk/releases)

### Integrating with Android Studio

1. From the menu bar, click **File -> New Module -> Import .JAR or .AAR package**
2. Navigate to the **surveymonkeyandroidsdk** directory that is contained in the **surveymonkeyandroidsdk** repo
3. Select the **surveymonkey-android-sdk.aar** file and click Finish
4. In your **AndroidManifest.xml** file, make sure you've included the `<uses-permission android:name="android.permission.INTERNET"/>` permission
5. Make sure your **build.gradle** file has `compile project(':surveymonkey_android_sdk')` under `dependencies {}`

That's it!

###Integrating the SurveyMonkey SDK with your app
For a detailed example, take a look at the **SimpleActivity** sample project in our Github repo


####Important
The SurveyMonkey Feedback SDK makes use of the `startActivityForResult(Intent, int)` method lifecycle - thus, if you want to consume the respondent data returned by our SDK, you'll need to implement `onActivityResult()` in whichever activity you present surveys from.

The survey respondent data is returned as JSON. To parse it, implement the following in your `onActivityResult()`:
```java
String respondent = intent.getStringExtra("smRespondent");
JSONObject surveyResponse = new JSONObject(respondent);
```

If `resultCode != RESULT_OK` in your `onActivityResult`, an error has occurred. To obtain and parse the error into an SMError object, call:
```java
SMError e = (SMError) intent.getSerializableExtra(SM_ERROR);
```

####Getting Started
1. `import com.surveymonkey.surveymonkeyandroidsdk.SurveyMonkey;` and (for error handling)
`import com.surveymonkey.surveymonkeyandroidsdk.utils.SMError;`
2. Initialize the SDK like so: `private SurveyMonkey sdkInstance = new SurveyMonkey();`

####The Intercept Modal
To kick off the SurveyMonkey Feedback SDK Intercept process, call:
```java
sdkInstance.onStart(this, [SAMPLE_APP_NAME], [SAMPLE_REQUEST_CODE], [SAMPLE_SURVEY_HASH]);
```
from your main activity. This will check to see if the user should be prompted to take your survey (i.e. `if (timeSinceLastSurveyPrompt > maxTimeIntervalBetweenSurveyPrompts)`). The copy of the prompts, as well as the time intervals, can be customized, see our docs for more information.


####Presenting a Survey to the User
To present a survey for the user to take, call
```java
sdkInstance.startSMFeedbackActivityForResult(this, [SAMPLE_REQUEST_CODE], [SAMPLE_SURVEY_HASH]);
```

####Issues and Bugs
Please submit any issues with the SDK to us via Github issues, for more critical bugs, email [support@surveymonkey.com](mailto:support@surveymonkey.com)
