import axios from "axios";
import React, { useState } from "react";
import { Alert, StyleSheet, Text, TextInput, TouchableOpacity, View } from "react-native";
import Icon from "react-native-vector-icons/Feather";

const Cadastro = ({navigation}) => {

    const style = StyleSheet.create({
        window: {
            padding:20,
            height:"100%",
            justifyContent:"space-between",
            flexDirection:"column"
        },
        title: {
            marginTop:30,
            color: "#312E49",
            fontSize: 40,
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
            createAlert("Dados inválidos.");
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
        <View style={style.window}>
            <View>
                <TouchableOpacity onPress={goBack}>
                    <Icon name="arrow-left" size={24} color={"#312E49"}/>
                </TouchableOpacity>
                <Text style={style.title}>Cadastro</Text>
                <Text style={style.label}>Nome</Text>
                <TextInput
                    placeholder="Nome"
                    value={nome}
                    onChangeText={setNome}
                    style={style.input}
                />
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
                <Text style={style.label}>Ao se inscrever, você concorda com nossos Termos & Condições e Política de Privacidade.*</Text>
            </View>
            
            <TouchableOpacity onPress={cadastro} style={style.button}>
                <Text style={style.buttonLabel}>Cadastro</Text>
            </TouchableOpacity>
        </View>
    );
}

export default Cadastro;