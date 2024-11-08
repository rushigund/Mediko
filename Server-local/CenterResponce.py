"""
SERVER 3
PORT: 8000
THIS SERVER CONTINUESLY TAKES ID AS INPUT FROM
HEALTHCARE SYSTEM AND CHECKS IF THERE IS A
REQUEST FOR THAT HEALTHCARE CENTER IF THERE IS
THEN IT RETURNS THE LATITUDE AND LONGITUDE OF ACCIDENT PLACE.
"""
import pandas as pd
import numpy as np
import math;
import socket;
import sys;

#HOST='104.198.168.13'
HOST = '192.168.0.100'
PORT = 8000

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print("Socket Created")

try:
    s.bind((HOST, PORT))
except socket.error as err:
    print('Bind failed error code :' + str(err[0]) + 'Message :' + err[1])
    sys.exit()

print("Bind Success")

s.listen(10)
print('Socket is Listning')

while True:
    s.listen(10)
    clientsocket, address = s.accept()
    print(f"connection form {address} has been established")
    length_of_message = int.from_bytes(clientsocket.recv(2), byteorder='big')
    msg = clientsocket.recv(length_of_message).decode("UTF-8")
    try:
        dt = np.dtype('>i4')
        print(msg)
        st1 = int(msg)
        #print(st1.dtype)
        #st2 =st1.split()
        df1 = pd.read_csv('C:\database\Processing\PData.txt', sep='/')
        dd = df1[df1["ID"] == st1]
        print(dd)
        if dd.empty:
            print("sent -1")
            message_to_send = '-1'.encode("UTF-8")
            clientsocket.send(len(message_to_send).to_bytes(2, byteorder='big'))
            clientsocket.send(message_to_send)
        else:
            df0 = dd.head(1)
            lis = []
            lis = df0.values.tolist()
            LAT = lis[0][1]
            LON = lis[0][2]
            ls1 = str(LAT)
            ls2 = str(LON)
            fs = ls1 + ',' + ls2
            print("sending :", fs)
            message_to_send = fs.encode("UTF-8")
            clientsocket.send(len(message_to_send).to_bytes(2, byteorder='big'))
            clientsocket.send(message_to_send)
    except IndexError:
        fs ="-1"
        print("sending :", fs)
        message_to_send = fs.encode("UTF-8")
        clientsocket.send(len(message_to_send).to_bytes(2, byteorder='big'))
        clientsocket.send(message_to_send)
        print("outofbound")

s.close()



