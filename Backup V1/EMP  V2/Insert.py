import sqlite3 as sl
con = sl.connect('Server-database.db')

#sql = 'INSERT INTO LData (ID, LATITUDE, LONGITUDE) values(?, ?, ?)'
#data = [
 #   ( 1 , 36.15011 , 44.46711 ),
  #  ( 2 , -60.99137 , -159.76605 ),
   # ( 3 , 89.80183 , -1.35701 )
#]


sql = 'INSERT INTO CData (ID, OName, CNo, Address, LATITUDE, LONGITUDE) values(?, ?, ? , ?,?, ?)'
data = [
    (1 , "pqrs" , 789456 , "block 44" , 36.15011 , 44.46711),
    (2 , "abcd" , 79791 , "block 4" , -60.99137 , -159.76605)
]

with con:
    con.executemany(sql, data)