# SQLBatch
A java swing application for executing batch DML SQL Statements from files stored in the specified directory.

[![Codacy Code Badge](https://api.codacy.com/project/badge/grade/d739b8f7d5714a73967803de79e041cd)](https://www.codacy.com/app/java-sqlbatch)
[![Circle CI](http://circleci.com/gh/nickrfer/java-sqlbatch.svg?style=svg)](http://circleci.com/gh/nickrfer/java-sqlbatch)
[![Dependency Status](https://dependencyci.com/github/nickrfer/java-sqlbatch/badge)](https://dependencyci.com/github/nickrfer/java-sqlbatch)
[![MIT license](http://img.shields.io/badge/license-MIT-brightgreen.svg)](http://opensource.org/licenses/MIT)
[![StackShare](http://img.shields.io/badge/tech-stack-0690fa.svg?style=flat)](http://stackshare.io/nickrfer/sqlbatch)
[![Stories in Ready](https://badge.waffle.io/nickrfer/sqlbatch.png?label=ready&title=Ready)](https://waffle.io/nickrfer/sqlbatch)

# Details

While reading the files, the program checks if the line starts with a DML Command (INSERT, DELETE, UPDATE, MERGE or CALL) and executes it, otherwise it is ignored.

For better optimization, the commit happens when 1000 batchs are added to the PreparedStatement, and at the final iteration.
Logs will be appended to sqlbatch.log where the generated JAR is executed.

The file sqlbatch.properties can be put in the same path as the generated JAR to configure the app form, if the user doesn't want to input the fields everytime. The properties are { db.name, server.name, db.port, db.user, db.password, import.path }.

If an error occurs while executing the commands, a message popup is shown stating the error, the line that contains the command that caused the error, and the line that contains the command that was last commited so the user can manually remove the lines above and the commands doesn't get commited again.

So far the SQLBatch app supports the DB2, Oracle and SQL Server Databases. I am planning to add support for other Databases.

# Configuration
Since the DB2 and Oracle drivers are not available at the mavenrepository.com, you need to run the following commands after downloading the drivers in order to add the dependencies to the local maven repository:

- ojdbc7:
mvn install:install-file Dpackaging=jar -Dfile=<USER_HOME>/ojdbc7.jar -DgroupId=com.oracle -DartifactId=ojdbc7 -Dversion=12.1.0.1

- db2jcc:
mvn install:install-file Dpackaging=jar -Dfile=<USER_HOME>/db2jcc.jar -DgroupId=com.ibm.db2.jcc -DartifactId=db2jcc -Dversion=3.8.47

- db2jcc_lcense_cu:
mvn install:install-file -Dpackaging=jar -Dfile=<USER_HOME>/db2jcc_license_cu.jar -DgroupId=com.ibm.db2.jcc -DartifactId=db2jcc_license_cu -Dversion=3.8.47

Feel free to fork and change the dependency versions that best suit your needs!
