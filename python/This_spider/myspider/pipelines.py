# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html
import re


class MyspiderPipeline(object):
    # process_item方法名不可修改
    def process_item(self, item, spider):
        item['content'] = self.process_content(item['content'])
        # print(item)
        for i in item['name'], item['content']:
            i = str(i)
            i = re.sub(r'\[|\\|n|\]|\'', '', i)
            # print(i)
            # print(type(i))
            fb = open('%s.txt' % '植物百科', 'a+', encoding='utf-8')
            fb.write(i)
            fb.write('\n')
            print('yes')
        return item

    def process_content(self, content):
        content = [re.sub(r'<p style="text-indent:2em;|<em>|</em>|</div>|">', "", i) for i in content]
        content = [re.sub(r'<a name="subt|</a>|<h2>|\n|\t|id="|content|</tbody>', "", i) for i in content]
        content = [re.sub(r'|<div|class="para"|label-module="para|<tr>|</tr>', "", i) for i in content]
        content = [re.sub(r'</div>|</td>|<td>|<center>|</table>|<tbody>', "", i) for i in content]
        content = [re.sub(r'<sup class="sup--normal" data-sup="1|</sup><a name="ref__6504565', "", i) for i in content]
        content = [re.sub(r'<table border="|" cellspacing="|" cellpadding="| |', "", i) for i in content]
        content = [re.sub(r'</h2>', "：", i) for i in content]
        content = [re.sub(r'</p>|</center>', "\n", i) for i in content]
        content = [i for i in content if len(i) > 0]
        return content


class MyspiderPipeline1(object):
    def process_item(self, item, spider):
        return item
