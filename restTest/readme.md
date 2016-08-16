Test Github rest api for user and user's repository
----------------------------------------------------

Introduction
-------------
Few words first, it was not as easy as I first thought to go back to java development, after more 
than a decade. I had forgotten how many libraries there were and it was not easy to find the 
suitables ones.

Environment used
-----------------
java IDE: Netbeans 8.1
java JDK: 1.7

Packages used Junit for testing, resteasy for api calls.
I wanted to used jackson to handle the json part, but unfortunately, i couldn't make it work.

Tests implemented
------------------
The class has two tests:
1/ testUser wich make user's api call and validate json syntax
2/ testRepoUser wich make user's repo api call and validate json syntax

Test Data:
Username parameter: already passed within the code. (2 values with different result expected)

Build and execute
-----------------
Launch project on netbeans, build and test.

