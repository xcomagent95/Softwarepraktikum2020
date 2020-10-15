# softwarepraktikum2020 - Wishing rod

Program analyzing radarpictures of the Sentinel-1 satellite (from the Copernicus-Project of ESA). It calculates the area of a coherent waterbody. It is supposed to compare seasonal deviations of waterlevels and long-/middle term tendencies of shrinking  or growing.

## Technology:
### Input:
1. Downloads namend data fromm ESA's Copernicus Open Access Hub and loads it into the program
2. Detects watersurfaces in radarpictures after colour-smoothing and recolouring into a black/white scheme
3. Finds and counts all pixels within the coherent waterbody through a floodfill-algorithm. Returns number of pixels.
4. One pixel has the area of 100m^2. Calculates the area of the waterbody.
### Output:
