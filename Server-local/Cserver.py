import socket
import sys
import pandas as pd
from numpy import double

#HOST='104.198.168.13'
HOST = '192.168.0.100'
PORT = 7900

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
    try:

        print(msg)
        st1=""
        st1 = str(msg)
        #st1 = st1[2:]
        #st1 = msg[2:len(st1)]
        st2 = st1.split("/")
        print(st2[0])
        df1 = pd.read_csv("C:\database\CData.txt", sep='/')
        #df2 = pd.read_csv("C:\database\LData.txt", sep='/')
        # print(df)
        tot = len(df1.index)
        dd = df1[df1["OName"] == st2[0]]
        #print(dd.empty)

        if(dd.empty):
            val='-1'
            print(type(val))
            print(type('-1'))
            val=tot+1
            val=str(val)
            df = pd.DataFrame({'ID': (val), 'OName': [st2[0]], 'CNo': [st2[1]], 'Address': [st2[2]], 'LATITUDE': [st2[3]], 'LONGITUDE': [st2[4]]})
            df.to_csv('C:\database\CData.txt', mode='a', header = False, sep='/', index=False)
            dfa = pd.DataFrame({'ID': (val), 'LATITUDE': [st2[3]], 'LONGITUDE': [st2[4]]})
            dfa.to_csv("C:\database\LData.txt", mode='a', header = False, sep='/', index=False)
            print("saved") ###CAREFULL: MANUALLY EDITING THE DATABASE MAY STORE SOME INDENTATION WHILE FURTHER EXECUTIONS.
            message_to_send = val.encode("UTF-8")
            clientsocket.send(len(message_to_send).to_bytes(2, byteorder='big'))
            clientsocket.send(message_to_send)
        else:
            val="-1"
            message_to_send = val.encode("UTF-8")
            clientsocket.send(len(message_to_send).to_bytes(2, byteorder='big'))
            clientsocket.send(message_to_send)

    except IndexError:
        print("outofbound")




s.close()
