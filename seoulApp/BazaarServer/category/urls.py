from django.urls import path

from category.views import *

urlpatterns = [
    path('<int:category_num>/<int:list_num>', get_category),
]