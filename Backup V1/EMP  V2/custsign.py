import socket
import sys
import pandas as pd
from numpy import double

#HOST='104.198.168.13'
HOST = '192.168.0.100'
PORT = 9100

import sqlite3 as sl
con = sl.connect('Server-database.db')

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print("Socket Created")

try:
    s.bind((HOST,PORT))
except socket.error as err:
    print('Bind failed error code :' + str(err[0])+ 'Message :'+ err[1])
    sys.exit()

print("Bind Success")

s.listen(10)
print('Socket is Listning')

while True:
    clientsocket, address = s.accept()
    print(f"connection form {address} has been established")
    length_of_message = int.from_bytes(clientsocket.recv(2), byteorder='big')
    msg = clientsocket.recv(length_of_message).decode("UTF-8")
    #msg = clientsocket.recv(length_of_message)
    print(msg)
    try:

        print(msg)
        st1=""
        st1 = str(msg)
        st2 = st1.split("/")
        print(st2[0])



        data = con.execute(f"SELECT * FROM User where email = '{st2[3]}'")
        count=0
        for row in data:
            count=count+1

        print(count)
        if(count==0):
            val='Y'


            sql = 'INSERT INTO User (name, contact, rcontact, email, pass) values(?, ?, ? , ?,?)'
            data = [
                (st2[0], st2[1], st2[2], st2[3], st2[4])
            ]

            with con:
                con.executemany(sql, data)

            print("saved") ###CAREFULL: MANUALLY EDITING THE DATABASE MAY STORE SOME INDENTATION WHILE FURTHER EXECUTIONS.
            message_to_send = val.encode("UTF-8")
            clientsocket.send(len(message_to_send).to_bytes(2, byteorder='big'))
            clientsocket.send(message_to_send)
        else:
            val="N"
            message_to_send = val.encode("UTF-8")
            clientsocket.send(len(message_to_send).to_bytes(2, byteorder='big'))
            clientsocket.send(message_to_send)

    except IndexError:
        print("outofbound")




s.close()
