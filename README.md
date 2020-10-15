# softwarepraktikum2020 - Divining Rod
![NeueLogo_DiviningRod](https://user-images.githubusercontent.com/61976211/96106434-88c26500-0edb-11eb-8529-3a5e7c3fcbde.png)

Our Program is analyzing radarpictures of the Sentinel-1 satellite from the Copernicus-Project of ESA. It calculates the area of a coherent waterbody and is supposed to compare seasonal deviations of waterlevels and long-/middle term tendencies of shrinking  or growing.

## Technology:
* The SENTINEL-1 Toolbox ([s1tbx](https://github.com/senbox-org/s1tbx))
* Java, Maven
## Input:
* SciHub Credentials 
* ingestion timeframe
* origin x-y-coordinate in *WGS 84* Referencesystem

## Workflow:
![neu1(1)](https://user-images.githubusercontent.com/61976211/96146058-2af74280-0f06-11eb-889f-699fe66b3792.jpg)

1. Downloads namend data fromm ESA's Copernicus Open Access Hub and loads it into the program
2. Detects watersurfaces in radarpictures after colour-smoothing and recolouring into a black/white scheme
3. Finds and counts all pixels within the coherent waterbody through a scanline-algorithm. Returns number of pixels.
4. One pixel has the area of 100m^2. Calculates the area of the waterbody.

### How our scanline version works:
![fill_algo](https://user-images.githubusercontent.com/61976211/96111127-13f22980-0ee1-11eb-9647-8f51196b4f91.gif)
* GIF Sample shows *Labussee* (lake in the *Mecklenburgische Seenplatte* district in *Mecklenburg-Vorpommern*, Germany. At an elevation of 57.5 metres (189 ft), its surface area is 2.51 square kilometres (0.97 sq mi)).  

## Software architecture
Consult our [UML-Diagram](https://github.com/xcomagent95/softwarepraktikum2020/blob/master/UML.pdf)

## Output:
* an Integer that represents the approximate area (in squaremeters) of water-sureface
* and the choosen smippet of sentinel-1 radar image gets saved, filtered (Gauß, Median or non) and the chosen lake gets colored

*grau eingefärbtes Bild ohne bearbeitung durch Filter
![grey_normal](https://user-images.githubusercontent.com/51150328/96146914-19fb0100-0f07-11eb-8ce6-60c847e24a06.jpeg)

## Projectteam:
* Alexander Pilz            [@xcomagent95](https://github.com/xcomagent95)
* Josefina Balzer           [@jbalzer12](https://github.com/jbalzer12)
* Gustav Freiherr von Arnim [@OTI2020](https://github.com/OTI2020)
#### joined for one week:
* Dorian Hennigfeld         [@dhenn12](https://github.com/dhenn12)
* Ole Heiland               [@heilandoo](https://github.com/heilandoo)
## Author of Readme: 
* Gustav Freiherr von Arnim [@OTI2020](https://github.com/OTI2020)
