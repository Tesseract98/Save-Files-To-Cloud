import os
from glob import glob as gl
from threading import Thread

import dropbox
from tqdm import tqdm


class SendThread(Thread):

    def __init__(self, token: str, path: str, glob: str):
        Thread.__init__(self)
        self.__dbx = dropbox.Dropbox(token)
        self.__path = path
        self.__glob = glob

    def run(self) -> None:
        client = self.__dbx.users_get_current_account()
        file_paths = gl(os.path.join(self.__path, self.__glob))

        n = 5
        for i in tqdm(range(n)):
            with open(file_paths[i], 'rb') as f:
                self.__dbx.files_upload(f.read(), f'/{os.path.basename(file_paths[i])}', autorename=True)
