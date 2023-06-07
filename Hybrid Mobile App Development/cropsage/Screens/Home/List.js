import AsyncStorage, { useAsyncStorage } from "@react-native-async-storage/async-storage";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { Alert, FlatList, Text, TouchableOpacity, View } from "react-native";

const List = () => {
    const [token, setToken] = useState('');
    const [list, setList] = useState([]);
    const [user, setUser] = useState("");

    const fetchUser = async () => {
        const Token = await AsyncStorage.getItem("token");
        setToken(Token);
        console.log(`pegando user com ${Token}`)
        try {
            const {data} = await axios.get(`http://10.0.2.2:8080/cropsage/api/usuario`,{
                headers:{Authorization:`Bearer ${Token}`}
            });
            setUser(data);
        } catch (error) {
            console.log(error);
        }
    }

    const fetchList = async () => {
        try {
        const {data} = await axios.get(`http://10.0.2.2:8080/cropsage/api/solo`,{
            headers:{Authorization:`Bearer ${token}`}
        });
        setList(data);
        } catch (error) {
            console.log(error);
        }
    }

    useEffect(() => {
        fetchUser();
    }, [])
    


    return(
        <View>
            <Text>{`Olá, ${user.nome}`}</Text>
            <Text>{JSON.stringify(list)}</Text>
        </View>
    )
}

export default List;