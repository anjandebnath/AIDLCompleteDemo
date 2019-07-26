This project actually contain 2 project inside it.
One is Server & another one is named AIDLClient2.
Just Run this 2 project separately.
Both Server and Client contain same aidl package
So they can communicate remotely.
It is mandatory to keep the aidl package name same on both app.

Ref: https://www.slideshare.net/AnjanDebnath5/aidl-service


## Server side class 
- Here a Service will be declared as Bounded service.
- on Android manifest file service will be exported = true and process should be named :remote and only then 2 app can communicate remotely
 <service android:name=".ServerService"
            android:exported="true"
            android:enabled="true"
            android:process=":remote">
            <intent-filter>
                <action android:name="service.multiply" />
            </intent-filter>
        </service>
