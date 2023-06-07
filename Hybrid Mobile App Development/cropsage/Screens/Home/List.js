import AsyncStorage, { useAsyncStorage } from "@react-native-async-storage/async-storage";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { Alert, FlatList, Text, TouchableOpacity, View } from "react-native";

const ListItem = ({item, navigation}) => {

    return (
        <View>
            <Text>{item.localizacao.nome}</Text>
            <Text>{`${item.localizacao.latitude}, ${item.localizacao.longitude}`}</Text>
            <Text>{item.localizacao.cep}</Text>
            <Text>{item.produto.nome}</Text>
        </View>
    )

}

const List = ({navigation}) => {
    const [list, setList] = useState([]);
    const [user, setUser] = useState("");

    const fetchUser = async () => {
        const Token = await AsyncStorage.getItem("token");
        console.log(`pegando user com ${Token}`);
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
        const Token = await AsyncStorage.getItem("token");
        console.log(`pegando lista com ${Token}`)
        try {
            const {data} = await axios.get(`http://10.0.2.2:8080/cropsage/api/solo`,{
                headers:{Authorization:`Bearer ${Token}`}
            });
            setList(data);
        } catch (error) {
            console.log(error);
        }
    }

    useEffect(() => {
        fetchUser();
        fetchList();
    }, []);

    const handlePress = () => {
        navigation.push("cadastroSolo");
    }

    return(
        <View>
            <Text>{`Olá, ${user.nome}`}</Text>
            <TouchableOpacity onPress={handlePress}><Text>Adicionar</Text></TouchableOpacity>
            <FlatList
                data={list.content}
                key={item => item.id}
                renderItem={props => <ListItem navigation={navigation} {...props}/>}
            />
        </View>
    )
}

export default List;