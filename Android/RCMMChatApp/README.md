# RCMMChatApp

Android chat application. Local net (Wifi) unicast/multicast and internet unicast. Unicast two devices and multicast two or more devices.

## Authors

Miguel Ángel Holgado Ceballos and Luis Fernando Melón Pérez

## Course

Fourth course first semester.

## Functionalities

* Two devices unicast communication (local and internet). Send text and images.
* More than two devices multiunicast communication (local). Send text and images.

## Notice

In unicast is neccesary to enter source and destination IPs and ports, the same in both devices and ports greater than 1023. In multicast must be used an allowed IP and the follow are forbiden:
* 224.0.0.0 net IP (forbiden)
* 224.0.0.1 All devices (forbiden)
* 224.0.0.2 All routers (forbiden)
* 224.0.0.4 All routers DVMRP (forbiden)
* 224.0.0.5 All routers OSPF (forbiden)
* 224.0.0.13 All routers PIM (forbiden)

## Prerequisites

Nop.

## Built With

* [AndroidStudio](https://developer.android.com/studio/index.html) - The client framework used

## Application working

### Unicast

<img src="https://cloud.githubusercontent.com/assets/13255003/23951551/ab70a0ea-098e-11e7-8ab9-76c0abffccb2.png" width="23%"></img>
<img src="https://cloud.githubusercontent.com/assets/13255003/23951550/ab6e6672-098e-11e7-9d87-01a91bbcd70c.png" width="23%"></img>
<img src="https://cloud.githubusercontent.com/assets/13255003/23951557/abb0f88e-098e-11e7-8fa2-27e272ba6fb9.png" width="23%"></img>
<img src="https://cloud.githubusercontent.com/assets/13255003/23951556/aba1eef2-098e-11e7-8f95-633eb6b9ea2e.png" width="23%"></img>

### Multicast

<img src="https://cloud.githubusercontent.com/assets/13255003/23951554/ab9df3ec-098e-11e7-9def-4aff25efd152.png" width="23%"></img> 
