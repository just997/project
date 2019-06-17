from django.urls import path
from .views import reg

urlpatterns = [
    path(r'reg/', reg),
]
