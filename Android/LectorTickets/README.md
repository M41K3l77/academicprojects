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
