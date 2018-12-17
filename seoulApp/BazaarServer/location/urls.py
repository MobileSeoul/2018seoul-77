from django.urls import path
from location.views import *

urlpatterns = [
    path('a/<int:list_num>', section_a),
    path('b/<int:list_num>', section_b),
    path('c/<int:list_num>', section_c),
    path('d/<int:list_num>', section_d),
    path('e/<int:list_num>', section_e),
]