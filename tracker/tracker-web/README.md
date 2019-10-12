# Tracker web user interface


--------------------------

##  Project description

TODO

--------------------------

## Code source structure

the structure is based on Maven + Spring-Boot recommandations:
* `src/main/java` : controllers and backend classes
* `src/main/resources/templates` : Thymeleaf templates. This is what pre-compiled and rendered to user
* "Frest-admin" UI resources
  * `src/main/resources/app-assets`:  Folder contain all the Frest Admin Template assets which has css, js, fonts, images & json files. **It is not recommend to change any files from this folder, use `assets` folder instead**.
  * `src/main/resources/assets` : Folder contain assets which has sample scss, css, js files. **Use folder for user customization purpose, you can add any custom css,js files & images in this folder**.

  
  
/!\ Do not edit `app-assets` and ``. Just **put the custom scripts and styles into the `assets` folder**.
 
 
(i) More information about folders' structure is available in [Frest-Admin documentation](src/main/resources/static/frest-admin/html/ltr/documentation/documentation-folder-structure.html)


## How to access UI "frest-admin" template?

The template is included inside the project. Just unzip the template, build project and launch it to access the template.

1. Unzip source files
  * `cd tracker-web/src/main/resources/static`
  * `unzip frest-admin.zip`
  * `cd ../../../../..`
2. Build project
  * `mvn clean install`
3. Launch project
  * `java -jar tracker-web/target/tracker-web-0.0.1-SNAPSHOT.war`
4. Navigate to help page
  * Launch web-browser
  * Go to [http://localhost:9090/frest-admin/index.html](http://localhost:9090/frest-admin/index.html)

(i) we are using the 
Launch the `index.html` file to access template's contents.
* `firefox ./frest-admin/index.html &`


## JSPs to Thymeleaf

You will find below some key points to easily move from JSPs to Thymeleaf.

### Request context path (server URL at runtime)

In Thymeleaf the equivalent of JSP's `${pageContext.request.contextPath}/edit.html` would be `@{/edit.html}`




--------------------------


## Optional tools

These are optional tools, only required to perform advanced UI debugging on the commercial template.

### NodeJS

The UI is build with NodeJS **10.16.3 LTS**. Get NodeJS from the official website [nodejs.org](https://nodejs.org/en/)

You can check it in your terminal window using these commands `node --version` and `npm --version`.

### NPM library: GulpJS

[GulpJS](https://gulpjs.com/) is a JavaScipt task runner. In one word: automation. The less work you have to do when performing repetitive tasks like minification, compilation, unit testing, etc...

Install _gulp_ from command line globally:
- `npm install gulp-cli -g`
    
To install LORA node packages: 
- navigate to **lora-ui/src/main/resources/static/frest-admin/** directory 
- Run `npm install` 

NPM uses the `package.json` file to automatically install the required local dependencies listed in it.



## Resources

### Icons

Thanks to _frest-admin_ template, this application uses:
* [LivIcons](https://livicons.com/icons-original.php)
* [BoxIcons](https://boxicons.com)



--------------------------

## Licence / legal aspects

### Source code access and usage

This is a **private repository**, this code is **not open-source** and it must not be used as such. 

The application inside this repository is not designed for production use: it must be reviewed and adapted before being released.


### 3rd party licenses and links

This application uses some none-free components: 
- User Interface uses [Frest Admin template](https://www.pixinvent.com/demo/frest-clean-bootstrap-admin-dashboard-template/landing-page/)
  - We are currently using a _regular license_ that is only suitable for development and tests purposes. This is not production ready
  - Link to [Pixent template's documentation](https://pixinvent.com/demo/frest-clean-bootstrap-admin-dashboard-template/documentation)

--------------------------

