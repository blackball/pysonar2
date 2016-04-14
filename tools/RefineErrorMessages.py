# In the raw output from pysonar2, you could not find the crosspondence line with the error. 
# This script will be the tool to find out the line number and column number.

import os

def main(filename, start):
    lines = open(filename, "r").readlines()
    ln = 0
    for l in lines:
        #print(len(l))
        start = start - len(l)
        if start <= 0:
            return ln + 1
        ln += 1
    return ln + 1

main("count.py", 17)
