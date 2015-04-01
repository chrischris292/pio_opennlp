import predictionio
import argparse

def sendData(args):
    # A user rates an item
    USER_ID = ""
    ITEM_ID = ""
    client = predictionio.EventClient(
        access_key=args.access_key,
        url=args.url,
        threads=5,
        qsize=500
    )
    file = open(args.file, "r")
    line = file.readline();
    counter = 1;
    while line!="": 
      print counter;
      RATING = 0;
      response = client.create_event(
          event="rate",
          entity_type="user",
          entity_id=counter,
          properties= { "tree" : line.rstrip()}
      )
      print response
      #Read New Line
      counter = counter + 1;
      line = file.readline();
if __name__ == '__main__':
  parser = argparse.ArgumentParser(
    description="Import sample data for classification engine")
  parser.add_argument('--access_key', default='invald_access_key')
  parser.add_argument('--url', default="http://localhost:7070")
  parser.add_argument('--file', default="dev.txt")

  args = parser.parse_args()
  print args
  sendData(args)
"""
client = predictionio.EventClient(
    access_key=<ACCESS KEY>,
    url=<URL OF EVENTSERVER>,
    threads=5,
    qsize=500
)
"""