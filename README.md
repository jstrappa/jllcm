/*
 * LLCM: Log-linear models comparison measure
 * 
 * This program computes the distance between two log-linear models'
 * structures according to the measure defined in the paper:
 * 'Log-linear models independence structure comparison',
 * see https://arxiv.org/abs/1907.08892 
 * Copyright (C) 2019  Jan Strappa
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 * 
 */
# Log-Linear Model Comparison Measure
## Description

This program is an implementation of the method presented in the paper
_Log-linear models independence structure comparison_,
see ```https://arxiv.org/abs/1907.08892```

## Usage:

```
$ java -jar jllcm.jar model1 model2 n [method]
```


`model1` and `model2` are Markov network (.mn) files in the format for Libra Toolkit. This implementation supports only binary variables.

`n` is the number of variables

`[method]` is an optional argument: `bf` for brute force; `both` for default and brute force; omit for default method;
`slowbf` uses the first implementation of brute force which generates all contexts and is more computationally expensive.

The output shows the values for the confusion matrix. Example output:

```
LLCM: [TP: 16, FN: 8; FP: 0, TN: 0]
BF:   [TP: 16, FN: 8; FP: 0, TN: 0]
```

For examples and more information see **examples/README** and **examples/README_featureDifference**.
