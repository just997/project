import requests
import re
from urllib import request

url = 'https://www.zhiwuwang.com/baike/'
j = 0
response = requests.get(url)
response.encoding = 'utf-8'
html = response.text
# print(html)
res_html = re.findall('class="bk-rm-fl-nr"(.*?)class="bk-rm"', html, re.S)[0]
res_url = re.findall('href="(.*?)" title', res_html, re.S)
res_name = re.findall('title="(.*?)"><img', res_html, re.S)
res_photo = re.findall('src="(.*?)">', res_html, re.S)
# print(res_photo)

for j in res_name:
    pass

def plant_url():
    for i in res_url:
        ponse = requests.get(i)
        ponse.encoding = 'utf-8'
        html_1 = ponse.text
        html_1 = re.findall('class="bk-list-title"(.*?)class="m1r side"', html_1, re.S)[0]
        plant_html_url = re.findall('href="(.*?)" target', html_1, re.S)
        plant_html_name = re.findall('title="(.*?)">', html_1, re.S)
        plant_html_photp = re.findall('src="(.*?)"', html_1, re.S)
        # print(plant_html_url)
        return plant_html_url


def plant_explain():
    url_explain = plant_url()
    for i in url_explain:
        ponse_explain = requests.get(i)
        ponse_explain.encoding = 'utf-8'
        explain = ponse_explain.text
        explain_name = re.findall('<h1 class="title" id="title">(.*?)</h1>', explain, re.S)
        explain = re.findall('class="m3l bk_m"(.*?)class="bk_tag"', explain, re.S)
        # print(explain)
        # print(ponse_explain.text)
        for i in explain:
            i = i.replace('\n', '')
            i = i.replace('</li>', '\n')
            i = i.replace('<li><span>', '')
            i = i.replace('</span>', ':')
            i = i.replace('</div>', '')
            i = i.replace('<ul>', '')
            i = i.replace('</h1>', '')
            i = i.replace('</p>', '')
            i = i.replace('<h2>', '')
            i = i.replace('</h2>', '')
            i = i.replace('</ul>', '')
            i = i.replace('<p style="text-indent:2em;">', '')
            i = i.replace('<a name="subt', '')
            i = i.replace('"></a>', '')
            i = i.replace('<div', '')
            i = i.replace('<li><a href="#subt', '')
            i = i.replace('">', '')
            i = i.replace('<h1 class="title" id="title', '')
            i = i.replace('<div class="bk_mulu">', '')
            i = i.replace('<span class="bk_mulu_bt">', '')
            i = i.replace('<ol class="bk_mulu_ol">', '')
            i = i.replace('class="bk_mulu', '')
            i = i.replace('</a>', '')
            i = i.replace('class="bk_introduce', '')
            i = i.replace('class="bk_ziliao', '')
            i = i.replace('</ol>', '')
            i = i.replace(' ', '')
            i = i.replace('  ', '')
            i = i.replace('class="c_bid="content1', '')
            i = i.replace('<ol_ol1', '')
            i = i.replace('<span_bt', '')
            # print(i)
            fb = open('%s.txt' % explain_name, 'w', encoding='utf-8')
            fb.write(i)
            print(explain_name)


def photos():
    j = 0
    for i in res_photo:
        print(i)
        request.urlretrieve(url=i, filename=res_name[j] + '.png')
        j += 1

if __name__ == '__main__':
    photos()
    plant_url()
    plant_explain()
