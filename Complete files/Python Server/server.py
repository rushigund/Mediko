import math;
import socket;
import sys;
#import exception;

import pandas as pd
from numpy import double

HOST='192.168.0.100'
PORT= 7800

s=socket.socket(socket.AF_INET, socket.SOCK_STREAM)


print("socket created")

try:
    s.bind((HOST,PORT))
except socket.error as err:
    print ('Bind Failed error coe:' + str(err[0])+ 'Message :'+ err[1])
    sys.exit()

print("Socket Bind Success")

s.listen(10)
print("Socket is listenning")

while (1):
    conn, addr = s.accept()
    print("connect with "+addr[0] + ":"+ str(addr[1]))
    buf= conn.recv(64)
    st1=str(buf)
    print(st1[2:len(st1)-2])
    st1 = st1[2:len(st1)-2]
    lst = []
    lst = st1.split(",")
    #print(lst[0])
    #print(lst[1])
    #print(st1[2:])


    file = open("C:\database\LData.txt", 'r')

    nearid=[]
    count=0


    for row in file:
        lst1=[]
        lst1=row.split("/")

        count=count+1
        if count !=1:
            try:
                l1 = double(lst[0])
                l2 = double(lst[1])
                d1 = double(lst1[1])
                d2 = double(lst1[2])
                dist=(math.sqrt((d1-l1)**2 + (d2-l2)**2))*100
                if dist <20:
                    nearid.append(lst1[0])
                print(dist)
                df1 = pd.read_csv('C:\database\Processing\PData.txt', sep='/')
                tot = len(df1.index)
                val = tot+1
                for i in range(len(nearid)):
                    df = pd.DataFrame({'ID': [nearid[i]], 'LATITUDE': [lst[0]], 'LONGITUDE': [lst[1]]})
                    df.to_csv('C:\database\Processing\PData.txt', mode='a', header = False, sep='/', index=False)
                    val = val+1
                    print("Saved")
            except IndexError:
                print("outofbound")

    ##
    print(nearid)

s.close()
