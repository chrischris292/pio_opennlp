# OpenNLP Interests Template

Given a sentence, return a interest category (i.e. Sports, Games, News). 

Interest categories are based off of Facebook Tier 1 interests. 

The engine uses the Apache OpenNLP library

## Versions

### v0.1.0

- initial version

## import sample data

```
$ python data/import_eventserver.py --access_key <your_access_key> --file data/train.txt
```

Use sample training data that has labeled sentences by category. Discretize the categories and label them accordingly in the event server. (

For example,
```
Russell Wilson is a super bowl quarterback	Sports	
```
In the python script associate sports with 1
```
if interest == "Sports":
interest = str(1)
```
Update the interest.scala code to associate sports with 1

```
val Sports = Value(1)
```
## Step to build, train and deploy the engine

```
$ pio build && pio train && pio deploy
```

## Query

The query takes a `String` `s`. The result contains a `Double` called `sentiment`. 

normal:

```
$ curl -H "Content-Type: application/json" \
-d '{
"s" : "I am happy"
}' \
http://localhost:8000/queries.json \
-w %{time_connect}:%{time_starttransfer}:%{time_total}

{"sentiment":3.0714285712172384}0.005:0.027:0.027
```

```
$ curl -H "Content-Type: application/json" \
-d '{
"s" : "This movie sucks!"
}' \
http://localhost:8000/queries.json \
-w %{time_connect}:%{time_starttransfer}:%{time_total}

{"sentiment":0.8000000001798788}0.005:0.031:0.031
```

