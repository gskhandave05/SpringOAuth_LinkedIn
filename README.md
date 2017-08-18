# SpringOAuth_LinkedIn

This is a simple example of securing RESTful services using OAuth2 with LinkedIn as an authorization server.  

Following are the steps to be considered in order to secure the RESTful services using OAuth2 with LinkedIn authorization server :

* Step 1 — Configuring your LinkedIn application : Register your application on https://www.linkedin.com/secure/developer. LinkedIn grants access tokens to only registered REST endpoints. So register those REST endpoints on LinkedIn authorization server which you want to secure. In this example, I have used created only one REST end point and imported the LinkedIn profile for demo purpose. Configure the obtained Client ID and Client Secret in application.yml file. 

* Step 2 — Request an Authorization Code : Once the application is properly configured, the next step is requesting for authorization code. To request an authorization code, you must direct the user's browser to LinkedIn's OAuth 2.0 authorization endpoint. If the user has not previously accepted the application's permission request, or the grant has expired or been manually revoked by the user, the browser will be redirected to LinkedIn's authorization screen (as seen below).  When the user completes the authorization process, the browser is redirected to the URL provided in the redirect_uri query parameter.
If there is a valid existing permission grant from the user, the authorization screen is by-passed and the user is immediately redirected to the URL provided in the redirect_uri query parameter. Use https://www.linkedin.com/oauth/v2/authorization link for user authorization url in application.yml

* Step 3 — Exchange Authorization Code for an Access Token : Once authorization code is obtained, the next step is to get access token for your application. Use access token uri https://www.linkedin.com/oauth/v2/accessToken in application.yml.

* Step 4 — Make authenticated requests : After getting access tokens, you can start make authenticated request on behalf of your application.This is accomplished by 'Authorization' header.

### Build the project using Maven and hit http://localhost:8080/ for demo.
