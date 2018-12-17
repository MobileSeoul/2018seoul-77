from django.urls import path

from seller.views import *


urlpatterns = [
    path('select/<str:shop>',get_goods),
    path('insert/goods',insert_goods),
    path('delete/goods',delete_goods),
    path('apply',apply)
]