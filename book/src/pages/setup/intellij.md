## IntelliJ

IntelliJ is an integrated development environment (IDE) for Scala and other programming languages. It integrates an number of programming tools into one application, and we recommend it for people who are used to using other IDEs such as Visual Studiol or Eclipse.

Start by [downloading][intellij-download] and installing IntelliJ. You can use the free community edition for Creative Scala. When installing IntelliJ you will be asked a lot of questions. You can accept the defaults for the most part. When you are asked about "featured plugins", *make sure you install the Scala plug-in*.

Once you have completed the configuration you will be presented with a dialog asking if you want to create a new project, import a project, open a file, or check out from version control.
Choose "checkout from version control", and select GitHub.

In the dialog box that opens change "Auth type" to Token.
Now visit GitHub in a web browser.
Select your account (top right hand of the page).
Choose "Settings" and then choose "Personal access tokens".
Generate a token. Call it "intellij" and select the "repo" check box.
Copy the long string of numbers and text and paste it into the "Token" box.
Now login to GitHub.

Install the SBT console add-on.

[intellij-download]: https://www.jetbrains.com/idea/download/
