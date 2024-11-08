from threading import Thread


def one(): import server
def two(): import FinalRes
def three(): import ClientAccept
def four(): import CenterResponce
def five(): import Cserver

Thread(target=one).start()
Thread(target=two).start()
Thread(target=three).start()
Thread(target=four).start()
Thread(target=five).start()