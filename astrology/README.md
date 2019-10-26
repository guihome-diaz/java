# Astrology

Simple JavaFX application that **computes both Western and Chinese zodiac** from a given birth date. 


--------------
## Change-logs

* 2016/12: project creation with Java 8 + JavaFX 8 (embedded in JDK)
* 2019/11: 
  * Upgrade to Java 11 & OpenJFX 11 (not included in the JDK anymore)
  * Dependencies cleanup
  * The application uses Java9+ modules
  * Switching to _jlink_ to generate standalone jar
--------------

## Project requirements


To ***build*** this project you need:
* **Maven** 3.6 or higher
* Java **JDK 11** or higher (see https://adoptopenjdk.net/)
* **OpenJFX 11** or higher (see [OpenJFX documentation](https://openjfx.io/) or [OpenJFX downloads](https://gluonhq.com/products/javafx/))

Optional:
* OpenJFX scene-builder (see [Gluon SceneBuilder](https://gluonhq.com/products/scene-builder/)) >> add this tool in IntelliJ / Eclipse IDE\
(i) default installation in "<code>ProgramFiles\SceneBuilder</code>"... Feel free to move that folder


\
To ***execute*** this project you just need the **_jlink_ folder** and execute the standalone jar.

## Build and execute locally

* Clone the repo
<code>git clone https://github.com/guihome-diaz/java.git</code>

* Build the project
  * <code>cd astrology</code> 
  * <code>mvn clean install</code>

* Run the artifact <code>mvn javafx:run</code>



## Build and execute from an IDE
From an IDE you just have to run **<code>AstrologyApp</code>**



## Generate and execute standalone jar
* Generate standalone jar (fat jar): <code>mvn javafx:jlink</code> 
* Run the application: 
  * Windows = <code>.\target\astrology-app\bin\astrology.bat</code>
  * Linux / Mac = <code>./target/astrology-app/bin/astrology</code>




## JavaFX resources

* [Javafx-maven-plugin](https://github.com/openjfx/javafx-maven-plugin) documentation ==> This plugin includes _jlink configuration_ to generate standalone jar and ZIP archive
* Very good article to start with JavaFX 11 and beyond (ex: JFX multi-threads): [Matthew Gillard - Getting start with JavaFX](https://www.twilio.com/blog/getting-started-with-javafx)
* Example of a real application OpenJFX 11 with modules: [Santulator](https://github.com/Santulator/Santulator/tree/modular)