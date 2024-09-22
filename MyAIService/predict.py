import tensorflow as tf
import numpy as np
import cv2
import sys
import json
import sys
import io

# 将标准输出重定向为支持 utf-8 编码
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')
# 加载模型
model = tf.keras.models.load_model('ceramic_model.h5')

# 标签映射字典
index_to_label = {
    0: "清, 粉彩, 景德镇窑, 五蝠捧寿, 折枝, 描金, 0, 0",
    1: "清, 粉彩, 景德镇窑, 丛竹, 折枝, 蕃莲, 0, 0",
    2: "新石器时代, 黑陶, 龙山文化, 素面, 双系, 0, 0, 0",
    3: "宋, 剪纸贴花, 吉州窑, 三凤, 黑釉, 0, 0, 0",
    4: "宋, 青白瓷, 景德镇窑, 梅瓶, 刻花, 弦纹, 漩涡纹, 涡纹"
}

def load_and_preprocess_image(image_path):
    img = cv2.imread(image_path)
    if img is None:
        raise ValueError(f"Image not found or unable to load: {image_path}")
    img = cv2.resize(img, (224, 224))  # 调整为 224x224 大小
    img = img / 255.0  # 归一化
    return img

def predict(image_path):
    img = load_and_preprocess_image(image_path)
    img = np.expand_dims(img, axis=0)  # 扩展维度为 (1, 224, 224, 3)
    prediction = model.predict(img)
    predicted_index = np.argmax(prediction)
    return index_to_label.get(predicted_index, "Unknown label")

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python predict.py <image_path>")
        sys.exit(1)

    image_path = sys.argv[1]
    result = predict(image_path)
    print(result)