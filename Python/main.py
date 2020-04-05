from threads.send import SendThread
import configparser
import pathlib

if __name__ == '__main__':
    config = configparser.ConfigParser()
    config.read('config.ini')
    path = pathlib.Path(config["INFO"]["PATH_TO_FILES"])
    glob = config["INFO"]["GLOB"]
    token = config["INFO"]["TOKEN"]

    assert len(token) > 60, 'wrong token'
    send_thread = SendThread(token, path, glob)
    send_thread.start()
