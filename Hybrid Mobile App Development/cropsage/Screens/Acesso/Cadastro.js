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

        try {
            const response = await axios.post(
                `http://10.0.2.2:8080/cropsage/api/usuario/cadastrar`,
                credential
            );
            console.log(response);
            createAlert("Cadastro realizado com sucesso!\nPor favor faça login");
            navigation.navigate("login");
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
            <TouchableOpacity onPress={cadastro}><Text>Cadastro</Text></TouchableOpacity>
        </View>
    );
}

export default Cadastro;