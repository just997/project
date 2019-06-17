import requests
import re
url = 'https://book.qidian.com/info/1015350009#Catalog'
response = requests.get(url)
response.encoding = 'UTF-8'
html = response.text
# 获取小说的名字
title = re.findall('<title>(.*?)</title>', html, re.S)
# 新建文件保存小说
fb = open('%s.txt' % title, 'w', encoding='utf-8')
# 获取每一章的信息
dl = re.findall(r'<ul class="cf">.*?<div class="book-content-wrap cf">', html, re.S)[0]
chaper_info_list = re.findall(r'href="(.*?)" target="_blank"(.*?)</a>', dl)
# 循环每一章分别下载
for chaper_info in chaper_info_list:
    chaper_url, chaper_title = chaper_info
    chaper_url = "https:%s" % chaper_url
    # 下载章节内容
    chapter_response = requests.get(chaper_url)
    chapter_response.encoding = 'utf-8'
    chapter_html = chapter_response.text
    # 提取章节内容
    chapter_list_name = re.findall(
        r'<h3 class="j_chapterName">(.*?)</h3>', chapter_html, re.S)[0]
    chapter_content = re.findall(
        r'<div class="read-content j_readContent">(.*?)<div class="admire-wrap">', chapter_html, re.S)[0]
    # 清洗数据
    chapter_content = chapter_content.replace(' ', '')
    chapter_content = chapter_content.replace('　　', '')
    chapter_content = chapter_content.replace('<p>', '')
    chapter_content = chapter_content.replace('</div>', '')
    chapter_content = chapter_content.replace('\n', '')

    # 持久化
    fb.write(chapter_list_name)
    fb.write('\n')
    fb.write(chapter_content)
    fb.write('\n')
    print(chaper_url)


# response = request.urlopen(url)
# html = response.read()  # .decode('utf-8')
