from django.urls import path

from admin.views import *

urlpatterns = [
    path('select/<int:list_num>',get_apply),
    path('random/<int:count>/<int:list_num>', random_assignment),
    path('firstcome/<int:count>/<int:list_num>', firstcome_assignment),
    path('one/<int:list_num>', one_assignment),
]