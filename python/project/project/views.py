from django.http import HttpResponse, JsonResponse
from django.shortcuts import render


def index(request):
    print(request)
    print(type(request))
    d = dict(zip('abcde', range(1, 6)))
    print(d)
    return render(request, 'index.html', {'d': d})
