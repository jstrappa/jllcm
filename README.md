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
