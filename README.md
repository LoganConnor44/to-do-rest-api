# API Documentation

`http://localhost:8080/swagger-ui.html#/`

# Database Creation

The application will create and drop the desired entities everytime it is alive/dies, but you must first create the schema/database. Do so with the following commands.

## Schema Creation

1. Log into mysql
	* `mysql -uroot -p`
	* Continue through next prompt without entering a password
2. Create database
	* `CREATE DATABASE THE_DATABASE_NAME_THAT_HAS_BEEN_DEFINED_IN_THE_APPLICATION.PROPERTIES_FILE`;
	* `CREATE USER 'THE_USER_THAT_HAS_BEEN_DEFINED_IN_THE_APPLICATION.PROPERTIES_FILE'@'%' IDENTIFIED BY 'THE_PASSWORD_THAT_HAS_BEEN_DEFINED_IN_THE_APPLICATION.PROPERTIES_FILE';`
	* `GRANT ALL ON THE_DATABASE_NAME_THAT_HAS_BEEN_DEFINED_IN_THE_APPLICATION.PROPERTIES_FILE.* TO 'THE_USER_THAT_HAS_BEEN_DEFINED_IN_THE_APPLICATION.PROPERTIES_FILE'@'%';
