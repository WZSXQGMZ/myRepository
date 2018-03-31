# -*- coding: UTF-8 -*- 
import urllib
import urllib2
import cookielib
import re
import threading
import Queue
import os
from connectionDatabase import insertEntry

class Traversaler(object):
    totalThreadNum = -1

    def __init__(self):
        Traversaler.totalThreadNum += 1
        self.threadNum = Traversaler.totalThreadNum
        #最大线程数
        self.maxThreadNum = 5
        #存放待读取链接的队列
        self.urlQueue_to_read = Queue.Queue()
        #存放读取失败连接的队列
        self.urlQueue_read_failed = Queue.Queue()
        #存放所有链接的数组
        #"""
        self.urlList_all = ["https://baike.baidu.com",
                            "https://baike.baidu.com/art", 
                            "https://baike.baidu.com/science", 
                            "http://baike.baidu.com/ziran", 
                            "http://baike.baidu.com/wenhua", 
                            "http://baike.baidu.com/dili", 
                            "http://baike.baidu.com/shenghuo", 
                            "http://baike.baidu.com/shehui", 
                            "http://baike.baidu.com/renwu", 
                            "http://baike.baidu.com/jingji", 
                            "http://baike.baidu.com/tiyu", 
                            "http://baike.baidu.com/lishi"]
        #"""
        #self.urlList_all = ["https://baike.baidu.com"]
        #读写urlList_all的线程锁
        self.lock_urlList = threading.Lock()
        #已访问的链接数
        self.access_count = 0
        #限制重新启动线程次数
        self.minRestartLimit = 5

    def traStartUrl(self, start_url):
        cookie = cookielib.CookieJar()
        opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cookie))
        head = {}
        #写入User Agent信息
        if(self.threadNum < 5):
            head['User-Agent'] = 'Mozilla/5.0 (Linux; Android 4.1.1; Nexus 7 Build/JRO03D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166  Safari/535.19'
        else:
            head['User-Agent'] = 'Mozilla/5.0 (Android; Mobile; rv:14.0) Gecko/14.0 Firefox/14.0'
        req = urllib2.Request(url = start_url,headers = head)

        print ("\nLoading... " + start_url + "\n")
        try:
            #建立连接，获取网页
            result = opener.open(req)
            resultPage = result.read()
            opener.close()
        except Exception,e :  
            opener.close()
            print (Exception,":",e)
            self.traStartUrl(start_url)
            return
        pos = 0
        pattern_str = "((baike.baidu.com/item/.*?)(\"|\'|#|/)|(\"|\')(/item/[%|A-Za-z|0-9]+)|(baike.baidu.com/view/[0-9]+\.html?)(\"|\'))"
        pattern = re.compile(pattern_str)
        patternList = []
        #新建捕获百科链接的正则表达式
        pattern_str_0 = "(baike.baidu.com/item/.*?)(\"|\'|#|/)"
        pattern_0 = re.compile(pattern_str_0)
        patternList.append(pattern_0)
        pattern_str_1 = "(\"|\')(/item/[%|A-Za-z|0-9]+)"
        pattern_1 = re.compile(pattern_str_1)
        patternList.append(pattern_1)
        pattern_str_2 = "(baike.baidu.com/view/[0-9]+\.html?)(\"|\')"
        pattern_2 = re.compile(pattern_str_2)
        patternList.append(pattern_2)

        while True:
            match_link = pattern.search(resultPage,pos)
            if(match_link):
                pos = match_link.end(1)
                line = match_link.group(1)
                url = 0
                #匹配链接
                pattern_in_list = patternList[0]
                match = pattern_in_list.search(line)
                if match:
                    url = "http://" + match.group(1)
                else:
                    pattern_in_list = patternList[1]
                    match = pattern_in_list.search(line)
                    if (match):
                        url = "http://baike.baidu.com" + match.group(2)
                    else:
                        pattern_in_list = patternList[2]
                        match = pattern_in_list.search(line)
                        if (match):
                            url = "http://" + match.group(1)

                if url:
                    flag = 0
                    url = unicode(url,'utf-8')
                    #遍历所有连接数组
                    self.lock_urlList.acquire()
                    for url_to_read in self.urlList_all :
                        #如果已存在重复链接，设置flag为1，break
                        if(url == url_to_read): 
                            #flag = 1
                            break

                    #如果连接未重复
                    if flag == 0 :
                        #添加链接
                        self.urlList_all.append(url)
                        self.lock_urlList.release()
                        self.urlQueue_to_read.put(url, True)
                        #打印链接
                        print (url + "\n")
                    else :
                        self.lock_urlList.release()    
                    url = 0
            else:
                break

    def traversalFunc(self, start_url, linksToAppend, saveLinks, saveEntry, threadNum):

        cookie = cookielib.CookieJar()
        opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cookie))
        head = {}
        #写入User Agent信息
        if(threadNum < 50):
            head['User-Agent'] = 'Mozilla/5.0 (Linux; Android 4.1.1; Nexus 7 Build/JRO03D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166  Safari/535.19'
        else:
            head['User-Agent'] = 'Mozilla/5.0 (Android; Mobile; rv:14.0) Gecko/14.0 Firefox/14.0'
        req = urllib2.Request(url = start_url,headers = head)

        print ("\nLoading... " + start_url + "\n")
        try:
            #建立连接，获取网页
            result = opener.open(req)
            resultPage = result.read()
            opener.close()
            #urlList_accessed.append(start_url)
        except Exception,e :  
            self.urlQueue_read_failed.put(start_url, True)
            opener.close()
            print (Exception,":",e)
            return
        if(saveEntry):
            #reasultPage = unicode(resultPage, 'utf-8')
            if self.saveEntry(resultPage, start_url) == 2:
                saveLinks = False
        if(not saveLinks):
            #print ("save only\n")
            return

        try:
        
            patternList = []
            #新建捕获百科链接的正则表达式
            pattern_str_0 = "(baike.baidu.com/item/.*?)(\"|\'|#|/)"
            pattern_0 = re.compile(pattern_str_0)
            patternList.append(pattern_0)
            pattern_str_1 = "(\"|\')(/item/[%|A-Za-z|0-9]+)"
            pattern_1 = re.compile(pattern_str_1)
            patternList.append(pattern_1)
            pattern_str_2 = "(baike.baidu.com/view/[0-9]+\.html?)(\"|\')"
            pattern_2 = re.compile(pattern_str_2)
            patternList.append(pattern_2)

            #将resultPage分行
            rasultPage_inLines = resultPage.splitlines()
            url = ""
            pos = 0
            for line in rasultPage_inLines :
                
                url = 0
                #匹配链接
                pattern_in_list = patternList[0]
                match = pattern_in_list.search(line)
                if match:
                    url = "http://" + match.group(1)
                else:
                    pattern_in_list = patternList[1]
                    match = pattern_in_list.search(line)
                    if (match):
                        url = "http://baike.baidu.com" + match.group(2)
                    else:
                        pattern_in_list = patternList[2]
                        match = pattern_in_list.search(line)
                        if (match):
                            url = "http://" + match.group(1)

                if url:
                    flag = 0
                    """
                    #遍历已保存链接数组
                    for url_accessed in urlList_accessed :
                        #如果已存在重复链接，设置flag为1，break
                        if(url == url_accessed): 
                            flag = 1
                            break
                    """
                
                    #遍历所有连接数组
                    self.lock_urlList.acquire()
                    for url_to_read in self.urlList_all :
                        #如果已存在重复链接，设置flag为1，break
                        if(url == url_to_read): 
                            flag = 1
                            break

                    #如果连接未重复
                    if flag == 0 :
                        #添加链接
                        self.urlList_all.append(url)
                        self.lock_urlList.release()
                        self.urlQueue_to_read.put(url, True)
                        #打印链接
                        print (url + "\n")
                        #链接数-1
                        linksToAppend -= 1
                    else :
                        self.lock_urlList.release()    
                    url = 0

        except Exception,e:  
            print (Exception,":",e)

        print ("Done.\n")


    def saveEntry(self, resultPage, url) :
        #获取标题的正则表达式
        pattern_str_title = "(<title>)(.*)(</title>)"
        pattern_title = re.compile(pattern_str_title)
        pattern_str_entry = "(.*)(_百度百科)"
        pattern_entry = re.compile(pattern_str_entry)
        #获取标题
        match = pattern_title.search(resultPage)
        if match:
            title = match.group(2)
            match_realTitle = pattern_entry.search(title)
            if(match_realTitle):
                title = match_realTitle.group(1)
            title_unicode = unicode(title, 'utf-8')

        pos = 0
        #匹配修改时间的正则表达式
        patterns_str_time = "itemprop=\"dateUpdate.*?content=\"(.+?)\""
        pattern_time = re.compile(patterns_str_time)
        match = pattern_time.search(resultPage, pos)
        time = ""
        if match :
            pos = match.end(1)
            time = match.group(1)
        #print time

        #匹配简介的正则表达式
        pattern_str_decription = "id=\"J-card-sound\">\n(<li>.*</li>\n)+?</ul>"
        pattern_description = re.compile(pattern_str_decription)
        description = ""
        match = pattern_description.search(resultPage)
        if match:
            pos = match.end(1)
            temp_str = match.group(0)
            pattern_str_temp = "(<li>.*?</li>)"
            pattern_temp = re.compile(pattern_str_temp)

            match_temp = pattern_temp.search(temp_str)
            if match_temp :
                #description += match_temp.group(1)
                while(match_temp.pos < match_temp.endpos):
                    match_temp = pattern_temp.search(temp_str, match_temp.end(1))
                    if match_temp :
                        match_temp_temp = re.search("<li>(.*)</li>",match_temp.group(1))
                        description += match_temp_temp.group(1) + "\n"
                    else :
                        break
            """
            for i in range(1,match.lastindex):
                match_temp = pattern_temp.search(match.group(i))
                if match_temp:
                    description += match_temp.group(1)
            """
        #description_u = unicode(description, 'utf-8')
        #print description_u
        match = None

        #匹配目录的正则表达式
        pattern_str_catalog = "data-index=\"header.*?\n(<li>.*</li>\n)+?</ul>"
        pattern_catalog = re.compile(pattern_str_catalog)
        catalogList = []
        catalog = ""
        while True:
            match = pattern_catalog.search(resultPage,pos)
            if match:
                pos = match.end(1)
                temp_str = match.group(0)
                pattern_str_temp = "(<li>.*?</li>)"
                pattern_temp = re.compile(pattern_str_temp)

                match_temp = pattern_temp.search(temp_str)
                if match_temp :
                    match_temp_temp = re.search("<li>(.*)\n?</li>",match_temp.group(1))
                    match_real_cata = re.search("(.*?)。",match_temp_temp.group(1))
                    if match_real_cata :
                        #test_u = unicode(match_real_cata.group(1),'utf-8')
                        catalogList.append(match_real_cata.group(1))
                    else :
                        catalogList.append(match_temp_temp.group(1))

                    catalog += "\n" + match_temp_temp.group(1) + "\n"
                    while(match_temp.pos < match_temp.endpos):
                        match_temp = pattern_temp.search(temp_str, match_temp.end(1))
                        if match_temp :
                            match_temp_temp = re.search("<li>(.*)</li>",match_temp.group(1))
                            catalog += match_temp_temp.group(1) + "\n"
                        else :
                            break
            else:
                break
            """
            for i in range(1,match.lastindex):
                match_temp = pattern_temp.search(match.group(i))
                if match_temp:
                    description += match_temp.group(1)
            """
        #catalog_u = unicode(catalog, 'utf-8')
        #for str in catalogList :
            #print unicode(str, 'utf-8') + "\n"
        #print catalog_u
    
        #匹配参考资料的正则表达式
        reference = ""
        pattern_str_reference = "(class=\"reference-list\">\n)"
        pattern_referance = re.compile(pattern_str_reference)
        match = pattern_referance.search(resultPage,pos)
        if match and match.end(1) > pos :
            pos = match.end(1)
            pattern_li_a = re.compile("<li.*\n.*<a href=\"(.+)\">(.+)</a>.*</li>\n")
            pattern_li = re.compile("<li.*\n(.+)</li>\n")
            #pattern_liEnd = re.compile("\n?</ul>")
            while(True):
                if(resultPage[pos] == '<' and resultPage[pos+1] == '/'and resultPage[pos+2]=='u'):
                    break
                match = pattern_li_a.search(resultPage,pos)
                if(match):
                    reference += match.group(1) + " " + match.group(2) + "\n"
                    pos = match.end(0)
                else:
                    match = pattern_li.search(resultPage,pos)
                    if(match):
                        reference += match.group(1) + "\n"
                        pos = match.end(0)
        if(reference == ""):
            reference += "无"
        #print reference
        path = "E:/VSWorkSpace/pythonTest/traResult/" + str(self.threadNum) + "/" + title_unicode + ".txt"
        file = open("../traResult/" + str(self.threadNum) + "/" + title_unicode + ".txt","w",-1)
        try:
            """
            file.write(title + "\n")
            file.write("\n" + url + "\n")
            file.write("\n修改时间\n" + time + "\n")
            file.write("\n词条简介\n")
            file.write(description)
            file.write("\n")
            file.write("\n词条目录\n\n")
            for line in catalogList:
                file.write(line)
                file.write("\n")
            """
            file.write("\n目录内容\n")
            file.write(catalog)
            file.write("\n")
            """
            file.write("\n参考资料\n")
            file.write(reference)
            file.write("\n")
            """
            file.close()
            saveResult = insertEntry(title_unicode,description,catalogList,path,reference,time,url)
            if saveResult == 0:
                print(title_unicode + " saved.\n")
            elif saveResult == 1:
                print(title_unicode + " save failed.\n")
            elif saveResult == 2:
                print(title_unicode + " already exists.\n")
        except:
            print(title_unicode + " save failed.\n")
            return saveResult
        return saveResult

    def threadTra(self, start_url, linksToAppend = -1, saveLinks = True, saveEntryOnly = True, threadNum = 0) :
        td = threading.Thread(target = self.traversalFunc, args=(start_url, linksToAppend, saveLinks, saveEntryOnly, threadNum,))
        return td

    #此函数用于新建线程循环爬取
    def traLoop(self, start_url, maxEntry = 6000):
        dirPath = "../traResult/" + str(self.threadNum)
        if (not os.path.exists(dirPath)):
            os.makedirs(dirPath)
        elif (os.path.isfile(dirPath)):
            os.makedirs(dirPath)

        #self.traversalFunc(start_url, -1, True, False, self.threadNum)
        self.traStartUrl(start_url)

        flag = True
        threadList = []
        for i in range(0, self.maxThreadNum):
            if(self.urlQueue_to_read.empty()):
                self.maxThreadNum = i
                break
            threadList.append(self.threadTra(self.urlQueue_to_read.get(True), -1, True, True, (self.threadNum*10+i)))
            threadList[i].start()
        
        if(len(threadList) == 0):
            flag = False
        while(flag):
            for i in range(0, self.maxThreadNum):
                if(self.urlQueue_to_read.empty()):
                    flag = False
                    break
                if(not threadList[i].is_alive()):
                    self.access_count += 1
                    print ("thread " + str(self.threadNum) + " accessed " + str(self.access_count) + " entries, " + str(self.urlQueue_read_failed.qsize()) + " failed\n")

                    if((len(self.urlList_all)) > maxEntry):
                        flag = False
                        break
                    if(not self.urlQueue_to_read.empty() and self.urlQueue_to_read.qsize() < 1000):
                        threadList[i] = self.threadTra(self.urlQueue_to_read.get(True), -1, True, True, (self.threadNum*10+i))
                        threadList[i].start()
                    elif(self.urlQueue_to_read.qsize >= 1000 and self.urlQueue_to_read.qsize() < 1500):
                        threadList[i] = self.threadTra(self.urlQueue_to_read.get(True), 10, True, True, (self.threadNum*10+i))
                        threadList[i].start()
                    elif(self.urlQueue_to_read.qsize() >= 1500):
                        overCount = 0
                        self.access_count -= 1
                        while True:
                            for i in range(0, self.maxThreadNum):
                                if(not threadList[i].is_alive()):
                                    self.access_count += 1
                                    print ("thread " + str(self.threadNum) + " accessed " + str(self.access_count) + " entries, " + str(self.urlQueue_read_failed.qsize()) + " failed\n")
                                    overCount += 1
                                    threadList[i] = self.threadTra(self.urlQueue_to_read.get(True), 0, False, True, i)
                                    threadList[i].start()
                                    if (overCount >= 1000):
                                        break
                    else:
                        flag = False
                        break
    
        while(not self.urlQueue_to_read.empty()):
            for i in range(0, self.maxThreadNum):
                if(not threadList[i].is_alive()):
                    self.access_count += 1
                    print ("thread " + str(self.threadNum) + " accessed " + str(self.access_count) + " entries, " + str(self.urlQueue_read_failed.qsize()) + " failed\n")
                    threadList[i] = self.threadTra(self.urlQueue_to_read.get(True), 0, False, True, i)
                    threadList[i].start()
                    if(self.urlQueue_to_read.empty()):
                        break

        while(not self.urlQueue_read_failed.empty()):
            for i in range(0, self.maxThreadNum):
                if(not threadList[i].is_alive()):
                    print ("thread " + str(self.threadNum) + " lefts " + str(self.urlQueue_read_failed.qsize()) + " failed entries\n")
                    threadList[i] = self.threadTra(self.urlQueue_to_read.get(True), 0, False, True, i)
                    threadList[i].start()
                    if(self.urlQueue_read_failed.empty()):
                        break

        
        if(self.access_count<self.minRestartLimit):
            print "thread" + str(self.threadNum) + " restart...\n"
            self.minRestartLimit -= 1
            self.traLoop(start_url,maxEntry)
            return

        print ("\n thread" + str(self.threadNum) + " all done with " + str(self.access_count) + " entries.\n")
        entryFile = open("../traResult/" + "entryList " + str(self.threadNum*10) + ".txt", "a", -1)
        entryFile.write("thread" + str(self.threadNum) + " all done with " + str(self.access_count) + " entries.\n" + start_url)
        entryFile.close()



    def traLoop_start(self, start_url, maxEntry = 6000):
        self.traLoop(start_url, maxEntry)

    