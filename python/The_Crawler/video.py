# 导入系统库urllib中的request方法
from urllib import request
# 第三方库的安装方法，打开cmd输入pip install 库名 （回车）执行
# 列：pip install requests
# 导入第三方库fake_useragent中的UserAgent方法
from fake_useragent import UserAgent
# 导入第三方库lxml中的etree
from lxml import etree
# 导入第三方库requests
import requests
# 导入系统库re
import re

# 随机UA
res = UserAgent()
headers = {
    'User-Agent': res.random
}


def index():
    '''
    @description:定义一个名叫index()无参函数
    '''
    # for...in...为num赋值，0~121之间，间隔是12
    for num in range(0, 121, 12):
        # print(num)
        # 创建变量base_url用于拼接网络链接
        base_url = 'https://www.pearvideo.com/category_loading.jsp?reqType=5&categoryId=6&start={}'.format(
            num)
        # 用Request类构建了一个完整的请求，增加了headers等一些信息
        full_url = request.Request(url=base_url, headers=headers)
        # 对网页就行请求
        response = request.urlopen(full_url)
        # 网页解码方式为utf-8
        html = response.read().decode('utf-8')
        htmls = etree.HTML(html)
        # print(html)
        # 解析网络中的html文件，提取需要的信息
        video_id = htmls.xpath('//div[@class="vervideo-bd"]/a/@href')
        # 调用page_html()函数
        # print(video_id)
        page_html(video_id)


def page_html(video_id):
    '''
    @description: 获取视频链接
    @param {type} :video_id
    @return: 无
    '''
    # 从video_id()的信息通过for...in...遍历到ids中
    for ids in video_id:
        # print(ids)
        # 拼接跳转的url链接
        full_url = 'https://www.pearvideo.com/' + ids
        # 使用requests方法中的get()请求，模拟浏览器访问网站
        response = requests.get(full_url, headers=headers)
        # 使用utf-8的解码方式对获取到的网络源码进行解码
        response.encoding = 'utf-8'
        # 根据HTTP头部对响应的编码做出有根据的推测，推测的文本编码
        htm = response.text
        # print(htm)
        # 调用re库，使用正则表达式，提取url链接
        video_list = re.findall('srcUrl="(.*?)",vdoUrl', htm)[0]
        # print(video_list)
        # 调用download_video()函数
        download_video(video_list)


def download_video(video_list):
    '''
    @description: 数据持久化
    @param {type}：video_list
    @return: 无
    '''
    # 将提取到的信息保存为文件名称
    video_name = video_list.split('/')[-1]
    # 输出等待命令和文件名称
    print('loading....' + video_name)
    # urlretrieve()方法直接将远程数据下载到本地
    request.urlretrieve(url=video_list, filename=video_name)
    # 爬取成功，输出suc！！！
    print('suc!!!')


# 执行入口
if __name__ == '__main__':
    index()
