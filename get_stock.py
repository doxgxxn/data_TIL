from airflow import DAG 
from airflow.operators.python import PythonOperator
import os 
import csv, pickle
import requests 
import datetime
import pymysql


dag = DAG(
    dag_id = 'stock',
    schedule_interval="0 18 * * *",
    start_date=datetime.datetime(year=2023, month=8, day=23)
)
os.chdir("/home/gen2/")

def _get_stock_day():
    sise_url = "https://api.finance.naver.com/siseJson.naver?symbol={}&requestType=1&startTime={}&endTime={}&timeframe=day"
    with open("/home/gen2/master.pkl", "rb") as f:
        master = pickle.load(f)
    
    if os.path.isdir("./stock")  == False:
        os.mkdir("./stock")
    today =  str(datetime.date.today()).replace("-", "")
    with open('./stock/{}.csv'.format(today), 'w', encoding='utf-8-sig', newline='') as csvfile:
        writer = csv.writer(csvfile)
        for code in master[:20]:
            r2 = requests.post(sise_url.format(code, today, today))
            data_list = eval(r2.text.replace("\n", "").strip())
            if len(data_list) == 1:
                continue
            for x in data_list[1:]:
                writer.writerow([f'{code}'] + x)


get_stock_day = PythonOperator(
    task_id = "get_stock_day",
    python_callable=_get_stock_day,
    dag=dag,
)

def _store_db():
    
    try:
        con = pymysql.connect(host='127.0.0.1', user='root', password='password',  db='encore', charset='utf8')
        cur = con.cursor()
    except Exception as e:
        print (e)
    sql = "INSERT INTO stock (stock_code, date, open, high, low, close, volume, foreign_ex_rate) VALUES(%s, %s, %s, %s, %s, %s, %s, %s)"
    today =  str(datetime.date.today()).replace("-", "")
    with open(f"./stock/{today}.csv", "r", encoding='utf-8') as f:
        for idx, x in enumerate(f):
            try:
                cur.execute(sql, x.strip().split(","))
            except Exception as e:
                print(e)
    con.commit()

store_db = PythonOperator(
    task_id = "store_db",
    python_callable=_store_db,
    dag=dag,
)

get_stock_day >> store_db