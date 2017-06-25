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



# Embedded database
This application will create and use a local database. 

## Where is the database file?
By default the database will be saved in **user.home**/daxiongmao/wordpress-utils/wordpress-utils.db

(i) you can change this default path in *src/main/resources/application.properties*


## How to access the database content?


### Local WEB connection
The application will start a local embedded server to manage database. 
(i) Just replace 'user.home' by your own path.

- **H2 console URL**:    http://localhost:8082
- **Database driver**:   H2 embedded
- **Database URL**:      jdbc:h2:file:*user.home*/daxiongmao/wordpress-utils/wordpress-utils.db


### TCP connection for SQL clients
The application will open and listen on a TCP socket

- **TCP socket**:        localhost:9092
- **Database driver**:   H2 embedded
- **Database URL**:      jdbc:h2:tcp://localhost:9092/file:/home/guillaume/daxiongmao/wordpress-utils/wordpress-utils.db




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

