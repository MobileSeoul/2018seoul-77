from django.urls import path

from notice.views import *

urlpatterns = [
    path('select/<int:list_num>',get_notice),
    path('search/<str:type>/<str:keyword>/<int:list_num>',search),
]