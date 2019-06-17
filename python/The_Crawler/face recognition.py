import cv2
import numpy as np
face_cascade = cv2.CascadeClassifier(r'C:\Users\wyx72\Desktop\data\haarcascade_frontalface_alt2.xml')
eye_cascade = cv2.CascadeClassifier(r'C:\Users\wyx72\Desktop\data\haarcascade_eye.xml')
cap = cv2.VideoCapture(0)
while True:
    # 读取每一帧图片
    ret, img = cap.read()
    # 灰度化处理
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray, 1.1, 5)
    # print(faces)
    # 判断人脸是否为空
    if len(faces) > 0:
        for faceRect in faces:
            x, y, w, h = faceRect
            # 打印坐标
            print('x--->', x, 'y--->', y, 'w--->', w, 'h--->', h)
            # 脸部画框
            cv2.rectangle(img, (x, y), (x + w, y + h), (250, 250, 250), 1)
            roi_gray = gray[y:y + h // 2, x:x + w]
            # print(roi_gray)
            roi_color = img[y:y + h // 2, x:x + w]
            # print(roi_color)
            eyes = eye_cascade.detectMultiScale(roi_gray, 1.1, 1, cv2.CASCADE_SCALE_IMAGE, (2, 2))
            for (ex, ey, ew, eh) in eyes:
                cv2.rectangle(roi_color, (ex, ey), (ex + ew, ey + eh), (0, 255, 0), 2)
    cv2.imshow('face_eyes', img)
    # 等待一个按键终止死循环
    if cv2.waitKey(1) == ord('q'):
        break
