'''
SERVER 5
PORT 8200
RETURNS THE LOCATION OF USER TO ACK THE USER
'''
from difflib import SequenceMatcher

import pandas as pd
import numpy as np
import math;
import socket;
import sys;

#HOST='104.198.168.13'
HOST = '192.168.0.100'
PORT = 8300

def similar(a, b):
    str(a)
    str(b)
    print(SequenceMatcher(None, a, b).ratio())
    return SequenceMatcher(None, a, b).ratio()

while True:
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

    s.listen(10)
    clientsocket, address = s.accept()
    print(f"connection form {address} has been established")
    length_of_message = int.from_bytes(clientsocket.recv(2), byteorder='big')
    msg = clientsocket.recv(length_of_message).decode("UTF-8")
    try:
        msgtoString = str(msg)
        print(msg)
        ##print(msgtoString)
        print("hi")
        str1 = msg.split(',')
        lat = np.float64(str1[0])
        lan = np.float64(str1[1])
        print(lat)
        print(lan)

        df = pd.read_csv(r'C:\database\Processing\UserProcessing.txt', sep='/',float_precision='round_trip')
        print(df)
        print(df.LATITUDE.dtype)
        print(df.LONGITUDE.dtype)

        df2 = pd.DataFrame()

        #for i in range(len(df)):
            #if ((similar(str(df.loc[i, "LATITUDE"]),str(lat)) > 0.8) and (similar(str(df.loc[i, "LONGITUDE"]), str(lat))>0.8)):
                #df2 = df2.append(pd.Series(
                    #[df.loc[i, "LATITUDE"], df.loc[i, "LONGITUDE"]], index=df.columns), ignore_index=True)



        df2 = df.loc[(df.LATITUDE==lat) & np.isclose(df.LONGITUDE, lan, rtol=1e-05, atol=0.00001, equal_nan=False)]
        print("printing df2",df2)
        if (df2.empty):
            print("in if")
            val = "N"
            message_to_send = val.encode("UTF-8")
            clientsocket.send(len(message_to_send).to_bytes(2, byteorder='big'))
            clientsocket.send(message_to_send)
        else:
            print("in elif")
            ind = df.loc[(df.LATITUDE == lat) & (np.isclose(df.LONGITUDE, lan, rtol=1e-05, atol=0.00001, equal_nan=False))].index
            df.drop(ind, inplace=True)
            df.to_csv(r'C:\database\Processing\UserProcessing.txt', header=True, sep='/', index=False)
            val = "Y"
            message_to_send = val.encode("UTF-8")
            clientsocket.send(len(message_to_send).to_bytes(2, byteorder='big'))
            clientsocket.send(message_to_send)
    except IndexError:
        print("outofbound")
    s.close()





