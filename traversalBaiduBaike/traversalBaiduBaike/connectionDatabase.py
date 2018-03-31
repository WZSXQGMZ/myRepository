#coding=utf-8
import MySQLdb

def insertEntry(title, description, catalogList, catalogContentPath, reference, modifyDate, url):
    HOST = 'localhost'
    PORT = 3307
    USER = 'root'
    PASSWD = '123456'
    DB = 'baikeDatabase'
    CHARSET = 'utf8'

    entryForm = "insert into entryInfo0 values(%s,%s,%s,%s,%s,%s,%s)"

    catalog = ""
    for item in catalogList:
        catalog += item + "\n"

    try:
        conn = MySQLdb.connect(host = HOST,port = PORT,user = USER,passwd = PASSWD,db = DB,charset = CHARSET)
        cur = conn.cursor()
        #cur.execute(entryForm,(title,unicode(description,'utf-8'),unicode(catalog,'utf-8'),catalogContentPath,unicode(reference,'utf-8'),modifyDate,url))
        cur.execute(entryForm,(title,description,catalog,catalogContentPath,modifyDate,reference,url))
        cur.close()
        conn.commit()
        conn.close()
    except Exception,e :  
        if e.args[0] == 1062:
            return 2
        print (Exception,":",e)
        return 1

    return True
