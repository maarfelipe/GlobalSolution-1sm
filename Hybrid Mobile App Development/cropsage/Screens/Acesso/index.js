import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import Login from './Login';
import Cadastro from './Cadastro';

const Stack = createNativeStackNavigator();

const Acesso = () => {
  return (
      <Stack.Navigator>
        <Stack.Screen name="login" component={Login} options={{headerTitle: "Login"}} />
        <Stack.Screen name="cadastro" component={Cadastro} options={{headerTitle: "Cadastro"}} />
      </Stack.Navigator>
  );
};

export default Acesso;
