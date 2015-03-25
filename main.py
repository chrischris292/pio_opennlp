__author__ = 'ChristopherChan'
import json
from pprint import pprint

with open('facebookCategories') as data_file:
    data = json.load(data_file)

output = open("interests","w")

data = data["data"]
counter = 0;
for category in data:
    name = category["name"].replace(" ","")
    name = name.replace("'","")
    name = name.replace("-","")
    output.write("val " +name + " = Value(" + str(counter) + ")");
    output.write("\n")
    counter = counter + 1;

'''
for category in data:
    print((data[0]))
'''