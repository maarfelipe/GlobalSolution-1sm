FROM python:3.9
WORKDIR /cropsage
VOLUME /ml_api
ENV FLASK_APP=deploy.py
ENV FLASK_RUN_HOST=0.0.0.0
COPY requirements.txt .
RUN pip install -r requirements.txt
EXPOSE 5000
COPY . .
CMD ["flask", "run"]