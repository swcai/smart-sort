#!/usr/bin/env python
# -*- coding: GB2312 -*-
# Last modified: 

"""docstring
"""

__revision__ = '0.1'

import sys
import time

def process(line):
    ts = int(line.strip().split('\t')[3])
    (year, mon, mday, hour, minu, sec, wday, yday, isdst) = time.gmtime(ts)
    return (wday, hour)

def main():
    days = [0] * 7
    hours = [0] * 24
    for filename in sys.argv[1:]:
        with open(filename) as f:
            for wday, hour in map(process, f.readlines()):
                days[wday] += 1
                hours[hour] += 1
    print days
    print hours

if __name__ == "__main__":
    main()

