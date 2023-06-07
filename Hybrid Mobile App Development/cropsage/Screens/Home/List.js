import AsyncStorage, { useAsyncStorage } from "@react-native-async-storage/async-storage";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { Alert, FlatList, Text, TouchableOpacity, View } from "react-native";

const List = () => {
    const [token, setToken] = useState('');
    const {getItem, setItem} = useAsyncStorage("token");

    const readItemFromStorage = async () => {
        const item = await getItem();
        setToken(item);
    }

    const writeItemToStorage = async newValue => {
        await setItem(newValue);
        setToken(newValue);
    }

    const [list, setList] = useState([]);
    const [user, setUser] = useState("");

    const fetchUser = async () => {
        try {
            const {data} = await axios.get(`http://10.0.2.2:8080/cropsage/api/usuario`,{
                headers:{Authorization:`Bearer ${token}`}
            });
            console.log(JSON.stringify(data))
            setUser(data);
        } catch (error) {
            console.log(error);
        }
    }

    useEffect(() => {
        readItemFromStorage();
        fetchUser();
    }, [])
    

    const fetchList = async () => {
        const {data} = await axios.get()
    }

    return(
        <View>
            <Text>List</Text>
            <TouchableOpacity onPress={fetchUser}><Text>POG</Text></TouchableOpacity>
            <Text>{user.nome}</Text>
        </View>
    )
}

export default List;