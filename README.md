# SpringBootMovieRest


### App Functionality
This SpringBoot REST app queries the "TheMovieDB" API, it saves the results in PostGres and provides an API for a client side application to access the data. It is in essence replicating the "TheMovieDB" API.

#### Why Does This App Exist
It was an exercise to learn fullStack development, I built this app alongside my Flutter App which depends on it.
Flutter App: https://github.com/TinNova/flutter_movies
  

## The App Architecture
I followed the standard architecture pattern for Spring Rest applications however I did use interfaces even though they aren't required for such a small app.

#### Controllers
The controllers define the api's that are available and what information a client can get. If it's not in the controller then it cannot be accessed.

#### Services
The services handle the business logic for the app they are also the access point to PostGres.

#### Repos
The repos directly interact with PostGres to save, delete and edit data.

<img src="https://github.com/TinNova/SpringBootMovieRest/blob/master/BulkDownload%20UML.png" width="500" align="right" hspace="20" title="Image description"/>

## App Functionality
### Bulk Download
To initially populate SQL with data a bulk download method has to be called, this is the most complex piece of code in the code.

1. the app retreives the latest 20 movies from theMovieDb and saves them to SQL (this data doesn't contain info about actors). 

2. the app retreives the actors in these 20 movies from theMovieDb and saves them to SQL. 

3. the app retreives the Credits data from theMovieDb of the 20 movies to access the Actor Ids associated with a movie. 

4. the app assigns Actors to the Movies saved in SQL.

This is complex, therefore see the Sequence Diagram as a visual explanation.
<img src="https://github.com/TinNova/SpringBootMovieRest/blob/master/SaveMovie%20UML.png" width="500" align="right" hspace="20">
### Get Data
The app provides api's to access Movies and Actors from SQL

### Authorisation / Create An Account
The app uses the security:spring-security-test library to enable authorisation, allowing for the creation of Users and the ability to provide bespoke data per user.

Some functionalities that are available:
- Reviewing movies
-- A review is associated with a user and a movie, all users can see these reviews but only the author can delete a review.

- Saving a movie
-- A movie can be associated with a user, only that user can see movies associated with them, only they can delete a saved movie.

### Authentication
Token authentication is enabled.

## How To Setup Project

### Install Postgresql
1. Install the latest version: https://postgresapp.com/downloads.html
2. Open PostgreSql and start the server
3. Go to Applications > right click and select Show Package Contents > contents > versions > {largest version} > bin > psql
- This will open postgres in the terminal command line
4. Use command line to create a database called "movie"
5. Run the app

This is all you need to do, the app contains the code that builds the rest of the database.


### How To Run The App
1. First bulk download data
- You need an access token to run the GET bulk command. To get the access token first POST Login using one of the users automatically created when the app runs found here: FluttermovierestApplication.kt
- The query params keys are "username" and "password"
- After logging in, take the access token and do a GET bulk call with headers, the key is "Authorization" and the value is "Bearer {access_token"

### Running App On Railway.app
It's a cloud service for shipping software.

Create a new empty project in Railway and start by creating a PostgresSQL database, then add a new project from Github,
you can use the following environment variable based on the database you just created.

```properties
spring_properties_active=prod
PROD_DB_HOST=HOST_HERE
PROD_DB_PORT=POST_HERE
PROD_DB_NAME=railway
PROD_DB_PASSWORD=PASSWORD_HERE
PROD_DB_USERNAME= postgres
PROD_MOVIE_DB_API_KEY=API_KEY_HERE
```
