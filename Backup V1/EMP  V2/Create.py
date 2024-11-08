import sqlite3 as sl
con = sl.connect('Server-database.db')

#with con:
 #   con.execute("""
  #      CREATE TABLE LData (
   #         ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    #        LATITUDE DOUBLE,
     #       LONGITUDE DOUBLE
      #  );
   # """)

#ID/OName/CNo/Address/LATITUDE/LONGITUDE
with con:
    con.execute("""
        CREATE TABLE CData (
            ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
            OName TEXT,
            CNo INTEGER,
            Address TEXT,
            LATITUDE DOUBLE,
            LONGITUDE DOUBLE
        );
    """)
