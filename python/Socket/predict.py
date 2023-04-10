import torch
from torchvision import transforms
import json
from vit_model import VIT_B16_224
from nmODE_0227.epsNet import epsNet
import numpy as np

def nn_predict(img):
    # 定义送入神经网络图片的预处理方式
    data_transform = transforms.Compose([
        # transforms.Resize((224, 224)), transforms.ToTensor()
        transforms.Resize((28, 28)), 
        transforms.ToTensor(),
        transforms.Normalize(mean=[0.5,0.5,0.5], std=[0.5,0.5,0.5]),
    ])

    # [N,C,H,W]
    img = data_transform(img)
    # expand batch dimension
    img = torch.unsqueeze(img, dim=0)

    # 判断当前环境是否支持cuda
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    img = img.to(device)

    imagenet_class_index = json.load(open('imagenet_class_index.json',encoding='utf-8'))
    # read class_indict
    # try:
    #     json_file = open('6_class_indices.json', 'r')
    #     class_indict = json.load(json_file)
    # except Exception as e:
    #     print(e)
    #     exit(-1)

    # create model
    # net = torch.load("./6_uav_boot_v5_299_nr-model.pkl")
    # net.to(device)
    # net.eval()

    # model = VIT_B16_224(num_classes = 2)
    model = epsNet(
        xsize=28 * 28 * 3, ysize=2048, asize=2, alpha=0.1, beta=0.1, Jsmall=0.1, eps=0.1, dist=0.1, step=2048
    )
    print(model)
    # model.load_state_dict(torch.load('epoch_960.pth', map_location=torch.device('cpu')))
    # model.eval()


    with torch.no_grad():
        # # predict class
        # outputs = model.forward(img)
        # # print('outputs : ' , outputs)
        # _, y_hat = outputs.max(1)
        # predicted_idx = str(y_hat.item())
        # return y_hat.item(),imagenet_class_index[predicted_idx]

        outputs = np.argmax(model.test(img),axis=1)
        print(model.test(img))
        predicted_idx = str(outputs.item())
        return predicted_idx,imagenet_class_index[predicted_idx]

