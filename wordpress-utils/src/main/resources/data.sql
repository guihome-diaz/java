-- Script that is automatically executed by SPRING BOOT on application start.
-- More info at: https://docs.spring.io/spring-boot/docs/current/reference/html/howto-database-initialization.html 
-- Version 1.0
-- Author: Guillaume Diaz

--
-- Application properties default
--
INSERT INTO APP_PROPERTY (key, value, description) VALUES ('db.version', '1.0', 'Version of the database');
INSERT INTO APP_PROPERTY (key, value, description) VALUES ('app.version', '1.0', 'Version of the current application');

INSERT INTO APP_PROPERTY (key, value, description) VALUES ('db.config.nbResultsPerPage', '15', 'To set the number of raw to return per page [Spring boot "PagingAndSortingRepository<T, K> repo" + "Page<T> results = repo.findAll(new PageRequest(x, x + maxNumber)"]');

INSERT INTO APP_PROPERTY (key, value, description) VALUES ('ftp.hostname', NULL, 'FTP server hostname (ex: ftp.daxiongmao.eu)');
INSERT INTO APP_PROPERTY (key, value, description) VALUES ('ftp.port', '21', 'FTP server control port (web standard is 21)');
INSERT INTO APP_PROPERTY (key, value, description) VALUES ('ftp.username', NULL, 'FTP username');
INSERT INTO APP_PROPERTY (key, value, description) VALUES ('ftp.password', NULL, 'FTP password');

INSERT INTO APP_PROPERTY (key, value, description) VALUES ('wordpress.root.relativePath', '/www/', 'Relative path on the FTP server to the Wordpress root (ex: /www or /www/myBlog)');

INSERT INTO APP_PROPERTY (key, value, description) VALUES ('local.backup.folder', NULL, 'Local folder for backup. This is where FTP files will be download (ex: D:\Backup\myBlog or /home/guillaume/backup/myBlog)');

