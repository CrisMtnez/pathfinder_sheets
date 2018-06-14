# pathfinder_sheets

Funcionan las tablas y todos los cálculos asociados a ellas, incluidas las armas y armaduras que se pueden ir añadiendo y
quedan almacenadas (falta la opción de borrar), los checkboxes y puntos de vida (partiendo del total puesto arriba va
calculando los puntos de vida actuales a medida que se le van añadiendo los daños en la parte de abajo como números positivos 
que se pueden ir sumando (2+15+5...) o restando si es una curación (+-2+-4...). 

Guarda los datos y lee las fichas guardadas excepto por la parte de armas y armaduras que aún da algún problema (duplica 
entradas) y algún problema de acceso a una nueva ficha desde otra ficha (en el menú File).

Quedan por implementar las condiciones, el panel de equipment y de spells que se abrirán y cerrarán apareciendo una u otra en 
el hueco libre que queda a la derecha del panel, los feats and features, que aparecerán en el marco destinado a eso, y las
otras opciones del menú de las fichas:

- Options: 
    - Reset All: borra todos los datos previa confirmación.
    - Dice Roller: abre un  panel modal en que se pone el número de dados y de cuántas caras y los tira y calcula el total.
    
- Help:
    - Tooltips: opción con checkbox marcable que activa o desactiva tooltips con información sobre todos los elementos de la 
      ficha, con qué valores se calculan, para qué sirven, etc.
    - About: información sobre el programa
    
Así como todo lo relativo a corrección de errores y mejora del código, que está sin depurar y posiblemente con partes repetidas,
reducibles o poco claras.
