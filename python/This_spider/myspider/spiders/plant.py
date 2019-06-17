# -*- coding: utf-8 -*-
import scrapy
import logging
# 日志
logger = logging.getLogger(__name__)


class PlantSpider(scrapy.Spider):
    # 爬虫名
    name = 'plant'
    # 允许爬取范围
    allowed_domains = ['zhiwuwang.com']
    # 最开始请求的url地址
    start_urls = ['https://www.zhiwuwang.com/baike/list-1712.html']

    # parse方法名不可修改
    def parse(self, response):
        # 处理start_url地址对应的响应
        # ret1 = response.xpath("//div[@class='catlist']//a/text()").extract()
        # print(ret1)

        # 分组提取
        li_list = response.xpath("//div[@class='catlist']//li")
        for li in li_list:
            # print(li)
            item = {}
            item['name'] = li.xpath(".//a/text()").extract_first()
            # 变量.xpath(".//提取头[last()]/text").extractfirst()
            # [last()]代表提取提取头类型的最后一个
            # extractfirst()只要提取的第一个值等同于extract()[0]
            item['explain'] = li.xpath(".//div[last()]/text()").extract_first()
            item['href'] = li.xpath(".//a/@href").extract()[-1]
            # print(item['href'])
            # 打印日志
            # logger.warning(item)
            yield scrapy.Request(
                item["href"],
                callback=self.parse_detail,
                meta={"item": item},
            )
        next_url = response.xpath("//div[@class='pages']//@value").extract()[1]
        # print(next_url)
        # 循环访问下一页爬取网站
        if next_url != 'https://www.zhiwuwang.com/baike/list-1712.html':
            # print('-' * 50)
            yield scrapy.Request(
                next_url,
                callback=self.parse
            )

    # 处理详情页
    def parse_detail(self, response):
        item = response.meta["item"]
        item["content"] = response.xpath("//div[@id='content']").extract()
        # for i in item["content"]:
        #     print(i)
        #     print('-' * 50)
        yield item
