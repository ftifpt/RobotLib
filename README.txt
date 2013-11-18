1. Thay thư viện RobotConnect.jar trong thư mục libs của project RobotAPIDemos.

2. Copy file Cuptracking.java vào package com.fpt.robot.example.apis.tracking

3. Add vào file AndroidManifest.xml:
 <activity
            android:name="com.fpt.robot.example.apis.tracking.CupTracking"
            android:label="Tracking/5. Cup Tracking"
            android:screenOrientation="portrait" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="com.fpt.robot.category.DEMO_APP" />
            </intent-filter>
        </activity>



4. Hướng dẫn sơ bộ dung các hàm checkTargetReachable(),goToTarget(),grabCup(),returnCup():
-          Hàm checkTargetReachable(); trả về true khi robot cách Cup 80cm-> có thể dung hàm này hoặc không trước khi dùng hàm goToTarget(); Khuyến cáo nên dung để đảm bảo chắc chắn đi chuẩn đến cup.
-          Hàm goToTarget() có 2 tham số là
   	MaxTargetDistance: giá trị trong khoảng 0.25m đến 0.3m
	threshold: giá trị trong khoảng 0.15m đến 0,18m
-          hàm grabCup(), thì sử dụng sau khi hàm goToTarget() trả về true(tức là đã thực hiện xong).
-          Hàm returnCup() thì sử dụng sau khi hàm grabCup() trả về true (tức là đã thực hiện xong).