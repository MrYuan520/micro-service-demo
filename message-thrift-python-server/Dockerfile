FROM 192.168.5.155/micro-service-demo/pythin-base:latest
MAINTAINER yuanbo

ENV PYTHONPATH /
COPY message /message

ENTRYPOINT ["python", "/message_service.py"]