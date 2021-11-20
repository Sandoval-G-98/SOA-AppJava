ARMADO DE AMBIENTE

#@title ## 2.1 Parámetros de ejecución
#@markdown ---
#@markdown ### Especifique la URL de la imagen:
url_imagen = "https://github.com/wvaliente/SOA_HPC/blob/main/unlam.jpg?raw=true" #@param {type:"string"}

#@markdown ---
# Leo la imagen desde internet.
#!wget https://github.com/wvaliente/SOA_HPC/blob/main/unlam.jpg?raw=true -O imagen.jpg

# TODO: Mejorar información y resultado de ejecución. 
!wget {url_imagen} -O imagen.jpg

#@markdown ### Especifique el Blur que se desea realizar (numero entero):
#@markdown ###### Numeros mayores a 10 tardaran más de 10 minutos
#@markdown ### Especifique la URL de la imagen:
blurfactor = 1 #@param {type:"integer"}



DESARROLLO
 %matplotlib inline
from datetime import datetime

tiempo_total = datetime.now()

import  matplotlib.pyplot as plt
import numpy
from PIL import Image 
from matplotlib.image import imread

# --------------------------------------------
# Definición de función que transforma el tiempo en  milisegundos 
tiempo_en_ms = lambda dt:(dt.days * 24 * 60 * 60 + dt.seconds) * 1000 + dt.microseconds / 1000.0
# --------------------------------------------

def average(img, x, y, blurfactor):
  rtotal = gtotal = btotal = 0
  for x2 in range(x-blurfactor, x+blurfactor+1):
    for y2 in range(y-blurfactor, y+blurfactor+1):
      r, g, b = img.getpixel((x2, y2))
      rtotal = rtotal + r
      gtotal = gtotal + g
      btotal = btotal + b
  rtotal = rtotal // ((blurfactor * 2 + 1)**2)
  gtotal = gtotal // ((blurfactor * 2 + 1)**2)
  btotal = btotal // ((blurfactor * 2 + 1)**2)
  return (rtotal, gtotal, btotal)


# Abro la imagen
img_nombre = 'imagen.jpg'
image = Image.open( img_nombre )
# Optengo las proporsiones de la imagen. 
img_ancho, img_alto = image.size 

newImg = Image.new("RGB", (img_ancho, img_alto), (0, 0, 0))
#blurfactor = 5

tiempo_blur = datetime.now()

for x in range(blurfactor, img_ancho - blurfactor):
  for y in range(blurfactor, img_alto - blurfactor):
    r, g, b = image.getpixel((x,y))
    r2, g2, b2, = average(image, x, y, blurfactor)
    newImg.putpixel((x,y), (r2, g2, b2))

tiempo_blur = datetime.now() - tiempo_blur

plt.figure()
imgplot=plt.imshow( image )
plt.figure()
imgplot=plt.imshow( newImg )

tiempo_total = datetime.now() - tiempo_total

print( "Tiempo de blur::", tiempo_en_ms( tiempo_blur   ), "[ms]" )
print( "Tiempo Total:", tiempo_en_ms( tiempo_total ), "[ms]" )
