import sqlite3 as sl
con = sl.connect('Server-database.db')

#with con:
 #   con.execute("""
  #      drop table LData;
   # """)

with con:
    con.execute("""
        drop table CData;
    """)