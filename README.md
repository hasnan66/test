# Getting Started

### Available Users:
```
User name: testadmin
Password: adminpassword

User name: testUser
Password: userpassword
```

### Getting an Access Token:
(provided by Spring Security OAuth2)
```
curl --location --request POST 'http://localhost:8082/oauth/token' \
--header 'Authorization: Basic d2ViOndlYg==' \
--form 'grant_type="password"' \
--form 'username="testuser"' \
--form 'password="userpassword"'
```

### Accessing the Resources:
```
curl --location --request GET 'http://localhost:8082/account/statements' \
--header 'Authorization: bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiYWJjZDEyMzQiLCJ1c2VyX25hbWUiOiJ0ZXN0YWRtaW4iLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiLCJ0cnVzdCJdLCJleHAiOjE2MDczNTgwNjYsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiZTZjNWYyOGMtNGFhZi00MmE4LTlmMDItY2M3NGQ3YjQ3MDcxIiwiY2xpZW50X2lkIjoid2ViIn0.aNN_oOdpN2CHZGF95C0-uMIG-R10hu0Kr0kxiGbR0LI' \
--header 'Content-Type: application/json' \
--data-raw '{
    "accountId" : 2,
    "fromDate" : "05-8-2012",
    "toDate" : "10-1-2020",
    "fromAmount" : 200.0,
    "toAmount" : 200000
}'
```

```
curl --location --request GET 'http://localhost:8082/account/statements' \
--header 'Authorization: bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiYWJjZDEyMzQ1IiwidXNlcl9uYW1lIjoidGVzdHVzZXIiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiLCJ0cnVzdCJdLCJleHAiOjE2MDczNzI2NzksImF1dGhvcml0aWVzIjpbIlVTRVIiXSwianRpIjoiYzU3ZTZkY2QtODhmMS00YjY1LWFkZDAtNDM4ZGYzZmJiNDMzIiwiY2xpZW50X2lkIjoid2ViIn0.CiXuS-biiFVa-U0ZW2qgHYRT7asXByGdqGMRwy4CBY0' \
--header 'Content-Type: application/json' \
--data-raw '{}'
```

### Assignment Info

- add ms access file path in properties.
- add jar exist under src/main/java/resource/lib in project structure(import).
- input request is given in above curl.
- MD5 is used for hashing account number.
- Error codes are in response dto( if there is error there will be erro code in response and data will be empty).
- as there are obnly 2 users so used hard coded.
- access token is valid for 5 mintues, seeion will expire after 5 mintues. use refresh token to perform more action or run project again.
- Oauth is used which gurrenties 1 login at time.
- I did my best according to my understanding of the requirement!.
