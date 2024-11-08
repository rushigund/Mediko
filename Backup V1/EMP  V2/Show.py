import sqlite3 as sl
con = sl.connect('Server-database.db')

#with con:
 #   data = con.execute("SELECT * FROM LData")
  #  for row in data:
   #     print(row[0])
    #    print(row[1])
     #   print(row[2])

with con:
    data = con.execute("SELECT * FROM CData")
    for row in data:
        print(row[0])
        print(row[1])
        print(row[2])
        print(row[3])
        print(row[4])
        print(row[5])