# SpringBootMovieRest

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
