# softwarepraktikum2020

Program analyzing radarpictures of the Sentinel-1 satellite (from the Copernicus-Project of ESA). It calculates the area of coherent a waterbody. It's supposed to compare seasonal deviations of waterlevels and long-/middle term tendencies of shrinking  or growing.

Technology:
1. Downloads namend data fromm ESA's Copernicus Open Access Hub and loads it into the program
2. Detects watersurfaces in radarpictures after colour-smoothing and recolouring into a black/white scheme
3. Finding all pixels within the coherent waterbody through a floodfill-algorithm
