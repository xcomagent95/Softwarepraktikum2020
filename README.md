# softwarepraktikum2020 - Divining Rod
![NeueLogo_DiviningRod](https://user-images.githubusercontent.com/61976211/96106434-88c26500-0edb-11eb-8529-3a5e7c3fcbde.png)

Our Program is analyzing radarpictures of the Sentinel-1 satellite (from the Copernicus-Project of ESA). It calculates the area of a coherent waterbody and is supposed to compare seasonal deviations of waterlevels and long-/middle term tendencies of shrinking  or growing.

## Technology:
* The SENTINEL-1 Toolbox [s1tbx](https://github.com/senbox-org/s1tbx)
* Java, Maven
## Input:
* SciHub Credentials 
* ingestion timeframe
* origin x-y-coordinate in WGS 84 Referencesystem

## Workflow:
![workflowDiagram_diviningRod](https://user-images.githubusercontent.com/61976211/96110552-4e0efb80-0ee0-11eb-92cf-b4135e20c406.png)

1. Downloads namend data fromm ESA's Copernicus Open Access Hub and loads it into the program
2. Detects watersurfaces in radarpictures after colour-smoothing and recolouring into a black/white scheme
3. Finds and counts all pixels within the coherent waterbody through a floodfill-algorithm. Returns number of pixels.
4. One pixel has the area of 100m^2. Calculates the area of the waterbody.
## Output:
![fill_algo](https://user-images.githubusercontent.com/61976211/96111127-13f22980-0ee1-11eb-9647-8f51196b4f91.gif)

## Projektteam:
* Alexander Pilz            [@xcomagent95](https://github.com/xcomagent95)
* Josefina Balzer           [@jbalzer12](https://github.com/jbalzer12)
* Gustav Freiherr von Arnim [@OTI2020](https://github.com/OTI2020)
#### einwöchig dabei:
* Dorian Hennigfeld         [@dhenn12](https://github.com/dhenn12)
* Ole Heiland               [@heilandoo](https://github.com/heilandoo)
## Autor des Readmes: 
* Gustav Freiherr von Arnim [@OTI2020](https://github.com/OTI2020)
