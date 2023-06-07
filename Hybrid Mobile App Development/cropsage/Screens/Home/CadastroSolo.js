import AsyncStorage from "@react-native-async-storage/async-storage";
import axios from "axios";
import React, { useState } from "react";
import { Alert, ScrollView, Text, TextInput, TouchableOpacity, View } from "react-native";

const CadastroSolo = ({navigation}) => {

    const [nomeLocal, setNomeLocal] = useState("");
    const [latitude, setLatitude] = useState("");
    const [longitude, setLongitude] = useState("");
    const [cep, setCep] = useState("");
    const [n, setN] = useState("");
    const [k, setK] = useState("");
    const [p, setP] = useState("");
    const [ph, setPh] = useState("");
    const [temperatura, setTemperatura] = useState("");
    const [umidade, setUmidade] = useState("");
    const [chuva, setChuva] = useState("");

    const cadastro = async () => {

        const Token = await AsyncStorage.getItem("token");
        console.log(`cadastrando solo com ${Token}`);

        const credential = {
            localizacao: {
                nome: nomeLocal,
                latitude: latitude,
                longitude: longitude,
                cep: cep
            },
            nitrogenio: parseFloat(n),
            potassio: parseFloat(k),
            fosforo: parseFloat(p),
            temperatura: parseFloat(temperatura),
            umidade: parseFloat(umidade),
            ph: parseFloat(ph),
            chuva: parseFloat(chuva)
        }

        try {
            const response = await axios.post(
                `http://10.0.2.2:8080/cropsage/api/solo`,
                credential,
                {
                    headers:{Authorization:`Bearer ${Token}`}
                }
            );
            console.log(JSON.stringify(response));
            createAlert(`Cadastrado novo solo,\nBaseado nas informações dadas, recomendamos o plantio de ${response.data.produto.nome}`)
            navigation.navigate("list");
        } catch (error) {
            console.log(error);
        }

    }

    const createAlert = (text) => {
        Alert.alert("Aviso", text, [{
            text: "OK",
        }])
    }

    return(
        <ScrollView>
            <Text>Local</Text>
            <TextInput
                placeholder="Digite o nome do local"
                value={nomeLocal}
                onChangeText={setNomeLocal}
            />
            <Text>Latitude</Text>
            <TextInput
                placeholder="Digite a latitude"
                value={latitude}
                onChangeText={setLatitude}
            />
            <Text>Longitude</Text>
            <TextInput
                placeholder="Digite a longitude"
                value={longitude}
                onChangeText={setLongitude}
            />
            <Text>CEP</Text>
            <TextInput
                placeholder="Digite o CEP"
                value={cep}
                onChangeText={setCep}
            />
            <Text>Características</Text>
            <Text>Nitrogênio</Text>
            <TextInput
                placeholder="n"
                value={n}
                onChangeText={setN}
            />
            <Text>Potássio</Text>
            <TextInput
                placeholder="k"
                value={k}
                onChangeText={setK}
            />
            <Text>Fósforo</Text>
            <TextInput
                placeholder="p"
                value={p}
                onChangeText={setP}
            />
            <Text>PH</Text>
            <TextInput
                placeholder="ph"
                value={ph}
                onChangeText={setPh}
            />
            <Text>Temperatura Média</Text>
            <TextInput
                placeholder="temperatura"
                value={temperatura}
                onChangeText={setTemperatura}
            />
            <Text>Umidade</Text>
            <TextInput
                placeholder="umidade"
                value={umidade}
                onChangeText={setUmidade}
            />
            <Text>Chuva</Text>
            <TextInput
                placeholder="chuva"
                value={chuva}
                onChangeText={setChuva}
            />
            <TouchableOpacity onPress={cadastro}><Text>Salvar</Text></TouchableOpacity>
        </ScrollView>
    );
}

export default CadastroSolo;