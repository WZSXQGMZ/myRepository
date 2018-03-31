# -*- coding: UTF-8 -*- 
from matchEntry import MatchEntry
from matchEntry import saveHtml
from traversalBaike import Traversaler

#MatchEntry("E:\\VSWorkSpace\\pythonTest\\traResult\\9\\比利时国家男子足球队_百度百科.html")
#saveHtml("https://baike.baidu.com/item/袋狼/")
traversaler = Traversaler()
#traversaler.traLoop("http://baike.baidu.com/dili",5000)
traversaler.traversalFunc("http://baike.baidu.com/view/3565553.html",-1,True,True,0)
traversaler.traversalFunc("http://baike.baidu.com/view/3565553.html",-1,True,True,0)