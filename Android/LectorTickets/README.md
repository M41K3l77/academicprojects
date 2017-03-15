# LectorTickets

Android client application. It allows to send to the cloud the data of your purchase tickets. The data are obteined from the image of the receipt and sent through Rest Service.

## Authors

Miguel Ángel Holgado Ceballos

## Functionalities

* Login
* Register
* ticket(receipt) recognizer
* Check product names (automatically)
* Edit ticket data
* Send ticket data

## Notice

This is the client part of a bigger system. With tess-two is obteined the product names but the application automatically sends these names to the server to make sure these names really exist in the D.D.B.B. (Cloud). Remember to change the Rest Services URL's for your owns.
The application use sheets for the receipts, in this moment only exists the sheet for MERCADONA supermarket but more can be added.
The language used in tess-two is spanish but can be changed https://github.com/tesseract-ocr/tessdata.

## Prerequisites

NDK integrated in AndroidStudio.
A compiled version of tess-two, in this case https://github.com/rmtheis/tess-two/releases/tag/5.4.1 (yes, I know it's an old version). Ones compiled, create two dirs in libraries (tess-two and eyes-two). Copy the content of the compiled version of tess-two inside the tess-two dir created in the project and the content of eyes-two inside eyes-two dir project. If you find another way (easier) to integrate tess-two into the project just do it ;).

## Built With

* [AndroidStudio](https://developer.android.com/studio/index.html) - The client framework used
* [tess-two](https://github.com/rmtheis/tess-two) - Text recognition engine for images
* [Retrofit](http://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java (use in the Rest service)

## Application working

### SignUp an SignIn

<img src="https://cloud.githubusercontent.com/assets/13255003/23949839/2675c802-0989-11e7-98a4-7a6500659703.png" width="23%"></img>
<img src="https://cloud.githubusercontent.com/assets/13255003/23949933/8ad5b10e-0989-11e7-9aa3-362f2620535d.png" width="23%"></img>
<img src="https://cloud.githubusercontent.com/assets/13255003/23950319/e48315b0-098a-11e7-90a0-c34675f45042.png" width="23%"></img>
<img src="https://cloud.githubusercontent.com/assets/13255003/23950318/e48159aa-098a-11e7-897c-1ccd7abac1ca.png" width="23%"></img>

### Taking photo and recognition

<img src="https://cloud.githubusercontent.com/assets/13255003/23950316/e47b44fc-098a-11e7-822c-a3ca99686ceb.png" width="23%"></img>
<img src="https://cloud.githubusercontent.com/assets/13255003/23950314/e472b904-098a-11e7-94bc-7d65b0433fba.png" width="23%"></img>
<img src="https://cloud.githubusercontent.com/assets/13255003/23950315/e479f3a4-098a-11e7-8435-f46af1388a65.png" width="23%"></img>
<img src="https://cloud.githubusercontent.com/assets/13255003/23950317/e47e80b8-098a-11e7-849d-72eee8eece92.png" width="23%"></img>

### Modifie and send data

<img src="https://cloud.githubusercontent.com/assets/13255003/23950320/e4a45eaa-098a-11e7-839a-e42e765b3ba8.png" width="23%"></img>
<img src="https://cloud.githubusercontent.com/assets/13255003/23950321/e4abc51e-098a-11e7-81c8-d5888b894828.png" width="23%"></img>
<img src="https://cloud.githubusercontent.com/assets/13255003/23950322/e4ac8bfc-098a-11e7-9da0-f1ef2ed08faf.png" width="23%"></img>
<img src="https://cloud.githubusercontent.com/assets/13255003/23950323/e4b16d02-098a-11e7-8b6a-38708405fb6f.png" width="23%"></img>
<img src="https://cloud.githubusercontent.com/assets/13255003/23950324/e4b65312-098a-11e7-94ca-caa7a71784c3.png" width="23%"></img>
<img src="https://cloud.githubusercontent.com/assets/13255003/23950328/e4dc69c6-098a-11e7-8375-e1e5abc05e0b.png" width="23%"></img>
<img src="https://cloud.githubusercontent.com/assets/13255003/23950325/e4b6a3b2-098a-11e7-8165-d56f257f3680.png" width="23%"></img> 