import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios';
import React, {createContext, useState, useEffect} from 'react';

const UserContext = createContext();

const UserProvider = ({children}) => {
    const [list, setList] = useState([]);
    const [listFilter, setListFilter] = useState([]);
    const [user, setUser] = useState("");
    const [isLoading, setIsLoading] = useState(true);
    const [select, setSelect] = useState("produto");
    const [search, setSearch] = useState("");

    const fetchUser = async () => {
        const Token = await AsyncStorage.getItem("token");
        console.log(`pegando user com ${Token}`);
        try {
            setIsLoading(true);
            const {data} = await axios.get(`http://10.0.2.2:8080/cropsage/api/usuario`,{
                headers:{Authorization:`Bearer ${Token}`}
            });
            setUser(data);
        } catch (error) {
            console.log(error);
        } finally {
            setIsLoading(false);
        }
    }

    const fetchList = async () => {
        const Token = await AsyncStorage.getItem("token");
        console.log(`pegando lista com ${Token}`)
        try {
            setIsLoading(true);
            const {data} = await axios.get(`http://10.0.2.2:8080/cropsage/api/solo`,{
                headers:{Authorization:`Bearer ${Token}`}
            });
            setList(data);
        } catch (error) {
            console.log(error);
        } finally {
            setIsLoading(false);
        }
    }

    const fetchListFilter = async () => {
        if (search == "") {
            fetchList()
                .then(console.log(JSON.stringify(list)));
        } else {
            const Token = await AsyncStorage.getItem("token");
            console.log(`pegando lista com ${Token}`);
            console.log(`http://10.0.2.2:8080/cropsage/api/solo/${select}/${search}`);
            try {
                setIsLoading(true);
                const {data} = await axios.get(`http://10.0.2.2:8080/cropsage/api/solo/${select}/${search}`,{
                    headers:{Authorization:`Bearer ${Token}`}
                });
                setList(data);
            } catch (error) {
                console.log(error);
            } finally {
                console.log(JSON.stringify(list));
                setIsLoading(false);
            }
        }
        
    }

    useEffect(() => {
        fetchUser();
        fetchList();
    }, []);

    return (
        <UserContext.Provider value={{fetchUser, user, fetchList, list, fetchListFilter, listFilter, isLoading, search, setSearch, select, setSelect}}>
        {children}
        </UserContext.Provider>
    );
};

export {UserContext, UserProvider};
