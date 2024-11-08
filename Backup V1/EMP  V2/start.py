from threading import Thread


def one(): import server
def two(): import FinalRes
def three(): import ClientAccept
def four(): import CenterResponce
def five(): import Cserver
def six(): import custlogin
def seven(): import custsign

Thread(target=one).start()
Thread(target=two).start()
Thread(target=three).start()
Thread(target=four).start()
Thread(target=five).start()
Thread(target=six).start()
Thread(target=seven).start()