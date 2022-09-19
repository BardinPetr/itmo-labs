# Лабораторная работа №1 вариант №311902

## Запуск

```
./build.sh
java -jar build/main.jar
```

## Задание

- Создать одномерный массив c типа int. Заполнить его числами от 5 до 18 включительно в порядке возрастания.
- Создать одномерный массив x типа double. Заполнить его 13-ю случайными числами в диапазоне от -7.0 до 8.0.
- Создать двумерный массив r размером 14x13. Вычислить его элементы по следующей формуле (где x = x[j]):
  - если c[i] = 11, то $r[i][j] = (\frac{2}{3}/(((\arcsin(\frac{x+0.5}{15}+1)/2)^2-0.25))^{((x/2)^2)^{((x*(3+x))^3+\frac{3}{4})}}$;
  - если c[i] ∈ {6, 8, 9, 10, 12, 15, 17}, то $r[i][j] = \arctan(1/e^{\sqrt{\arccos(\frac{x+0.5}{15})}})$;
  - для остальных значений c[i]: $r[i][j] = (e^{(\frac{1}{3}\cdot(2x)^x)^3}/2)^2$.
- Напечатать полученный в результате массив в формате с четырьмя знаками после запятой.