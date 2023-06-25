# JavaPC

Parallel Coordinates n-D data visualization implementation in Java.  

Parses CSV files (must have class/id in the lass column for now) and displays in a parallel coordinates plot.  

## How to use

Currently program plots a dataset in Parallel Coordinates and this is the only feature.  
To plot a dataset make sure the class/id column is the last column.  
- Start program with Java  
- Click 'Load CSV', pick your file  
- Click 'Render'  

## Project Goals

- last axis plot for class/id   
- axis name and range labels  
- move dataset processing to preprocssing step on dataset load  
- dataset class abstraction to store mins, maxes, names, classCount  
- detect class/id column on dataset load  
- other dataset formats (.txt, .pdf?)
- axis inversion    

## Early screenshots

### Iris dataset with class specific colors:  
![Iris dataset with class specific colors](screenshots/iris_with_class_specific_colors.png)
### Iris dataset with random colors:  
![Iris dataset with random colors](screenshots/iris_with_random_colors.png)
### first wbc diagnosis dataset render:  
![first wbc diag render](screenshots/first_wbc_diag_render.png)
### first mnist letters dataset render:  
![first mnist letters render](screenshots/first_mnist_letters_render.png)
