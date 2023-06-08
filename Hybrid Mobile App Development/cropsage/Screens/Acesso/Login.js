import AsyncStorage from "@react-native-async-storage/async-storage";
import axios from "axios";
import React, { useState } from "react";
import { Alert, StyleSheet, Text, TextInput, TouchableOpacity, View } from "react-native";

const Login = ({navigation}) => {

    const style = StyleSheet.create({
        window: {
            padding:20,
            height:"100%",
            justifyContent:"space-between",
            flexDirection:"column"
        },
        title: {
            color: "#312E49",
            fontSize: 40,
            marginTop: 55,
            marginBottom:30,
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
        }
    });

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
            createAlert("Dados inválidos");
        }

    }

    const createAlert = (text) => {
        Alert.alert("Aviso", text, [{
            text: "OK",
        }])
    }

    return(
        <View style={style.window}>
            <View>
                <Text style={style.title}>Login</Text>
                <Text style={style.label}>Email</Text>
                <TextInput
                    placeholder="Email"
                    value={email}
                    onChangeText={setEmail}
                    style={style.input}
                />
                <Text style={style.label}>Senha</Text>
                <TextInput
                    placeholder="Senha"
                    value={senha}
                    onChangeText={setSenha}
                    secureTextEntry={true}
                    style={style.input}
                />
                <TouchableOpacity onPress={() => {navigation.push('cadastro')}}>
                    <Text style={style.link} >Cadastre-se</Text>
                </TouchableOpacity>
            </View>
            <TouchableOpacity style={style.button} onPress={login}>
                <Text style={style.buttonLabel}>Login</Text>
            </TouchableOpacity>
        </View>
    );
}

export default Login;