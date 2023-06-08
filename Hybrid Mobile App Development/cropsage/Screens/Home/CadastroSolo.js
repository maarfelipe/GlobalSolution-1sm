import AsyncStorage from "@react-native-async-storage/async-storage";
import axios from "axios";
import React, { useContext, useState } from "react";
import { Alert, Image, ScrollView, StyleSheet, Text, TextInput, TouchableOpacity, View } from "react-native";
import Icon from "react-native-vector-icons/Feather";
import { UserContext } from "../../Context/UserContext";

const CadastroSolo = ({navigation}) => {

    const style = StyleSheet.create({
        window: {
            padding:20,
            height:"100%",
            justifyContent:"space-between",
            flexDirection:"column"
        },
        title: {
            color: "#312E49",
            fontSize: 22,
            marginBottom:10,
            marginTop:10,
        },
        label: {
            color: "#312E49",
            fontSize: 18,
        },
        input: {
            borderColor: "#A2A2A6",
            borderWidth:1,
            borderRadius:8,
            padding: 12,
            color: "#312E49",
            fontSize:16,
            marginBottom:16,
        },
        button: {
            backgroundColor:"#0E5540",
            borderRadius:8,
            padding:12,
            justifyContent:"center",
            alignItems:"center",
            marginBottom:20,
        },
        buttonLabel: {
            color: "#FFF",
            fontSize: 18,
        },
        link: {
            color: "#0E5540",
            fontSize: 14,
        },
        line: {
            flexDirection: "row",
            justifyContent: "space-between",
            alignItems: "center",
        },
    });

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

    const {fetchList} = useContext(UserContext);

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
            fetchList();
            navigation.goBack();
        } catch (error) {
            console.log(error);
        }

    }

    const createAlert = (text) => {
        Alert.alert("Aviso", text, [{
            text: "OK",
        }])
    }

    const goBack = () => {
        navigation.goBack();
    }

    return(
        <View  style={style.window}>
            <ScrollView>
                <View style={style.line} >
                    <Image style={{width:80, height:80}} source={require('../../Assets/logo.png')} />
                    <TouchableOpacity onPress={goBack}>
                        <Icon name="arrow-left" size={24} color={"#312E49"}/>
                    </TouchableOpacity>
                </View>
                <Text style={style.label} >Local</Text>
                <TextInput
                    placeholder="Digite o nome do local"
                    value={nomeLocal}
                    onChangeText={setNomeLocal}
                    style={style.input}
                />
                <Text style={style.label} >Latitude</Text>
                <TextInput
                    placeholder="Digite a latitude"
                    value={latitude}
                    onChangeText={setLatitude}
                    style={style.input}
                />
                <Text style={style.label} >Longitude</Text>
                <TextInput
                    placeholder="Digite a longitude"
                    value={longitude}
                    onChangeText={setLongitude}
                    style={style.input}
                />
                <Text style={style.label} >CEP</Text>
                <TextInput
                    placeholder="Digite o CEP"
                    value={cep}
                    onChangeText={setCep}
                    style={style.input}
                />
                <Text style={style.title} >Características</Text>
                <Text style={style.label} >Nitrogênio</Text>
                <TextInput
                    placeholder="Porcentagem de Nitrogênio"
                    value={n}
                    onChangeText={setN}
                    style={style.input}
                />
                <Text style={style.label} >Potássio</Text>
                <TextInput
                    placeholder="Porcentagem de Potássio"
                    value={k}
                    onChangeText={setK}
                    style={style.input}
                />
                <Text style={style.label} >Fósforo</Text>
                <TextInput
                    placeholder="Porcentagem de Fósforo"
                    value={p}
                    onChangeText={setP}
                    style={style.input}
                />
                <Text style={style.label} >PH</Text>
                <TextInput
                    placeholder="Escala PH"
                    value={ph}
                    onChangeText={setPh}
                    style={style.input}
                />
                <Text style={style.label} >Temperatura Média</Text>
                <TextInput
                    placeholder="Temperatura Média Local"
                    value={temperatura}
                    onChangeText={setTemperatura}
                    style={style.input}
                />
                <Text style={style.label} >Umidade</Text>
                <TextInput
                    placeholder="Umidade Local"
                    value={umidade}
                    onChangeText={setUmidade}
                    style={style.input}
                />
                <Text style={style.label} >Chuva</Text>
                <TextInput
                    placeholder="Chuva em mm"
                    value={chuva}
                    onChangeText={setChuva}
                    style={style.input}
                />
            </ScrollView>
            <TouchableOpacity onPress={cadastro} style={style.button}>
                <Text style={style.buttonLabel}>Salvar</Text>
            </TouchableOpacity>
        </View>
    );
}

export default CadastroSolo;