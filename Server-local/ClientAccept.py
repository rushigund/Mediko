"""
SERVER 4
PORT: 8100

INPUTS : ID, Y/N, LAT AND LAN OF ACC SITE.
THIS SERVER TAKES INPUT FROM HEALTHCARE CENTRE AND DOSE THE NECESSARY PROCESSING ON THE DATABASE
AND RETURNS y OR n TO CLIENT DEVICE.
"""
import pandas as pd
import numpy as np
import math;
import socket;
import sys;

#HOST='104.198.168.13'
HOST = '192.168.0.100'
PORT = 8100

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
        st1 = ""
        st1 = str(msg)
        st2 = st1.split("/")
        print(st2[0])
        df1 = pd.read_csv('C:\database\Processing\PData.txt', sep='/')
        Cstr = st2[1]
        Cstr = Cstr.lower()
        print(Cstr)

        if (Cstr == 'n'):
            print(df1.loc[(df1.ID == int(st2[0]))])
            ind = df1.loc[(df1.ID == int(st2[0]))].index
            print(ind)
            df1.drop(ind, inplace=True)
            df1.to_csv('C:\database\Processing\PData.txt', header=True, sep='/', index=False)
            val = "Y"
            message_to_send = val.encode("UTF-8")
            clientsocket.send(len(message_to_send).to_bytes(2, byteorder='big'))
            clientsocket.send(message_to_send)
        elif (Cstr == 'y'):
            print("in elif")
            lat = ''
            lan = ''
            cord = st2[2]
            cord = cord.split(",")
            lat = cord[0]
            lan = cord[1]
            lat1 = np.float64(lat)
            lan1 = np.float64(lan)
            df2 = df1.loc[(df1.LATITUDE == lat1) & (df1.LONGITUDE == lan1)]
            print(df2)
            if df2.empty:
                val = "N"
                message_to_send = val.encode("UTF-8")
                clientsocket.send(len(message_to_send).to_bytes(2, byteorder='big'))
                clientsocket.send(message_to_send)
            else:
                CT = df2.LATITUDE
                CN = df2.LONGITUDE
                CT = str(CT)
                CN = str(CN)
                FC = lat + ',' + lan
                #with open(r'C:\database\Processing\UserProcessing.txt','a')as obj:
                    #obj.write(FC);
                    #obj.write('\n');

                ##dd = pd.DataFrame({'LATLAN': [FC]})
                ##dd.to_csv(r'C:\database\Processing\UserProcessing.txt', mode='a', header=False, index=False)
                dd = pd.DataFrame({'LAT':[lat1], 'LAN':[lan1]})
                dd.to_csv(r'C:\database\Processing\UserProcessing.txt',mode='a',sep='/',header=False,index=False)
                ind = df1.loc[(df1.LATITUDE == lat1) & (df1.LONGITUDE == lan1)].index
                df1.drop(ind, inplace=True)
                print(df1)
                df1.to_csv('C:\database\Processing\PData.txt', header=True, sep='/', index=False)
                val = "Y"
                message_to_send = val.encode("UTF-8")
                clientsocket.send(len(message_to_send).to_bytes(2, byteorder='big'))
                clientsocket.send(message_to_send)

        print("here")
    except IndexError:
        print("Outofbound")

s.close()





