FROM python:3.9
VOLUME /ml_api
COPY requirements.txt .
RUN pip install -r requirements.txt
COPY . .
EXPOSE 5000
CMD ["python", "deploy.py"]
