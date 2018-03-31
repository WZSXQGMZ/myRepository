# -*- coding: UTF-8 -*- 
import io
import os
import re
import urllib
import urllib2
import cookielib

def MatchEntry(path):
    path_unicode = unicode(path, 'utf-8')
    if(not os.path.isfile(path_unicode)):
        return

    entryFile = open(path_unicode,"r",-1)
    resultPage = entryFile.read()
    entryFile.close()
    
    pos = 0
    #匹配修改时间的正则表达式
    patterns_str_time = "itemprop=\"dateUpdate.*?content=\"(.+?)\""
    pattern_time = re.compile(patterns_str_time)
    match = pattern_time.search(resultPage, pos)
    time = ""
    if match :
        pos = match.end(1)
        time = match.group(1)
    print time

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
    description_u = unicode(description, 'utf-8')
    print description_u
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
                match_temp_temp = re.search("<li>(.*)</li>",match_temp.group(1))
                catalogList.append(match_temp_temp.group(1))
                catalog += match_temp_temp.group(1) + "\n"
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
    catalog_u = unicode(catalog, 'utf-8')
    for str in catalogList :
        print unicode(str, 'utf-8') + "\n"
    print catalog_u
    
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
    print reference

    print "done"

def saveHtml(start_url):
    cookie = cookielib.CookieJar()
    opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cookie))
    head = {}
    #写入User Agent信息
    head['User-Agent'] = 'Mozilla/5.0 (Android; Mobile; rv:14.0) Gecko/14.0 Firefox/14.0'
    req = urllib2.Request(url = start_url,headers = head)

    print ("\nLoading... " + start_url + "\n")
    try:
        #建立连接，获取网页
        result = opener.open(req)
        resultPage = result.read()
        opener.close()
        tempFile = open("../temp.html","w",-1)
        tempFile.write(resultPage)
        tempFile.close()
        #urlList_accessed.append(start_url)
    except Exception,e :  
        opener.close()
        print (Exception,":",e)
    print "Done\n"