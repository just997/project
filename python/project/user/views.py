from django.shortcuts import render
# Create your views here.
from django.http import JsonResponse, HttpRequest, HttpResponse, HttpResponseBadRequest
import logging
import simplejson
from .models import User
FORMAT = "%(asctime)s %(threadName)s %(thread)d %(message)s"
logging.basicConfig(format=FORMAT, level=logging.INFO)


def checkemail(request):
    return HttpResponse()


def reg(request: HttpRequest):
    try:
        try:
            qs = User.objects.get(pk=2)
            print(qs)
        except Exception as e:
            print('-' * 30)
        payload = simplejson.loads(request.body)
        email = payload['email']
        name = payload['name']
        password = payload['password']
        print(email, name, password)
        qs = User.objects.filter(email=email)
        print(qs.query, '---------')
        if qs:
            return HttpResponseBadRequest()
        user = User()
        user.email = email
        user.name = name
        user.password = password
        try:
            user.save()
            return JsonResponse({'user_id': user.id})
        except Exception as e:
            raise
        # raise 1
        return HttpResponse("WELCOME TO MAGEDU")
    except Exception as e:
        logging.info(e)
        return HttpResponseBadRequest()
