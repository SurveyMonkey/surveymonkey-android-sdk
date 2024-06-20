## SurveyMonkey Feedback SDK for Android

Want to improve your product and app store ratings? The SurveyMonkey Mobile Feedback SDK gives you all the tools you need to collect user feedback about your in-app experience.

### How It Works

1. Log in to SurveyMonkey and create a survey asking users what improvements they want to see and how they’d rate your app
2. Integrate the survey into your mobile app using our [mobile SDK](http://help.surveymonkey.com/articles/en_US/kb/Mobile-SDK)
3. Get product feedback in real time and prompt satisfied customers to rate you

### Steps To Integrate

#### Step 1: Download Mobile SDK

Gradle (mavenCentral):
```groovy
implementation 'com.surveymonkey:surveymonkey-android-sdk:3.0.5'
```
or

Install via Maven (mavenCentral):
```xml
<dependency>
   <groupId>com.surveymonkey</groupId>
   <artifactId>surveymonkey-android-sdk</artifactId>
   <version>3.0.5</version>
</dependency>
``` 

**OR**

Download the [latest release](https://github.com/SurveyMonkey/surveymonkey-android-sdk/releases) 

**Importing to Android Studio**

1. From the menu bar, click **File -> Project Structure -> Dependencies -> JAR/AAR dependency**
2. Provide a path to the  **surveymonkey_android_sdk.aar** directory that is contained in the **surveymonkeyandroidsdk** repo
3. Select the **surveymonkey_android_sdk.aar** file
4. In your **AndroidManifest.xml** file, make sure you\'ve included the `<uses-permission android:name="android.permission.INTERNET"/>` permission
5. Add following dependencies to your **build.gradle** file
```groovy
   implementation "com.squareup.retrofit2:retrofit:$latestversion"
   implementation "com.squareup.retrofit2:converter-gson:$latestversion"  
   implementation "com.squareup.okhttp3:logging-interceptor:$latestversion"  
   implementation "com.squareup.retrofit2:converter-scalars:$latestversion"   
   implementation "androidx.lifecycle:lifecycle-runtime-ktx:$latestversion"
   ```
6. Make sure your **build.gradle** file has `implementation project(':surveymonkey_android_sdk')` under `dependencies {}`


#### Step 2: Set up your SDK Collector
You must create your survey and set up your SDK Collector in [www.surveymonkey.com](https://www.surveymonkey.com).

1. Once you create your survey, navigate to the **Collect** tab and select **+New Collector > SDK** from the menu on the righthand side
2. Click **Generate**. The code you generate is your **Survey Hash**, you'll **Copy** this and use it to point the SDK to your survey in the steps below
   <img  alt="sdk_collector" src="https://user-images.githubusercontent.com/119406475/227167584-a52bf4b4-1531-4cc7-8fe6-f052d01d2c05.png">

### Step 3: Integrate the SurveyMonkey SDK with your app

1. Import the SDK
   `import com.surveymonkey.surveymonkeyandroidsdk.SurveyMonkey;` and (for error handling)
   `import com.surveymonkey.surveymonkeyandroidsdk.utils.SMError;`
2. Initialize the SDK: `private SurveyMonkey sdkInstance = new SurveyMonkey();`

##### Important consideration
SurveyMonkey Android SDK requires androidx.appcompat.app.AppCompatActivity context .
For ex-
```java
sdkInstance.onStart(appCompatActivityContext, [SAMPLE_APP_NAME], [SAMPLE_REQUEST_CODE], [SAMPLE_SURVEY_HASH], [SAMPLE_CUSTOM_VARIABLES_DICTIONARY]);
```
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
Look at the [Simple Survey](https://github.com/SurveyMonkey/surveymonkey-android-sdk/tree/master/SimpleSurvey) sample project in our Github repo for a detailed example.

##### The Intercept Modal
To kick off the SurveyMonkey Feedback SDK Intercept process, call the following from your main activity:
```java
sdkInstance.onStart(this, [SAMPLE_APP_NAME], [SAMPLE_REQUEST_CODE], [SAMPLE_SURVEY_HASH]);
```
This will initialise the SDK and check to see if the user should be prompted to take your survey (i.e. if (timeSinceLastSurveyPrompt > maxTimeIntervalBetweenSurveyPrompts)).

If you are on Advantage Plan or higher and want to include custom variables with each survey response, create a flat JSONObject with your custom variables and use:
```java
sdkInstance.onStart(this, [SAMPLE_APP_NAME], [SAMPLE_REQUEST_CODE], [SAMPLE_SURVEY_HASH], [SAMPLE_CUSTOM_VARIABLES_DICTIONARY]);
```

You can customize the copy of the prompts, as well as the time intervals. See our [documentation](http://surveymonkey.github.io/surveymonkey-android-sdk/) for more information about specific features and classes.

##### Presenting a Survey to the User
To present a survey for the user to take, call:
```java
sdkInstance.startSMFeedbackActivityForResult(this, [SAMPLE_REQUEST_CODE], [SAMPLE_SURVEY_HASH]);
```

If you are on Advantage Plan or higher and want to include custom variables with each survey response, create a flat JSONObject with your custom variables and use:
```java
sdkInstance.startSMFeedbackActivityForResult(this, [SAMPLE_REQUEST_CODE], [SAMPLE_SURVEY_HASH], [SAMPLE_CUSTOM_VARIABLES_DICTIONARY]);
```

##### Optional: Fragments

**New in version 1.0.5:** The SMFeedbackFragment class allows you to embed our SDK into an Activity, rather than displaying it in a fullscreen activity. To instantiate an SMFeedbackFragment use:
```java
sdkInstance.newSMFeedbackFragmentInstance([SAMPLE_SURVEY_HASH]);
```

For a helpful example, see our [Simple Survey](https://github.com/SurveyMonkey/surveymonkey-android-sdk/tree/master/SimpleSurvey) sample project and see our [documentation](http://surveymonkey.github.io/surveymonkey-android-sdk/) for more details.

#### Issues and Bugs
To report issues with the SDK collector, please contact us at api-support@surveymonkey.com.

#### FAQ
*What can I use the mobile SDK for?*

###### Measure overall satisfaction
A simple, multiple choice question can help you understand just how satisfied users are with your app. Based on their level of satisfaction, you can tailor the rest of their in-app experience. For example, if a user reports a moderate level of satisfaction, you can ask them a follow-up question to identify the problem and prioritize a solution by your development team.

###### Conduct real-time product research
The best way to perform user research is to listen. A product manager can quickly find out which features a user is yearning for, and which features aren’t meeting expectations. Based on the findings, a product team can adjust roadmaps to be more responsive for their growing mobile user base and don’t have to passively wait for an app store review to see what is and isn’t working.

*How can I prompt users to give feedback?*

###### Random polling
You can set up predefined time intervals to prompt for in-app feedback from a random sample of your users. This is referred to as a “scheduled intercept” in the developer documents. For example, you can set the in-app feedback to prompt a user within 3 days after they install or update the app. If the user selects “Give Feedback,” you don’t show the prompt for 3 months. If the user selects “Not Now,” you prompt them again in 3 weeks. The time intervals are completely customizable by the developer.

###### Event-based triggers
You can prompt the user for in-app feedback if they visit a certain part of the app or click a specific button. For example, if you notice users dropping out of your checkout screen, you can ask them why in real time. This information can lead to key product insights, such as moving certain fields around to better align with your customers’ priorities.

###### Passive feedback
Many apps have a slide-out menu that allows their users to access a variety of items such as Account, About Us, or Help. At SurveyMonkey, we’ve included Feedback in our slide-out menu to passively prompt users for feedback. You can incorporate this into your own app so users can provide feedback whenever they want.

*How can I route my app users to different flows based on their survey response?*

If you have a ADVANTAGE plan or higher, you can program your app to route your users into different flows based on their responses to your survey. For example, if a user responds to your in-app feedback survey and gives your app a 5-star rating, your app could take that user down the “5-Star Rating Flow” into the app store to rate your app. You could also take a user down the “Needs Improvement Flow” to the help center in your app.

*Is the mobile SDK free?*

Yes, the mobile SDK can be incorporated into your app with any SurveyMonkey plan. However, developers must upgrade to ADVANTAGE or higher to take actions based on responses to survey questions (prompt users who report high satisfaction with your app to review it).

[Custom variables](http://help.surveymonkey.com/articles/en_US/kb/What-are-custom-variables-and-how-do-I-use-them) are available in Advantage Plan or higher.

*How can I style the survey?  How will it look on a mobile device?*

As with all SurveyMonkey surveys, you have the ability to customize the look and feel of your survey to match your mobile app.  The survey page is mobile responsive so you can use the SDK on both smartphone and tablet devices.

*Can I make edits once a survey is deployed?*

You can edit a survey after you send it out. If the survey doesn’t have any responses, you can fully edit the survey. If the survey is live and already has responses, your editing options are [limited](http://help.surveymonkey.com/articles/en_US/kb/Am-I-able-to-edit-a-live-survey-and-does-this-change-the-link). Edits you make to the survey go live as soon as you save them, so make sure you preview your work beforehand. You won’t need to submit a new version of your app to the app store to reflect any updates, and your survey hash will remain the same.

If you close or delete a collector in SurveyMonkey, your users will not see a prompt for in-app feedback.

*Is the survey native to the app?*

Yes, although the SDK requires an internet connection to load the survey. If the user’s device is offline he/she will not be prompted to take the survey.

*I have an app on Android and iOS. How many mobile collectors do I need to create?*

You can create one survey and set up multiple collectors to help you track where responses are coming from, i.e. one for your Android app and one for your iOS app.

*How do I localize the survey for various locations?*

We recommend creating multiple [surveys](http://help.surveymonkey.com/articles/en_US/kb/How-can-I-create-two-surveys-and-direct-respondents-to-one-version), with the survey translated into different languages.

*What is the minimum Android version supported?

As of version 3.0.4, the minsdk for this library is Api 21 (Android 5.x)
