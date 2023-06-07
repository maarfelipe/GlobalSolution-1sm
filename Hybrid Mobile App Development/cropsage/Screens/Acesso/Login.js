import AsyncStorage from "@react-native-async-storage/async-storage";
import axios from "axios";
import React, { useState } from "react";
import { Alert, Text, TextInput, TouchableOpacity, View } from "react-native";

const Login = ({navigation}) => {

    const [email, setEmail] = useState("");
    const [senha, setSenha] = useState("");

    const login = async () => {

        const credential = {
            email: email,
            senha: senha
        }

        try {
            const response = await axios.post(
                `http://10.0.2.2:8080/cropsage/api/usuario/login`,
                credential
            );

            try {
                await AsyncStorage.setItem("token",response.data.token);
                console.log(await AsyncStorage.getItem("token"));
                navigation.navigate("home");
            } catch (error) {
                console.log(error);
            }

        } catch (error) {
            console.log(error);
        }

    }

    const getToken = async () => {
        try {
            createAlert(await AsyncStorage.getItem("token"));
        }  catch (error) {
            console.log(error);
        }
    }

    const createAlert = (text) => {
        Alert.alert("Aviso", `Token: ${text}`, [{
            text: "OK",
        }])
    }

    return(
        <View>
            <Text>Login</Text>
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
            <TouchableOpacity onPress={login}><Text>Login</Text></TouchableOpacity>
            <TouchableOpacity onPress={getToken}><Text>Token</Text></TouchableOpacity>
            <TouchableOpacity onPress={() => {navigation.push('cadastro')}}><Text>Cadastre-se</Text></TouchableOpacity>
        </View>
    );
}

export default Login;