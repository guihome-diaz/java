# Java FX8 client

This is a working JavaFX8 project with:
* Maven support
* Creation of a standalone uber jar
* Launcher scripts

The stand-alone application is in `/target/fx-client` 

## Usage

You must have a server running "daxiongmao" server.



## Maven configuration

### Application packaging 

JavaFX8 has a dedicated Maven plugin to create bundles.
You can customize the output artifact (see https://javafx-maven-plugin.github.io/)

Use the maven resources plugins to copy the launcher inside the target directory.


### Resources

You must tell Maven to include all FXML and other resources (css, images, ...). 
```xml
    <build>
        <!-- Artifact name -->
        <finalName>diffing</finalName>
        
        <!-- Include all resources in the output artifact (FXML, CSS, images, etc.) -->
        <resources>
            <resource>
                <filtering>false</filtering>
                <directory>${basedir}/src/main/java</directory>
                <includes>
                    <include>**/*.fxml</include>
                </includes>
            </resource>
            <resource>
                <filtering>false</filtering>
                <directory>${basedir}/src/main/resources</directory>
                <includes>
                    <include>**/*.*</include>
                </includes>
            </resource>
            <resource>
              <filtering>false</filtering>
              <directory>${basedir}/src/main/launcher</directory>
              <includes>
                <include>**/*.bat</include>
                <include>**/*.sh</include>
              </includes>
            </resource>
        </resources>
```


