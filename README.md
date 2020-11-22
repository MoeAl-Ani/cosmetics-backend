# Cosmetics Backend
# Overview
```
Simple webshop backend written using Jersey Framework. backend support list products, create order, pay your order using
favorites payment method from paytrail, receive email notifiction on successful payment.
there is no Admin API created for managing products but it should be trivial to add. the Backend
support annoynmous users and valid users sessions, users can login using social login like facebook, google etc.
currently only facebook is supported but it is trivial to add others social login capibilites.
the backend system is divided into set of maven modules and it is extendable. it easy to create a new web service 
with different set of configuration, for reference look at the com.infotamia.cosmetics package.
```
# Table of contents
- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Setup](#setup)
    - [Local Deployment](#local-deployment)
    - [Re Deployment](#re-deployment)
- [Usage](#usage)
    - [API](#api)
        - [LOGIN](#login)
        - [PRODUCTS](#products)
        - [ORDERS](#orders)
        - [PAYMENT](#payment)
- [License](#license)
## Prerequisites
```
- host machine must have the docker/docker-compose installation done before can proceed to next steps.
```
## Setup
### LOCAL DEPLOYMENT
- under the config directory in root project, lookup the **config.yaml.dev**
    - copy this file but without the dev extension.
    - apply to it your facebook/google login configurations (**OPTIONAL**).
    - make sure you have the callback urls set in to your facebook dashboard the same as the one provided in 
    the configuration (**OPTIONAL**).
    - apply your AWS S3 bucket config (**OPTIONAL**).
    - apply your JWT config (**OPTIONAL**).
- under the config directory, lookup **datasource-config.yaml.dev**
    - copy this file without the dev extension.
    - apply your **database user/password** configs.
- server certificate is a self signed, to supply your own:
    - under config directory replace myselfsigned.pem with your own (***OPTIONAL***).
- server using haproxy as the main API gateway:
    - you can supply the haproxy.cfg with your own configurations under config directory (***OPTIONAL***).
- email templates can be modified under config/templates (***OPTIONAL***).
- you can configure your own deployment script using the docker-compose.yaml under root directory (***OPTIONAL***).
- **SMTP configuration (MANDATORY)**:
    - inorder to receive email, smtp configurations must be supplied under config/config.yaml.

- from the command line under root directory:
    - execute **make init**
    - open up the browser and call ***(https://127.0.0.1/cosmetics/api/v1/auth/facebook/)***
    - accept the self sign certificate or provide your own valid certificate as explained earlier.
    - login with facebook ***username and password.***
    - if login succeeded, you will be redirected to success page.
    - you can replace this by your web app main page or redirect to your third party web app route.
- in you web app / mobile client you can extract the jwt from the authorization header and use it for any secure resources.

### RE DEPLOYMENT
- **make redeploy** will clean, build and deploy new jar/docker container.
- **make reload** will reload running containers with new supplied configurations.
## Usage
### API
- #### LOGIN
    - **FACEBOOK**
        - curl -X GET 'https://127.0.0.1/cosmetics/api/v1/auth/facebook/' -H 'Content-Type: application/json' -H 'jwt-auth-token: {{JWT_CUST}}' -k - i
        - open up the link from the Location header.
        - type in your username / password
        - extract header and use it for subsequent requests.
    - **PROFILE**
        - replace JWT in the next line from the Authorization header from the previous request.
        - curl -X GET 'https://127.0.0.1/cosmetics/api/v1/user/profile' -H 'Authorization: Bearer JWT' -k
- #### PRODUCTS
    - **GET PRODUCTS**
        - curl -X GET 'https://127.0.0.1/cosmetics/api/v1/products/' -H 'Content-Type: application/json' -k
    - **GET SINGLE PRODUCT**
        - curl -X GET 'https://127.0.0.1/cosmetics/api/v1/products/1' -H 'Content-Type: application/json' -k
- #### ORDERS
    - **CREATE ORDER**
        - curl -X POST 'https://127.0.0.1/cosmetics/api/v1/orders' -H 'jwt-auth-token: {{JWT_EMP}}' -H 'Content-Type: application/json' --data-raw '{
          	"email":"YOUR EMAIL",
          	"phoneNumber": "PHONE",
          	"address":{
          		"country":"Finland",
          		"city": "Oulu",
          		"street": "Veteläisenkuja 11",
          		"zip": "90540"
          	},
          	"selectedProducts": [
          		{
          			"productId" : 1,
          			"quantity": 2,
          			"comment": "whatever"
          		},
          		{
          			"productId" : 2,
          			"quantity": 1,
          			"comment": "whatever 2"
          		}
          	]
          }' -k
    - **GET ORDER DETAILS**
        - if order paid, either from email open up the order details or copy the order reference code from the email, or the paid order and replace it in the next line.
        - curl -X GET 'https://127.0.0.1/cosmetics/api/v1/orders/c91355c1-2b0f-4b29-910d-49660145a780' -H 'Content-Type: application/json' -k
- #### PAYMENT
    - **CREATE PAYMENT**
        - replace the orderId by the one created
        - curl -X POST 'https://127.0.0.1/cosmetics/api/v1/payment/paytrail/orders/1' -H 'Content-Type: application/json' --data-raw '' -k -i
        - open up the link from the Location header.
        - choose OP payment method
        - userId = 123456,  password = 7890, code = 1212
        - accept payment or cancel.
        - on payment success, email notification will be sent.

## Database
![Alt text](./misc/database/EER/cosmetics.png?raw=true)

## License
```
MIT License

Copyright (c) 2020 infotamia

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
 

