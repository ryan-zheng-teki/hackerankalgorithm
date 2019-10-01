#!/bin/python3

import math
import os
import random
import re
import sys

# Complete the jumpingOnClouds function below.
def jumpingOnClouds(c):
    totalCount  = len(c)
    totalStep = 0 
    currentStep = 0



class _OldClass:
    pass


class _NewClass:
    pass

_all_vars = set(dir(_OldClass) + dir(_NewClass))


class TestClass:
    a = 5

    def showWhatIHave(self):
        print("God is with you,so don't be afraid")

obj1 = TestClass()
obj2 = TestClass()

obj1.a = 23


def props(x):
    return {
        key: vars(x).get(key, getattr(x, key)) for key in dir(x) if key not in _all_vars
    }


if __name__ == '__main__':
    print(props(TestClass))
    print(vars(TestClass))