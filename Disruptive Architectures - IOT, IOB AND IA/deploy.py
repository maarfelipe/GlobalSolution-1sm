#!/usr/bin/env python
# coding: utf-8

# In[1]:


from flask import Flask
import pandas as pd
import joblib


# In[ ]:


app = Flask(__name__) 

x = {'N': 0,
    'P': 0,
    'K': 0,
    'temperature': 0,
    'humidity': 0,
    'ph': 0,
    'rainfall': 0,}

mapeamento_datas = {
    "apple": 'julho a setembro',
    "banana": 'outubro a março',
    "blackgram": 'setembro a novembro',
    "chickpea": 'primavera ou verão',
    "coconut": 'janeiro e fevereiro',
    "coffee": 'outubro a março',
    "cotton": 'fevereiro a junho',
    "grapes": 'julho a agosto',
    "jute": 'outubro a março',
    "kidneybeans": 'fevereiro a março e abril a julho',
    "lentil": 'maio',
    "maize": 'final de setembro a começo de novembro',
    "mango": 'outubro e março',
    "mothbeans": 'setembro a novembro, janeiro a março, maio a julho',
    "mungbean": 'agosto e março',
    "muskmelon": 'outubro a fevereiro',
    "orange": 'setembro a dezembro',
    "papaya": 'qualquer época do ano',
    "pigeonpeas": 'setembro e novembro',
    "pomegranate": 'setembro',
    "rice": 'outubro a dezembro',
    "watermelon": 'outubro a fevereiro'
}

mapeamento_alimentos = {
    "apple": 'maçã',
    "banana": 'banana',
    "blackgram": 'feijão preto',
    "chickpea": 'grão-de-bico',
    "coconut": 'coco',
    "coffee": 'café',
    "cotton": 'algodão',
    "grapes": 'uvas',
    "jute": 'juta',
    "kidneybeans": 'feijão vermelho',
    "lentil": 'lentilha',
    "maize": 'milho',
    "mango": 'manga',
    "mothbeans": 'feijão carioca',
    "mungbean": 'feijão moyashi',
    "muskmelon": 'melão',
    "orange": 'laranja',
    "papaya": 'mamão',
    "pigeonpeas": 'sorgo',
    "pomegranate": 'romã',
    "rice": 'arroz',
    "watermelon": 'melancia'
}

@app.route("/")
def inicio():
    return x

@app.route("/<n>/<p>/<k>/<temperature>/<humidity>/<ph>/<rainfall>")
def main(n, p, k, temperature, humidity, ph, rainfall):
    
    x = {'N': float(n),
    'P': float(p),
    'K': float(k),
    'temperature': float(temperature),
    'humidity': float(humidity),
    'ph': float(ph),
    'rainfall': float(rainfall)}
    
    valores_x = pd.DataFrame(x, index=[0])
    modelo = joblib.load('modelo.joblib')
    label = modelo.predict(valores_x)
    
    return {'label': mapeamento_alimentos[label[0]],
            'epoca': mapeamento_datas[label[0]]}

app.run()

