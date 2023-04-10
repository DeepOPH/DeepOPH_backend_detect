package com.pig4cloud.pigx.files.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.Socket;
import java.util.Base64;

public class  PyUtil {
	public static Object callNNbyImg(MultipartFile image){

		// 要传输的本地图片路径地址
//        File f = new File("C:\\Users\\yangpengfei\\Desktop\\INSPIRE_10M_FLYING_ABOVE00001.png");

		String host = "127.0.0.1"; // 本机ip
		int port = 9998;
		Socket socket = null;
		try {
			socket = new Socket(host,port);

			OutputStream os = socket.getOutputStream();
//            FileInputStream imageIs = new FileInputStream(f); // 使用本地图片时打开此注释

			InputStream imageIs = image.getInputStream();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int length = 0;
			byte[] sendBytes = null;
			sendBytes = new byte[1024*20];
			while ((length = imageIs.read(sendBytes,0,sendBytes.length))>0){
				baos.write(sendBytes,0,length);
			}

			baos.flush();
			PrintWriter pw = new PrintWriter(os);
			pw.write(Base64.getEncoder().encodeToString(baos.toByteArray()));
			pw.flush();

			socket.shutdownOutput();  // 告诉服务端 客户端已经发送完毕

			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
			String info = br.readLine();
			//格式化JSON，将字符串转化成JSONObject
			JSONObject body = JSON.parseObject(info);
			//取到data的值，将其转化为JSONObject
			JSONObject data = (JSONObject)body.get("data");

			os.close();
			imageIs.close();
			pw.close();
			baos.close();

			return data;

		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (socket!=null){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}


}