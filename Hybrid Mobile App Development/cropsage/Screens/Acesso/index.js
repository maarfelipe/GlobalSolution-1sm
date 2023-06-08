import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import Login from './Login';
import Cadastro from './Cadastro';

const Stack = createNativeStackNavigator();

const Acesso = () => {
  return (
      <Stack.Navigator>
        <Stack.Screen name="login" component={Login} options={{headerShown: false}} />
        <Stack.Screen name="cadastro" component={Cadastro} options={{headerShown: false}} />
      </Stack.Navigator>
  );
};

export default Acesso;
