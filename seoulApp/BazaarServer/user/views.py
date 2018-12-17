import json
import logging

import bcrypt
from django.shortcuts import render
from rest_framework.decorators import api_view, parser_classes
from rest_framework.parsers import FormParser, MultiPartParser
from rest_framework.response import Response
# Create your views here.
from BazaarServer.settings import user_collection


logger = logging.getLogger("user")
SUCCESS = {"response": "success"}
FAIL = {"response": "fail"}


@api_view(['POST'])
def signup(request):
    logger.info("signup")
    json_body = json.loads(request.body.decode("utf-8"))
    logger.info("request body -> " + str(json_body))
    hashed_password = bcrypt.hashpw(json_body['pw'].encode('utf8'), bcrypt.gensalt(14))
    user = user_collection.find_one({
        "id": json_body['id']
    })

    if user == None:
        logger.info("기존에 없는 id")
        json_body['pw'] = hashed_password
        json_body['role'] = 1
        json_body['shop'] = ''
        user_collection.insert_one(json_body)
        print(json_body)
        return Response(SUCCESS)
    logger.info("기존에 있는 id")
    return Response(FAIL)


@api_view(['POST'])
def signin(request):
    logger.info("signin")
    json_body = json.loads(request.body.decode("utf-8"))
    logger.info("request body -> " + str(json_body))
    user = user_collection.find_one(
        {
            "id": json_body['id']
        },
        {
            "_id": False,
        }
    )

    if user == None:
        logger.info("아이디가 존재 하지 않음.")
        return Response(FAIL)
    else:
        if bcrypt.checkpw(json_body['pw'].encode('utf-8'), user['pw']):
            logger.info("sign in 성공")
            user['response'] = 'success'
            user.pop('pw')
            return Response(user)
        else:
            logger.info("아이디가 존재, 비밀번호 틀림")
            return Response(FAIL)


@api_view(['POST'])
def findid(request):
    logger.info("findid")
    json_body = json.loads(request.body.decode("utf-8"))
    logger.info("request body -> " + str(json_body))
    user = user_collection.find_one(
        {
            "name": json_body['name'],
            "email": json_body['email']
        },
        {
            "_id": False,
        }
    )
    if user == None:
        logger.info("이름과 이메일로 찾아지는 아이디 없음")
        return Response(FAIL)
    else:
        logger.info("아이디 찾기 성공")
        user.pop("pw")
        user['response'] = 'success'
        return Response(user)
