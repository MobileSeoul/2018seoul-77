import json
import logging
import requests
import pymongo
from django.shortcuts import render

# Create your views here.

from rest_framework.decorators import api_view
from rest_framework.response import Response
from BazaarServer.settings import apply_collection, shop_collection, user_collection

SUCCESS = {"response": "success"}
FAIL = {"response": "fail"}
logger = logging.getLogger("admin")

location = ["a", "b", "c", "d", "e"]

def select_all_apply(list_num):
    data = {}
    applies = list(apply_collection.aggregate(
        [

            {"$project": {
                "_id": 0,
                "id": 1,
                "title": 1,
                "contents": 1,
                "role": 1,
                "name": 1,
                "date": 1
            }}
            , {"$sort": {"date": 1}}
            , {"$skip": (list_num - 1) * 10}
            , {"$limit": 10}
        ]
    ))
    data['items'] = applies
    data['meta'] = {'count': len(applies)}
    return data


def give_apply_role(applies):
    for apply in applies:
        apply_collection.update(
            {
                "id": apply['id']
            },
            {
                "$set": {
                    "role": 2
                }
            }
        )

    apply_collection.update(
        {
            "role": 1
        },
        {
            "$set": {
                "role": 3
            }
        }
    )


def send_fcm_notification(ids, title):
    # fcm 푸시 메세지 요청 주소
    url = 'https://fcm.googleapis.com/fcm/send'

    # 인증 정보(서버 키)를 헤더에 담아 전달
    server_key = "AAAAFPcQCHw:APA91bHyveN1_c7AZ0TWScQrmKMuhrIZNgXFapMBge2RaeAZX3zYyXe5RYFLiUUM0KVyLc_0Gkm4-vA1lR7loUbQiG7EGy5DuchOQqgOq3yfnayghgwRipTRLdxMvmjhwshCoDOjVDUO"
    headers = {
        'Authorization': 'key='+server_key,
        'Content-Type': 'application/json',
    }
    token = ids['token']
    ids.pop('token')

    # 보낼 내용과 대상을 지정
    content = {
        'to': token,
        'notification': {
            'title': title,
            'body': json.dumps(ids)
        }
    }
    logger.info(str(content))

    # json 파싱 후 requests 모듈로 FCM 서버에 요청
    requests.post(url, data=json.dumps(content), headers=headers)

@api_view(['GET'])
def get_apply(request, list_num):
    logger.info("get_apply")
    logger.info("list_num_ ->" + str(list_num))
    return Response(select_all_apply(list_num))


@api_view(['GET'])
def random_assignment(request, count, list_num):
    logger.info("random_assignment")
    logger.info("count -> " + str(count))
    logger.info("list_num -> " + str(list_num))
    applies = list(apply_collection.aggregate(
        [
            {"$match": {"role": 1}},
            {"$project": {
                "_id": 0,
                "id": 1,
                "title": 1,
                "contents": 1,
                "role": 1,
                "name": 1,
                "date": 1
            }}
            , {"$sample": {"size": count}}
        ]
    ))
    logger.info("after random extraction")
    give_apply_role(applies)
    logger.info("after give_apply_role ")
    return Response(select_all_apply(list_num))


@api_view(['GET'])
def firstcome_assignment(request, count, list_num):
    logger.info("firstcome_assignment")
    logger.info("count -> " + str(count))
    logger.info("list_num -> " + str(list_num))
    applies = list(apply_collection.aggregate(
        [
            {"$match": {"role": 1}},
            {"$project": {
                "_id": 0,
                "id": 1,
                "title": 1,
                "contents": 1,
                "role": 1,
                "name": 1,
                "date": 1
            }}
            , {"$sort": {"date": 1}}
            , {"$limit": count}
        ]
    ))
    logger.info("after firstcome extraction")
    give_apply_role(applies)
    logger.info("after give_apply_role")
    return Response(select_all_apply(list_num))


@api_view(['POST'])
def one_assignment(request, list_num):
    logger.info("one_assignment")
    logger.info("list_num -> " + str(list_num))
    json_body = json.loads(request.body.decode("utf-8"))
    apply_collection.update(
        {
            "id": json_body['id']
        },
        {
            "$set": {
                "role": json_body['role']
            }
        }
    )
    logger.info("apply_role_update")
    return Response(select_all_apply(list_num))


@api_view(['GET'])
def send_admission(request):
    # shop goods 초기화
    logger.info("send_admission")
    shop_collection.update_many(
        {},
        {"$set": {"goods": []}}
    )
    applies = list(apply_collection.aggregate(
        [
            {"$match": {"role": 2}},
            {"$project": {
                "_id": 0,
                "id": 1,
                "title": 1,
                "contents": 1,
                "role": 1,
                "name": 1,
                "date": 1
            }}
        ]
    ))
    location_index = 0
    shop_index = 1
    logger.info("applies role 2 -> "+str(applies))
    for apply in applies:
        if shop_index == 81:
            shop_index = 1
            location_index += 1
        user_collection.update(
            {"id": apply['id']},
            {"$set": {
                "role": apply['role'],
                "shop": location[location_index].upper()+str(shop_index),
                "location": location[location_index]
            }
        }
        )
        shop_index += 1
    logger.info("updated role 2 -> " + str(applies))
    #   파이어베이스 연동
    user_collection.update(
        {"role":1}
        ,{"$set": {
                "role": 3
            }
        }
    )
    logger.info("updated role 3 -> " + str(applies))
    token_list = list(user_collection.find(
        {},
        {"_id":False,
         "role":True,
         "token":True,
         "shop":True
         }
    ))
    logger.info("token_list -> " + str(token_list))
    success_ids = []
    fail_ids = []

    for i in token_list:
        if i['role'] == 2:
            success_ids.append(i)
        elif i['role'] == 3:
            fail_ids.append(i)

    logger.info("success_ids -> " + str(success_ids))
    logger.info("fail_ids -> " + str(fail_ids))

    for i in success_ids:
        i['text'] = "바자회에 당첨되셨습니다."
        send_fcm_notification(i, "승인")

    for i in fail_ids:
        i['text']="아쉽게 이번 바자회에 당첨이 안되었습니다."
        send_fcm_notification(i, "미승인")


    apply_collection.remove({})
    logger.info("remove apply")

    return Response(SUCCESS)
