# -*- coding: utf-8 -*-
from __future__ import unicode_literals

import logging

import pymongo
from django.shortcuts import render

# Create your views here.
from rest_framework.decorators import api_view
from rest_framework.response import Response
from BazaarServer.settings import notice_collection

logger = logging.getLogger("notice")

def search_title_or_contents(keyword,type,list_num):
    notices = list(notice_collection
        .aggregate(
        [
            {"$project": {
                "_id": 0,
                "no": 1,
                "title": 1,
                "contents": 1,
                "files": 1,
                "date":1
            }}
            , {"$match": {type: {"$regex": keyword}}}
            , {"$sort": {"date": -1}}
            , {"$skip": (list_num - 1) * 10}
            , {"$limit": 10}
        ]
    ))

    meta = list(notice_collection
        .aggregate(
        [
            {"$match": {type: {"$regex": keyword}}},
            {"$group": {
                "_id": "null",
                "count": {"$sum": 1}
            }}
            , {"$project": {"_id": 0}}
        ]
    ))
    data = {}
    data['items'] = notices
    data['meta'] = {}
    if len(meta) > 0:
        data['meta'] = meta[0]
    return Response(data)

def search_titlecontents(keyword,list_num):
    data = {}
    notices = list(notice_collection
        .aggregate(
        [
            {"$project": {
                "_id": 0,
                "no": 1,
                "title": 1,
                "contents": 1,
                "files": 1,
                "date":1
            }}
            , {"$match": {"$or": [{"title": {"$regex": keyword}}, {"contents": {"$regex": keyword}}]}}
            , {"$sort": {"date": -1}}
            , {"$skip": (list_num - 1) * 10}
            , {"$limit": 10}
        ]
    ))
    meta = list(notice_collection
        .aggregate(
        [
            {"$match": {"$or": [{"title": {"$regex": keyword}}, {"contents": {"$regex": keyword}}]}},
            {"$group": {
                "_id": "null",
                "count": {"$sum": 1}
            }}
            , {"$project": {"_id": 0}}
        ]
    ))
    data = {}
    data['items'] = notices
    data['meta'] = {}
    if len(meta) > 0:
        data['meta'] = meta[0]
    return Response(data)

@api_view(['GET'])
def get_notice(request, list_num):
    logger.info("get_notice")
    data = {}
    notices = list(notice_collection
                  .aggregate(
        [
            {"$project":{
                "_id":0,
                 "no":1,
                "title":1,
                "contents":1,
                "files":1,
                 "date":1
            }}
        , {"$sort": {"date":-1}}
        , {"$skip": (list_num - 1) * 10}
        , {"$limit": 10}
        ]
    ))
    meta = list(notice_collection
        .aggregate(
        [
            {"$group": {
                "_id":"null",
                "count":{"$sum":1}
            }}
            ,{"$project":{"_id":0}}
        ]
    ))

    data['items']=notices
    data['meta']=meta[0]
    return Response(data)

@api_view(['GET'])
def search(request, type,keyword,list_num):
    logger.info("search")
    if type == "titlecontents":
        logger.info("type ->" + str(type))
        return search_titlecontents(keyword,list_num)
    else:
        logger.info("type ->" + str(type))
        return search_title_or_contents(keyword,type,list_num)

