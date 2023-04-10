import socket
import threading
import json
import numpy as np
import matplotlib.pyplot as plt
import base64
import cv2
from predict import nn_predict
from PIL import Image


def main():
    # 创建服务器套接字
    serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # 获取本地主机名称
    # host = socket.gethostname()  # LAPTOP-00KFAMOU
    host = '127.0.0.1'
    # 设置一个端口号
    port = 9998
    # 将套接字与本地主机和端口绑定
    serversocket.bind((host, port))
    # 设置监听最大连接数
    serversocket.listen(5)
    # 获取本地服务器信息
    myaddr = serversocket.getsockname()
    print("服务器地址:%s" % str(myaddr))

    # 循环等待接受客户端信息
    while True:
        # 获取一个客户端连接
        clientsocket, addr = serversocket.accept()
        # print("连接地址:%s" % str(addr))
        try:
            t = ImgServerThreading(clientsocket)  # 为每一个请求开启一个线程处理请求
            t.start()
            pass
        except Exception as identifier:
            print(identifier)
            pass
        pass

    serversocket.close()
    pass


class ImgServerThreading(threading.Thread):
    def __init__(self, clientsocket, recvsize=1024 * 20, encoding="utf-8"):
        threading.Thread.__init__(self)
        self._socket = clientsocket
        self._recvsize = recvsize
        self._encoding = encoding
        pass

    def run(self):
        # print("开启线程......")

        try:
            # 接受数据
            rec_d = bytes([])
            while True:
                # 读取recvsize个字节
                data = self._socket.recv(self._recvsize)
                if not data or len(data) == 0:
                    break
                else:
                    rec_d = rec_d + data
            # print(rec_d)
            rec_d = base64.b64decode(rec_d)
            # print(rec_d)
            # cv2方法
            np_arr = np.frombuffer(rec_d, np.uint8)
            image = cv2.imdecode(np_arr, cv2.IMREAD_COLOR)  # 函数从指定的内存缓存中读取数据，并把数据转换(解码)成图像格式;主要用于从网络传输数据中恢复出图像。
            image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)  # cv2解码以后的格式是BGR格式 而matplotlib requires RGB 格式
            # 展示图片
            # plt.imshow(image)
            # plt.show()
            image = Image.fromarray(image)
            # 神经网络处理
            class_id, class_name = nn_predict(image)
            print(class_id, class_name)

            sendmsg = {
                'data': {'class_id': class_id, 'class_name': class_name }
            }

            print(sendmsg)

            # 转换为json格式字符串
            sendmsg = json.dumps(sendmsg)
            # 发送数据
            self._socket.send(("%s" % sendmsg).encode(self._encoding))
            pass
        except Exception as identifier:
            sendmsg = {
                None
            }
            # 转化为json格式字符串
            sendmsg = json.dumps(sendmsg)
            self._socket.send(("%s" % sendmsg).encode(self._encoding))
            print(identifier)
            pass
        finally:
            self._socket.close()
        # print("线程任务结束......")

        pass

    def __del__(self):

        pass


if __name__ == '__main__':
    main()
