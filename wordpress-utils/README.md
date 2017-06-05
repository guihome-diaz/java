# Wordpress utils

Small application to interact with a WORDPRESS blog and perform some maintenance operations such as:
* Download all gallery files (original | reduced versions)
* Download all Wordpress media library (= "uploads directories")


## How to build and run it?

* Clone the repo
<code>git clone https://github.com/guihome-diaz/java.git</code>

* Build the project
<code>mvn clean install</code>

* Run the artifact
<code>java -jar target/wordpress-utils/wordpress-utils.jar</code>


## From the IDE

From the IDE you just have to run <code>Main</code>


## How to deploy it?

TODO: check / adjust scripts (.bat and .sh) + <code>$target/wordpress-utils</code> folder
 
# Credits

This application is based on:
* FTP client
* Spring Boot
* JavaFX
* SpringBoot <> JavaFX bridge made by "roskenet"and forked by me (see github: https://github.com/guihome-diaz/springboot-javafx-support.git)

