import AsyncStorage, { useAsyncStorage } from "@react-native-async-storage/async-storage";
import axios from "axios";
import React, { useEffect, useState, useContext } from "react";
import { Alert, FlatList, Image, StyleSheet, Text, TextInput, TouchableOpacity, View } from "react-native";
import Icon from "react-native-vector-icons/Feather";
import {Picker} from '@react-native-picker/picker';
import { UserContext } from "../../Context/UserContext";

const Vazio = () => {
    const style = StyleSheet.create({
        vazio: {
            justifyContent:"center",
            alignItems:"center",
        },
        vazioLabel: {
            color: "#A2A2A6",
            fontSize:24,
        }
    })
    return (
        <View style={style.vazio}>
            <Text style={style.vazioLabel}>Não há Locais...</Text>
        </View>
    )
}

const ListItem = ({item, navigation}) => {

    const style = StyleSheet.create({
        card: {
            backgroundColor:"#0E5540",
            borderRadius:8,
            padding:12,
            marginBottom:5,
            marginTop:5,
        },
        nome: {
            color: "#FFF",
            fontWeight: "bold",
            fontSize: 16,
            marginBottom:4,
        },
        label: {
            color: "#FFF",
            fontSize: 14,
        },
    });

    return (
        <View style={style.card}>
            <Text style={style.nome}>{item.localizacao.nome}</Text>
            <Text style={style.label}><Icon name="map-pin" style={{marginLeft:16}} /> {`${item.localizacao.latitude}, ${item.localizacao.longitude}`}</Text>
            <Text style={style.label}><Icon name="map" style={{marginLeft:16}} /> {item.localizacao.cep}</Text>
            <Text style={style.label}><Icon name="feather" style={{marginLeft:16}} /> {item.produto.nome}</Text>
            <Text style={style.label}><Icon name="calendar" style={{marginLeft:16}} /> {item.produto.epoca}</Text>
        </View>
    )

}

const List = ({navigation}) => {

    const style = StyleSheet.create({
        window: {
            padding:20,
            height:"100%",
            justifyContent:"space-between",
            flexDirection:"column"
        },
        title: {
            marginTop:10,
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
            width:"86%",
        },
        button: {
            backgroundColor:"#0E5540",
            borderRadius:8,
            padding:12,
            justifyContent:"center",
            alignItems:"center",
            marginBottom:20,
            marginTop:20,
        },
        buttonLabel: {
            color: "#FFF",
            fontSize: 18,
        },
        link: {
            color: "#0E5540",
            fontSize: 14,
        },
        list: {
            height:"40%",
        },
        search: {
            flexDirection: "row",
            alignItems: "center",
        },
        icon: {
            marginLeft:15,
            marginTop:-20
        },
        select: {
            marginTop:-14,
            color:"#312E49",
        },
        
    });
    
    const {fetchUser, user, fetchList, list, fetchListFilter, listFilter, isLoading, search, setSearch, select, setSelect} = useContext(UserContext);
    
    useEffect(() => {
        fetchUser();
        fetchList();
    }, []);

    const handlePress = () => {
        navigation.push("cadastroSolo");
    }

    return(
        <View style={style.window}>
            <View>
                <Image style={{width:80, height:80}} source={require('../../Assets/logo.png')} />
                <Text style={style.title}>{`Olá, ${user.nome}`}</Text>
                <View style={style.search}>
                    <TextInput
                        placeholder={`Pesquisar por ${select}`}
                        value={search}
                        onChangeText={setSearch}
                        style={style.input}
                    />
                    <TouchableOpacity onPress={fetchListFilter}>
                        <Icon name="search" size={32} style={style.icon} />
                    </TouchableOpacity>
                </View>
                <Picker
                    selectedValue={select}
                    onValueChange={(itemValue, itemIndex) => setSelect(itemValue)}
                    style={style.select}
                >
                    <Picker.Item label="Produto" value="produto" />
                    <Picker.Item label="Localização" value="localizacao" />
                </Picker>
                <Text style={style.label}>Meus locais</Text>
                {(list.empty) ? 
                    <Vazio/>
                    :
                    <FlatList
                        data={list.content}
                        key={item => item.id}
                        renderItem={props => <ListItem navigation={navigation} {...props}/>}
                        style={style.list}
                    />
                }
            </View>
            <TouchableOpacity style={style.button} onPress={handlePress}>
                <Text style={style.buttonLabel}>Adicionar</Text>
            </TouchableOpacity>
        </View>
    )
}

export default List;