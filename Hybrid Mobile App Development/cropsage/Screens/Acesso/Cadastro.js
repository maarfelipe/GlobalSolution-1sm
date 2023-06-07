import AsyncStorage from "@react-native-async-storage/async-storage";
import axios from "axios";
import React, { useState } from "react";
import { Alert, Text, TextInput, TouchableOpacity, View } from "react-native";

const Cadastro = ({navigation}) => {

    const [nome, setNome] = useState("");
    const [email, setEmail] = useState("");
    const [senha, setSenha] = useState("");

    const cadastro = async () => {

        const credential = {
            nome: nome,
            email: email,
            senha: senha
        }

        createAlert(JSON.stringify(credential))

        try {
            const response = await axios.post(
                `http://10.0.2.2:8080/cropsage/api/usuario/cadastrar`,
                credential
            );
            console.log(response);
        } catch (error) {
            console.log(error);
        }

        /*
        try {
            const response = await axios.post(
                `http://localhost:8080/cropsage/api/usuario/cadastro`,
                credential
            );
        } catch (error) {
            console.log(JSON.stringify(error));
        }
        */
    }

    const createAlert = (text) => {
        Alert.alert("Aviso", `Token: ${text}`, [{
            text: "OK",
        }])
    }

    const getToken = async () => {
        try {
            createAlert(await AsyncStorage.getItem("token"));
        }  catch (error) {
            console.log(error);
        }
    }

    return(
        <View>
            <Text>Cadastro</Text>
            <TextInput
                placeholder="Nome"
                value={nome}
                onChangeText={setNome}
            />
            <TextInput
                placeholder="Email"
                value={email}
                onChangeText={setEmail}
            />
            <TextInput
                placeholder="Senha"
                value={senha}
                onChangeText={setSenha}
            />
            <TouchableOpacity onPress={getToken}><Text>Token</Text></TouchableOpacity>
            <TouchableOpacity onPress={cadastro}><Text>Cadastro</Text></TouchableOpacity>
        </View>
    );
}

export default Cadastro;